import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

//tentaive may not use
public class ScoringPanel extends JPanel{
	private BufferedImage bCard, eCard, fCard, hCard, sCard;
	private BufferedImage[] cards = {bCard, eCard, fCard, hCard, sCard};
	public ScoringPanel() {
		try {
			bCard = ImageIO.read(new File("img/scoring-goals/bear-large.jpg"));
			eCard = ImageIO.read(new File("img/scoring-goals/elk-large.jpg"));
			fCard = ImageIO.read(new File("img/scoring-goals/fox-large.jpg"));
			hCard = ImageIO.read(new File("img/scoring-goals/hawk-large.jpg"));
			sCard = ImageIO.read(new File("img/scoring-goals/salmon-large.jpg"));
		}
		catch(Exception e) {
			
		}
	}
	public void paint(Graphics g) {
		for(int i =0; i<cards.length; i++) {
			g.drawImage(cards[i], 50+i*70, 50, 50, 50, null);
		}
		repaint();
	}
}
