package droolstutorial;


import javax.persistence.CollectionTable;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

import util.DateHelper;
import util.KnowledgeSessionHelper;
import util.OutputDisplay;


public class TesteLicao2 {
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
    public void test2Fatos() {
        sessionStatefull = KnowledgeSessionHelper
                .getStatefulKnowledgeSessionWithCallback(kieContainer,"ksession-licao2");

        OutputDisplay display = new OutputDisplay();
        sessionStatefull.setGlobal("resultado", display);
        Conta conta = new Conta();
        sessionStatefull.insert(conta);
        PeriodoContabil periodoContabil = new PeriodoContabil();
        sessionStatefull.insert(periodoContabil);
        sessionStatefull.fireAllRules();
    }

    @Test
    public void test2FatosPopulados() {
    	sessionStatefull = KnowledgeSessionHelper
    			.getStatefulKnowledgeSessionWithCallback(kieContainer,"ksession-licao2");
    	
    	OutputDisplay display = new OutputDisplay();
    	sessionStatefull.setGlobal("resultado", display);
    	
    	Conta conta = new Conta();
    	conta.setNrConta(1);
    	conta.setVlBalanco(0);
    	sessionStatefull.insert(conta);
    	
    	MovimentacaoCaixa caixa = new MovimentacaoCaixa();
    	caixa.setNrConta(1);
    	caixa.setTpMov(MovimentacaoCaixa.CREDITO);
    	caixa.setVlMontante(1000.0);
    	sessionStatefull.insert(caixa);
    	
    	sessionStatefull.fireAllRules();
    	
    	Assert.assertEquals(1000.0, conta.getVlBalanco(), 0);
    }

    @Test
    public void test2FatosPopuladosComContasDiferentes() throws Exception {
    	sessionStatefull = KnowledgeSessionHelper
    			.getStatefulKnowledgeSessionWithCallback(kieContainer,"ksession-licao2");
    	
    	OutputDisplay display = new OutputDisplay();
    	sessionStatefull.setGlobal("resultado", display);
    	
    	Conta conta = new Conta();
    	conta.setNrConta(1);
    	conta.setVlBalanco(0);
    	sessionStatefull.insert(conta);
    	
    	MovimentacaoCaixa caixa = new MovimentacaoCaixa();
    	caixa.setNrConta(1);
    	caixa.setTpMov(MovimentacaoCaixa.CREDITO);
    	caixa.setVlMontante(1000.0);
    	caixa.setDtMov(DateHelper.getDate("2010-01-15"));
    	sessionStatefull.insert(caixa);
    	
    	MovimentacaoCaixa caixa2 = new MovimentacaoCaixa();
    	caixa2.setNrConta(2);
    	caixa2.setTpMov(MovimentacaoCaixa.CREDITO);
    	caixa2.setVlMontante(1000.0);
    	caixa2.setDtMov(DateHelper.getDate("2010-01-15"));
    	sessionStatefull.insert(caixa2);
    	
    	sessionStatefull.fireAllRules();
    	
    	Assert.assertEquals(1000.0, conta.getVlBalanco(), 0);
    }
    
    @Test
    public void testCalculandoBalanco() throws Exception {
    	sessionStatefull = KnowledgeSessionHelper
    			.getStatefulKnowledgeSessionWithCallback(kieContainer,"ksession-licao2");
    	
    	OutputDisplay display = new OutputDisplay();
    	sessionStatefull.setGlobal("resultado", display);
    	
    	Conta conta = new Conta();
    	conta.setNrConta(1);
    	conta.setVlBalanco(0);
    	sessionStatefull.insert(conta);
    	
    	MovimentacaoCaixa caixa = new MovimentacaoCaixa();
    	caixa.setNrConta(1);
    	caixa.setTpMov(MovimentacaoCaixa.CREDITO);
    	caixa.setVlMontante(1000.0);
    	caixa.setDtMov(DateHelper.getDate("2016-01-15"));
    	sessionStatefull.insert(caixa);
    	
    	MovimentacaoCaixa caixa2 = new MovimentacaoCaixa();
    	caixa2.setNrConta(1);
    	caixa2.setTpMov(MovimentacaoCaixa.DEBITO);
    	caixa2.setVlMontante(500.0);
    	caixa2.setDtMov(DateHelper.getDate("2016-01-15"));
    	sessionStatefull.insert(caixa2);
    	
    	MovimentacaoCaixa caixa3 = new MovimentacaoCaixa();
    	caixa3.setNrConta(1);
    	caixa3.setTpMov(MovimentacaoCaixa.CREDITO);
    	caixa3.setVlMontante(1000.0);
    	caixa3.setDtMov(DateHelper.getDate("2016-04-15"));
    	sessionStatefull.insert(caixa3);
    	
    	PeriodoContabil contabil = new PeriodoContabil();
    	contabil.setDtInicio(DateHelper.getDate("2016-01-01"));
    	contabil.setDtFim(DateHelper.getDate("2016-03-31"));
    	sessionStatefull.insert(contabil);
    	
    	sessionStatefull.fireAllRules();
    	
    	Assert.assertEquals(500.0, conta.getVlBalanco(), 0);
    }
}