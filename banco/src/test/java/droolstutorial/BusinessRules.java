package droolstutorial;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.KieServiceResponse.ResponseType;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;

public class BusinessRules {
	//Servidor disponibilizado ao subir o container do Kie-server vinculado ao workbench	
	private static final String URL = "http://192.168.99.100:8082/kie-server/services/rest/server";
	//usuário e senha do kie-server
    private static final String USER = "kieserver";  
    private static final String PASSWORD = "kieserver1!";
    //container exposto ao buildar o projeto de regras
    private static String CONTAINER_ID = "banco.regras_1.0.0-SNAPSHOT";
    
    private static final MarshallingFormat FORMAT = MarshallingFormat.XSTREAM;  
  
    private KieServicesConfiguration conf;  
    private KieServicesClient kieServicesClient;  
	
	
    public void initialize(HashMap<String, Object> fatos) {  
        conf = KieServicesFactory.newRestConfiguration(URL, USER, PASSWORD);  
        
        Set<Class<?>> extraClass = new HashSet<Class<?>>();
        
        fatos.forEach((id, fato) -> extraClass.add(fato.getClass()));
        
        //Adiciona as classes/fatos enviados à chamada 
        conf.addExtraClasses(extraClass);
        
        conf.setUseSsl(true);
        
        conf.setMarshallingFormat(FORMAT);
        
        kieServicesClient = KieServicesFactory.newKieServicesClient(conf);  
    }

  	
	public ServiceResponse<ExecutionResults> executeCommands(HashMap<String, Object> fatos, 
						String processo) {  
    	
        System.out.println("== Sending commands to the server ==");  
        
        KieCommands commandsFactory = KieServices.Factory.get().getCommands();
        
        ArrayList<Command<?>> commands = new ArrayList<Command<?>>();
        //cria lista de inserts
        fatos.forEach((id, fato) -> commands.add(commandsFactory.newInsert(fato, id)));
       
        //verifica se existe ID
        if(processo != null) {
        	commands.add(commandsFactory.newStartProcess(processo));
        }
        
        //insere comando para executar regras
        commands.add(commandsFactory.newFireAllRules());  
        
        Command<?> batchCommand = commandsFactory.newBatchExecution(commands, "defaultStatelessKieSession");
        		
		RuleServicesClient rulesClient = kieServicesClient.getServicesClient(RuleServicesClient.class);  
		
		//Executa regras
		ServiceResponse<ExecutionResults> executeResponse = rulesClient.executeCommandsWithResults(CONTAINER_ID, batchCommand);
		
		return executeResponse;
        
    }   
	
	@Test
    public void testExecutadoRegraNoWBPelaAplicacao() {
    	//container utilizado no momento
    	CONTAINER_ID = "banco.regras_1.0.0-SNAPSHOT";
    	
    	//lista de fatos
    	HashMap<String, Object> fatos = new HashMap<>();   	
    	
    	//ID do processo criado no workbench
    	String processo = "RF2";
    	
    	//fato criado
    	Conta conta = new Conta();
    	conta.setNrConta(0);
//    	conta.setVlBalanco(2000);
    	conta.setVlBalanco(500);
    	
    	//fato inserido à lista
    	fatos.put("conta1", conta);
    	
    	System.out.println("Fato: Conta Enviada");
    	System.out.println(conta.toString());
    	System.out.println(">>>>>>");
    	
    	//Inicialização da chamada
    	initialize(fatos);
    	
    	//Execução da regra
    	ServiceResponse<ExecutionResults> response = executeCommands(fatos, processo);
    	
    	//Resposta
    	if(response.getType() == ResponseType.SUCCESS) {  
    		System.out.println("Commands executed with success! Response: ");  
    		
    		System.out.println("Fato: Conta recebida");
    		conta = new Conta();
    		//Fato retornado
    		conta = (Conta) response.getResult().getValue("conta1");
    		System.out.println(conta.toString());
    		System.out.println("<<<<<<");
    	}  
    	else {  
    		System.out.println("Error executing rules. Message: ");  
    		System.out.println(response.getMsg());  
    	}      	
    }
}
