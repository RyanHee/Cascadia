import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class StartPanel extends JPanel implements ActionListener {
    //private int[]xlst;
    //private int[]ylst;

    private HashMap<String, BufferedImage[]>animalTokenMap;

    private int angle, numSelectedTile, numSelectedAnimal;
    private static BufferedImage background, buttimg, backPlay, buttImgPlay;
    private Node nodeSelected;
    private HexButton rotate;

    private BufferedImage[] frames;
    int i=1;
    boolean cont= false;

    //private HexButton hexButton;
    public StartPanel() throws FileNotFoundException {
        
        try{
            frames=new BufferedImage[66];

            //img = ImageIO.read(Panel.class.getResource("tile.png"));
            //img1 = ImageIO.read(Panel.class.getResource("tile1.png"));
            background=ImageIO.read(new File("img/tileOutline.png"));
            
            String b;
            for (int i=1;i<67;i++){
            	if (i<10) {
            		b="img/framez/000"+i+".jpg";
            	}
            	else {
            		b="img/framez/00"+i+".jpg";
            	}

                frames[i-1]=ImageIO.read(new File(b));

            }


        }
        catch (Exception e){
            System.out.println(1231);
        }
        

       
        setBackground(Color.WHITE);
        setBackground(new Color(3, 107, 156));
    }


    public void paint(Graphics g){
    	if(cont==false){
	        g.drawImage(frames[i], 0, 0, getWidth(), getHeight(), null);

    	}
    	else {
    	if(i<66) {
	        super.paint(g);
	        g.setColor(new Color(0,80,117));
	        g.drawImage(frames[i], 0, 0, getWidth(), getHeight(), null);
	        wait(20);
	        System.out.println(i);
	        i++;	
	        repaint();
	    	}
	    	else {
	            g.drawImage(frames[65], 0, 0, getWidth(), getHeight(), null);
	    	}
    	}
    	
        
        
    }
    public static void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}



 
}