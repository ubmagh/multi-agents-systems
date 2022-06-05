package agents;


import containers.MyAgentContainer;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

public class MyAgent extends GuiAgent {

    private transient MyAgentContainer theGui; // when migrating



    // called after instanciating the agent
    @Override
    protected void setup() {

        System.out.println("******* My agent Setup() *******");

        theGui = (MyAgentContainer) getArguments()[0];

        theGui.setMyAgent( this );

        ParallelBehaviour parallelBehaviour = new ParallelBehaviour();


        // addBehaviour(parallelBehaviour);


        /*
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.addReceiver( new AID("rma", AID.ISLOCALNAME));
        message.setContent("Hello rma agent !");
        send(message);
        */

        /*addBehaviour(new Behaviour() {
            private int cpt=0;
            @Override
            public void action() {
                cpt++;
                System.out.println("Action....."+cpt);
            }

            @Override
            public boolean done() {
                if(cpt==100)
                    return true;
                return false;
            }
        });*/

        /*parallelBehaviour.addSubBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                System.out.println("Action.....");
            }
        });*/
        parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                System.out.println("Action....");
            }
        });
        parallelBehaviour.addSubBehaviour(new TickerBehaviour(this, 1) {
            @Override
            protected void onTick() {
                System.out.println("***************action tick*********************");
            }
        });

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {

                ACLMessage receivedMsg = receive();

                if (receivedMsg != null) {
                    theGui.getMessages().add( receivedMsg.getSender().getName()+" : "+receivedMsg.getContent() );
                    /*ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                    message.setContent("Bien reçu!");
                    message.addReceiver(receivedMsg.getSender());
                    send(message);*/
                }
                else
                    block();
            }
        });
    }


    // before destruction
    @Override
    protected void takeDown() {
        System.out.println("******* My agent takeDown() *******");
    }


    //before migrating agent to other container
    @Override
    protected void beforeMove() {
        System.out.println("******* My agent beforeMove() *******");
    }

    @Override
    protected void afterMove() {
        System.out.println("******* My agent afterMove() *******");
    }

    @Override
    public void onGuiEvent(GuiEvent guiEvent) { // this should be public !
        if( guiEvent.getType()==1 ){
            String receiver = guiEvent.getParameter(0).toString();
            String message = guiEvent.getParameter(1).toString();
            ACLMessage messageACL = new ACLMessage(ACLMessage.INFORM);
            messageACL.setContent(message);
            messageACL.addReceiver(new AID( receiver,AID.ISLOCALNAME) );
            send(messageACL);
        }
    }
}
