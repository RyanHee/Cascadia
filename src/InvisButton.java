import javax.swing.*;


public class InvisButton extends JButton {

    public InvisButton (String s){
        super(s);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    public void showButton(){
        setOpaque(true);
        setContentAreaFilled(true);
        setBorderPainted(true);
    }

    public void hideButton(){
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }
}
