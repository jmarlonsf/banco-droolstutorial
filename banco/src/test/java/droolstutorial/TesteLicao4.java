package droolstutorial;

import util.OutputDisplay;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import util.KnowledgeSessionHelper;

public class TesteLicao4 {
	static KieContainer kieContainer;
    KieSession sessionStatefull = null;

    @BeforeClass
    public static void beforeClass() {
        kieContainer = KnowledgeSessionHelper.criarRegraBase();
    }
    @Before
    public void setUp() throws Exception {
        System.out.println("------------Before------------");
    }
    @After
    public void tearDown() throws Exception {
        System.out.println("------------After-------------");
    }

    @Test
    public void testRuleFlow1() {
        sessionStatefull = KnowledgeSessionHelper
                .getStatefulKnowledgeSessionForJBPM(kieContainer, "ksession-licao4");
        
        OutputDisplay display = new OutputDisplay();
        sessionStatefull.setGlobal("resultado", display);
        
        Conta conta = new Conta();
        sessionStatefull.insert(conta);
        
        sessionStatefull.startProcess("RF1");
        
        sessionStatefull.fireAllRules();
    }
    
    @Test
    public void testRuleFlow2() {
    	sessionStatefull = KnowledgeSessionHelper
                .getStatefulKnowledgeSessionForJBPM(kieContainer, "ksession-licao4");
        
        OutputDisplay display = new OutputDisplay();
        sessionStatefull.setGlobal("resultado", display);
        
        Conta conta = new Conta();
        sessionStatefull.insert(conta);
        
        sessionStatefull.fireAllRules();
    }
    
    @Test
    public void testRuleFlow3() {
    	sessionStatefull = KnowledgeSessionHelper
                .getStatefulKnowledgeSessionForJBPM(kieContainer, "ksession-licao4a");
        
        OutputDisplay display = new OutputDisplay();
        sessionStatefull.setGlobal("resultado", display);
           
        Conta conta = new Conta();
//        conta.setVlBalanco(2500);
        conta.setVlBalanco(500);
        sessionStatefull.insert(conta);
        
        PeriodoContabil periodoContabil = new PeriodoContabil();
        sessionStatefull.insert(periodoContabil);
        
        sessionStatefull.fireAllRules();

    }
}
