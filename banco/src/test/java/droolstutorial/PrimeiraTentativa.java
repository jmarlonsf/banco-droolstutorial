package droolstutorial;

import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

import util.KnowledgeSessionHelper;

public class PrimeiraTentativa {

	StatelessKieSession sessionStateless = null;
	KieSession sessionStateFull = null;
	static KieContainer kieContainer;
	
	@BeforeClass
	public static void beforeClass() {
		kieContainer = KnowledgeSessionHelper.criarRegraBase();
	}
	
	@Test
	public void testeNumeroUm() {
		sessionStateFull = KnowledgeSessionHelper.getStateFullKnowledgeSession(kieContainer, "ksession-rules");
		sessionStateFull.fireAllRules();
	}	
}
