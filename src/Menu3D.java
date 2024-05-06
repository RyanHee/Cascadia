import java.awt.AlphaComposite;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import test.EventMenu;
import java.lang.ClassLoader;

public class Menu3D extends JPanel implements MouseListener {


    private final List<EventMenu> events = new ArrayList<>();
    private final List<Menu3dItem> items = new ArrayList<>();
    private int menuHeight = 120;//thickness y
    private int shadowSize = 15;
    private int left = 60;//height
    private float angle = 180f;
    private int pressedIndex = -1;
    private BufferedImage title;
    private BufferedImage imgShade;
    public static int playerCount=4;
    int v= -1;
    String b;
    int fram =0;
    int Flipfram =0;
    boolean pressedBut=false;
    private BufferedImage[] Startframes;
    private BufferedImage[] frames;
    Rectangle2D rect;

    boolean paint;
    public static int mrect;
    BufferedImage buttonPlayer, meme, credit;
    private JPanel contentPane;
    boolean runAnim= false;



    public Menu3D(JPanel panel) {
        contentPane = panel;

        Startframes=new BufferedImage[13];
        frames=new BufferedImage[66];
        rect = new Rectangle2D.Double(2, 3, 4, 4);
        paint = true;
        mrect = 50;



        init();
        initAnimator();
        //System.out.println(System.getProperty("java.class.path"));





        addMouseListener(this);



        try {
            for (int i=1;i<13;i++){
                if(i<10) {
                    b="img/StartFramz/ezgif-frame-00"+i+".png";
                }
                else {
                    b="img/StartFramz/ezgif-frame-0"+i+".png";

                }
                //System.out.println(b);
                Startframes[i-1]=ImageIO.read(getClass().getResource(b));

            }
            for (int i=1;i<66;i++){
                if (i<10) {
                    b="img/framez/000"+i+".jpg";
                }
                else {
                    b="img/framez/00"+i+".jpg";
                }

                frames[i-1]=ImageIO.read(getClass().getResource(b));

            }

        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("images for board frames f-ed");
        }

        try {
            credit=ImageIO.read(getClass().getResource("img/title-endGame.png"));
            meme=ImageIO.read(getClass().getResource("img/meme.jpg"));

            buttonPlayer = ImageIO.read(getClass().getResource("img/Generalimgs/Blue-Button-PNG-File.png"));
            imgShade = ImageIO.read(getClass().getResource("img/Generalimgs/ezgif-frame-003ddd.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
    }

    private void initAnimator() {
        MouseAdapter mouse = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = getOverIndex(e.getPoint());
                if (index != pressedIndex) {
                    pressedIndex = index;
                    //System.out.println("pressedIndex: "+pressedIndex);
                    if (pressedIndex != -1) {
                        items.get(pressedIndex).getAnimator().show();
                        hideMenu(pressedIndex);
                        runEvent();
                    }

                }

            }
        };
        addMouseListener(mouse);
    }

    private void init() {
        setForeground(new Color(238, 238, 238));
        addMenuItem("PLAY (press)");
        addMenuItem("MORE (press)");
        addMenuItem("CREDITS (press)");
    }

    public void addEvent(EventMenu event) {
        this.events.add(event);
    }

    private void runEvent() {
        for (EventMenu event : events) {
            event.menuSelected(pressedIndex);
        }
    }

    public void addMenuItem(String menu) {
        int y = items.size() * menuHeight + left;
        items.add(new Menu3dItem(this, 170, y, menuHeight, shadowSize, menu));
    }

    private int getOverIndex(Point mouse) {
        int index = -1;
        for (Menu3dItem d : items) {
            index++;
            if (d.isMouseOver(mouse)) {
                return index;
            }
        }
        return -1;
    }

    private void hideMenu(int exitIndex) {
        for (int i = 0; i < items.size(); i++) {
            if (i != exitIndex) {
                items.get(i).getAnimator().hide();
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

    public void paint(Graphics g) {

        mrect+=50;
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLUE);

        if(pressedIndex==-1) {
            for (int i = 0; i < items.size(); i++) {
                items.get(i).getAnimator().hide();

            }
            g2d.drawImage(Startframes[fram], 0, 0, getWidth(), getHeight(), null);

            //g2 = (Graphics2D) h;



            fram++;
        }
        if(fram>12)fram=5;


        if(pressedIndex==0 && !runAnim) {
           
	        pressedBut=true;
	        g2d.drawImage(imgShade,0, 0,getWidth(),getHeight(), null);
	
	        BufferedImage image = null;
	        double b= getWidth()/1.92;
	        g2d.drawImage(buttonPlayer,getWidth()*0, getHeight()*6/8,getWidth()/7,getHeight()/7, null);
	        g2d.drawImage(buttonPlayer,(int)((b-(b/7))/2), getHeight()*6/8,getWidth()/7,getHeight()/7, null);
	        g2d.drawImage(buttonPlayer,(int)(b-(b/7)), getHeight()*6/8,getWidth()/7,getHeight()/7, null);
	        g2d.setColor(new Color(255, 255, 255));
	        g2d.setFont(new Font("Arial", Font.PLAIN, 60));
	        g2d.drawString("Select the Number of Players", getWidth()*2/32-10, getHeight()*7/16);
	        g2d.setFont(new Font("Arial", Font.PLAIN, 20));
	        g2d.drawString("2 Players", getWidth()*2/32-30, getHeight()*13/16+getHeight()/128);
	        g2d.drawString("3 Players", getWidth()*9/32-20, getHeight()*13/16+getHeight()/128);
	        g2d.drawString("4 Players", getWidth()*16/32-10, getHeight()*13/16+getHeight()/128);
            

		    /*if(once==0) {
		        addMenuItem("2 PLAYERS");
		        addMenuItem("3 PLAYERS");
		        addMenuItem("4 PLAYERS");
	        once++;}*/
        }
        if(pressedIndex==1) {
            pressedBut=false;
            g2d.drawImage(imgShade,0, 0,getWidth(),getHeight(), null);
            g2d.drawImage(meme, 500, 300, 310, 198, null);

        }
        if(pressedIndex==2) {
            pressedBut=false;
            g2d.setColor(new Color(91,103,171));
            g2d.setFont(new Font("Arial", Font.PLAIN, 30));
            g2d.drawImage(imgShade,0, 0,getWidth(),getHeight(), null);
            g2d.drawImage(credit, 75, 300, 738, 95, null);
            g2d.drawString("This game is programmed by Matthew, Ryan, Jola, and Ratnesh", 75, 495);

        }


        Graphics2D g2 = (Graphics2D) g2d.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //System.out.println(getWidth());
        for (int i = items.size() - 1; i >= 0; i--) {
            items.get(i).render(g2, 360 - angle, getWidth()*470/1441, getWidth(), getHeight(), this);
            //System.out.println(3);
        }
        g2.dispose();
        if(runAnim && Flipfram<66) {
            //System.out.println("rhaeiowhgioi3oawhgrpehqgrheawghreawghr+"+Flipfram);
            g2d.drawImage(frames[Flipfram], 0, 0, getWidth(), getHeight(), null);
            Flipfram++;
            //System.out.println("rhaeiowhgioi3oawhgrpehqgrheawghreawghr+");
        }

        wait(10);
        //System.out.println(pressedIndex);

        revalidate();
        if(!runAnim ||Flipfram<66) {
            repaint();
        }
        else {

            CardLayout cardLayout = (CardLayout) contentPane.getLayout();
            cardLayout.show(contentPane, "gamePanel");
            //Frame.update();
            //runAnim = false;

        }







    }
    @Override
    public void paintComponent(Graphics g) {


    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        double b= getWidth()/1.92;


        Point clicked = e.getPoint();

        Rectangle b1 = new Rectangle(0, getHeight()*6/8, getWidth()/7, getHeight()/7);
        Rectangle b2 = new Rectangle((int)((b-(b/7))/2), getHeight()*6/8, getWidth()/7, getHeight()/7);
        Rectangle b3 = new Rectangle((int)(b-(b/7)), getHeight()*6/8, getWidth()/7, getHeight()/7);

        if(b1.contains(clicked)&&pressedBut){
            runAnim= true;
            playerCount=2;
            contentPane.add(new Panel(contentPane, 2), "gamePanel");
        }
        else if(b2.contains(clicked)&&pressedBut){
            runAnim= true;
            playerCount=3;
            contentPane.add(new Panel(contentPane, 3), "gamePanel");
        }
        else if(b3.contains(clicked)&&pressedBut){
            runAnim= true;
            playerCount=4;
            contentPane.add(new Panel(contentPane, 4), "gamePanel");
        }



    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }
}