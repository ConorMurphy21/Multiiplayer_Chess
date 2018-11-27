package networking;

import board.Board;
import javafx.application.Platform;
import main.Main;

import java.io.*;
import java.net.Socket;

import static utils.StringJoin.joinWithCommas;

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

    //sends a move packet to the server
    public synchronized void sendMove(int x, int y, int newX, int newY){
        String send = joinWithCommas("04",x,y,newX,newY);
        out.println(send);
    }

    //sends a packet to the server that the client would like to quit
    public synchronized void sendQuit(){
       String send = "05";
       out.println(send);
    }

    //sends a packet ot the server that the client is taking a piece at x,y
    public synchronized void sendTake(int x, int y){
        String send = joinWithCommas("06",x,y);
        out.println(send);
    }

    public synchronized void sendPromote(int x1, int y1, int x2, int y2, char c){
        String line = joinWithCommas("07",x1,y1,x2,y2,c);
        out.println(line);
    }


    /***PROTOCOL FOR PACKETS SENT TO CLIENT***/
    //all packet handling from the server happens here
    @Override
    public void run() {
        try {
            startConnection("localhost");
            System.out.println("waiting for opponent...");

            while(!isInterrupted()){

                String message = in.readLine();
                String[] parts = message.split(",");

                /*
                *   All packets are of the form
                *   packet,param1,param2,...
                *   packet is the code that it should be treated as
                *   all of the parameters needed are split by commas
                 */


                /*
                    param 1: isWhite (boolean), whether the player is white or black
                 */
                switch (parts[0]) {
                /*
                 *  reset packet - receives info the opponent wants to restart or game is ready to start
                 *  param 1: isWhite (boolean), whether the player is white or black
                 */
                    case "00":
                        isWhite = Boolean.parseBoolean(parts[1]);
                        Platform.runLater(() -> Main.ini(isWhite));
                        break;

                    /*
                     * move packet - takes info from the server on where to move a piece (opponents piece)
                     * param 1: x1 (int) x pos of piece to be moved
                     * param 2: y1 (int) y pos of piece to be moved
                     * param 3: x2 (int) x pos of where piece will move to
                     * param 4: y2 (int) y pos of where piece will move to
                     * action:
                     * apply move in clients game
                     */
                    case "01":
                        //parse values of what piece is moving where
                        int[] nums = new int[4];
                        for (int i = 0; i < 4; i++) {
                            nums[i] = Integer.parseInt(parts[i + 1]);
                        }
                        //move that piece
                        Platform.runLater(() -> Board.getInstance().movePieceFromServer(nums[0], nums[1], nums[2], nums[3]));
                        break;

                    /*
                     * disconnect packet - receives info that one of the players has disconnected
                     * param 1: thisPlayer (boolean) whether or not it's this player disconnecting
                     * or the opponent
                     * action:
                     * either close the program because this player wants to quit or,
                     * inform the client that the other player has left the game/let them win
                     */
                    case "02":
                        if (Boolean.parseBoolean(parts[1])) {
                            //say you win the game cuz the other player disconnected
                            System.out.println("other player is disconnecting");
                            interrupt();
                        } else {
                            System.out.println("this player is disconnecting");
                            interrupt();
                        }
                        break;

                    /*
                     * take packet - receives info that a piece should be taken
                     * (this packet is only sent when the taking is occurring in a separate
                     * location than the move) or from pond sliding
                     * param 1: x (int) x pos that needs to be taken
                     * param 2: Y (int) y pos that needs to be taken
                     * action:
                     * apply the taking of this piece on the player's board
                     */
                    case "03":
                        Platform.runLater(() -> Board.getInstance()
                                .takeFromServer(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
                        break;

                    /*
                     * promote packet - receives info that an opponents piece should be promoted
                     */
                    case "04":

                        break;
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
}