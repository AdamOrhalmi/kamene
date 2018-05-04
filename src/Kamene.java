import java.io.Serializable;

public class Kamene implements Serializable {

  private static long startMillis;
    static ConsoleUI cui;
    public static void main(String[] args) {
        cui = new ConsoleUI();
        startMillis= System.currentTimeMillis();
        Field field = new Field();
        cui.newGame(field);
    }

    public static int getPlayingSeconds(){
        return (int)(System.currentTimeMillis() - startMillis)/1000;
    }
}
