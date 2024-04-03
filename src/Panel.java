import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

public class Panel extends JPanel implements ActionListener {
    //private int[]xlst;
    //private int[]ylst;

    private HashMap<String, BufferedImage[]>animalTokenMap;
    //private ArrayList<Node>visted;
    private int[]xPoints, yPoints;
    private int angle, numSelectedTile, numSelectedAnimal;
    private static BufferedImage selectOutline, outline, rotateImage;
    private Node test, n1, nodeSelected;
    private HexButton rotate;
    private ArrayList<String> tileNames, animalDeck;
    private String[] tileName4, animalToken4;
    private BufferedImage[] tiles4;
    private HexButton[] fourButtonTiles;
    private InvisButton[]fourButtonAnimal;
    private int state;
    private boolean drawHighlightAnimal, pickTurn, putTurn, rotateTurn, confirmTurn, animalTurn, putAnimalTurn;
    private JButton confirmB, cancelB;
    private String curVal, curAnimal;
    private BoardPanel bp;
    private BufferedImage dpad;
    private InvisButton up, down, right, left;

    //private HexButton hexButton;
    public Panel(){
        nodeSelected=null;
        numSelectedAnimal=-1;
        try{
            //img = ImageIO.read(Panel.class.getResource("tile.png"));
            //img1 = ImageIO.read(Panel.class.getResource("tile1.png"));
            dpad=ImageIO.read(new File("img/DPAD.jpg"));
            outline=ImageIO.read(new File("img/tileOutline.png"));
            selectOutline=ImageIO.read(new File("img/selectedTile.png"));

            Scanner sc = new Scanner(new File("names.txt"));
            tileNames = new ArrayList<>();
            animalDeck = new ArrayList<>();
            while (sc.hasNext()){
                tileNames.add(sc.next());
            }
            for (int i=0;i<20;i++){
                animalDeck.add("B");
                animalDeck.add("E");
                animalDeck.add("H");
                animalDeck.add("S");
                animalDeck.add("F");
            }
            Collections.shuffle(animalDeck);
            Collections.shuffle(tileNames);
            tiles4=new BufferedImage[4];
            fourButtonTiles =new HexButton[4];
            fourButtonAnimal= new InvisButton[4];
            tileName4 =new String[4];
            animalToken4=new String[4];
            animalTokenMap=new HashMap<>();
            for (int i=0;i<4;i++){
                tiles4[i]=ImageIO.read(new File("img/Tile/"+ tileNames.get(0)+".png"));
                tileName4[i]= tileNames.get(0);
                tileNames.remove(0);
                fourButtonTiles[i]=new HexButton("");
                fourButtonTiles[i].addActionListener(this);
                fourButtonAnimal[i]=new InvisButton("");
                fourButtonAnimal[i].addActionListener(this);
                //fourButtonAnimal[i].showButton();
                animalToken4[i]= animalDeck.remove(0);
            }

            System.out.println("here");
            String[]A=new String[]{"B", "E", "F", "H", "S"};
            String[] ALong=new String[]{"bear", "elk", "fox", "hawk", "salmon"};
            for (int i=0;i<5;i++){
                animalTokenMap.put(A[i], new BufferedImage[]{ImageIO.read(new File("img/tokens/"+ALong[i]+".png")),
                        ImageIO.read(new File("img/tokens/"+ALong[i]+"Active.png")),
                        ImageIO.read(new File("img/tokens/"+ALong[i]+"Inactive.png"))});
            }
            System.out.println("blck");
            rotateImage=ImageIO.read(new File("img/tilePlacementRotateClockwise.png"));
            //System.out.println(Arrays.toString(tiles4));
        }
        catch (Exception e){
            System.out.println(1231);
        }
        angle=0;
        numSelectedTile =-1;
        confirmB=new JButton("confirm");
        confirmB.addActionListener(this);
        cancelB=new JButton("cancel");
        cancelB.addActionListener(this);

        up=new InvisButton("");
        down=new InvisButton("");
        right=new InvisButton("");
        left=new InvisButton("");
        up.addActionListener(this);
        down.addActionListener(this);
        right.addActionListener(this);
        left.addActionListener(this);

        test=new Node("", "MS-FHB");
        n1 = new Node("");
        n1.addActionListener(this);
        curVal="";
        state=0;
        rotate = new HexButton("");
        rotate.addActionListener(this);

        bp=new BoardPanel(test, animalTokenMap, this);
        add(bp);
        setBackground(Color.WHITE);





    }


    public void paint(Graphics g){

        super.paint(g);
        add(confirmB);
        confirmB.setBounds(1200, 700, 200, 50);

        add(cancelB);
        cancelB.setBounds(1200, 800, 200, 50);

        for (int i=0;i<4;i++){
            add(fourButtonTiles[i]);
            add(fourButtonAnimal[i]);

            //FourButtons[i].paintComponent(g);
            g.drawImage(tiles4[i], 765, 100+i*100, 75, 87, null);
            g.drawImage(outline, 765, 100+i*100, 75, 87, null);
            fourButtonTiles[i].setBounds(760, 100+i*100, 87, 87);
            if (i== numSelectedTile){
                g.drawImage(selectOutline, 765, 100+i*100, 75, 87, null);
            }
            if (i==numSelectedAnimal&&drawHighlightAnimal){
                g.drawImage(animalTokenMap.get(animalToken4[i])[1], 865, 113+i*100, 60, 60, null);
            }
            else{
                g.drawImage(animalTokenMap.get(animalToken4[i])[0], 865, 113+i*100, 60, 60, null);
            }
            fourButtonAnimal[i].setBounds(865, 113+i*100, 60, 60);

        }

        g.drawImage(dpad, 800, 600, 240, 240, null);

        g.drawImage(rotateImage, 1204, 500, 75, 87, null);
        add(rotate);
        rotate.setBounds(1200, 500, 87, 87);

        //left.showButton();
        add(left);
        left.setBounds(847, 698, 50, 50);
        //right.showButton();
        add(right);
        right.setBounds(946, 698, 50, 50);
        //up.showButton();
        add(up);
        up.setBounds(897, 650, 50, 50);
        //down.showButton();
        add(down);
        down.setBounds(897, 747, 50, 50);
        bp.setBounds(30, 80, 700, 700);

    }

    public int getState(){
        return state;
    }

    public String getCurVal(){
        return curVal;
    }

    public String getCurAnimal(){
        return curAnimal;
    }

    public void nextA(){
        state++;
        animalToken4[numSelectedAnimal]=animalDeck.remove(0);
        numSelectedAnimal=-1;
        animalTurn=false;
        drawHighlightAnimal=false;
        repaint();
    }
    public void next(Node node){
        nodeSelected=node;
        state++;
        //update deck
        tileName4[numSelectedTile] = tileNames.get(0);
        tileNames.remove(0);
        try {
            tiles4[numSelectedTile] = ImageIO.read(new File("img/Tile/" + tileName4[numSelectedTile] + ".png"));
        } catch (Exception E) {
            System.out.println("blah");
        }
        numSelectedTile=-1;
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(up)){
            bp.shift(0, -116);
            repaint();
            return;
        }
        if (e.getSource().equals(down)){
            bp.shift(0, 116);
            repaint();
            return;
        }
        if (e.getSource().equals(right)){
            bp.shift(100, 0);
            repaint();
            return;
        }
        if (e.getSource().equals(left)){
            bp.shift(-100, 0);
            repaint();
            return;
        }
        System.out.println(state);
        if (state==5){
            pickTurn=true;
            state=0;
        }
        else{
            pickTurn=false;
        }



        //select tile
        for (int i=0;i<4;i++){
            HexButton b = fourButtonTiles[i];
            if (e.getSource().equals(b)&&state==0){
                System.out.println("FOurbUttons");
                putTurn=true;
                pickTurn=false;
                curVal= tileName4[i];
                System.out.println(curVal);
                numSelectedTile =i;
                numSelectedAnimal=i;
                nodeSelected=null;
                state++;
                repaint();
                return;
            }
        }

        //rotate angle
        if (nodeSelected!=null && e.getSource().equals(rotate) && state==2){
            nodeSelected.addRotateAngle();
            System.out.println("rotateeeee");
            //rotateTurn=false;
            confirmTurn=true;
            repaint();
            return;
        }

        //confirm tile placement
        if (e.getSource().equals(confirmB)&&state==2){
            animalTurn=true;
            confirmTurn=false;
            rotateTurn=false;
            drawHighlightAnimal=true;
            state++;
            System.out.println(state);
            repaint();
            return;
        }

        if (state==3){

            //pick animal
            if (fourButtonAnimal[numSelectedAnimal].equals(e.getSource())){
                curAnimal=animalToken4[numSelectedAnimal];
                animalTurn=false;
                putAnimalTurn=true;
                state++;
                repaint();
                return;
            }
            //cancel animal
            else if (e.getSource().equals(cancelB)){
                curAnimal="";
                numSelectedAnimal=-1;
                animalTurn=false;
                drawHighlightAnimal=false;
                repaint();
                state+=2;
                return;
            }

        }



        if (e.getSource().equals(cancelB)&&state==4){
            curAnimal="";
            numSelectedAnimal=-1;
            animalTurn=false;
            drawHighlightAnimal=false;
            repaint();
            state+=1;
            return;
        }
    }
}