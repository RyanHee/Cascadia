import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Panel extends JPanel implements ActionListener {
    //private int[]xlst;
    //private int[]ylst;

    private HashMap<String, BufferedImage[]>animalTokenMap;

    private int angle, numSelectedTile, numSelectedAnimal;
    private static BufferedImage selectOutline, outline, rotateImage;
    private Node nodeSelected;
    private HexButton rotate;

    private BufferedImage[] tiles4;
    private HexButton[] fourButtonTiles;
    private InvisButton[]fourButtonAnimal;
    private int state;
    private boolean drawHighlightAnimal;
    private JButton confirmB, cancelB, nextB;
    private String curVal, curAnimal;
    private BoardPanel bp;
    private BufferedImage dpad;
    private JButton up,down,right,left;
    private Game game;

    //private HexButton hexButton;
    public Panel() throws FileNotFoundException {
        nodeSelected=null;
        numSelectedAnimal=-1;
        game=new Game();
        try{
            //img = ImageIO.read(Panel.class.getResource("tile.png"));
            //img1 = ImageIO.read(Panel.class.getResource("tile1.png"));
            dpad=ImageIO.read(new File("img/DPAD.jpg"));
            outline=ImageIO.read(new File("img/tileOutline.png"));
            selectOutline=ImageIO.read(new File("img/selectedTile.png"));



            tiles4=new BufferedImage[4];
            fourButtonTiles =new HexButton[4];
            fourButtonAnimal= new InvisButton[4];
            animalTokenMap=new HashMap<>();

            for (int i=0;i<4;i++){
                tiles4[i]=ImageIO.read(new File("img/Tile/"+game.getTileName4()[i]+".png"));
                fourButtonTiles[i]=new HexButton("");
                fourButtonTiles[i].addActionListener(this);
                fourButtonAnimal[i]=new InvisButton("");
                fourButtonAnimal[i].addActionListener(this);
                //fourButtonAnimal[i].showButton();
            }

            System.out.println("here");
            String[]A=new String[]{"B", "E", "F", "H", "S"};
            String[] ALong=new String[]{"bear", "elk", "fox", "hawk", "salmon"};
            for (int i=0;i<5;i++){
                animalTokenMap.put(A[i], new BufferedImage[]{ImageIO.read(new File("img/tokens/"+ALong[i]+".png")),
                        ImageIO.read(new File("img/tokens/"+ALong[i]+"Active.png")),
                        ImageIO.read(new File("img/tokens/"+ALong[i]+"Inactive.png"))});
            }
            rotateImage=ImageIO.read(new File("img/tilePlacementRotateClockwise.png"));
            //System.out.println(Arrays.toString(tiles4));
        }
        catch (Exception e){
            System.out.println(1231);
        }
        angle=0;
        numSelectedTile =-1;
        confirmB=new JButton("confirm");
        cancelB=new JButton("cancel");
        nextB=new JButton("next turn");

        confirmB.addActionListener(this);
        cancelB.addActionListener(this);
        nextB.addActionListener(this);

        up=new InvisButton("");
        down=new InvisButton("");
        right=new InvisButton("");
        left=new InvisButton("");
        up.addActionListener(this);
        down.addActionListener(this);
        right.addActionListener(this);
        left.addActionListener(this);

        //test=new Node("", "MS-FHB");

        curVal="";
        state=0;
        rotate = new HexButton("arrow.png");
        rotate.addActionListener(this);

        bp=new BoardPanel(game.getCurrPlayer().getBoard(), animalTokenMap, this);
        add(bp);
        setBackground(Color.WHITE);
        setBackground(new Color(3, 107, 156));
    }


    public void paint(Graphics g){
        super.paint(g);
        g.setColor(new Color(0,80,117));
        for(int i = 0;i<5;i++) {
            g.drawRect(getWidth()/8-i, getHeight()/8-i, getWidth() - getWidth() / 5+2*i, getHeight()*3/4 +2*i);
        }
        bp.setBounds(getWidth()/8, getHeight()/8, getWidth()-getWidth()/10-5, getHeight()*3/4);
        add(nextB);
        nextB.setBounds(getWidth()/30, getHeight()*3/5+getHeight()/10, getWidth()/15, getHeight()/15);

        add(confirmB);
        confirmB.setBounds(getWidth()/30, getHeight()*3/5+getHeight()/5, getWidth()/15, getHeight()/15);

        add(cancelB);
        cancelB.setBounds(getWidth()/30, getHeight()*3/5, getWidth()/15, getHeight()/15);

        add(up);

        up.setBounds(0,0,50,50);
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
        for (int i=0;i<4;i++){
            add(fourButtonTiles[i]);
            add(fourButtonAnimal[i]);
            g.drawImage(tiles4[i], getWidth()/75, getHeight()/8+i*95, 75, 87, null);
            g.drawImage(outline,   getWidth()/75, getHeight()/8+i*95, 75, 87, null);
            fourButtonTiles[i].setBounds(getWidth()/75, getHeight()/8+i*95, 75, 87);
            if (i == numSelectedTile){
                g.drawImage(selectOutline, getWidth()/75, getHeight()/8+i*95, 75, 87, null);
            }
            if (i==numSelectedAnimal&&drawHighlightAnimal) {
                g.drawImage(animalTokenMap.get(game.getAnimalToken4()[i])[1], 115, getHeight()/8+i*95, 60, 60, null);

            }else{
                g.drawImage(animalTokenMap.get(game.getAnimalToken4()[i])[0], 115, getHeight()/8+i*95, 60, 60, null);
            }
            fourButtonAnimal[i].setBounds(115, getHeight()/8+i*95, 60, 60);
        }

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
        game.updateAnimalDeck(numSelectedAnimal);

        numSelectedAnimal=-1;
        drawHighlightAnimal=false;
        repaint();
    }
    public void next(Node node){
        nodeSelected=node;
        state++;
        //update deck
        game.updateTileDeck(numSelectedTile);
        try {
            tiles4[numSelectedTile] = ImageIO.read(new File("img/Tile/" + game.getTileName4()[numSelectedTile] + ".png"));
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

        if (e.getSource().equals(nextB) && state==5){
            game.nextTurn();
            bp.setBoard(game.getCurrPlayer().getBoard());
            state=0;
            repaint();
            return;
        }
        //select tile
        for (int i=0;i<4;i++){
            HexButton b = fourButtonTiles[i];
            if (e.getSource().equals(b)&&state==0){
                System.out.println("FourbUttons");
                curVal= game.getTileName4()[i];
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
            repaint();
            return;
        }
        //confirm tile placement
        if (e.getSource().equals(confirmB)&&state==2){
            drawHighlightAnimal=true;
            state++;
            System.out.println(state);
            repaint();
            return;
        }

        if (state==3){

            //pick animal
            if (fourButtonAnimal[numSelectedAnimal].equals(e.getSource())){
                curAnimal=game.getAnimalToken4()[numSelectedAnimal];
                state++;
                repaint();
                return;
            }
            //cancel animal
            else if (e.getSource().equals(cancelB)){
                curAnimal="";
                numSelectedAnimal=-1;
                drawHighlightAnimal=false;
                repaint();
                state+=2;

                return;
            }

        }



        if (e.getSource().equals(cancelB)&&state==4){
            curAnimal="";
            numSelectedAnimal=-1;
            drawHighlightAnimal=false;
            repaint();
            state++;


            return;
        }
    }
}