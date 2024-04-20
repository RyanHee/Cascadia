import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.Buffer;
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
    private int hs,vs;

    public BoardPanel (Node n, HashMap<String, BufferedImage[]>map, Panel BigPan){
        hs = 0;
        vs = 0;
        board=n;
        animalTokenMap=map;
        setBackground(new Color(222,184,135));
        //setBackground(new Color(3, 107, 156));
        bigPanel=BigPan;

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
        int w=116;
        int h=116;
        visited =new HashSet<>();
        
        g.translate(r,u);
        /*left.setVisible(true);
        right.setVisible(true);
        up.setVisible(true);
        down.setVisible(true);*/
        add(up);
        add(down);
        add(left);
        add(right);
        left.setBounds(getWidth()-90, getHeight()-60 , 30,30);
        right.setBounds(getWidth()-30 , getHeight()-60, 30, 30);
        up.setBounds(getWidth()-60, getHeight()-90 ,30,30);
        down.setBounds(getWidth()-60, getHeight()-30,30,30);
        g.drawImage(dpad, getWidth()-90-r, getHeight()-90-u, 90, 90, null);
        
        putButtons(g, board,getWidth()/2-50, getHeight()/2-h, w, h);
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
        g2.rotate(Math.toRadians(n.getRotateAngle()), x + 58, y + 58);
        g2.drawImage(n.getImg(), x + 8, y, w * 50 / 58, h, null);
        g.drawImage(outline, x+8, y, w*50/58, h, null);
        // 9,132,219
        /*
        if(n.getPlaced()) {
            g2.drawImage(n.getImg(), x + 8, y, w * 50 / 58, h, null);
        }else{
            g2.setColor(new Color(22, 162, 227));
            g2.fillPolygon(xlst,ylst,6);
        }

         */
        //g.setColor(Color.BLACK);
        //g.drawPolygon(xlst,ylst,6);
        if (animalTokenMap.get(n.getAnimal())!=null){
            //System.out.println("animal: "+n.getAnimal());
            g.drawImage(animalTokenMap.get(n.getAnimal())[0], (x-17+w*50/116), (y-25+h/2), 50, 50, null);
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
            //System.out.println(curNode);
            if (bigPanel.getState()==1){
                if (curNode.getVal()==null|| curNode.getVal().isEmpty()){
                    setCurNodeVal(bigPanel.getCurVal());
                    bigPanel.next(curNode);
                }

            }
            else if (bigPanel.getState()==4){

                if (setCurNodeAnimal(bigPanel.getCurAnimal())){
                    //System.out.println("goofy");
                    //if keystone tile-> add nature token
                    String[] hold = curNode.getSides();
                    if(hold[0].equals(hold[3])) {
                    	bigPanel.getGame().getCurrPlayer().addNt();
                    }
                    bigPanel.nextA();
                    bigPanel.nextTurn();
                    /*bigPanel.getGame().nextTurn();
                    setBoard(bigPanel.getGame().getCurrPlayer().getBoard());*/
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
        //System.out.println("setvallllllll");
        curNode.setVal(s);
        //stop=false;
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
}
