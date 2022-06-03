package containers;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.ControllerException;

public class SimpleContainer {

    public static void main(String[] args) throws ControllerException {

        Runtime runtime = Runtime.instance();
        ProfileImpl profile = new ProfileImpl();
        profile.setParameter( ProfileImpl.MAIN_HOST, "localhost");
        profile.setParameter( ProfileImpl.CONTAINER_NAME, "Simple Container");
        AgentContainer agentContainer = runtime.createAgentContainer(profile);
        agentContainer.start();

    }

}
