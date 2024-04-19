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
    private static BufferedImage natureToken;
    private Node nodeSelected;
    private HexButton rotate;

    private BufferedImage[] tiles4;
    private HexButton[] fourButtonTiles;
    private InvisButton[]fourButtonAnimal;
    private boolean dupAnimalsUsed = false, natureTokenUsed = false, mixMatchUsed = false, clearAnimalsUsed = false;
    private int state;
    private boolean drawHighlightAnimal;
    private JButton confirmB, cancelB, nextB;
    private JButton help, scoreCards, actionLog, useNature, removeDups;
    private JButton confirmClear, clearAnimals, mixMatch;
    private ArrayList<Integer> animalsToClear = new ArrayList<>();
    private String curVal, curAnimal;
    private BoardPanel bp;
    private BufferedImage dpad;
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
            natureToken=ImageIO.read(new File("img/tokens/nature-token.png"));


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
        help=new JButton("Help");
        scoreCards=new JButton("Scoring Cards");
        actionLog=new JButton("Action Log");
        useNature=new JButton("Use Nature Token");
        removeDups=new JButton("Remove Duplicate Tokens");
        confirmClear = new JButton("Confirm Clearing Animals");
        clearAnimals = new JButton("Clear X Animals");
        mixMatch = new JButton("Mix & Match tile & token");
        
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
        g.setColor(new Color(0,0,0));
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Turn "+game.getTurn(), getWidth()/40-20, 40);
        g.setFont(new Font("Arial", Font.PLAIN, 25));
        g.drawString("Player "+(game.getPlayerNum()+1), getWidth()/15+30, 40);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawImage(natureToken, getWidth()/5, 10, 50, 50, null);
        g.drawString(": "+game.getCurrPlayer().getNumTokens(), getWidth()/5+60, 40);
        g.drawString("Current Score: "+String.valueOf(game.curPlayerScore()), getWidth()/15+20, 70);
        int yPlay = 0;
        for(int pNum = 1; pNum<5; pNum++) {
        	if(pNum != game.getPlayerNum()+1) {
        		g.setFont(new Font("Arial", Font.PLAIN, 18));
        		g.drawString("Player "+pNum, getWidth()*13/16 +10, getHeight()*yPlay/4 +50);
        		g.setFont(new Font("Arial", Font.PLAIN, 15));
        		g.drawImage(natureToken, getWidth()*7/8+10, getHeight()*yPlay/4+30, 30, 30, null);
        		g.drawString(": "+game.getPlayerList()[pNum-1].getNumTokens(), getWidth()*7/8+40, getHeight()*yPlay/4+50);
        		g.setFont(new Font("Arial", Font.PLAIN, 10));
        		g.drawString("Current Score: "+String.valueOf(game.curPlayerScore()), getWidth()*13/14, getHeight()*yPlay/4+50);
        		yPlay++;
        		//draw other players boards (but not as buttons)
        		//drawBoard(g, game.getCurrPlayer().getBoard(), getWidth()*13/16 +10, getHeight()*yPlay/4 +70);
        	}
        }
        for(int i = 0;i<5;i++) {
            g.drawRect(getWidth()/7-i, getHeight()/8-i, getWidth() - getWidth() / 3+2*i, getHeight()*3/4 +2*i);
        }
        bp.setBounds(getWidth()/7, getHeight()/8, getWidth() - getWidth() / 3, getHeight()*3/4);
        add(cancelB);
        cancelB.setBounds(getWidth()/30-30, getHeight()*3/5+getHeight()/10, getWidth()/15-10, getHeight()/15);
        add(nextB);
        nextB.setBounds(getWidth()/30-30, getHeight()*3/5+getHeight()/5, getWidth()/15-10, getHeight()/15);
        add(confirmB);
        confirmB.setBounds(getWidth()/30-30, getHeight()*3/5+getHeight()/5+getHeight()/10, getWidth()/15-10, getHeight()/15);
        add(help);
        help.setBounds(getWidth()/3, getHeight()/25, getWidth()/15, getHeight()/15);
        add(scoreCards);
        scoreCards.setBounds(getWidth()/3+getWidth()/10, getHeight()/25, getWidth()/15, getHeight()/15);
        add(actionLog);
        actionLog.setBounds(getWidth()/3+getWidth()/5, getHeight()/25, getWidth()/15, getHeight()/15);
        add(useNature);
        useNature.setBounds(getWidth()/3+getWidth()/5+getWidth()/10, getHeight()/25, getWidth()/15, getHeight()/15);
        add(removeDups);
        removeDups.setBounds(getWidth()/3+getWidth()/5+getWidth()/5, getHeight()/25, getWidth()/15, getHeight()/15);
        add(confirmClear);
        confirmClear.setBounds(getWidth()/3, getHeight()/25, getWidth()/15, getHeight()/15);
        add(clearAnimals);
        clearAnimals.setBounds(getWidth()/3, getHeight()/25, getWidth()/15, getHeight()/15);
        add(mixMatch);
        mixMatch.setBounds(getWidth()/3+getWidth()/10, getHeight()/25, getWidth()/15, getHeight()/15);
        if(!natureTokenUsed) {
        	clearAnimals.setVisible(false);
        	mixMatch.setVisible(false);
        	confirmClear.setVisible(false);
        }
        
        if(game.getCurrPlayer().getNumTokens() == 0) {
        	useNature.setVisible(false);
        }
        else {
        	useNature.setVisible(true);
        }
        //only show removeDups when 3 animals are same
        
        //4 animals are same
        if(game.getAnimalToken4()[0]==game.getAnimalToken4()[1] && game.getAnimalToken4()[2]==game.getAnimalToken4()[3] && game.getAnimalToken4()[1]==game.getAnimalToken4()[2]) {
        	for(int i =0; i<4; i++) {
        		//System.out.println("animal tokens: "+game.getAnimalToken4()[i]);
        		game.returnAnimalToken(game.getAnimalToken4()[i]);
        		game.updateAnimalDeck(i);
        	}
        }
        boolean match12 = (game.getAnimalToken4()[0]==game.getAnimalToken4()[1]);
        boolean match34 = (game.getAnimalToken4()[2]==game.getAnimalToken4()[3]);
        boolean match23 = (game.getAnimalToken4()[1]==game.getAnimalToken4()[2]);
        //System.out.println(match12 +""+ match34 + ""+ match23);
        if(!dupAnimalsUsed) {
        	//2 or 1 animals are same
	        if((match12 && match34 && !match23) || (!match12 && !match34 && match23) || (!match12 && !match34 && !match23)) {
	        	removeDups.setVisible(false);
	        }
	        //3 animals are same
	        else if((match12 && !match34 && match23) || (!match12 && match34 && match23)) {
	        	removeDups.setVisible(true);
	        }
	        //2 animals are same (3rd could exist)
	        else {
	        	removeDups.setVisible(game.getAnimalToken4()[0]==game.getAnimalToken4()[3]);
	        }
        }
        //g.drawImage(dpad, 800, 600, 240, 240, null);


        g.drawImage(rotateImage, 125, 488, 50, 55, null);
        add(rotate);
        rotate.setBounds(125, 490, 50, 50);
        //left.showButton();
        
        for (int i=0;i<4;i++){
            add(fourButtonTiles[i]);
            add(fourButtonAnimal[i]);
            g.drawImage(tiles4[i], getWidth()/75, getHeight()/8+i*95, 75, 87, null);
            g.drawImage(outline,   getWidth()/75, getHeight()/8+i*95, 75, 87, null);
            fourButtonTiles[i].setBounds(getWidth()/75, getHeight()/8+i*95, 75, 87);
            if (i == numSelectedTile){
                g.drawImage(selectOutline, getWidth()/75, getHeight()/8+i*95, 75, 87, null);
            }
            if(!clearAnimalsUsed) {
	            if (i==numSelectedAnimal&&drawHighlightAnimal) {
	                g.drawImage(animalTokenMap.get(game.getAnimalToken4()[i])[1], 115, getHeight()/8+i*95, 60, 60, null);
	
	            }else{
	                g.drawImage(animalTokenMap.get(game.getAnimalToken4()[i])[0], 115, getHeight()/8+i*95, 60, 60, null);
	            }
            }
            else {
            	if(animalsToClear.contains(i)) {
            		g.drawImage(animalTokenMap.get(game.getAnimalToken4()[i])[1], 115, getHeight()/8+i*95, 60, 60, null);
            	}
            	else {
            		g.drawImage(animalTokenMap.get(game.getAnimalToken4()[i])[0], 115, getHeight()/8+i*95, 60, 60, null);
            	}
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
    
    public Game getGame() {
    	return game;
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
    
    public void openWebPage(String url){
	   try {         
		   java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
	   }
	   catch (java.io.IOException e) {
	       System.out.println(e.getMessage());
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
        help.setVisible(true);
    	scoreCards.setVisible(true);
        actionLog.setVisible(true);
        if(game.getTurn() >= 20) {
        	//end the game
        }
        //repaint();
        //return;
    }
    
    /*public void drawBoard(Graphics g, Node n, int x, int y) {
    	HashSet<Node>visited = new HashSet<Node>();
    	int w=116;
        int h=116;
    	if(n == null) {
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
        g2.drawImage(n.getImg(), x + 8, y, w * 25 / 58, h/2, null);
        g.drawImage(outline, x+8, y, w*25/58, h/2, null);

        if (animalTokenMap.get(n.getAnimal())!=null){
            //System.out.println("animal: "+n.getAnimal());
            g.drawImage(animalTokenMap.get(n.getAnimal())[0], (x-17+w*25/116), (y-25+h/4), 50, 50, null);
        }
        g2.dispose();

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
            drawBoard(g, n.getNeighbors()[i], nx[i], ny[i]);
        }
    }*/
    
    @Override
    public void actionPerformed(ActionEvent e) {
       
        System.out.println(state);
        
        if(e.getSource().equals(confirmClear)) {
        	for(int i=animalsToClear.size()-1; i>-1; i--) {
        		int hold = animalsToClear.get(i);
        		game.returnAnimalToken(game.getAnimalToken4()[hold]);
            	game.updateAnimalDeck(hold);
        	}
        	clearAnimalsUsed = false;
        	drawHighlightAnimal = false;
        	confirmClear.setVisible(false);
        	animalsToClear.clear();
        	state = 0;
        	repaint();
    		return;
        }
        
        if(e.getSource().equals(clearAnimals)) {
        	clearAnimals.setVisible(false);
            mixMatch.setVisible(false);
            confirmClear.setVisible(true);
            clearAnimalsUsed = true;
            state = 3;
        }
        if(e.getSource().equals(mixMatch)) {
        	//numSelectedAnimal = -1;//basically u can choose any animal
        	mixMatchUsed = true;
        	clearAnimals.setVisible(false);
            mixMatch.setVisible(false);
        }
        
        if(e.getSource().equals(useNature)) {
        	help.setVisible(false);
        	scoreCards.setVisible(false);
            actionLog.setVisible(false);
            useNature.setVisible(false);
            removeDups.setVisible(false);
            clearAnimals.setVisible(true);
            mixMatch.setVisible(true);
            natureTokenUsed = true;
            game.getCurrPlayer().useNt();//remove token (token >0 -> nature token buttons appears)
        }
        
        //help button -> open link
        if(e.getSource().equals(help)) {
        	openWebPage("https://www.alderac.com/wp-content/uploads/2021/08/Cascadia-Rules.pdf");
        }
        //remove duplicate animals
        if(e.getSource().equals(removeDups) && !dupAnimalsUsed) {
        	dupAnimalsUsed = true;
        	boolean match12 = (game.getAnimalToken4()[0]==game.getAnimalToken4()[1]);
            boolean match34 = (game.getAnimalToken4()[2]==game.getAnimalToken4()[3]);
            boolean match23 = (game.getAnimalToken4()[1]==game.getAnimalToken4()[2]);
            boolean match14 = (game.getAnimalToken4()[0]==game.getAnimalToken4()[3]);
            if(match12 && !match34 && match23) {
            	for(int i =0; i<3; i++) {
            		game.returnAnimalToken(game.getAnimalToken4()[i]);
            		game.updateAnimalDeck(i);
            	}
            }
            else if(!match12 && match34 && match23) {
            	for(int i =1; i<4; i++) {
            		game.returnAnimalToken(game.getAnimalToken4()[i]);
            		game.updateAnimalDeck(i);
            	}
            }
            else if(match12 && !match34 && !match23 && match14) {
            	for(int i =0; i<4; i++) {
            		game.returnAnimalToken(game.getAnimalToken4()[i]);
            		game.updateAnimalDeck(i);
            		if(i==1) 
            			i=2;
            	}
            }
            else {
            	for(int i =0; i<4; i++) {
            		game.returnAnimalToken(game.getAnimalToken4()[i]);
            		game.updateAnimalDeck(i);
            		if(i==0) 
            			i=1;
            	}
            }
            removeDups.setVisible(false);
            repaint();
            return;
        }
        
        //will need to remove later on
        if (e.getSource().equals(nextB)){
            game.nextTurn();
            bp.setBoard(game.getCurrPlayer().getBoard());
            state=0;
            dupAnimalsUsed = false;
            natureTokenUsed = false;
            mixMatchUsed = false;
            clearAnimalsUsed = false;
            help.setVisible(true);
        	scoreCards.setVisible(true);
            actionLog.setVisible(true);
            if(game.getTurn() >= 20) {
            	//end the game
            }
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
                if(!mixMatchUsed) {
                	numSelectedAnimal=i;
                	curAnimal = game.getAnimalToken4()[i];
                	//state = 3;
                }
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
            //return;
        }

        if (state==3){

        	//clearAnimals
        	if(clearAnimalsUsed) {
	        	for (int i=0;i<4;i++){
	                InvisButton b = fourButtonAnimal[i];
	                if (e.getSource().equals(b)){
	                	System.out.println("click to clear");
	                	if(!animalsToClear.contains(i)) {
	                		animalsToClear.add(i);
	                	}
	                	else {
	                		animalsToClear.remove(Integer.valueOf(i));
	                	}
	                	numSelectedAnimal = i;
	                	drawHighlightAnimal = true;
	                	repaint();
	                	return;
	                }
	    		}
        	}
            //pick animal (mix&match)
        	else if(mixMatchUsed) {
        		System.out.println("grr");
        		for (int i=0;i<4;i++){
                    InvisButton b = fourButtonAnimal[i];
                    if (e.getSource().equals(b)){
                    	System.out.println("click");
                    	numSelectedAnimal = i;
                    	curAnimal = game.getAnimalToken4()[i];
                    	state++;
                    	drawHighlightAnimal = true;
                    	repaint();
                    	return;
                    }
        		}
        	}
        	//pick animal regular
        	else if (!mixMatchUsed /*&& fourButtonAnimal[numSelectedAnimal].equals(e.getSource())*/){
                //curAnimal=game.getAnimalToken4()[numSelectedAnimal];
        		//System.out.println("whats up");
                state++;
                //System.out.println("placed animal");
                repaint();
                return;
            }
            //cancel animal
            else if (e.getSource().equals(cancelB)){
            	game.returnAnimalToken(game.getAnimalToken4()[numSelectedAnimal]);
            	game.updateAnimalDeck(numSelectedAnimal);
                curAnimal="";
                numSelectedAnimal=-1;
                drawHighlightAnimal=false;
                repaint();
                state+=2;
                nextTurn();
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