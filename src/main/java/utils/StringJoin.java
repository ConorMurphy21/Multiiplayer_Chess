package utils;

public class StringJoin {


    public static String joinWithCommas(Object...args){
        String line = "";
        for (Object arg : args) {
            line += arg + ",";
        }
        return line;
    }
}
