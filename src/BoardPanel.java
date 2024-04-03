import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.HashSet;

public class BoardPanel extends JPanel implements ActionListener {
    private Node board;
    private HashSet<Node>visited;
    private HashMap<String, BufferedImage[]>animalTokenMap;
    private Node curNode;
    private boolean stop;
    private Panel bigPanel;
    private int r, u;

    public BoardPanel (Node n, HashMap<String, BufferedImage[]>map, Panel BigPan){
        board=n;
        animalTokenMap=map;
        setBackground(new Color(222,184,135));
        bigPanel=BigPan;
    }



    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.translate(r,u);
        int w=116;
        int h=116;
        visited =new HashSet<>();
        putButtons(g, board,getWidth()/2-50, getHeight()/2-58, w, h);
        //animalTokenMap=new HashMap<>();

    }



    private void putButtons(Graphics g, Node n, int x, int y, int w, int h){
        if (n==null){
            return;
        }
        if (visited.contains(n)){
            return;
        }
        double[]xlst=new double[6];
        double[]ylst=new double[6];
        for(int i = 0; i < 6; i++) {
            double v = i*Math.PI/3;
            //use this for ^
            xlst[i] = x+w/2-w/2*Math.cos(v + Math.PI/2);
            ylst[i] = y+h/2-h/2*Math.sin(v + Math.PI/2);
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

        g2.drawImage(n.getImg(), x+8, y, w*50/58, h, null);
        if (animalTokenMap.get(n.getAnimal())!=null){
            //System.out.println("animal: "+n.getAnimal());
            g.drawImage(animalTokenMap.get(n.getAnimal())[0], x-17+w*50/116, y-25+h/2, 50, 50, null);
        }
        g2.dispose();
        n.paintComponent(g);
        n.addActionListener(this);
        add(n);
        n.setBounds(x,y,w,h);
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

        try{

            //System.out.println("hereee");
            curNode=(Node) e.getSource();
            if (bigPanel.getState()==1){
                if (curNode.getVal()==null||curNode.getVal().equals("")){
                    setCurNodeVal(bigPanel.getCurVal());
                    bigPanel.next(curNode);
                }
                //stop=true;

            }
            else if (bigPanel.getState()==4){

                if (setCurNodeAnimal(bigPanel.getCurAnimal())){
                    System.out.println("goofy");
                    repaint();
                    bigPanel.nextA();
                }
            }
            repaint();
            //System.out.println(curNode);

        }
        catch (ClassCastException E){
            System.out.println("error");
        }

    }

    public Node getCurNode(){
        return curNode;
    }

    public void setCurNodeVal(String s){
        System.out.println("setvallllllll");
        curNode.setVal(s);
        //stop=false;
        this.repaint();

    }

    public boolean setCurNodeAnimal(String s){
        if (curNode.setAnimal(s)){
            stop=false;
            repaint();
            return true;
        }
        return false;
    }

    public void shift(int a, int b){
        r-=a;
        u-=b;
    }
}
