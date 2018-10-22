package pieces;


import pieces.graphics.PieceNode;

class Knight extends PieceBase {
    Knight(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        if(isWhite) {
            node = new PieceNode("Chess_klt60.png");
        }else{
            node = new PieceNode("Chess_kdt60.png");
        }
    }
}
