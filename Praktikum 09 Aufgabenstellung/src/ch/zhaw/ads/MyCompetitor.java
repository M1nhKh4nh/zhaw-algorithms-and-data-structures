package ch.zhaw.ads;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyCompetitor implements Comparable<MyCompetitor> {
    private final String name;
    private final String time;
    private int rank;

    public MyCompetitor(int rank, String name, String time)  {
        this.rank = rank;
        this.name = name;
        this.time = time;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    private static long parseTime(String s)  {
        try {
            DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date date = sdf.parse(s);
            return date.getTime();
        } catch (Exception e) {System.err.println(e);}
        return 0;
    }

    private static String timeToString(int time) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(new Date(time));
    }

    public String toString() {
        return ""+ rank + " "+name+" "+time;
    }

    @Override
    public int compareTo(MyCompetitor o) {
        return this.hashCode() - o.hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 13 + rank;
        hash = hash * 17 + name.hashCode();
        return hash;
    }

    @Override
    public boolean equals (Object o) {
        if(this == o){
            return true;
        }

        if(o == null || (this.getClass() != o.getClass())){
            return false;
        }

        MyCompetitor competitor = (MyCompetitor)o;
        return this.name.equals(competitor.name) && this.rank == competitor.rank;
    }
}
