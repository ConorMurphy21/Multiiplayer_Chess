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
        String send = "04,"+x+","+y+","+newX+","+newY;
        out.println(send);
    }

    public synchronized void sendQuit(){
       String send = "05";
       out.println(send);
    }

    public synchronized void sendTake(int x, int y){
        String send = "06,"+x+","+y;
        out.println(send);
    }

    //all of the recieving happens from here
    @Override
    public void run() {
        try {
            startConnection("localhost");
            System.out.println("waiting for opponent...");


            while(!isInterrupted()){

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
                    if(Boolean.parseBoolean(parts[1])){

                        //say you win the game cuz the other player disconnected

                        System.out.println("other player is disconnecting");
                        interrupt();
                    }else{
                        System.out.println("this player is disconnecting");
                        interrupt();
                    }
                    //take packet
                }else if(parts[0].equals("03")){
                    Platform.runLater(()->Board.getInstance()
                            .takeFromServer(Integer.parseInt(parts[1]),Integer.parseInt(parts[2])));
                }

            }

            //will have a stop condition eventually
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