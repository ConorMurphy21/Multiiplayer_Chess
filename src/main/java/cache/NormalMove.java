package cache;

import pieces.Piece;

public class NormalMove extends Move {
    private NormalMove(int fromX, int fromY, int toX, int toY) {
        super(fromX, fromY, toX, toY);
    }

    private NormalMove(Piece piece, int toX, int toY) {
        super(piece, toX, toY);
    }

    @Override
    public char getChar() {
        return 'n';
    }

    @Override
    public String asPacket() {
        return super.asPacket();
    }

    public static Move fromPacket(String[] parts){
        int[] terms = Move.intTerms(parts,4);
        return new NormalMove(terms[0],terms[1],terms[2],terms[3]);
    }
}

