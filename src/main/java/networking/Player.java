
package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static utils.StringJoin.joinWithCommas;

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

    //sends a move packet to client by the protocol in client
    private synchronized void sendMove(int[] points){
        String send = joinWithCommas("01",points[0],points[1],points[2],points[3]);
        output.println(send);
    }

    //sends disconnect packet to client by the protocol in client
    private synchronized void sendDis(boolean otherPlayersClient){
        String send = joinWithCommas("02",otherPlayersClient);
        output.println(send);
    }

    //sends valid take packet to client by the protocol in client
    private synchronized void sendTake(String args){
        String send = joinWithCommas("03",args);
        output.println(send);
    }

    //sends valid ini packet to client by the protocol in client
    private synchronized void sendIniPacket(){
        String send = joinWithCommas("00",isWhite);
        output.println(send);
    }

    /***Protocol for how server handles packets from client***/
    public void run() {
        try {

            //if the player has been started, then both players are ready to play,
            //and ini packet can be sent
            sendIniPacket();

            // Repeatedly get commands from the client and process them.
            while (!Thread.interrupted()) {
                String message = input.readLine();
                String[] parts = message.split(",");

                switch (parts[0]) {
                    /*
                     * move packet - the client would like to move their player
                     * param 1: x1 (int) x pos of piece to be moved
                     * param 2: y1 (int) y pos of piece to be moved
                     * param 3: x2 (int) x pos of where piece will move to
                     * param 4: y2 (int) y pos of where piece will move to
                     * action:
                     * send a move packet to the opponent so the opponent will know our client has moved
                     */
                    case "04":
                        int[] nums = new int[4];
                        for (int i = 0; i < 4; i++) {
                            nums[i] = Integer.parseInt(parts[i + 1]);
                        }
                        opponent.sendMove(nums);
                        break;

                        /*
                         * quit packet - the client would like to quit the game
                         * action:
                         * send disconnect packet to both clients
                         */
                    case "05":
                        //quit packet
                        opponent.sendDis(true);
                        sendDis(false);
                        interrupt();
                        break;

                        /*
                         * take packet - the client would like to take an opponents piece
                         * param 1: x (int) - x pos of piece to be taken
                         * param 2: y (int) - y pos of piece to be taken
                         * action:
                         * send take packet to opponent so the opponent know their piece has been taken
                         */
                    case "06":
                        opponent.sendTake(parts[1] + "," + parts[2]);
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Player died: " + e);
        } finally {
            try {socket.close();} catch (IOException e) {}
        }
    }
}
