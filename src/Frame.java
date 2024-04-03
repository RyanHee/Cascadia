import javax.swing.*;

public class Frame extends JFrame {
    public Frame(String s){
        super(s);
        Panel panel = new Panel();
        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1600, 900);
        setVisible(true);
    }
}
