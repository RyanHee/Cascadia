import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.*;

public class Panel extends JPanel implements ActionListener {
    //private int[]xlst;
    //private int[]ylst;

    private HashMap<String, BufferedImage[]>animalTokenMap;
    private static BufferedImage scoreCard;
    private int angle, numSelectedTile, numSelectedAnimal;
    private static BufferedImage selectOutline, outline, rotateImage;
    private static BufferedImage natureToken;
    private BufferedImage actionLogImage, cancelImage, clearAnimalImage, confirmImage, confirmClearImage;
    private BufferedImage helpImage, mixMatchImage, nextImage, removeDupImage, scoreCardImage, useNTImage;
    private BufferedImage frameImg, pfpFrameImg;
    private Node nodeSelected;
    private HexButton rotate;
    private BufferedImage[] tiles4;
    private HexButton[] fourButtonTiles;
    private InvisButton[]fourButtonAnimal;
    private boolean tileChose = false, dupAnimalsUsed = false, natureTokenUsed = false, mixMatchUsed = false, clearAnimalsUsed = false, noAnimalPlace = false, actionLogUsed = false;
    private int state;
    private int prog;
    private boolean drawHighlightAnimal;
    private JButton confirmB, cancelB, nextB;
    private JButton help, scoreCards, actionLog, useNature, removeDups;
    private JButton confirmClear, clearAnimals, mixMatch;
    private JButton scorePB;
    private HashSet<Integer> animalsToClear = new HashSet<>();
    private String curVal, curAnimal;
    private BoardPanel bp,sp1,sp2,sp3;
    private BoardPanel[]bplst;
    private BufferedImage dpad, nextimg;
    private Game game;
    private static int aggrrrrhhhhhhh;
    private boolean first = true;
    private int mini = 0;
    private JPanel p;//added
    private int increment;
    public Panel(JPanel panel, int numOfPlayers)  {
        p = panel;//added
        nodeSelected=null;
        numSelectedAnimal=-1;
        increment =4;
        game=new Game(numOfPlayers);
        try{
            //img = ImageIO.read(Panel.class.getResource("tile.png"));
            //img1 = ImageIO.read(Panel.class.getResource("tile1.png"));
            nextimg=ImageIO.read(getClass().getResource("img/Next.png"));
            pfpFrameImg=ImageIO.read(getClass().getResource("img/Frame.png"));
            dpad=ImageIO.read(getClass().getResource("img/DPAD.jpg"));
            outline=ImageIO.read(getClass().getResource("img/tileOutline.png"));
            selectOutline=ImageIO.read(getClass().getResource("img/selectedTile.png"));
            natureToken=ImageIO.read(getClass().getResource("img/tokens/nature-token.png"));
            actionLogImage=ImageIO.read(getClass().getResource("img/buttonimages/action log.png"));
            cancelImage= ImageIO.read(getClass().getResource("img/buttonimages/cancel.png"));
            clearAnimalImage=ImageIO.read(getClass().getResource("img/buttonimages/clear animals.png"));
            confirmImage=ImageIO.read(getClass().getResource("img/buttonimages/confirm.png"));
            confirmClearImage=ImageIO.read(getClass().getResource("img/buttonimages/confirm clear animals.png"));
            helpImage=ImageIO.read(getClass().getResource("img/buttonimages/help.png"));
            mixMatchImage=ImageIO.read(getClass().getResource("img/buttonimages/mix match.png"));
            nextImage=ImageIO.read(getClass().getResource("img/buttonimages/next.png"));
            removeDupImage=ImageIO.read(getClass().getResource("img/buttonimages/Remove Triplets.png"));
            scoreCardImage= ImageIO.read(getClass().getResource("img/buttonimages/score cards.png"));
            useNTImage=ImageIO.read(getClass().getResource("img/buttonimages/use nature token.png"));
            frameImg=ImageIO.read(getClass().getResource("img/goldframe.png"));


            tiles4=new BufferedImage[4];
            fourButtonTiles =new HexButton[4];
            fourButtonAnimal= new InvisButton[4];
            animalTokenMap=new HashMap<>();

            for (int i=0;i<4;i++){
                tiles4[i]=ImageIO.read(getClass().getResource("img/Tile/"+game.getTileName4()[i]+".png"));
                fourButtonTiles[i]=new HexButton("");
                fourButtonTiles[i].addActionListener(this);
                fourButtonAnimal[i]=new InvisButton("");
                fourButtonAnimal[i].addActionListener(this);
            }

            //System.out.println("here");
            String[]A=new String[]{"B", "E", "F", "H", "S"};
            String[] ALong=new String[]{"bear", "elk", "fox", "hawk", "salmon"};
            for (int i=0;i<5;i++){
                animalTokenMap.put(A[i], new BufferedImage[]{ImageIO.read(getClass().getResource("img/tokens/"+ALong[i]+".png")),
                        ImageIO.read(getClass().getResource("img/tokens/"+ALong[i]+"Active.png")),
                        ImageIO.read(getClass().getResource("img/tokens/"+ALong[i]+"Inactive.png"))});
            }
            rotateImage=ImageIO.read(getClass().getResource("img/tilePlacementRotateClockwise.png"));
            //System.out.println(Arrays.toString(tiles4));
        }
        catch (Exception e){
            System.out.println(1231);
        }
        angle=0;
        numSelectedTile =-1;
        confirmB=new JButton(new ImageIcon(confirmImage.getScaledInstance(91, 51, Image.SCALE_SMOOTH)));
        cancelB=new JButton(new ImageIcon(cancelImage.getScaledInstance(91, 51, Image.SCALE_SMOOTH)));
        nextB=new JButton(new ImageIcon(nextImage.getScaledInstance(91, 51, Image.SCALE_SMOOTH)));
        help=new JButton(new ImageIcon(helpImage.getScaledInstance(91, 51, Image.SCALE_SMOOTH)));
        scoreCards=new JButton(new ImageIcon(scoreCardImage.getScaledInstance(91, 51, Image.SCALE_SMOOTH)));
        actionLog=new JButton(new ImageIcon(actionLogImage.getScaledInstance(91, 51, Image.SCALE_SMOOTH)));
        useNature=new JButton(new ImageIcon(useNTImage.getScaledInstance(91, 51, Image.SCALE_SMOOTH)));
        removeDups=new JButton(new ImageIcon(removeDupImage.getScaledInstance(91, 51, Image.SCALE_SMOOTH)));
        confirmClear = new JButton(new ImageIcon(confirmClearImage.getScaledInstance(91, 51, Image.SCALE_SMOOTH)));
        clearAnimals = new JButton(new ImageIcon(clearAnimalImage.getScaledInstance(91, 51, Image.SCALE_SMOOTH)));
        mixMatch = new JButton(new ImageIcon(mixMatchImage.getScaledInstance(91, 51, Image.SCALE_SMOOTH)));
        scorePB = new JButton(new ImageIcon(nextImage.getScaledInstance(100, 50, Image.SCALE_SMOOTH)));
        rotate = new HexButton("arrow.png");


        confirmB.addActionListener(this);
        cancelB.addActionListener(this);
        nextB.addActionListener(this);
        help.addActionListener(this);
        scoreCards.addActionListener(this);
        actionLog.addActionListener(this);
        useNature.addActionListener(this);
        removeDups.addActionListener(this);
        confirmClear.addActionListener(this);
        clearAnimals.addActionListener(this);
        mixMatch.addActionListener(this);
        scorePB.addActionListener(this);


        add(cancelB);
        add(nextB);
        add(confirmB);
        add(help);
        add(scoreCards);
        add(actionLog);
        add(useNature);
        add(removeDups);
        add(confirmClear);
        add(clearAnimals);
        add(mixMatch);
        add(rotate);
        add(scorePB);
        scorePB.setVisible(false);


        curVal="";
        state=0;
        prog = 102;
        rotate.addActionListener(this);
        add(rotate);

        bplst=new BoardPanel[numOfPlayers-1];
        for (int i=0;i<bplst.length;i++){
            bplst[i]=new BoardPanel(game.getCurrPlayer().getBoard(), animalTokenMap, this);
            add(bplst[i]);
        }
    	
        bp=new BoardPanel(game.getCurrPlayer().getBoard(), animalTokenMap, this);

        add(bp);

        //setBackground(Color.WHITE);
        //setBackground(new Color(3, 107, 156)) // blue;
        //setBackground(new Color(251, 206, 177));//servicable orange
        //setBackground(new Color(216, 191, 216));
        //setBackground(new Color(82, 120, 134)); //grayish blue
        aggrrrrhhhhhhh= 0;
    }



    public void paint(Graphics g){
       
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        setBackground(game.getCurrPlayer().getColor());


        game.scoreAllPlayer();
        g2.setFont(new Font("Arial", Font.BOLD, 30));
        g2.drawImage(selectOutline, getWidth()*57/128, getHeight()*22/25+4, 50, 58, null);
        g2.setFont(new Font("Comic Sans", Font.BOLD, 45));
        FontMetrics f = g2.getFontMetrics();
        g2.drawString("H", getWidth()*57/128+25 - f.stringWidth("H")/2, getHeight()*22/25+27+f.getAscent()/2);
        g2.setFont(new Font("Arial", Font.BOLD, 15));

        String h = Integer.toString(game.getCurPlayerNum());
        
        int n = game.getCurrPlayer().getLandScore();

        g2.drawString(n +" / "+game.getCurrPlayer().bonusScore(), getWidth()*126/256, getHeight()*23/25);
        String[] animal = new String[]{"B", "E", "F", "H", "S"};
        for(int i = 0; i<animal.length; i++) {
            g2.drawImage(animalTokenMap.get(animal[i])[0], getWidth()*(134+15*i)/256, getHeight()*22/25+getHeight()/128, 50, 50, null);
            g2.drawString(": "+game.getCurrPlayer().getAnimalmp().get(animal[i]), getWidth()*(134+15*i)/256+53, getHeight()*23/25);
        }
        g2.drawImage(game.getCurrPlayer().getPfp(), 17, 22, 75, 75, null);

        g2.setColor(new Color(0,0,0));
        g2.setFont(new Font("Arial", Font.PLAIN, 30));

        g2.drawString("Turn "+Math.min(game.getTurn(), 20), getWidth()/15+30, 40);
        g2.setFont(new Font("Arial", Font.PLAIN, 25));
        g2.drawString("Player "+(game.getCurPlayerNum()+1), getWidth()/15+30, 75);
        g2.setFont(new Font("Arial", Font.PLAIN, 20));

        g2.drawImage(natureToken, getWidth()/5, 10, 50, 50, null);
        g2.drawString(": "+game.getCurrPlayer().getNumTokens(), getWidth()/5+60, 40);
        g2.drawString("Current Score: "+ game.curPlayerScore(), getWidth()/5, 75);

        g2.setColor(new Color(222,184,135));
        g2.setColor(new Color(159, 223, 223));
        g2.setColor(new Color(165, 213, 232));



        if(prog<100+ increment){
            g2.setStroke(new BasicStroke(6));
            g2.setColor(Color.BLACK);
            g2.drawRect(getWidth()/15, getHeight()/10*9, getWidth()/3, getHeight()/20);
            g2.setColor(Color.GREEN);
            g2.fillRect(getWidth()/15, getHeight()/10*9, getWidth()*prog/300, getHeight()/20);
            prog+= increment;

            if(game.getTurn() == 1 && game.getCurPlayerNum() == 0 || prog!=106) {
                nextB.setVisible(false);
            }

            try{
                wait(3);
            }
            catch (Exception E){

            }
            repaint();
        }else if (prog==100+ increment){
            try{
                wait(10);
                prog++;
                nextTurn();
            }
            catch (Exception E){

            }
        }



        if (actionLogUsed){
            
            g2.setColor(new Color(0, 40, 86));
            g2.fillRect(getWidth()*6/8+getWidth()/16,getHeight()*3/4,getWidth()*2/10,getHeight()/4);
            g2.setColor(Color.WHITE);
            Queue<String> actions = game.getActionLog();
            g2.setFont(new Font("Comic Sans", Font.BOLD, 12));
            Iterator<String> it = actions.iterator();
            int i=0;
            while (it.hasNext()) {
                g2.drawString(it.next(), getWidth()*269/320, getHeight()*81/100+(i*20));
                i++;
            }
            g2.drawImage(frameImg, getWidth()*13/16, getHeight()*3/4+2, getWidth()*24/128, getHeight()/4-2, null);
        }

        g2.setColor(Color.BLACK);

        int yPlay = 0;
        for(int pNum = 1; pNum<game.getPlayerList().length+1; pNum++) {
            if(pNum != game.getCurPlayerNum()+1){

                bplst[yPlay].setScale(.3);
                bplst[yPlay].sp = true;
                bplst[yPlay].setBoard(game.pList()[pNum-1].getBoard());
                bplst[yPlay].setBackground(game.pList()[pNum-1].getColor());
                bplst[yPlay].setShift(0,(int)(120*.7));
                bplst[yPlay].setBounds(getWidth()*6/8+getWidth()/16,getHeight()*yPlay/4+getHeight()/12,getWidth()*2/10,getHeight()*3/18);
                g2.setColor(game.pList()[pNum-1].getColor());
                g2.fillRect(getWidth()*6/8+getWidth()/16,getHeight()*yPlay/4,getWidth()*2/10,getHeight()*3/18);

                g2.setColor(Color.BLACK);

                g2.setFont(new Font("Arial", Font.BOLD, 14*getWidth()/1920));
                g2.drawImage(selectOutline,  getWidth()*5/6-getWidth()/64,  getHeight()*yPlay/4+getHeight()/24+1, 22, 25, null);

                FontMetrics f1 = g2.getFontMetrics();
                g2.drawString("H", getWidth()*5/6-getWidth()/64+11-f1.stringWidth("H")/2, getHeight()*yPlay/4+12+getHeight()/24+f1.getAscent()/2);
                g2.setFont(new Font("Arial", Font.BOLD, 12*getWidth()/1920));
                int sjs = game.getPlayerList()[pNum-1].getLandScore();
                g2.drawString(sjs +" / "+ game.getPlayerList()[pNum-1].bonusScore(), getWidth()*5/6+getWidth()/512, getHeight()*yPlay/4+getHeight()/16);


                for(int i = 0; i<animal.length; i++) {
                    g2.drawImage(animalTokenMap.get(animal[i])[0], getWidth()*144/168+getWidth()*i*15/512, getHeight()*(yPlay)/4+getHeight()*3/64, 20, 20, null);
                    g2.drawString(game.getPlayerList()[pNum-1].getAnimalmp().get(animal[i]).toString(), getWidth()*144/168+getWidth()*i*15/512+25, getHeight()*yPlay/4+getHeight()/16);
                }

                g2.setFont(new Font("Arial", Font.PLAIN, 18*getWidth()/1920));
                g2.drawString("Player "+pNum, getWidth()*13/16+10,getHeight()*(yPlay)/4+getHeight()*8/256);
                g2.drawImage(game.getPlayerList()[pNum-1].getPfp(), getWidth()*13/16+20+g2.getFontMetrics().stringWidth("Player "+pNum), getHeight()*yPlay/4+getHeight()/32-g2.getFontMetrics().getAscent()-5, 25, 25, null);
                g2.setFont(new Font("Arial", Font.BOLD, 15*getWidth()/1920));
                g2.drawImage(natureToken, getWidth()*7/8+getWidth()/128, getHeight()*yPlay/4+getHeight()/128+getHeight()/512, 25, 25, null);
                g2.drawString(": "+game.getPlayerList()[pNum-1].getNumTokens(), getWidth()*7/8+getWidth()/32-getWidth()/128, getHeight()*yPlay/4+getHeight()/32-getHeight()/256);
                g2.setFont(new Font("Arial", Font.PLAIN, 18*getWidth()/1920));
                g2.drawString("Current Score: "+ game.getPlayerList()[pNum - 1].getScore(), getWidth()*13/14-20, getHeight()*yPlay/4+getHeight()*7/256);
                g2.fillRect(getWidth()*13/16, getHeight()*(yPlay+1)/4, getWidth()*24/128, 2);
                yPlay++;
            }
        }



        for(int i = 0;i<5;i++) {
            g2.drawRect(getWidth()/7-i, getHeight()/8-i, getWidth() - getWidth() / 3+2*i, getHeight()*3/4 +2*i);
        }

        //System.out.println("tile" + tileChose);
        if((tileChose || (mixMatchUsed && state == 4) || noAnimalPlace) && (prog == 106 || prog == 105)) {
            cancelB.setVisible(true);
            if(noAnimalPlace) {
                g2.setFont(new Font("Arial", Font.PLAIN, 13));
                g2.drawString("There is no place for this animal. Please click cancel to replace the animal and end your turn.", getWidth()*1/50, getHeight()*23/25);
            }
            if(tileChose) {
                removeDups.setVisible(false);
                useNature.setVisible(false);
            }
        }
        //allow the user to choose not to keep animal
        else if(!noAnimalPlace && !clearAnimalsUsed && state == 3 && (prog == 106 || prog == 105)) {
            cancelB.setVisible(true);
            g2.setFont(new Font("Arial", Font.PLAIN, 15));
            g2.drawString("Click Cancel to not place an animal and end your turn.", getWidth()*1/50, getHeight()*23/25);
            g2.drawString("OR choose an available space to put the animal on your board.", getWidth()*1/50, getHeight()*24/25);
        }
        else {
            cancelB.setVisible(false);
            if(!tileChose && nodeSelected == null && (prog == 106 || prog == 105)) {
            	g2.setFont(new Font("Arial", Font.PLAIN, 20));
            	g2.drawString("Press on a habitat tile to place on the board.", getWidth()/30-30, getHeight()*19/20);
            }
        }
        if(nodeSelected!=null && state > 0) {
            //removeDups.setVisible(false);
            confirmB.setVisible(true);
            g2.drawImage(rotateImage, 125, getHeight()*2/3-2, 50, 55, null);
            rotate.setVisible(true);
        }
        else {
            confirmB.setVisible(false);
            rotate.setVisible(false);
        }

        if(game.getCurrPlayer().getNumTokens() == 0 || natureTokenUsed) {
            useNature.setVisible(false);
        }
        else if(!tileChose && state < 2){
            useNature.setVisible(true);
            clearAnimals.setVisible(false);
            mixMatch.setVisible(false);
            confirmClear.setVisible(false);
        }

        if(!dupAnimalsUsed && !tileChose) {
            //3 same animal
            if (game.cntDup()>=3 && state <2){
                removeDups.setVisible(true);
            }
            else{ // less than 3
                removeDups.setVisible(false);
            }

        }


        for (int i=0;i<4;i++){
            add(fourButtonTiles[i]);
            add(fourButtonAnimal[i]);
            int a = 6;
            g2.drawImage(tiles4[i], getWidth()/75, getHeight()/a+i*95, 75, 87, null);
            g2.drawImage(outline,   getWidth()/75, getHeight()/a+i*95, 75, 87, null);
            fourButtonTiles[i].setBounds(getWidth()/75, getHeight()/a+i*95, 75, 87);
            if (i == numSelectedTile){
                g2.drawImage(selectOutline, getWidth()/75, getHeight()/a+i*95, 75, 87, null);
            }
            if(!clearAnimalsUsed) {
                if (i==numSelectedAnimal&&drawHighlightAnimal) {
                    g2.drawImage(animalTokenMap.get(game.getAnimalToken4()[i])[1], 115, getHeight()/a+i*95+13, 60, 60, null);

                }else{
                    g2.drawImage(animalTokenMap.get(game.getAnimalToken4()[i])[0], 115, getHeight()/a+i*95+13, 60, 60, null);
                }
            }
            else {
                if(animalsToClear.contains(i)) {
                    g2.drawImage(animalTokenMap.get(game.getAnimalToken4()[i])[1], 115, getHeight()/a+i*95+13, 60, 60, null);
                }
                else {
                    g2.drawImage(animalTokenMap.get(game.getAnimalToken4()[i])[0], 115, getHeight()/a+i*95+13, 60, 60, null);
                }
            }
            fourButtonAnimal[i].setBounds(115, getHeight()/a+i*95+13, 60, 60);
        }


        //91 width = getWidth()/15; 51 height = getHeight()/15 
        rotate.setBounds(125, getHeight()*2/3, 50, 50);
        
        bp.setBounds(getWidth()/7, getHeight()/8, getWidth()*2/3, getHeight()*3/4);
        scorePB.setBounds(getWidth()-100, getHeight()-50, 100, 50);
        if (Constants.stop){
            
            g2.drawImage(nextImage, getWidth()-100, getHeight()-50, 100, 50, null);
            scorePB.setVisible(true);

        }

        //image and buttons
        cancelB.setBounds(getWidth()/30-30, getHeight()*3/5+getHeight()/10, 91, 51);
        nextB.setBounds(getWidth()/30-30, getHeight()*3/5+getHeight()/5, 91, 51); //only turn on for testing
        confirmB.setBounds(getWidth()/30-30, getHeight()*3/5+getHeight()/5+getHeight()/10, 91, 51);
        help.setBounds(getWidth()/3, getHeight()/25, 91, 51);
        scoreCards.setBounds(getWidth()/3+getWidth()/10, getHeight()/25, 91, 51);
        actionLog.setBounds(getWidth()/3+getWidth()/5, getHeight()/25, 91, 51);
        useNature.setBounds(getWidth()/3+getWidth()/5+getWidth()/10, getHeight()/25, 91, 51);
        removeDups.setBounds(getWidth()/3+getWidth()/5+getWidth()/5, getHeight()/25, 91, 51);
        confirmClear.setBounds(getWidth()/3, getHeight()/25, 91, 51);
        clearAnimals.setBounds(getWidth()/3, getHeight()/25, 91, 51);
        mixMatch.setBounds(getWidth()/3+getWidth()/10, getHeight()/25, 91, 51);
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

    public Game getGame() {
        return game;
    }

    public void nextA(){
        state++;
        game.updateAnimal4(numSelectedAnimal);

        numSelectedAnimal=-1;
        drawHighlightAnimal=false;

    }
    public void next(Node node){
        nodeSelected=node;
        tileChose = false;
        state++;
        //update deck
        game.updateTileDeck(numSelectedTile);
        numSelectedAnimal=numSelectedTile;
        try {
            tiles4[numSelectedTile] = ImageIO.read(getClass().getResource("img/Tile/" + game.getTileName4()[numSelectedTile] + ".png"));
        } catch (Exception E) {
            System.out.println("blah");
        }
        numSelectedTile=-1;
    }

    public void openWebPage(String url){
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        }
        catch (java.io.IOException e) {
            //System.out.println(e.getMessage());
        }
    }

    public void nextTurn() {
        
        game.nextTurn();
        bp.setBoard(game.getCurrPlayer().getBoard());
        state=0;
        dupAnimalsUsed = false;
        natureTokenUsed = false;
        mixMatchUsed = false;
        clearAnimalsUsed = false;
        noAnimalPlace = false;
        curVal="";
        numSelectedTile = -1;
        numSelectedAnimal =-1;
        curAnimal ="";
        rotate.setVisible(false);
        confirmB.setVisible(false);
        cancelB.setVisible(false);
        help.setVisible(true);
        scoreCards.setVisible(true);
        actionLog.setVisible(true);
        nextB.setVisible(false); //only for testing
        mini =0;
        game.addAction("Next Turn: Player "+(game.getCurPlayerNum()+1));
        if(game.getTurn() > 20) {

            if (!Constants.stop){
                p.add(new ScoringPanel(p, game), "Scoring panel");
                System.out.println("here");
                CardLayout cardLayout = (CardLayout) p.getLayout();

                cardLayout.show(p, "Scoring panel");
            }

        }
        repaint();
        //return;
    }

    public void infoBox(String message) {
        if(message.equals("score")) {
            try {
                scoreCard = ImageIO.read(getClass().getResource("img/CascadiaCards.jpg"));
            }
            catch(Exception e) {

            }
            ImageIcon card = new ImageIcon(scoreCard.getScaledInstance(1112, 514, Image.SCALE_SMOOTH));
            JOptionPane.showMessageDialog(null, "Bear: Group of 3\nElk: Group\nFox: Adjacent Unique Animals\nHawk: Individual\nSalmon: Run (2 or Less Neighbors)", "Cascadia Scoring Cards", JOptionPane.INFORMATION_MESSAGE, card);
        }
        else {
            JOptionPane.showMessageDialog(null, message, "Cascadia", JOptionPane.INFORMATION_MESSAGE);
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(nextB)){
            if (prog<100+ increment){
                prog=100+ increment;
                return;
            }
            nextTurn();

            return;
        }

        if (e.getSource().equals(scorePB)){
            CardLayout cardLayout = (CardLayout) p.getLayout();

            cardLayout.show(p, "Scoring panel");
        }

        if(e.getSource().equals(scoreCards)) {
            //show scoring cards
            infoBox("score");
        }

        if(e.getSource().equals(actionLog)) {
            actionLogUsed = !actionLogUsed;
            if(actionLogUsed) {
                game.getActionLog().clear();
                game.addAction("Action Log was turned on.");
            }
            repaint();
            return;
        }

        //help button -> open link
        if(e.getSource().equals(help)) {
            openWebPage("https://www.alderac.com/wp-content/uploads/2021/08/Cascadia-Rules.pdf");
        }

        if (Constants.stop){
            return;
        }

        if(e.getSource().equals(confirmClear)) {
            for (int i:animalsToClear){
                game.returnAnimalToken(game.getAnimalToken4()[i]);
                game.updateAnimal4(i);
            }
            clearAnimalsUsed = false;
            drawHighlightAnimal = false;
            confirmClear.setVisible(false);
            help.setVisible(true);
            scoreCards.setVisible(true);
            actionLog.setVisible(true);
            if(mini == 0) {
                dupAnimalsUsed = false;
            }
            animalsToClear.clear();
            state = 0;
            if(actionLogUsed) {
                game.addAction("Player "+(game.getCurPlayerNum()+1)+" confirmed clearing animals.");
            }
            natureTokenUsed = false;
            repaint();
            infoBox("You may now choose a tile and its adjacent token.");
            return;
        }

        if(e.getSource().equals(clearAnimals)) {
            clearAnimals.setVisible(false);
            mixMatch.setVisible(false);
            confirmClear.setVisible(true);
            clearAnimalsUsed = true;
            state = 3;
            if(actionLogUsed) {
                game.addAction("Player "+(game.getCurPlayerNum()+1)+" chose to clear animals.");
            }

            repaint();
        }
        if(e.getSource().equals(mixMatch)) {
            curAnimal="";
            numSelectedAnimal = -1;//basically u can choose any animal
            mixMatchUsed = true;
            clearAnimals.setVisible(false);
            mixMatch.setVisible(false);
            if(actionLogUsed) {
                game.addAction("Player "+(game.getCurPlayerNum()+1)+" chose to mix and match tile & token.");
            }
            infoBox("You may now choose a tile and any token you like afterwards.");
            help.setVisible(true);
            scoreCards.setVisible(true);
            actionLog.setVisible(true);
            repaint();
        }

        if(e.getSource().equals(useNature)) {
            if (!game.getCurrPlayer().useNt())
                return;
            help.setVisible(false);
            scoreCards.setVisible(false);
            actionLog.setVisible(false);
            useNature.setVisible(false);
            dupAnimalsUsed = true;
            removeDups.setVisible(false);
            clearAnimals.setVisible(true);
            mixMatch.setVisible(true);
            natureTokenUsed = true;
            if(actionLogUsed) {
                game.addAction("Player "+(game.getCurPlayerNum()+1)+" used a nature token.");
            }
            repaint();
            
        }


        //remove duplicate animals
        if(e.getSource().equals(removeDups) && !dupAnimalsUsed) {
            dupAnimalsUsed = true;
            mini = 1;
            game.removeDups();
            removeDups.setVisible(false);
            if(actionLogUsed) {
                game.addAction("Player "+(game.getCurPlayerNum()+1)+" chose to remove duplicate animals.");
            }
            repaint();
            return;
        }


        //select tile
        if(!natureTokenUsed || mixMatchUsed) {
	        for (int i=0;i<4;i++){
	            HexButton b = fourButtonTiles[i];
	            if (e.getSource().equals(b) && state==0 && !curVal.equals(game.getTileName4()[i])){
	                
	                curVal= game.getTileName4()[i];
	                
	                numSelectedTile=i;
	                if(!mixMatchUsed) {
	                    
	                    numSelectedAnimal=i;
	                    curAnimal = game.getAnimalToken4()[i];
	                    
	                }
	
	                nodeSelected=null;
	                state++;
	                tileChose = true;
	                if(actionLogUsed) {
	                    String habitats = curVal.substring(0,2);
	                    String animals = curVal.substring(3);
	                    int l = animals.length();
	                    if(habitats.contains("D")) habitats += " and Desert";
	                    if(habitats.contains("F")) habitats += " and Forest";
	                    if(habitats.contains("L")) habitats += " and Lake";
	                    if(habitats.contains("M")) habitats += " and Mountain";
	                    if(habitats.contains("S")) habitats += " and Swamp";
	                    habitats = habitats.substring(7);
	                    if(animals.contains("B")) animals += " and Bear";
	                    if(animals.contains("E")) animals += " and Elk";
	                    if(animals.contains("F")) animals += " and Fox";
	                    if(animals.contains("H")) animals += " and Hawk";
	                    if(animals.contains("S")) animals += " and Salmon";
	                    animals = animals.substring(l+5);
	
	                    game.addAction("Player "+(game.getCurPlayerNum()+1)+" picked tile: "+habitats+".");
	                    game.addAction("This tile can hold "+animals+".");
	                }
	
	                repaint();
	                return;
	            }
	            //cancel tile via clicking it
	            else if(curVal.equals(game.getTileName4()[i])){
	                curVal ="";
	                numSelectedTile = -1;
	                numSelectedAnimal = -1;
	                curAnimal = "";
	                state = 0;
	                tileChose = false;
	                if(actionLogUsed) {
	                    game.addAction("Player "+(game.getCurPlayerNum()+1)+" unselected their tile.");
	                }
	                repaint();
	                return;
	            }
	        }
        }
        //cancel tile
        if(e.getSource().equals(cancelB) && state==1) {
            curVal="";
            numSelectedTile = -1;
            numSelectedAnimal =-1;
            curAnimal ="";
            state = 0;
            tileChose = false;
            if(actionLogUsed) {
                game.addAction("Player "+(game.getCurPlayerNum()+1)+" unselected their tile.");
            }
            repaint();
            return;

        }
        //rotate angle
        if (nodeSelected!=null && e.getSource().equals(rotate) && state==2){
            nodeSelected.addRotateAngle();
            if(actionLogUsed) {
                game.addAction("Player "+(game.getCurPlayerNum()+1)+" rotated their tile.");
            }
            repaint();
            return;
        }
        //confirm tile placement
        if (e.getSource().equals(confirmB)&&state==2) {
            nodeSelected = null;
            if (!mixMatchUsed)
                drawHighlightAnimal = true;
            else
                infoBox("You may now choose any animal you would like.");
            dupAnimalsUsed = true;
            removeDups.setVisible(false);//turns off replace duplicate after placing tile
            state++;
            if(actionLogUsed) {
                game.addAction("Player "+(game.getCurPlayerNum()+1)+" confirmed their tile placement.");
            }
            //System.out.println(state);
            repaint();
            //return;
        }




        //clearAnimals
        if(state == 3 && clearAnimalsUsed) {
            for (int i=0;i<4;i++){
                InvisButton b = fourButtonAnimal[i];
                if (e.getSource().equals(b)){
                    
                    if(!animalsToClear.contains(i)) {
                        animalsToClear.add(i);
                    }
                    else {
                        animalsToClear.remove(i);
                    }
                    repaint();
                    return;
                }
            }
        }
        //pick animal (mix&match)
        else if(state == 3 && mixMatchUsed) {
            for (int i=0;i<4;i++){
                InvisButton b = fourButtonAnimal[i];
                //cancel animal by clicking on it
                if (e.getSource().equals(b) && i==numSelectedAnimal) {
                    numSelectedAnimal = -1;
                    curAnimal = "";
                    drawHighlightAnimal = false;
                    cancelB.setVisible(false);
                    repaint();
                    return;
                }
                else if (e.getSource().equals(b)){
                    numSelectedAnimal = i;
                    curAnimal = game.getAnimalToken4()[i];
                    drawHighlightAnimal = true;
                    cancelB.setVisible(true);
                    repaint();
                    return;
                }
            }
        }

        //no place to put animal
        else if (state == 3 && !game.getAnimalAllowed(game.getCurrPlayer().getBoard(), curAnimal)) {
            cancelB.setVisible(true);
            noAnimalPlace = true;
            drawHighlightAnimal = false;
            state++;
            if(actionLogUsed) {
                

                if(curAnimal.equals("B")) {
                    game.addAction("Player "+(game.getCurPlayerNum()+1)+ " had no place to put a bear token.");
                }
                if(curAnimal.equals("E")) {
                    game.addAction("Player "+(game.getCurPlayerNum()+1)+ " had no place to put an elk token.");
                }
                if(curAnimal.equals("F")) {
                    game.addAction("Player "+(game.getCurPlayerNum()+1)+ " had no place to put a fox token.");
                }
                if(curAnimal.equals("H")) {
                    game.addAction("Player "+(game.getCurPlayerNum()+1)+ " had no place to put a hawk token.");
                }
                if(curAnimal.equals("S")) {
                    game.addAction("Player "+(game.getCurPlayerNum()+1)+ " had no place to put a salmon token.");
                }
            }
            repaint();

            
        }

        else if(state == 3 && game.getAnimalAllowed(game.getCurrPlayer().getBoard(), curAnimal)) {
            cancelB.setVisible(true);
            repaint();
        }

        if(e.getSource().equals(cancelB)&&state==3 &&!mixMatchUsed) {
            if(actionLogUsed) {
                game.addAction("Player "+(game.getCurPlayerNum()+1)+" chose not to place an animal token.");
            }
            drawHighlightAnimal = false;
            resetProg();
            
            repaint();
            return;
        }


        if(e.getSource().equals(cancelB)&&state==4 &&!mixMatchUsed) {
            game.returnAnimalToken(game.getAnimalToken4()[numSelectedAnimal]);
            game.updateAnimal4(numSelectedAnimal);
            resetProg();
           
            repaint();
            return;
        }

        //cancel animal chosen wrong in mix and match
        if (e.getSource().equals(cancelB)&&state==4 &&mixMatchUsed){
            curAnimal="";
            numSelectedAnimal=-1;
            drawHighlightAnimal=false;
            repaint();

            state--;


            return;
        }
    }
    public void resetProg(){
        prog = 0;
    }
    public void wait(int x){
        try{
            Thread.sleep(x);
        }
        catch (Exception E){

        }
    }
}