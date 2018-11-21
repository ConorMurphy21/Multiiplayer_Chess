
package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class Player extends Thread{

    private Player opponent;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private boolean isWhite;

    Player(Socket socket, boolean isWhite) {

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

    void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    //return's whether the move requested by the player is valid
    private boolean validMove(int x, int y, int newX, int newY){

        //for now no validation
        return true;
    }

                //this is called by the opponent
    private synchronized void sendMove(int[] points){
        output.println("01,"+points[0]+","+points[1]+","+points[2]+","+points[3]);
    }

    private synchronized void sendDis(){
        output.println("02,"+true);
        interrupt();
    }

    private synchronized void sendTake(String args){
        output.println("03,"+args);
    }

    public void run() {
        try {

            output.println("00,"+isWhite);

            // Repeatedly get commands from the client and process them.
            while (!Thread.interrupted()) {
                String message = input.readLine();
                String[] parts = message.split(",");

                    //move packet
                if(parts[0].equals("04")){
                    int[] nums = new int[4];
                    for(int i = 0; i < 4; i++){
                        nums[i] = Integer.parseInt(parts[i+1]);
                    }
                    opponent.sendMove(nums);

                        //quit packet
                }else if(parts[0].equals("05")){
                    output.println("02,"+false);
                    opponent.sendDis();
                    interrupt();
                }else if(parts[0].equals("06")){
                    opponent.sendTake(parts[1] +","+ parts[2]);
                }
            }
        } catch (IOException e) {
            System.out.println("Player died: " + e);
        } finally {
            try {socket.close();} catch (IOException e) {}
        }
    }
}
