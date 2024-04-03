import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class HexButton extends JButton {
    private Polygon hexagon;
    private int[]xPoints, yPoints;
    private BufferedImage img;
    public HexButton(String label){
        super(label);
        xPoints = new int[6];
        yPoints = new int[6];
        //System.out.println(Arrays.toString(xPoints));
        //System.out.println(Arrays.toString(yPoints));
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        hexagon = new Polygon(xPoints, yPoints, 6);
        try{
            img = ImageIO.read(Panel.class.getResource("tile.png"));
        }
        catch (Exception e){
            System.out.println("fuck");
        }
    }

    public HexButton(String label, int x, int y, int width, int height){
        super(label);
        xPoints = new int[6];
        yPoints = new int[6];
        for(int i = 0; i < 6; i++) {
            double v = i*Math.PI/3;
            xPoints[i] = x +width/2+ (int)Math.round(-width/2*Math.cos(v + Math.PI/2));
            yPoints[i] = y +height/2+ (int)Math.round(-height/2*Math.sin(v + Math.PI/2));
        }
        //System.out.println(Arrays.toString(xPoints));
        //System.out.println(Arrays.toString(yPoints));
        hexagon = new Polygon(xPoints, yPoints, 6);
    }

    protected void paintBorder(Graphics g) {
        g.setColor(Color.BLACK);
        int x0 = getSize().width/2;
        int y0 = getSize().height/2;
        for(int i=0; i<6; i++) {
            double v = i*Math.PI/3;
            xPoints[i] = x0 + (int)Math.round((getWidth()/2)*Math.sin(v));
            yPoints[i] = y0 + (int)Math.round((getHeight()/2)*Math.cos(v));
        }
        g.drawPolygon(xPoints, yPoints,6);
    }

    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.lightGray);
        } else {
            g.setColor(getBackground());
        }
        int x0 = getSize().width/2;
        int y0 = getSize().height/2;
        for(int i=0; i<6; i++) {
            double v = i*Math.PI/3;
            xPoints[i] = x0 + (int)Math.round((getWidth()/2)*Math.sin(v));
            yPoints[i] = y0 + (int)Math.round((getHeight()/2)*Math.cos(v));
        }
        //System.out.println(Arrays.toString(xPoints));
        //System.out.println(Arrays.toString(yPoints));
        g.fillPolygon(xPoints, yPoints, 6);
        super.paintComponent(g);
    }



    @Override
    public boolean contains(int x1, int y1) {
        if (hexagon == null ||
                !hexagon.getBounds().equals(getBounds())) {
            int x0 = getSize().width/2;
            int y0 = getSize().height/2;
            for(int i=0; i<6; i++) {
                double v = i*Math.PI/3;
                xPoints[i] = x0 + (int)Math.round((getWidth()/2)*Math.cos(v));
                yPoints[i] = y0 + (int)Math.round((getHeight()/2)*Math.sin(v));
            }
            hexagon = new Polygon(xPoints,yPoints,6);
        }
        return hexagon.contains(x1, y1);
    }

}