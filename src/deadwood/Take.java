package deadwood;

import javax.swing.*;

public class Take {
    JLabel takeLabel;
    public Take(){
            
    }
    protected String takeNum;
    protected String x;
    protected String y;
    protected String w;
    protected String h;

    public void printTakeData(){
        System.out.printf("    Take %s, Take Area(x, y, w, h): %s %s %s %s%n", takeNum, x, y, w, h);
    }
}
