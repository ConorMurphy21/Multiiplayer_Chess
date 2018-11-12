package networking;

import board.Board;
import javafx.application.Platform;
import main.Main;

import java.io.*;
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

    private Client(){}

    private void startConnection(String ip) throws IOException {
        this.clientSocket = new Socket(ip, PORT);
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.out = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    public synchronized void sendMove(int x, int y, int newX, int newY){
        if(out == null) System.out.println("It's the output");
        String send = "04,"+x+","+y+","+newX+","+newY;
        out.println(send);
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


                    int[] nums = new int[4];
                    for(int i = 0; i < 4; i++){
                        nums[i] = Integer.parseInt(parts[i+1]);
                    }

                    Platform.runLater(()->Board.getInstance().movePieceFromServer(nums[0],nums[1],nums[2],nums[3]));


                    //disconnect packet
                }else if(parts[0].equals("02")){


                }

            }

            //will have a stop condition eventually
            //stopConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized PrintWriter getOut(){
        return out;
    }

}