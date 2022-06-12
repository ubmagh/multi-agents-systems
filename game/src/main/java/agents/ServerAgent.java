package agents;


import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class ServerAgent extends Agent {

    private int targetNumber =0;


    // called after instanciating the agent
    @Override
    protected void setup() {

        System.out.println("******* Server created *******");
        randomizeNumber();


        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {

                ACLMessage receivedMsg = receive();

                if (receivedMsg != null) {
                    if( receivedMsg.getPerformative() == ACLMessage.PROPOSE ){
                        if( receivedMsg.getContent().equals("p1") ) {
                            ACLMessage messageACL = new ACLMessage(ACLMessage.INFORM);
                            messageACL.setContent("Player 1 Connected !");
                            messageACL.addReceiver(new AID( "player1", AID.ISLOCALNAME));
                            messageACL.addReceiver(new AID( "player2", AID.ISLOCALNAME));
                            send(messageACL);
                        }
                        if( receivedMsg.getContent().equals("p2") ){
                            ACLMessage messageACL = new ACLMessage(ACLMessage.INFORM);
                            messageACL.setContent("Player 2 Connected !");
                            messageACL.addReceiver(new AID( "player1", AID.ISLOCALNAME));
                            messageACL.addReceiver(new AID( "player2", AID.ISLOCALNAME));
                            send(messageACL);
                        }
                    }
                    if( receivedMsg.getPerformative()==ACLMessage.CONFIRM ){


                        int tryied = Integer.parseInt( receivedMsg.getContent() ) ;
                        if( tryied> targetNumber ){
                            ACLMessage messageACL = new ACLMessage(ACLMessage.INFORM);
                            messageACL.setContent(tryied+" is big ! ");
                            messageACL.addReceiver( receivedMsg.getSender() );
                            send(messageACL);
                        }
                        if( tryied < targetNumber ){
                            ACLMessage messageACL = new ACLMessage(ACLMessage.INFORM);
                            messageACL.setContent(tryied+" is small ! ");
                            messageACL.addReceiver( receivedMsg.getSender() );
                            send(messageACL);
                        }
                        if( tryied==targetNumber ){
                            ACLMessage messageACL = new ACLMessage(ACLMessage.INFORM);
                            messageACL.setContent(" Player '"+receivedMsg.getSender().getLocalName()+"' Has won the game !!! number = "+tryied);
                            messageACL.addReceiver(new AID( "player1", AID.ISLOCALNAME));
                            messageACL.addReceiver(new AID( "player2", AID.ISLOCALNAME));
                            send(messageACL);
                            randomizeNumber();
                            messageACL = new ACLMessage(ACLMessage.INFORM);
                            messageACL.setContent(" A new number has been randomized ! ");
                            messageACL.addReceiver(new AID( "player1", AID.ISLOCALNAME));
                            messageACL.addReceiver(new AID( "player2", AID.ISLOCALNAME));
                            send(messageACL);
                        }
                    }
                }
                else
                    block();
            }
        });
    }


    // before destruction
    @Override
    protected void takeDown() {
        System.out.println("******* Server stopped *******");
    }

    private void randomizeNumber(){
        this.targetNumber = (int) (Math.random() * 100);
    }

}
