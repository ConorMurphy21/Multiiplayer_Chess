package pieces;

import pieces.graphics.PieceNode;

class Pond extends PieceBase {
    Pond(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        if(isWhite) {
            node = new PieceNode("Chess_plt60.png");
        }else{
            node = new PieceNode("Chess_pdt60.png");
        }

    }

}
