package containers;

import agents.MyAgent;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MyAgentContainer extends Application {

    MyAgent myAgent;
    ObservableList<String> messages = FXCollections.observableArrayList();


    public void setMyAgent(MyAgent myAgent) {
        this.myAgent = myAgent;
    }

    public static void main(String[] args) throws ControllerException {
        launch(args);

    }

    private void startAgentContainer() throws ControllerException{
        Runtime runtime = Runtime.instance();
        ProfileImpl profile = new ProfileImpl();
        profile.setParameter( ProfileImpl.MAIN_HOST, "localhost");
        profile.setParameter( ProfileImpl.CONTAINER_NAME, "My Agent Container");
        AgentContainer agentContainer = runtime.createAgentContainer(profile);
        AgentController agentController = agentContainer.createNewAgent("agent1", "agents.MyAgent", new Object[]{this});
        agentController.start();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        startAgentContainer();
        BorderPane root = new BorderPane();
        HBox hbox = new HBox();
        hbox.setPadding( new Insets(15) );
        hbox.setSpacing(10);
        Label label2 = new Label(" receiver name : ");
        TextField textField2 = new TextField();
        VBox vBox2 = new VBox();
        HBox hBox2 = new HBox();
        hBox2.setPadding( new Insets(15) );
        hBox2.setSpacing(10);
        hBox2.getChildren().addAll( label2, textField2);
        Label label = new Label(" your message : ");
        TextField textField = new TextField();
        Button button = new Button("Send");
        hbox.getChildren().addAll(label, textField, button);
        vBox2.getChildren().addAll( hBox2, hbox);
        root.setTop(vBox2);
        ListView<String> listView = new ListView<>( this.messages );
        VBox vBox = new VBox();
        listView.setPadding( new Insets(10));
        vBox.getChildren().add(listView);
        root.setCenter( vBox );

        button.setOnAction(event -> {
            String receiver = textField2.getText();
            String message = textField.getText();
            GuiEvent guiEvent = new GuiEvent( this, 1);
            guiEvent.addParameter(receiver);
            guiEvent.addParameter( message);
            this.myAgent.onGuiEvent( guiEvent);
        });

        primaryStage.setTitle("agent 1");
        Scene scene = new Scene( root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public ObservableList<String> getMessages() {
        return messages;
    }
}
