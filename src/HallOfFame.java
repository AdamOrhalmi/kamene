import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HallOfFame {
   private List<HighScore> highScores = new ArrayList<>();


    public void addHighScore(String name, int time) {
        highScores.add(new HighScore(name, time));

        highScores.sort(Comparator.comparing(t -> t.getTime()));
    }

    public int getSlowestHSTime() {
        List<HighScore> list1 = highScores.stream().sorted(Comparator.comparing(t -> t.getTime())).collect(Collectors.toList());
        return list1.get(list1.size() - 1).getTime();
    }

    public void printHOF() {
        System.out.println(" HALL OF FAME");
        for (int i = 0; i < highScores.size(); i++) {
            System.out.println(i + ". " + highScores.get(i).getName());
            System.out.println("   ------------> " + highScores.get(i).getTime() + " seconds");
        }
    }
}
