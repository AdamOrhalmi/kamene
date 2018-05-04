import java.util.Comparator;

public class HighScore {
    private String name;
    private int time;
    public HighScore(String name, int time){
        this.name=(name);
        this.time=(time);
    }

    public int getTime() {
        return time;
    }

    public String getName() {
        return name;
    }


    public boolean equals(Object o){
        if(o instanceof HighScore){
          return(((HighScore) o).getName().equals(this.getName())&&((HighScore) o).getTime() == this.getTime());
        }else return false;
    }
    public int hashCode(){
        return time;
    }
}
