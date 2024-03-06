package ch.zhaw.ads;

public class KgvServer implements CommandExecutor {
    public int kgv(int a, int b) {
        return a * b / ggt(a, b);
    }
    
    private int ggt(int a, int b) {
        if (b == 0) return a;
        return ggt(b, a % b);
    }

    public String execute(String s) {
        String[] numbers = s.split("[ ,]+");
        int a = Integer.parseInt(numbers[0]);
        int b = Integer.parseInt(numbers[1]);
        return Integer.toString(kgv(a,b));
    }
}
