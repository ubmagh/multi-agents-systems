package agents;

import containers.Player1Container;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

public class Player1Agent extends GuiAgent {

    private transient Player1Container theGui; // when migrating


    // called after instanciating the agent
    @Override
    protected void setup() {

        System.out.println("******* Player 1 Created *******");

        theGui = (Player1Container) getArguments()[0];
        theGui.setMyAgent(this);

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {

                ACLMessage receivedMsg = receive();

                if (receivedMsg != null) {
                    if( receivedMsg.getPerformative() ==ACLMessage.INFORM )
                        theGui.getMessages().add( "Server : "+receivedMsg.getContent() );
                    else{

                    }
                } else
                    block();
            }
        });

        ACLMessage messageACL = new ACLMessage(ACLMessage.PROPOSE);
        messageACL.setContent("p1");
        messageACL.addReceiver(new AID( "server", AID.ISLOCALNAME));
        send(messageACL);
    }


    // before destruction
    @Override
    protected void takeDown() {
        System.out.println("******* Player 1 disconnected *******");
    }


    @Override
    public void onGuiEvent(GuiEvent guiEvent) { // this should be public !
        if (guiEvent.getType() == 1) {
            String message = guiEvent.getParameter(0).toString();
            ACLMessage messageACL = new ACLMessage(ACLMessage.CONFIRM);
            messageACL.setContent(message);
            messageACL.addReceiver(new AID( "server", AID.ISLOCALNAME));
            send(messageACL);
        }
    }
}
