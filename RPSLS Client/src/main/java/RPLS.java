import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

//GuiClient class
public class RPLS extends Application{

    TextField enterPort, enterIP;
    Button connect, playAgain, quit, r, p, s, l, sp;
    VBox entering, choices, bottom;
    HBox options;
    Scene startScene;
    int cNum = 2;
    BorderPane startPane;
    Client clientConnection;
    ListView<String> listItems;
    Label points, welcome, you, opp, result, thing, thing2, wait, pNum, gameWinner;
    Image pic1, pic2, pic3, pic4, pic5;
    ImageView rock, paper, scissors, lizard, spock;
    int score1, score2 = 0;
    String gameText = "";

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        primaryStage.setTitle("RPSLS Client");

        this.connect = new Button("Connect");
        playAgain = new Button("Play Again");
        quit = new Button("Quit");
        thing = new Label("Enter port number");
        thing.setFont(new Font("Comic Sans MS", 16));
        thing2 = new Label("\nEnter IP address");
        thing2.setFont(new Font("Comic Sans MS", 16));
        you = new Label("You Played:");
        you.setFont(new Font("Comic Sans MS", 26));
        opp = new Label("Opponent Played:");
        opp.setFont(new Font("Comic Sans MS", 26));
        enterPort = new TextField("5555");
        enterIP = new TextField("127.0.0.1");
        wait = new Label("");
        wait.setFont(new Font("Comic Sans MS", 16));
        pNum = new Label("You are Player 2");
        pNum.setFont(new Font("Comic Sans MS", 16));

        pic1 = new Image("rock.jpg");
        pic2 = new Image("paper.jpg");
        pic3 = new Image("scissors.jpg");
        pic4 = new Image("lizard.jpg");
        pic5 = new Image("spock.jpg");
        rock = new ImageView(pic1);
        rock.setFitWidth(75);
        rock.setFitHeight(75);
        rock.setPreserveRatio(true);
        paper = new ImageView(pic2);
        paper.setFitWidth(75);
        paper.setFitHeight(75);
        paper.setPreserveRatio(true);
        scissors = new ImageView(pic3);
        scissors.setFitWidth(75);
        scissors.setFitHeight(75);
        scissors.setPreserveRatio(true);
        lizard = new ImageView(pic4);
        lizard.setFitWidth(75);
        lizard.setFitHeight(75);
        lizard.setPreserveRatio(true);
        spock = new ImageView(pic5);
        spock.setFitWidth(75);
        spock.setFitHeight(75);
        spock.setPreserveRatio(true);
        r = new Button("", rock);
        p = new Button("", paper);
        s = new Button("", scissors);
        l = new Button("", lizard);
        sp = new Button("", spock);

        points = new Label();
        points.setFont(new Font("Comic Sans MS", 16));

        this.connect.setOnAction(e-> {
            primaryStage.setTitle("This is a client");
            int port = Integer.parseInt(enterPort.getText());
            String ip = enterIP.getText();  //the ip is 127.0.0.1 or "localhost"
            clientConnection = new Client(data->{
                Platform.runLater(()->{
                    listItems.getItems().add(data.toString());
                    score1 = clientConnection.theGame.p1score;
                    score2 = clientConnection.theGame.p2score;
                    points.setText("SCORE:     Player 1 - " + score1+"          Player 2 -" +score2+ "\n\n");
                    if(data.toString().equals("waiting for second player")) {
                        connect.setDisable(true);
                        cNum = 1;
                        wait.setText("Waiting for second player");
                        pNum.setText("You are Player 1");
                        primaryStage.setScene(startScene);
                        primaryStage.show();
                    }
                    else {
                        wait.setText("");
                        primaryStage.setScene(createClientGui());
                    }
                    if(clientConnection.theGame.p1score==3)
                        primaryStage.setScene(endGui(1));
                    else if(clientConnection.theGame.p2score==3)
                        primaryStage.setScene(endGui(2));
                });
            }, port, ip);
            clientConnection.start();
            connect.setDisable(true);
        });

        playAgain.setOnAction(e-> {
            clientConnection.theGame = new GameInfo();
            score1 = 0;
            score2 = 0;
            clientConnection.theGame.message = "again";
            points.setText("SCORE:     Player 1 - " + score1+"          Player 2 -" +score2+ "\n\n");
            clientConnection.sendGame();
            playAgain.setVisible(false);
            quit.setVisible(false);
            gameText = "";
            primaryStage.setScene(createClientGui());
        });

        quit.setOnAction(e-> {
            clientConnection.theGame = new GameInfo();
            clientConnection.theGame.message = "bye";
            clientConnection.sendGame();
            try {
                clientConnection.socketClient.close();
            } catch (IOException ez) {

            }
            Platform.exit();
            System.exit(0);
        });

        r.setOnAction(e-> {
            clientConnection.theGame.choice = "rock";
            clientConnection.theGame.message = "go";
            clientConnection.sendGame();
            primaryStage.setScene(endGui(clientConnection.theGame.roundWinner()));
        });
        p.setOnAction(e-> {
            clientConnection.theGame.choice = "paper";
            clientConnection.theGame.message = "go";
            clientConnection.sendGame();
            primaryStage.setScene(endGui(clientConnection.theGame.roundWinner()));
        });
        s.setOnAction(e-> {
            clientConnection.theGame.choice = "scissors";
            clientConnection.theGame.message = "go";
            clientConnection.sendGame();
            primaryStage.setScene(endGui(clientConnection.theGame.roundWinner()));
        });
        l.setOnAction(e-> {
            clientConnection.theGame.choice = "lizard";
            clientConnection.theGame.message = "go";
            clientConnection.sendGame();
            primaryStage.setScene(endGui(clientConnection.theGame.roundWinner()));
        });
        sp.setOnAction(e-> {
            clientConnection.theGame.choice = "spock";
            clientConnection.theGame.message = "go";
            clientConnection.sendGame();
            primaryStage.setScene(endGui(clientConnection.theGame.roundWinner()));
        });

        welcome = new Label("Welcome to Rock,\n  Paper, Scissors,\n   Lizard, Spock!\n");
        welcome.setFont(new Font("Comic Sans MS", 36));
        welcome.setStyle("-fx-font-weight: bold");

        this.entering = new VBox(20, welcome, thing, enterPort, thing2, enterIP, connect,wait);
        entering.setAlignment(Pos.CENTER);
        startPane = new BorderPane();
        startPane.setStyle("-fx-background-color: honeydew");
        startPane.setPadding(new Insets(70));
        startPane.setCenter(entering);
        startScene = new Scene(startPane, 500,600);

        listItems = new ListView<String>();


        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                clientConnection.theGame.message = "bye";
                clientConnection.theGame.p1score = 0;
                clientConnection.theGame.p2score = 0;
                clientConnection.sendGame();
                try {
                    clientConnection.socketClient.close();
                } catch (IOException e) {

                }
                Platform.exit();
                System.exit(0);
            }
        });
        primaryStage.setScene(startScene);
        primaryStage.show();
    }

    public Scene createClientGui() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(30));
        choices = new VBox(20, r,p,s,l,sp);
        choices.setAlignment(Pos.CENTER);
        VBox top = new VBox(20, pNum, points);
        pane.setStyle("-fx-background-color: lightblue");
        pane.setTop(top);
        pane.setLeft(listItems);
        pane.setCenter(choices);
        pane.setBorder(new Border(new BorderStroke(Color.TEAL, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        return new Scene(pane, 500, 600);
    }
    public Scene endGui(int n) {
        playAgain.setVisible(false);
        quit.setVisible(false);
        String words = "";
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(30));
        if (n == 0)
        {words = "   Waiting for\n  other player...";}
        result = new Label(words);
        result.setFont(new Font("Comic Sans MS", 20));

        if((score1 == 3) || (score2 == 3))
        {gameText = "\nPlayer " + n + " Wins\n  the Game!!!";}
        else {gameText = "";}
        gameWinner = new Label(gameText);
        gameWinner.setFont(new Font("Comic Sans MS", 42));
        options = new HBox(50, playAgain, quit);
        options.setAlignment(Pos.CENTER);
        if ((score1 == 3) || (score2 == 3))
        {playAgain.setVisible(true);
            quit.setVisible(true);}
        bottom = new VBox(30, result, options, gameWinner);
        bottom.setAlignment(Pos.CENTER);
        pane.setStyle("-fx-background-color: lavender");
        pane.setTop(points);
        pane.setCenter(bottom);
        pane.setBorder(new Border(new BorderStroke(Color.TEAL, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        return new Scene(pane, 500, 600);
    }
}
