package highlighters;

import pieces.Piece;
import utils.Vec;

public interface Highlighter {

    void highlight(Piece p);

    boolean isStraight();

    boolean isDiagonal();

    //returns whether a hl can be stopped ie rook, queen, bishop
    //whereas a rook cannot be blocked
    boolean isStoppable();

    boolean canAttack(Piece p, Vec agr);

    boolean canAttack(Piece p, Piece agr);

    boolean canAttack(Piece p, int x, int y);

    boolean canMove(Piece p, int x, int y);

}
