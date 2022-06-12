package containers;

import agents.Player2Agent;
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
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Player2Container extends Application {

    Player2Agent player2Agent;
    ObservableList<String> messages = FXCollections.observableArrayList();

    public void setPlayer2Agent(Player2Agent player2Agent) {
        this.player2Agent = player2Agent;
    }

    public static void main(String[] args) throws ControllerException {
        launch(args);
    }

    private void startAgentContainer() throws ControllerException{
        Runtime runtime = Runtime.instance();
        ProfileImpl profile = new ProfileImpl();
        profile.setParameter( ProfileImpl.MAIN_HOST, "localhost");
        profile.setParameter( ProfileImpl.CONTAINER_NAME, "Player 2 Container");
        AgentContainer agentContainer = runtime.createAgentContainer(profile);
        AgentController agentController = agentContainer.createNewAgent("player2", "agents.Player2Agent", new Object[]{this});
        agentController.start();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        startAgentContainer();
        createGui( primaryStage);
        primaryStage.show();
    }

    private void createGui( Stage primaryStage){
        BorderPane root = new BorderPane();
        HBox hbox = new HBox();
        hbox.setPadding( new Insets(15) );
        hbox.setSpacing(10);
        Label label = new Label(" Guess the number : ");

        Button button = new Button("Check");
        TextField textField = new TextField();

        hbox.getChildren().addAll(label, textField, button);
        root.setTop(hbox);
        ListView<String> listView = new ListView<>( this.messages );
        VBox vBox = new VBox();
        listView.setPadding( new Insets(10));
        vBox.getChildren().add(listView);
        root.setCenter( vBox );

        button.setOnAction(event -> {
            String message = textField.getText();
            if( message.length()==0 ){
                Alert alert = new Alert( Alert.AlertType.INFORMATION);
                alert.setTitle("invalid number");
                alert.setContentText(" Please enter a number ");
                alert.show();
                return;
            }
            GuiEvent guiEvent = new GuiEvent( this, 1);
            guiEvent.addParameter( message);
            this.player2Agent.onGuiEvent( guiEvent);
            textField.setText("");
        });

        primaryStage.setTitle("Player 2");
        Scene scene = new Scene( root, 600, 400);
        primaryStage.setScene(scene);
    }

    public ObservableList<String> getMessages() {
        return messages;
    }
}
