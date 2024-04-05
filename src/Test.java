import java.io.FileNotFoundException;

public class Test {

    public static void main(String[] args) throws FileNotFoundException {
        //Game game=new Game();
        Scoring scoring=new Scoring();
        Node n = new Node("", "MF-FBE");
        n.getNeighbors()[0].setVal("FF-E");

        System.out.println(scoring.landMass(n));
    }
}
