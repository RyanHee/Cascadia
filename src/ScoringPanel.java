import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.*;

//tentaive may not use
public class ScoringPanel extends JPanel{
	private static BufferedImage scoreCard;
	public ScoringPanel() {
		
	}
	
	public static void infoBox() {
		try {
			scoreCard = ImageIO.read(new File("img/CascadiaCards.jpg"));
		}
		catch(Exception e) {
			
		}
	
		ImageIcon card = new ImageIcon(scoreCard);
		JOptionPane.showMessageDialog(null, "", "Scoring Cards", JOptionPane.INFORMATION_MESSAGE, card);
	}
}
