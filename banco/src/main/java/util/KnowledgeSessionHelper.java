package util;

import org.jbpm.workflow.instance.node.RuleSetNodeInstance;
import org.kie.api.KieServices;
import org.kie.api.event.process.ProcessCompletedEvent;
import org.kie.api.event.process.ProcessEventListener;
import org.kie.api.event.process.ProcessNodeLeftEvent;
import org.kie.api.event.process.ProcessNodeTriggeredEvent;
import org.kie.api.event.process.ProcessStartedEvent;
import org.kie.api.event.process.ProcessVariableChangedEvent;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.event.rule.AgendaGroupPoppedEvent;
import org.kie.api.event.rule.AgendaGroupPushedEvent;
import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.kie.api.event.rule.MatchCancelledEvent;
import org.kie.api.event.rule.MatchCreatedEvent;
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleFlowGroupActivatedEvent;
import org.kie.api.event.rule.RuleFlowGroupDeactivatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

public class KnowledgeSessionHelper {
	
	public static KieContainer criarRegraBase() {
		
		KieServices ks = KieServices.Factory.get();
		KieContainer kieContainer = ks.getKieClasspathContainer();
		
		return kieContainer;
	}
	
	public static StatelessKieSession getStatelessKnowledgeSession(KieContainer kieContainer, String sessionName) {
		
		StatelessKieSession ksSession = kieContainer.newStatelessKieSession(sessionName);
		
		return ksSession;
	}
	
	public static KieSession getStateFullKnowledgeSession(KieContainer kieContainer, String sessionName) {
		KieSession kSession = kieContainer.newKieSession(sessionName);
		
		return kSession;
	}
	
	public static KieSession getStatefulKnowledgeSessionWithCallback( KieContainer kieContainer, String sessionName) {
	        KieSession session = getStateFullKnowledgeSession(kieContainer, sessionName);
	        session.addEventListener(new RuleRuntimeEventListener() {
	            public void objectInserted(ObjectInsertedEvent event) {
	                System.out.println("Objecto inserido \n"
	                        + event.getObject().toString());
	            }
	            public void objectUpdated(ObjectUpdatedEvent event) {
	                System.out.println("Objecto foi atualizado \n"
	                        + "new Content \n" + event.getObject().toString());
	            }
	            public void objectDeleted(ObjectDeletedEvent event) {
	                System.out.println("Objecto removido \n"
	                        + event.getOldObject().toString());
	            }
	        });
	        session.addEventListener(new AgendaEventListener() {
	            public void matchCreated(MatchCreatedEvent event) {
	                System.out.println("A regra \'"
	                        + event.getMatch().getRule().getName()
	                        + "\' pode ser executada na agenda");
	            }
	            public void matchCancelled(MatchCancelledEvent event) {
	                System.out.println("A regra \'"
	                        + event.getMatch().getRule().getName()
	                        + "\\' não pode ser executada na agenda");
	            }
	            public void beforeMatchFired(BeforeMatchFiredEvent event) {
	                System.out.println("A regra \'"
	                        + event.getMatch().getRule().getName()
	                        + "\' será executada");
	            }
	            public void afterMatchFired(AfterMatchFiredEvent event) {
	                System.out.println("A regra \'"
	                        + event.getMatch().getRule().getName()
	                        + "\' foi executada");
	            }
	            public void agendaGroupPopped(AgendaGroupPoppedEvent event) {
	            }
	            public void agendaGroupPushed(AgendaGroupPushedEvent event) {
	            }
	            public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
	            }
	            public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
	            }
	            public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
	            }
	            public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
	            }
	        });
	        
			return session;
	 }
	
	public static KieSession getStatefulKnowledgeSessionForJBPM(
            KieContainer kieContainer, String sessionName) {
          KieSession session = getStatefulKnowledgeSessionWithCallback(kieContainer,sessionName);
        session.addEventListener(new ProcessEventListener() {

              @Override
              public void beforeVariableChanged(ProcessVariableChangedEvent arg0) {}

              @Override
              public void beforeProcessStarted(ProcessStartedEvent arg0) {
                  System.out.println("Processo: \'"+arg0.getProcessInstance().getProcessName()+"\' foi iniciado");
              }

              @Override
              public void beforeProcessCompleted(ProcessCompletedEvent arg0) {}

              @Override
              public void beforeNodeTriggered(ProcessNodeTriggeredEvent arg0) {}

              @Override
              public void beforeNodeLeft(ProcessNodeLeftEvent arg0) {
                 if (arg0.getNodeInstance() instanceof RuleSetNodeInstance){
                      System.out.println("Noh: \'"+ arg0.getNodeInstance().getNodeName()+"\' foi deixado");        
                  }
              }

              @Override
              public void afterVariableChanged(ProcessVariableChangedEvent arg0) {}

              @Override
              public void afterProcessStarted(ProcessStartedEvent arg0) {}

              @Override
              public void afterProcessCompleted(ProcessCompletedEvent arg0) {
                  System.out.println("Processo: \'"+arg0.getProcessInstance().getProcessName()+"\' parou");
              }

              @Override
              public void afterNodeTriggered(ProcessNodeTriggeredEvent arg0) {
                  if (arg0.getNodeInstance() instanceof RuleSetNodeInstance){
                      System.out.println("Noh \'"+ arg0.getNodeInstance().getNodeName()+"\' foi inserido");        
                  }
              }

              @Override
              public void afterNodeLeft(ProcessNodeLeftEvent arg0) {}
          });
        return session;
    }
}