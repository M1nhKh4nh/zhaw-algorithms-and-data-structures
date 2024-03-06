package ch.zhaw.ads;

public class SnowflakeServer implements CommandExecutor{
    Turtle turtle = null;
    @Override
    public String execute(String command) {
        turtle = new Turtle(0.1, 0.7);
        snowflake(Integer.parseInt(command), 0.8);
        turtle.turn(-120);
        snowflake(Integer.parseInt(command), 0.8);
        turtle.turn(-120);
        snowflake(Integer.parseInt(command), 0.8);
        return turtle.getTrace();
    }

    private void snowflake(int stufe, double dist) {
        if(stufe == 0) {
            turtle.move(dist);
        } else {
            stufe--;
            dist = dist/3;
            snowflake(stufe, dist);
            turtle.turn(60);
            snowflake(stufe, dist);
            turtle.turn(-120);
            snowflake(stufe, dist);
            turtle.turn(60);
            snowflake(stufe, dist);
        }
    }

}
