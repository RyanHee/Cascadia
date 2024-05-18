import java.io.FileNotFoundException;

public class Test {

    public static void main(String[] args) throws FileNotFoundException {
        //Game game=new Game();
        Scoring scoring=new Scoring();
        Node n = new Node("", "MF-FBE");
        n.getNeighbors()[0].setVal("FF-E");
        int x=29;
        int w=29;
        System.out.println((double) x+8+ w*50/116);
    }
}
