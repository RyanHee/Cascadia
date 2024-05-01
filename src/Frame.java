
import javax.swing.*;

import java.awt.CardLayout;
import java.io.FileNotFoundException;

public class Frame {
    private JPanel contentPane;
    private Menu3D panel1;
    private Panel panel2;
    private ScoringPanel panel3;//added end panel
    public void runner(){
        JFrame frame = new JFrame("CASCADIA");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setSize(1600, 900);        
        JPanel contentPane = new JPanel();
        panel1 = new Menu3D(contentPane);
        panel2 = new Panel(contentPane, Menu3D.playerCount);
        panel3 = new ScoringPanel(contentPane);//added end panel
        contentPane.setLayout(new CardLayout());
        frame.setVisible(true);
        contentPane.add(panel1, "Panel 1"); 
        contentPane.add(panel2, "Panel 2");
        contentPane.add(panel3, "Panel 3");//added end panel
        frame.setContentPane(contentPane);
        
    }
    public static void main (String[] args) {
        new Frame().runner();

    }
}
