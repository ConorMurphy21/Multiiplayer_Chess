package board;

public class Check {
    private static Check ourInstance = new Check();

    public static Check getInstance() {
        return ourInstance;
    }

    private Check() {

    }
}
