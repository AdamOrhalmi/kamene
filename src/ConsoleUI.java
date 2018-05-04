import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI implements Serializable {

    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private Field field;
    private String regex = "([wasdc]|up|down|left|right|save)";
    private static final String SAVE_FILE = "savedgame.bin";
    private HallOfFame hof = new HallOfFame();
    private int timeGameEnded;

    /**
     * Reads line of text from the reader.
     *
     * @return line as a string
     */
    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public void newGame(Field field) {

        hof.addHighScore("some slow dude ", 990);
        hof.addHighScore("another slow dude ", 995);
        hof.addHighScore("crazy slow dude ", 999);
        hof.addHighScore("Chuck norris", -1);
        this.field = field;

        System.out.println("Welcome to Kamene! do you wish to load your previous game? (y/n)");
        String command = this.readLine().toLowerCase();
        if (command.equals("y")) {
            load();
        }

        do {
            update();
            processInput();
        } while (!field.isSolved());
        update();
        timeGameEnded = Kamene.getPlayingSeconds();
        System.out.println("congratulations, you have solved the game in " + timeGameEnded + " seconds!");
        System.out.println("write your name:");
        String name = readLine();
        System.out.println(name);
        hof.addHighScore(name, timeGameEnded);
        hof.printHOF();
    }

    private void update() {
        System.out.println("Time passed: " + Kamene.getPlayingSeconds());
        for (int row = 0; row < field.getRows(); row++) {
            for (int column = 0; column < field.getColumns(); column++) {
                Stone s = field.getStone(row, column);
                if (s.getValue() != field.getStonesNr()) {

                    System.out.print(s.getValue() + " ");
                    if (s.getValue() < 10) {
                        System.out.print(" ");
                    }
                } else {
                    System.out.print("[] ");
                }
            }
            System.out.println();
        }
    }

    private void processInput() {
        String command = this.readLine().toLowerCase();

        try {
            handleInput(command);
        } catch (WrongFormatException e) {

            System.err.println(e.getMessage());
            return;
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(command);
        boolean moved = false;
        if (matcher.matches()) {
            switch (command) {
                case "up":
                case "w":
                    moved = field.switchNullStoneWith(-1, 0);
                    break;
                case "down":
                case "s":
                    moved = field.switchNullStoneWith(1, 0);
                    break;
                case "left":
                case "a":
                    moved = field.switchNullStoneWith(0, -1);
                    break;
                case "right":
                case "d":
                    moved = field.switchNullStoneWith(0, 1);
                    break;
                case "c":
                    field.solve();
                    return;
                case "save":
                    save();
                    return;
            }
            if (!moved)
                System.err.println("can't move there!");
        }
    }

    private void handleInput(String input) throws WrongFormatException {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (!(matcher.matches())) {
            throw new WrongFormatException("Wrong command format");
        }

    }

    private void save() {

        try {
            FileOutputStream os = new FileOutputStream(SAVE_FILE);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(this.field);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            FileInputStream is = new FileInputStream(SAVE_FILE);
            ObjectInputStream ois = new ObjectInputStream(is);
            Field newField = (Field) ois.readObject();
            this.field = newField;

        } catch (Exception e) {
            System.err.println(e.getMessage());

        }

    }


}




