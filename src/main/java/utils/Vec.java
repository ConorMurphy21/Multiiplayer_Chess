package utils;

public class Vec {
    private int x, y;

    public Vec(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean equals(Object obj){
        if(obj == null)return false;
        if(!(obj instanceof Vec))return false;
        Vec vec = (Vec) obj;
        return (vec.x == x && vec.y == y);
    }
}
