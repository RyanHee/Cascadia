import javax.swing.*;
import java.io.FileNotFoundException;

public class Frame extends JFrame {
    public Frame(String s) throws FileNotFoundException {
        super(s);
        Panel panel = new Panel();
        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1600, 900);
        setVisible(true);
    }
}
