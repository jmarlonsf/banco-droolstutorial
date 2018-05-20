package droolstutorial;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.FactHandle;

import util.KnowledgeSessionHelper;
import util.OutputDisplay;

public class TesteLicao1 {
	StatelessKieSession sessionStateless = null;
	KieSession sessionStateFull = null;
	static KieContainer kieContainer;
	
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
	public void testeNumeroUm() {
		sessionStateFull = KnowledgeSessionHelper.getStateFullKnowledgeSession(kieContainer, "ksession-rules");
		
//		Variavel glogal
		OutputDisplay outputDisplay = new OutputDisplay();
		sessionStateFull.setGlobal("resultado", outputDisplay);
		
//		Fato Conta
		Conta conta = new Conta();
		sessionStateFull.insert(conta);
		
		sessionStateFull.fireAllRules();
	}	
	
	@Test
	public void testeComFatoEUsandoCallBackGlobal() {
		sessionStateFull = KnowledgeSessionHelper.getStateFullKnowledgeSession(kieContainer, "ksession-rules");
		
		sessionStateFull.addEventListener(new RuleRuntimeEventListener() {
			
			@Override
			public void objectUpdated(ObjectUpdatedEvent arg0) {
				System.out.println("Objeto atualizado: \n"
						+ arg0.getObject().toString());
				
			}
			
			@Override
			public void objectInserted(ObjectInsertedEvent arg0) {
				System.out.println("Objeto inserido: \n"
						+ arg0.getObject().toString());
				
			}
			
			@Override
			public void objectDeleted(ObjectDeletedEvent arg0) {
				System.out.println("Objeto removido: \n"
						+ arg0.getOldObject().toString());
				
			}
		});
				
//		Fato Conta
		Conta conta = new Conta();
		conta.setNrConta(1);
		
		FactHandle factHandle = sessionStateFull.insert(conta);		
		conta.setVlBalanco(12.0);
		
		sessionStateFull.update(factHandle, conta);
		sessionStateFull.delete(factHandle);		
		sessionStateFull.fireAllRules();
		
		System.out.println("\nPercebeu?");
	}	
	
	@Test
	public void testeComFatoEDoisFireAllRules() {
		sessionStateFull = KnowledgeSessionHelper.getStateFullKnowledgeSession(kieContainer, "ksession-rules");
		
//		Variavel glogal
		OutputDisplay outputDisplay = new OutputDisplay();
		sessionStateFull.setGlobal("resultado", outputDisplay);
		
//		Fato Conta
		Conta conta = new Conta();
		sessionStateFull.insert(conta);
		
		System.out.println("Primeiro FireAllRules()");
		sessionStateFull.fireAllRules();
		System.out.println("Segundo FireAllRules()");
		sessionStateFull.fireAllRules();
	}
	
	@Test
	public void testeComFatoEDoisFireAllRulesEUmSetter() {
		sessionStateFull = KnowledgeSessionHelper.getStateFullKnowledgeSession(kieContainer, "ksession-rules");
		
//		Variavel glogal
		OutputDisplay outputDisplay = new OutputDisplay();
		sessionStateFull.setGlobal("resultado", outputDisplay);
		
//		Fato Conta
		Conta conta = new Conta();
		sessionStateFull.insert(conta);
		
		System.out.println("Primeiro FireAllRules()");
		sessionStateFull.fireAllRules();
		conta.setNrConta(1);
		
		System.out.println("Segundo FireAllRules()");
		sessionStateFull.fireAllRules();
	}
	
	@Test
	public void testeComFatoDoisFireAllRulesUmSetterEUpdate() {
		sessionStateFull = KnowledgeSessionHelper.getStateFullKnowledgeSession(kieContainer, "ksession-rules");
		
//		Variavel glogal
		OutputDisplay outputDisplay = new OutputDisplay();
		sessionStateFull.setGlobal("resultado", outputDisplay);
		
//		Fato Conta
		Conta conta = new Conta();
		FactHandle factHandle = sessionStateFull.insert(conta);
		
		System.out.println("Primeiro FireAllRules()");
		sessionStateFull.fireAllRules();
		
		conta.setNrConta(1);
		
		sessionStateFull.update(factHandle, conta);
		System.out.println("Segundo FireAllRules()");
		sessionStateFull.fireAllRules();
	}
	
	@Test
	public void testeComFatoQueInsereObjeto() {
		sessionStateFull = KnowledgeSessionHelper.getStateFullKnowledgeSession(kieContainer, "ksession-rules");
		
//		Variavel glogal
		OutputDisplay outputDisplay = new OutputDisplay();
		sessionStateFull.setGlobal("resultado", outputDisplay);
		
		sessionStateFull.addEventListener(new RuleRuntimeEventListener() {
			@Override
			public void objectUpdated(ObjectUpdatedEvent arg0) {
				System.out.println("Objeto atualizado: \n"
						+ arg0.getObject().toString());
			}
			@Override
			public void objectInserted(ObjectInsertedEvent arg0) {
				System.out.println("Objeto inserido: \n"
						+ arg0.getObject().toString());
			}
			@Override
			public void objectDeleted(ObjectDeletedEvent arg0) {
				System.out.println("Objeto removido: \n"
						+ arg0.getOldObject().toString());
			}
		});
				
//		Fato Movimentação do Caixa
		MovimentacaoCaixa movimentacaoCaixa = new MovimentacaoCaixa();
		FactHandle factHandle = sessionStateFull.insert(movimentacaoCaixa);
		
		sessionStateFull.fireAllRules();
	}
}