import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.*;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.*;

//fix animation part of code
public class ScoringPanel extends JPanel implements ActionListener {
	private BufferedImage testPanel, backimg, skipimg;
	private JPanel p;//added end panel
	private int prog, waitnum;
	private boolean hover=false;
	private static Game game;
	private HashMap<Integer, String> inttostring;
	private JButton back, skip;
	Rectangle r;
	public ScoringPanel(JPanel panel, Game g) {
		p = panel;//added end panel
		prog = 0;
		waitnum=50;
		game = g;
		inttostring =new HashMap<>();
		inttostring.put(0, "B");
		inttostring.put(1, "E");
		inttostring.put(2, "S");
		inttostring.put(3, "H");
		inttostring.put(4, "F");
		inttostring.put(6, "M");
		inttostring.put(7, "F");
		inttostring.put(8, "D");
		inttostring.put(9, "S");
		inttostring.put(10, "L");
		inttostring.put(11, "M");
		inttostring.put(12, "F");
		inttostring.put(13, "D");
		inttostring.put(14, "S");
		inttostring.put(15, "L");

		//back = new InvisButton("BACK");
		//back.addActionListener(this);

		try {
			skipimg = ImageIO.read(getClass().getResource("img/skip.png"));
			backimg = ImageIO.read(getClass().getResource("img/GO BACK.png"));
			testPanel = ImageIO.read(getClass().getResource("img/EndPanelUI.png"));
		}
		catch(Exception e) {
			
		}


	}
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g2.drawImage(testPanel, 0, 0, getWidth(), getHeight(), null);
		g2.setFont(new Font("Arial", Font.BOLD, 30));
		//add logic to get everyone's scores

		int cnt = 0;

		for (int i=0;i<game.getPlayerNum();i++){
			g2.drawImage(game.getPlayerList()[i].getPfp(), getWidth()*40/256-25+getWidth()*149*i/1257, getHeight()*3/104, 50, 50, null);
			g2.drawString("("+(i+1)+")", getWidth()*40/256-25+getWidth()*149*i/1257+58, getHeight()*3/104+35);
			g2.drawImage(game.getPlayerList()[i].getPfp(), getWidth()*(198+12*i)/256-25, getHeight()*5/104, 50, 50, null);
		}

		int i = 0;
		while(prog>=(cnt*game.getPlayerNum()+i)) {
			//System.out.println(i);
			Player p = game.getPlayerList()[i];
			HashMap<String, Integer>animalmp=p.getAnimalmp();
			HashMap<String, Integer>landmp=p.getLandmp();
			HashMap<String, Integer>bonusmp=p.getBonusmp();



			if(cnt >= 0 && cnt < 5) {//each player, each animal score
				g2.drawString(String.valueOf(animalmp.get(inttostring.get(cnt))), getWidth()*40/256+getWidth()*149*i/1257, getHeight()*14/104+getHeight()*45*(cnt)/688);
			}
			else if(cnt == 5) {//each player, total animal score
				g2.drawString(String.valueOf(p.getAnimalScore()), getWidth()*40/256+getWidth()*149*i/1257, getHeight()*14/104+getHeight()*45*(cnt)/688);
			}
			else if(cnt >= 6 && cnt < 11) {//each player, each habitat score
				g2.drawString(landmp.get(inttostring.get(cnt)).toString(), getWidth()*33/256+getWidth()*149*i/1257, getHeight()*57/104+getHeight()*55*(cnt-6)/688);
			}
			else if(cnt >= 11 && cnt < 16) {//each player, habitat bonus score
				g2.drawString(bonusmp.get(inttostring.get(cnt)).toString(), getWidth()*47/256+getWidth()*149*i/1257, getHeight()*57/104+getHeight()*55*(cnt-11)/688);
			}
			else if(cnt == 16){//each player, total habitat score
				g2.drawString(String.valueOf(p.getLandScore()),  getWidth()*40/256+getWidth()*149*i/1257, getHeight()*57/104+getHeight()*55*(cnt-11)/688);
			}
			else if(cnt == 17) {//nature token
				g2.drawString(String.valueOf(p.getNumTokens()), getWidth()*(198+12*i)/256, getHeight()*14/104+getHeight()*85/688);
			}
			else if (cnt == 18){//final score
				g2.drawString(String.valueOf(p.getScore()), getWidth()*(198+12*i)/256, getHeight()*14/104+getHeight()*193/688);
			}
			i++;
			if(i == game.getPlayerNum()/*number of players in game*/) {
				i=0;
				cnt++;
			}
		}
		prog++;
		if(prog<19*game.getPlayerNum()+1 /*19*number of players in game +1*/) { // 19 rows*4players = 76
			wait(waitnum);
			repaint();
		}
		else{
			g2.drawImage(backimg, getWidth()*894/1257, getHeight()/2, getWidth()*363/1257, getHeight()/4, null);
			back = new JButton(new ImageIcon(backimg.getScaledInstance(getWidth()*363/1257, getHeight()/4, Image.SCALE_SMOOTH)));
			back.addActionListener(this);
			add(back);
			back.setBounds(getWidth()*894/1257, getHeight()/2, getWidth()*363/1257, getHeight()/4);
			int max = 0;
			String first = "";
			for(int q =0; q<game.getPlayerList().length; q++) {
				if(game.getPlayerList()[q].getScore() >= max) {
					max = game.getPlayerList()[q].getScore();
					if(!first.equals("")) {
						first = first+" & "+(Integer.toString(q+1));
					}
					else {
						first = (Integer.toString(q+1));
					}
				}
			}
			g.setFont(new Font("Arial", Font.BOLD, 30));
			if(first.length() > 1) {
				g.drawString("WINNER: Players "+first, getWidth()*29/40, getHeight() *7/8);//add winner
			}
			else {
				g.drawString("WINNER: Player "+first, getWidth()*29/40, getHeight() *7/8);
			}
		}
	}
	
	public void wait(int x){
        try{
            Thread.sleep(x);
        }  
        catch (Exception E){

        }
    }
	
	public static void updateGame(Game g) {
		game = g;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(back)){
			CardLayout cl = (CardLayout) p.getLayout();
			Constants.stop=true;
			cl.show(p, "gamePanel");
		}


	}





}
