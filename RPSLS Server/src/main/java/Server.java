import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Server{
    public
        GameInfo theGame = new GameInfo();
        int counter = 1;
        ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
        TheServer server;
        int port;
        int left = 1;
        String p1Hold, p2Hold;
    private Consumer<Serializable> callback;

	Server(Consumer<Serializable> call, int p){
		callback = call;
		server = new TheServer();
		server.start();
		port = p;
		p1Hold = "";
		p2Hold = "";
	}

	public class TheServer extends Thread{

		public void run() {
			try(ServerSocket mysocket = new ServerSocket(port);){
				System.out.println("Server is waiting for a client!");

				while(true) {

					ClientThread c = new ClientThread(mysocket.accept(), counter);
					callback.accept("client has connected to server: " + "client #" + counter);
					clients.add(c);
					c.start();
					counter++;
				}
			}//end of try
			catch(Exception e) {
				callback.accept("Server socket did not launch");
			}
		}//end of while
	}

	class ClientThread extends Thread{

		Socket connection;
		int count;
		ObjectInputStream in;
		ObjectOutputStream out;

		ClientThread(Socket s, int count){
			this.connection = s;
			this.count = counter;
		}

		public void updateClients(String message) {
            for(int i = 0; i < clients.size(); i++) {
                theGame.message = message;
                ClientThread t = clients.get(i);
                try {
                    t.out.writeObject(theGame);
                    t.out.reset();
                }
                catch(Exception e) {}
            }
		}

        public void sendGame() {
            for(int i = 0; i < clients.size(); i++) {
                ClientThread t = clients.get(i);
                try {
                    t.out.writeObject(theGame);
                    t.out.reset();
                }
                catch(Exception e) {}
            }
        }

		public void run(){

			try {
				in = new ObjectInputStream(connection.getInputStream());
				out = new ObjectOutputStream(connection.getOutputStream());
				connection.setTcpNoDelay(true);
			}
			catch(Exception e) {
				System.out.println("Streams not open");
			}

			updateClients("new client on server: client #"+count);
			if(clients.size() < 2) {
                updateClients("waiting for second player");
            }

			while(true) {
                try {
                    theGame = (GameInfo) in.readObject();
                    if(theGame.message.equals("go")) {
                        callback.accept("client: " + count + " played: " + theGame.choice);
                        theGame.update(count);
                        theGame.message = "";
                        sendGame();
                    }
                    if(theGame.message.equals("again")) {
                        callback.accept("client: " + count + " is playing again");
                        theGame.message = "";
                        theGame.p2score = 0;
                        theGame.p1score = 0;
                        sendGame();
                    }
                    if(theGame.bothPlayed()){
                        p1Hold = theGame.p1plays;
                        p2Hold = theGame.p2plays;
                        int win = theGame.roundWinner();
                        if(win==0){
                            theGame.message = "it's a draw!";
                            callback.accept(theGame.message);
                        }
                        else if(win==1){
                            theGame.message = "Player 1 wins the round!";
                            callback.accept(theGame.message);
                        }
                        else if(win==2){
                            theGame.message = "Player 2 wins the round!";
                            callback.accept(theGame.message);
                        }
                        sendGame();
                    }
                }
                catch(Exception e) {
                    if(theGame.message.equals("bye")) {
                        callback.accept("OOPPs...Something wrong with the socket from client: " + count + "....closing down!");
                        left++;
                        updateClients("waiting for second player");
                        theGame = new GameInfo();
                        clients.remove(this);
                        sendGame();
                        break;
                    }
                }
			}
		}//end of run
	}//end of client thread
}