import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

//GuiServer class
public class RPLS extends Application{

    TextField enterPort;
    Button serverChoice;
    HBox players, pics;
    VBox game, buttonBox;
    Scene startScene;
    BorderPane startPane;
    Server serverConnection;
    ListView<String> listItems;
    Label con, p1, p2, winner, portLab;
    Image pic1, pic2, pic3, pic4, pic5;
    ImageView p1pic, p2pic;
    int count, score1, score2 = 0;
    PauseTransition pause = new PauseTransition(Duration.seconds(3));

    private void assignPictures(){

        switch (serverConnection.p1Hold) {
            case "rock":
                p1pic = new ImageView(pic1);
                break;
            case "spock":
                p1pic = new ImageView(pic5);
                break;
            case "paper":
                p1pic = new ImageView(pic2);
                break;
            case "lizard":
                p1pic = new ImageView(pic4);
                break;
            case "scissors":
                p1pic = new ImageView(pic3);
                break;
        }
        p1pic.setFitWidth(75);
        p1pic.setFitHeight(75);
        p1pic.setPreserveRatio(true);
        switch (serverConnection.p2Hold) {
            case "rock":
                p2pic = new ImageView(pic1);
                break;
            case "spock":
                p2pic = new ImageView(pic5);
                break;
            case "paper":
                p2pic = new ImageView(pic2);
                break;
            case "lizard":
                p2pic = new ImageView(pic4);
                break;
            case "scissors":
                p2pic = new ImageView(pic3);
                break;
        }
        p2pic.setFitWidth(75);
        p2pic.setFitHeight(75);
        p2pic.setPreserveRatio(true);
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        primaryStage.setTitle("RPSLS Server");

        portLab = new Label("Enter port number");
        portLab.setFont(new Font("Comic Sans MS", 16));
        enterPort = new TextField("5555");
        serverChoice = new Button("Start");
        winner = new Label("");
        winner.setFont(new Font("Comic Sans MS", 18));

        pic1 = new Image("rock.jpg");
        pic2 = new Image("paper.jpg");
        pic3 = new Image("scissors.jpg");
        pic4 = new Image("lizard.jpg");
        pic5 = new Image("spock.jpg");


        con = new Label("Clients Connected: "  + count);
        con.setFont(new Font("Comic Sans MS", 15));
        p1 = new Label("\n\nPlayer 1: " + score1);
        p1.setFont(new Font("Comic Sans MS", 15));
        p2 = new Label("\n\nPlayer 2: " + score2);
        p2.setFont(new Font("Comic Sans MS", 15));

        p1pic = new ImageView();
        p2pic = new ImageView();

        EventHandler<ActionEvent> veryEnd = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(serverConnection.theGame.p1score==3)
                    primaryStage.setScene(endGui(1));
                else if(serverConnection.theGame.p2score==3)
                    primaryStage.setScene(endGui(2));
            }
        };

        this.serverChoice.setOnAction(e->{ primaryStage.setScene(createServerGui());
            primaryStage.setTitle("This is the Server");
            int port = Integer.parseInt(enterPort.getText());
            serverConnection = new Server(data -> {
                Platform.runLater(()->{
                    listItems.getItems().add(data.toString());
                    assignPictures();
                    score1 = serverConnection.theGame.p1score;
                    score2 = serverConnection.theGame.p2score;
                    if(data.toString().equals("bye") || data.toString().equals("again")){
                        p1pic = new ImageView();
                        p2pic = new ImageView();
                    }
                    count = serverConnection.counter - serverConnection.left;
                    if(count < 0 )
                        count = 0;
                    con.setText("Clients Connected: "  + count);
                    p1.setText("\n\nPlayer 1: " + score1);
                    p2.setText("\n\nPlayer 2: " + score2);
                    if(data.toString().contains("wins") || data.toString().contains("draw"))
                        winner.setText(data.toString());
                    else
                        winner.setText("");
                    primaryStage.setScene(createServerGui());
                    pause.setOnFinished(veryEnd);
                    pause.play();
                });
            }, port);
        });

        buttonBox = new VBox(40, portLab, enterPort,serverChoice);
        startPane = new BorderPane();
        startPane.setPadding(new Insets(70));
        startPane.setCenter(buttonBox);
        startPane.setStyle("-fx-background-color: ivory");
        startPane.setPadding(new Insets(70));

        startScene = new Scene(startPane, 400,600);

        listItems = new ListView<String>();
        listItems.setPrefHeight(300);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                //System.exit(0);
            }
        });

        primaryStage.setScene(startScene);
        primaryStage.show();
    }

    public Scene createServerGui() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(20));
        pane.setStyle("-fx-background-color: mistyrose");

        players = new HBox(150, p1, p2);
        players.setAlignment(Pos.CENTER);
        pics = new HBox(140, p1pic, p2pic);
        pics.setAlignment(Pos.CENTER);
        game = new VBox(20, players, pics, winner);
        game.setAlignment(Pos.CENTER);
        pane.setCenter(game);
        pane.setTop(con);
        pane.setBottom(listItems);
        return new Scene(pane, 400, 600);
    }

    public Scene endGui(int n) {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(20));
        pane.setStyle("-fx-background-color: mistyrose");

        players = new HBox(150, p1, p2);
        players.setAlignment(Pos.CENTER);
        winner.setText("Player " + n + " Wins\n  the Game!!!\n");
        game = new VBox(20, players, winner);
        game.setAlignment(Pos.CENTER);
        pane.setCenter(game);
        pane.setTop(con);
        pane.setBottom(listItems);
        return new Scene(pane, 400, 600);
    }
}