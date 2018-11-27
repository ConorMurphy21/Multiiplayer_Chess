package utils;

public class StringJoin {


    public static String joinWithCommas(Object...args){
        String line = "";
        for(int i = 0; i < args.length; i++){
            line += args[i] + ",";
        }
        return line;
    }
}
