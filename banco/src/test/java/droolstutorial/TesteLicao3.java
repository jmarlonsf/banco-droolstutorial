package droolstutorial;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.FactHandle;

import droolstutorial.service.ClienteService;
import util.DateHelper;
import util.KnowledgeSessionHelper;
import util.OutputDisplay;

public class TesteLicao3 {
	static KieContainer kieContainer;
    StatelessKieSession sessionStateless = null;
    KieSession  sessionStatefull = null;

	@BeforeClass
	public static void beforeClass() {
		kieContainer = KnowledgeSessionHelper.criarRegraBase();
	}
	
	@Before
	public void antes() {
		System.out.println("----------Antes----------");
	}
	
	@After
	public void depois() {
		System.out.println("----------depois----------");
	}
	
	@Test
    public void testInConstrait() throws Exception {
		sessionStatefull = KnowledgeSessionHelper
                .getStatefulKnowledgeSessionWithCallback(kieContainer,"ksession-licao3");
        
		OutputDisplay display = new OutputDisplay();
        sessionStatefull.setGlobal("resultado", display);
        
        MovimentacaoCaixa caixa = new MovimentacaoCaixa();
        caixa.setTpMov(MovimentacaoCaixa.CREDITO);
        sessionStatefull.insert(caixa);
        
        sessionStatefull.fireAllRules();
    }    
	
	@Test
    public void testAccessor() throws Exception {
        sessionStatefull = KnowledgeSessionHelper
                .getStatefulKnowledgeSessionWithCallback(kieContainer, "ksession-licao3");
        
        OutputDisplay display = new OutputDisplay();
        sessionStatefull.setGlobal("resultado", display);
        Cliente cliente = new Cliente();
        
        cliente.setNome("João");
        cliente.setSobrenome("Ferreira");
        
        ContaPrivada contaPrivada = new ContaPrivada();
        contaPrivada.setDono(cliente);

        sessionStatefull.insert(contaPrivada);
        
        sessionStatefull.fireAllRules();
    }

	@Test
    public void testInOrFact() throws Exception {
        sessionStatefull = KnowledgeSessionHelper
                .getStatefulKnowledgeSessionWithCallback(kieContainer, "ksession-licao3");
        OutputDisplay display = new OutputDisplay();
        sessionStatefull.setGlobal("resultado", display);
        Cliente cliente = new Cliente();

        cliente.setPais("GB");
        sessionStatefull.insert(cliente);
        
        ContaPrivada contaPrivada = new ContaPrivada();
        contaPrivada.setDono(cliente);
        sessionStatefull.insert(contaPrivada);
        
        sessionStatefull.fireAllRules();
    }
	
	@Test
    public void testNotCondition() throws Exception {
        sessionStatefull = KnowledgeSessionHelper
                .getStatefulKnowledgeSessionWithCallback(kieContainer, "ksession-licao3");
        
        OutputDisplay display = new OutputDisplay();
        sessionStatefull.setGlobal("resultado", display);
        
        sessionStatefull.fireAllRules();
    }
	
    @Test
    public void testExistsCondition() throws Exception {
        sessionStatefull = KnowledgeSessionHelper
                .getStatefulKnowledgeSessionWithCallback(kieContainer, "ksession-licao3");
        
        OutputDisplay display = new OutputDisplay();
        sessionStatefull.setGlobal("resultado", display);
        
        Conta conta = new Conta();
        sessionStatefull.insert(conta);
        
        Cliente cliente = new Cliente();
        sessionStatefull.insert(cliente);
        
        sessionStatefull.fireAllRules();
    }
    
    @Test
    public void testForAll() throws Exception {
        sessionStatefull = KnowledgeSessionHelper
                .getStatefulKnowledgeSessionWithCallback(kieContainer, "ksession-licao3");
        
        OutputDisplay display = new OutputDisplay();
        sessionStatefull.setGlobal("resultado", display);
        
        Conta conta = new Conta();
        conta.setNrConta(1);
        conta.setVlBalanco(0);
        sessionStatefull.insert(conta);

        MovimentacaoCaixa caixa = new MovimentacaoCaixa();
        caixa.setNrConta(1);
        sessionStatefull.insert(caixa);
        
        MovimentacaoCaixa caixa2 = new MovimentacaoCaixa();
        caixa2.setNrConta(1);
        sessionStatefull.insert(caixa2);
        
        Conta conta2 = new Conta();
        conta.setNrConta(2);
        conta.setVlBalanco(0);
        sessionStatefull.insert(conta2);

        MovimentacaoCaixa caixa3 = new MovimentacaoCaixa();
        caixa3.setNrConta(2);
        sessionStatefull.insert(caixa3);

        sessionStatefull.fireAllRules();
    }
    
    @Test
    public void testFromLHS() throws Exception {
        sessionStatefull = KnowledgeSessionHelper
        		.getStatefulKnowledgeSessionWithCallback(kieContainer, "ksession-licao3");
        
        OutputDisplay display = new OutputDisplay();
        sessionStatefull.setGlobal("resultado", display);
        
        sessionStatefull.setGlobal("clienteService", new ClienteService());
        Cliente cliente = new Cliente("João", "Ferreira", "Br");
        sessionStatefull.insert(cliente);
        
        sessionStatefull.fireAllRules();
    }
    
    @Test
    public void testCollecting() throws Exception {
        sessionStatefull = KnowledgeSessionHelper
        		.getStatefulKnowledgeSessionWithCallback(kieContainer, "ksession-licao3");
        
        OutputDisplay display = new OutputDisplay();
        sessionStatefull.setGlobal("resultado", display);
        
        Conta conta = new Conta();
        conta.setNrConta(1);
        conta.setVlBalanco(0);
        sessionStatefull.insert(conta);
        
        sessionStatefull.insert(new MovimentacaoCaixa(DateHelper.getDate("2010-01-15"), 1000, MovimentacaoCaixa.CREDITO, 1));
        sessionStatefull.insert(new MovimentacaoCaixa(DateHelper.getDate("2010-02-15"), 500, MovimentacaoCaixa.DEBITO, 1));
        sessionStatefull.insert(new MovimentacaoCaixa(DateHelper.getDate("2010-04-15"), 1000, MovimentacaoCaixa.CREDITO, 1));
        sessionStatefull.insert(new PeriodoContabil(DateHelper.getDate("2010-01-01"), DateHelper.getDate("2010-31-31")));
        
        sessionStatefull.fireAllRules();
    }
    
    @Test
    public void testAccumulate() throws Exception {
        sessionStatefull = KnowledgeSessionHelper
        		.getStatefulKnowledgeSessionWithCallback(kieContainer, "ksession-licao3");
        
        OutputDisplay display = new OutputDisplay();
        sessionStatefull.setGlobal("resultado", display);
        
        Conta conta = new Conta();
        conta.setNrConta(1);
        conta.setVlBalanco(0);
        sessionStatefull.insert(conta);

        FactHandle fa = sessionStatefull.insert(new MovimentacaoCaixa(DateHelper.getDate("2010-01-15"), 1000, MovimentacaoCaixa.CREDITO, 1));
        sessionStatefull.insert(new MovimentacaoCaixa(DateHelper.getDate("2010-02-15"), 500, MovimentacaoCaixa.DEBITO,1));
        sessionStatefull.insert(new MovimentacaoCaixa(DateHelper.getDate("2010-04-15"), 1000, MovimentacaoCaixa.CREDITO,1));
        sessionStatefull.insert(new PeriodoContabil(DateHelper.getDate("2010-01-01"), DateHelper.getDate("2010-12-31")));
        
        sessionStatefull.fireAllRules();
        
        sessionStatefull.delete(fa);
        
        sessionStatefull.fireAllRules();
    }
    
}


