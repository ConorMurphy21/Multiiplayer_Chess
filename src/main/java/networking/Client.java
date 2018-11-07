package networking;

import javafx.application.Platform;
import main.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread {

    private static final Client ourInstance = new Client();

    private boolean isWhite;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private static final int PORT = 6666;

    public static Client getInstance(){
        return ourInstance;
    }


    private void startConnection(String ip) throws IOException {
        clientSocket = new Socket(ip, PORT);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    private boolean correctPlayer(String whiteStr){
        boolean white = Boolean.parseBoolean(whiteStr);
        return (white == isWhite);
    }
    //all of the recieving happens from here
    @Override
    public void run() {
        try {
            startConnection("localhost");
            System.out.println("waiting for opponent...");

            while(true){
                String message = in.readLine();
                String[] parts = message.split(",");


                //reset packet (will suffice for starting the game as well)
                if(parts[0].equals("00")){

                    isWhite = Boolean.parseBoolean(parts[1]);
                    Platform.runLater(()->Main.ini(isWhite));

                    //move packet
                }else if(parts[0].equals("01")){
                    //only necessary for testing
                    if(!correctPlayer(parts[1]))continue;

                    //disconnect packet
                }else if(parts[0].equals("02")){

                    //only necessary for testing
                    if(!correctPlayer(parts[1]))continue;

                }

            }

            //will have a stop condition eventually
            //stopConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}