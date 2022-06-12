package containers;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;


public class ServerContainer  {

    public static void main(String[] args) throws StaleProxyException {
        Runtime runtime = Runtime.instance();
        ProfileImpl profile = new ProfileImpl();
        profile.setParameter( ProfileImpl.MAIN_HOST, "localhost");
        profile.setParameter( ProfileImpl.CONTAINER_NAME, "Server Container");
        AgentContainer agentContainer = runtime.createAgentContainer(profile);
        AgentController agentController = agentContainer.createNewAgent("server", "agents.ServerAgent", new Object[]{0});
        agentController.start();
    }
}
