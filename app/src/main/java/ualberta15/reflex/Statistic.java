package ualberta15.reflex;

/**
 * Created by Ben on 03/10/2015.
 */
public class Statistic implements Comparable<Statistic> {
    private String name;
    private String type;
    private int reactionTime;

    public Statistic(String name, String type, int reactionTime) {
        this.name = name;
        this.type = type;
        this.reactionTime = reactionTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getReactionTime() {
        return reactionTime;
    }

    public void setReactionTime(int reactionTime) {
        this.reactionTime = reactionTime;
    }

    //Based on "http://www.mkyong.com/java/java-object-sorting-example-comparable-and-comparator/"
    public int compareTo(Statistic compareStat) {
        int compareReactionTime = ((Statistic) compareStat).getReactionTime();
        return this.reactionTime - compareReactionTime;
    }
}