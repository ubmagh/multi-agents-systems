package agents;


import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;

public class MyAgent extends Agent {

    // called after instanciating the agent
    @Override
    protected void setup() {

        System.out.println("******* My agent Setup() *******");


        ParallelBehaviour parallelBehaviour=new ParallelBehaviour();
        addBehaviour(parallelBehaviour);

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
        parallelBehaviour.addSubBehaviour(new TickerBehaviour(this,1) {
            @Override
            protected void onTick() {
                System.out.println("***************action tick*********************");
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
}
