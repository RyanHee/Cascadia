import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.Buffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.HashSet;

public class BoardPanel extends JPanel implements ActionListener {
    private Node board;
    private HashSet<Node>visited;
    private HashMap<String, BufferedImage[]>animalTokenMap;
    private Node curNode;
    private Panel bigPanel;
    private int r, u;
    private JButton up,down,right,left;
    private BufferedImage outline, dpad;
    private int moveUD=0, moveLR=0;
    private HashMap<String, String>mp;
    private double scale;
    
    public BoardPanel (Node n, HashMap<String, BufferedImage[]>map, Panel BigPan){
        super();

        scale = 1;
        board=n;
        animalTokenMap=map;
        
        setBackground(new Color(159, 223, 223));//bright light sky blue
        //setBackground(new Color(210, 232, 206));//light mint
        //setBackground(new Color(200, 207, 208));//light gray-faint blue
        //setBackground(new Color(165, 213, 232));//light glacier blue
        //setBackground(new Color(100, 139, 37));//medium green
        //setBackground(new Color(216, 191, 216));/*203, 195, 227*/ /*162, 162, 208*/ //all are light purple
        //setBackground(new Color(222,184,135));//tan
        //setBackground(new Color(3, 107, 156));//dark blue
        bigPanel=BigPan;
        mp=new HashMap<>();
        mp.put("M", "Mountain");
        mp.put("D", "Desert");
        mp.put("S", "Swamp");
        mp.put("L", "Lake");
        mp.put("F", "Forest");

        up   =new InvisButton("");
        down =new InvisButton("");
        right=new InvisButton("");
        left =new InvisButton("");
        up.addActionListener(this);
        down.addActionListener(this);
        right.addActionListener(this);
        left.addActionListener(this);
        try{
            outline= ImageIO.read(new File("img/tileOutline.png"));
            dpad=ImageIO.read(new File("img/DPAD.png"));
        }
        catch (Exception E){

        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int w=120;
        int h=120;
        visited =new HashSet<>();
        
        g.translate(r,u);
        add(up);
        add(down);
        add(left);
        add(right);
        left.setBounds(getWidth()-90, getHeight()-60 , 30,30);
        right.setBounds(getWidth()-30 , getHeight()-60, 30, 30);
        up.setBounds(getWidth()-60, getHeight()-90 ,30,30);
        down.setBounds(getWidth()-60, getHeight()-30,30,30);
        g.drawImage(dpad, getWidth()-90-r, getHeight()-90-u, (int)(90*scale), (int)(90*scale), null);
        
        putButtons(g, board,getWidth()/2-50, getHeight()/2-h, (int)(w*scale),  (int)(h*scale));
//
    }
    private void putButtons(Graphics g, Node n, int x, int y, int w, int h){
        if (n==null){
            return;
        }
        if (visited.contains(n)){
            return;
        }

        int[]xlst=new int[6];
        int[]ylst=new int[6];
        for(int i = 0; i < 6; i++) {
            double v = i*Math.PI/3;
            //use this for ^
            xlst[i] = (int)(x+w/2-w/2*Math.cos(v + Math.PI/2));
            ylst[i] = (int) (y+h/2-h/2*Math.sin(v + Math.PI/2));
            //use this for ------
            //xPoints[i] = x + (int)Math.round(-width*Math.sin(v + Math.PI/2));
            //yPoints[i] = y + (int)Math.round(-height*Math.cos(v + Math.PI/2));
        }
        if (n.getVal()!=null){
            n.updateNeighbor();
        }
        else{
            if (n.neighborCount()==6){
                n.updateNeighbor();
            }
        }
        Graphics2D g2 = (Graphics2D) g.create();
        g2.rotate(Math.toRadians(n.getRotateAngle()),x+8+ w*50/116,y+ (h/2));
        g2.drawImage(n.getImg(), x+8 , y, w * 50 / 58, h, null);
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.BLACK);
        // g2.drawPolygon(xlst,ylst,6);

        if (animalTokenMap.get(n.getAnimal())!=null){
            //System.out.println("animal: "+n.getAnimal());
            g.drawImage(animalTokenMap.get(n.getAnimal())[0], x+8+w/2-(int)(25*scale), y+h/2-(int)(25*scale), (int)(50*scale),(int)(50*scale), null);

        }
        g2.dispose();
        n.paintComponent(g);
        n.addActionListener(this);
        add(n);
        n.setBounds(x+r,y+u,w,h);
        visited.add(n);


        int[]nx=new int[6];
        int[]ny=new int[6];
        nx[0]=x+w*50/116;
        nx[1]=x+w*50/58;
        nx[2]=x+w*50/116;
        nx[3]=x-w*50/116;
        nx[4]=x-w*50/58;
        nx[5]=x-w*50/116;

        ny[0]=y-h*3/4;
        ny[1]=y;
        ny[2]=y+h*3/4;
        ny[3]=y+h*3/4;
        ny[4]=y;
        ny[5]=y-h*3/4;
        for (int i=0;i<6;i++){
            putButtons(g, n.getNeighbors()[i], nx[i], ny[i], w, h);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(up)){
            System.out.println("UP");
            if(moveUD < 6) {
            	down.setVisible(true);
            	moveUD++;
            	this.shift(0, -116);
            }
            else {
            	up.setVisible(false);
            }
            repaint();
            return;
        }
        if (e.getSource().equals(down)){
            System.out.println("DOWN");
            if(moveUD > -6) {
            	up.setVisible(true);
            	moveUD--;
            	this.shift(0, 116);
            }
            else {
            	down.setVisible(false);
            }
            repaint();
            return;
        }
        if (e.getSource().equals(right)){
            System.out.println("RIGHT");
            if(moveLR < 9) {
            	left.setVisible(true);
            	moveLR++;
            	this.shift(100, 0);
            }
            else {
            	right.setVisible(false);
            }
            repaint();
            return;
        }
        if (e.getSource().equals(left)){
            System.out.println("LEFT");
            if(moveLR > -9) {
            	right.setVisible(true);
            	moveLR--;
            	this.shift(-100, 0);
            }
            else {
            	left.setVisible(false);
            }
            repaint();
            return;
        }
        try{
            curNode=(Node) e.getSource();
            if (bigPanel.getState()==1){
                if (curNode.getVal()==null|| curNode.getVal().isEmpty()){
                    setCurNodeVal(bigPanel.getCurVal());
                    bigPanel.next(curNode);
                }

            }
            else if (bigPanel.getState()==3){

                if (setCurNodeAnimal(bigPanel.getCurAnimal())){
                    //if keystone tile-> add nature token
                    String[] hold = curNode.getSides();
                    /*
                    for(int i =0; i<6;i++) {
                    	if(hold[i].equals("D")) {
                    		hold[i] = "Desert";
                    	}
                    	if(hold[i].equals("F")) {
                    		hold[i] = "Forest";
                    	}
                    	if(hold[i].equals("L")) {
                    		hold[i] = "Lake";
                    	}
                    	if(hold[i].equals("M")) {
                    		hold[i] = "Mountain";
                    	}
                    	if(hold[i].equals("S")) {
                    		hold[i] = "Swamp";
                    	}
                    }

                     */
                    if(bigPanel.getCurAnimal().equals("B")) {
                    	bigPanel.getGame().addAction("Player "+(bigPanel.getGame().getPlayerNum()+1)+ " placed a bear token.");
                    }
                    if(bigPanel.getCurAnimal().equals("E")) {
                    	bigPanel.getGame().addAction("Player "+(bigPanel.getGame().getPlayerNum()+1)+ " placed an elk token");
                    }
                    if(bigPanel.getCurAnimal().equals("F")) {
                    	bigPanel.getGame().addAction("Player "+(bigPanel.getGame().getPlayerNum()+1)+ " placed a fox token");
                    }
                    if(bigPanel.getCurAnimal().equals("H")) {
                    	bigPanel.getGame().addAction("Player "+(bigPanel.getGame().getPlayerNum()+1)+ " placed a hawk token");
                    }
                    if(bigPanel.getCurAnimal().equals("S")) {
                    	bigPanel.getGame().addAction("Player "+(bigPanel.getGame().getPlayerNum()+1)+ " placed a salmon token");
                    }
                    if(hold[0].equals(hold[3])) {
                        bigPanel.getGame().addAction("It was placed on a " +mp.get(hold[0])+" tile.");
                    	bigPanel.getGame().addAction("Player "+(bigPanel.getGame().getPlayerNum()+1)+ " gained a nature token.");
                    	bigPanel.getGame().getCurrPlayer().addNt();
                    }
                    else {
                    	bigPanel.getGame().addAction("It was placed on a " +mp.get(hold[0])+" and "+mp.get(hold[3])+" tile.");
                    }
                    bigPanel.nextA();
                    bigPanel.resetProg();
                    repaint();
                }
            }
            repaint();

        }
        catch (ClassCastException E){
            System.out.println("error");
        }
    }
    public Node getCurNode(){
        return curNode;
    }
    public void setCurNodeVal(String s){
        curNode.setVal(s);
        this.repaint();

    }
    public boolean setCurNodeAnimal(String s){
        if (curNode.setAnimal(s)){
            repaint();
            return true;
        }
        return false;
    }
    public void shift(int a, int b){
        r+=a;
        u+=b;
    }
    public void setBoard(Node n){
        board=n;
        this.removeAll();
        repaint();
    }
    public void setScale(double x){
    	scale= x;
    }
    public void setShift(int x,int y){
        r=x;
       u=y;
    }
}
