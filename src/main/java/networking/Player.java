
package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player extends Thread{

    Player opponent;
    Socket socket;
    BufferedReader input;
    PrintWriter output;
    boolean isWhite;

    public Player(Socket socket, boolean isWhite) {

        this.isWhite = isWhite;
        this.socket = socket;
        try {
            input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            //print here what type of packet it is
                        //send reset/set packet, to reset the board as white
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    //return's whether the move requested by the player is valid
    private boolean validMove(int x, int y, int newX, int newY){

        //for now no validation
        return true;
    }

                //this is called by the opponent
    private void sendMove(int[] points){
        output.println("01,"+isWhite+","+points[0]+","+points[1]+","+points[2]+","+points[3]);
    }

    private boolean correctPlayer(String whiteStr){
        boolean white = Boolean.parseBoolean(whiteStr);
        return (white == isWhite);
    }

    public void run() {
        try {

            output.println("00,"+isWhite);

            // Repeatedly get commands from the client and process them.
            while (true) {
                String message = input.readLine();
                String[] parts = message.split(",");

                //this is only needed because I'm testing this on the same computer and need to distinguish
                //between players besides ips
                if(!correctPlayer(parts[1]))continue;
                    //move packet
                if(parts[0].equals("01")){
                    int[] nums = new int[4];
                    for(int i = 0; i < 4; i++){
                        nums[i] = Integer.parseInt(parts[i+2]);
                    }
                    opponent.sendMove(nums);
                        //quit packet
                }else if(message.equals("02")){


                }
            }
        } catch (IOException e) {
            System.out.println("Player died: " + e);
        } finally {
            try {socket.close();} catch (IOException e) {}
        }
    }
}
