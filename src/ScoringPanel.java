import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.*;

public class ScoringPanel extends JPanel{
	private BufferedImage testPanel;
	//private Game game;
	public ScoringPanel(/*Game game*/) {
		try {
			testPanel = ImageIO.read(new File("img/test cascadia.png"));
		}
		catch(Exception e) {
			
		}
	}
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(testPanel, 0, 0, getWidth(), getHeight(), null);
		g.setFont(new Font("Arial", Font.PLAIN, 13));
		//animal scoring
		for(int q =0; q<6; q++) {
			for(int i = 0; i<4; i++) {
				if(q!=5) {//each player, each animal score
					g.drawString("AScore", getWidth()*(33+32*i)/256, getHeight()*(2+q)/15);
				}
				else {//each player, total animal score
					g.drawString("Animal Score", getWidth()*(33+32*i)/256, getHeight()*(2+q)/15);
				}
			}
		}
		//habitat scoring
		for(int q =0; q<6; q++) {
			for(int i = 0; i<4; i++) {
				if(q!=5) {//each player, each habitat score
					g.drawString("HScore", getWidth()*(27+32*i)/256, getHeight()*(57+8*q)/104);
				}
				else {//each player, total habitat score
					g.drawString("Habitat Score",  getWidth()*(27+32*i)/256, getHeight()*(29+4*q)/52);
				}
			}
		}
		//habitat bonus
		for(int q =0; q<6; q++) {
			for(int i = 0; i<4; i++) {
				g.drawString("HBonus", getWidth()*(43+32*i)/256, getHeight()*(57+8*q)/104);
			}
		}
		//nature token and final score
		for(int q =0; q<2; q++) {
			for(int i = 0; i<4; i++) {
				g.drawString("Final", getWidth()*(195+12*i)/256, getHeight()*(8+4*q)/30);
			}
		}
		
	}
}
