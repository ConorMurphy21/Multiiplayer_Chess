package pieces;

public class PieceFactory {


    public static Piece create(int x, int y){
       switch(y){
           case 0:
               switch (x){
                   case 0:
                   case 7:
                       return new Rook(false,x,y);
                   case 1:
                   case 6:
                       return new Knight(false,x,y);
                   case 2:
                   case 5:
                       return new Bishop(false,x,y);
                   case 3:
                       return new Queen(false,x,y);
                   case 4:
                       return new King(false,x,y);
               }
           case 7:
               switch (x){
                   case 0:
                   case 7:
                       return new Rook(true,x,y);
                   case 1:
                   case 6:
                       return new Knight(true,x,y);
                   case 2:
                   case 5:
                       return new Bishop(true,x,y);
                   case 3:
                       return new Queen(true,x,y);
                   case 4:
                       return new King(true,x,y);
               }

           case 1:
               return new Pond(false,x,y);
           case 6:
               return new Pond(true,x,y);

               default:
                   return null;
       }
    }
}
