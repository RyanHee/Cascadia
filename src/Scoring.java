import java.util.HashMap;
import java.util.HashSet;

public class Scoring {
    private HashMap<Integer, Integer> opp;
    private HashMap<String, Integer> landScore, animalScore;
    private HashSet<Node> landVisited, bVisited, fVisited, hVisited, eVisited, sVisited;
    private String[]land;
    private int bearBScore, foxAScore, hawkAcnt, elkCScore, salmonCScore;
    private boolean sValid;
    public Scoring(){
        opp = new HashMap<>();
        animalScore=new HashMap<>();
        opp.put(0,3);
        opp.put(1,4);
        opp.put(2,5);
        opp.put(3,0);
        opp.put(4,1);
        opp.put(5,2);
        land=new String[]{"D", "F", "S", "L", "M"};
        landScore=new HashMap<>();
        for (String s:land){
            landScore.put(s, 0);
        }
        //landScore.put("B", 0);//add bonus
    }

    public int score(Node node){

        animalScore=new HashMap<>();
        int score=landMass(node);
        score+=bearB(node);
        score+=elkC(node);
        score+=foxA(node);
        score+=hawkA(node);
        score+=salmonC(node);


        //System.out.println("Land: D" + landScore.get("D") + "F" + landScore.get("F") +"S" + landScore.get("S") + "L" + landScore.get("L") + "M" + landScore.get("M")+"Bonus"+landScore.get("B"));
        //add bonus habitat score here
        //System.out.println("Animal: B" + animalScore.get("B") + "E" + animalScore.get("E") +"F" + animalScore.get("F") + "H" + animalScore.get("H") + "S" + animalScore.get("S"));
        return score;
    }


    public HashMap<String, Integer> getAnimalmp(){
        //System.out.println(animalScore);
        return animalScore;
    }


    public Integer getAnimalScore(){
        int sum=0;
        for (String s:animalScore.keySet()){
            sum+=animalScore.get(s);
        }
        return sum;
    }

    public Integer getLandScore(){
        int sum =0;
        for(int i =0; i<landScore.size();i++) {
            sum+=landScore.get(land[i]);
        }
        //System.out.println("total land"+sum);
        return sum;
    }

    public HashMap<String, Integer> getLandScoreMap() {
        return landScore;
    }

    public int landMass(Node node){
        int s=0;

        landScore=new HashMap<>();
        for (String l:land){
            landScore.put(l, 1);
        }
        for (int i=0;i<5;i++){
            landVisited =new HashSet<>();
            dfsLand(node, land[i]);
            //System.out.println(land[i]+landScore);
            s+=landScore.get(land[i]);
        }
        //System.out.println(landScore);
        return s;
    }

    public int bearB(Node node){
        bearBScore=0;
        bVisited=new HashSet<>();
        dfsBear(node);
        animalScore.put("B", bearBScore);
        return bearBScore;
    }

    public int elkC(Node node){
        elkCScore=0;
        eVisited=new HashSet<>();
        dfsElk(node);
        animalScore.put("E", elkCScore);
        return elkCScore;
    }

    public int foxA(Node node){
        foxAScore=0;
        fVisited=new HashSet<>();
        dfsFox(node);
        animalScore.put("F", foxAScore);
        return foxAScore;
    }

    public int hawkA(Node node){
        hVisited=new HashSet<>();
        hawkAcnt=0;
        dfsHawk(node);
        //System.out.println(hawkAcnt);
        if (hawkAcnt<1) {
            animalScore.put("H", 0);
            return 0;
        }
        if (hawkAcnt==1) {
            animalScore.put("H", 2);
            return 2;
        }
        if (hawkAcnt<6) {
            animalScore.put("H", hawkAcnt*3-1);
            return hawkAcnt*3-1;
        }
        if (hawkAcnt==6) {
            animalScore.put("H", 18);
            return 18;
        }
        if (hawkAcnt==7) {
            animalScore.put("H", 22);
            return 22;
        }
        animalScore.put("H", 28);
        return 28;
    }


    public int salmonC (Node node){
        salmonCScore =0;
        sVisited=new HashSet<>();
        dfsSalmon(node);
        animalScore.put("S", salmonCScore);
        return salmonCScore;
    }

    private void dfsLand(Node node, String land){
        if (landVisited.contains(node)){
            return ;
        }
        if (node.getVal()==null || node.getVal().isEmpty()){
            return ;
        }
        landVisited.add(node);
        if (node.hasLand(land)){
            // System.out.println("a"+land);
            landScore.replace(land, Math.max(landScore.get(land), scoreLand(node, land, new HashSet<>(), 0)));
        }
        for (int i=0;i<6;i++){
            dfsLand(node.getNeighbors()[i], land);
        }
    }

    private void dfsBear(Node n){
        int a;
        if (bVisited.contains(n)){
            return;
        }
        if (n==null){
            return;
        }

        if (n.getAnimal().equals("B")){
            //System.out.println("at bear");
            a = cntBear(n,0, new HashSet<>());

            //System.out.println(a);
            if (a==3){
                bearBScore+=10;
            }
        }
        bVisited.add(n);
        for (Node c:n.getNeighbors()){
            dfsBear(c);
        }
    }

    private void dfsElk(Node n){
        if (n==null)
            return;
        if (eVisited.contains(n))
            return;
        eVisited.add(n);
        if (n.getAnimal().equals("E")){
            int cnt = cntElk(n, 0, new HashSet<>());
            if (cnt<3){
                elkCScore+=cnt*2;
            }
            if (cnt==3) elkCScore+=7;
            if (cnt==4) elkCScore+=10;
            if (cnt==5) elkCScore+=14;
            if (cnt==6) elkCScore+=18;
            if (cnt==7) elkCScore+=23;
            if (cnt>=8) elkCScore+=28;
        }
        for (Node c:n.getNeighbors())
            dfsElk(c);
    }

    private void dfsFox(Node n){
        if (n==null)
            return;
        if (fVisited.contains(n))
            return;
        fVisited.add(n);
        if (n.getAnimal().equals("F")){
            HashSet<String>st=new HashSet<>();
            for (Node c:n.getNeighbors())
                st.add(c.getAnimal());
            foxAScore+=st.size()-1;
        }
        for (Node c:n.getNeighbors())
            dfsFox(c);
    }

    private void dfsHawk(Node n){
        if (n==null)
            return;
        if (hVisited.contains(n))
            return;
        hVisited.add(n);
        if (validHawk(n))
            hawkAcnt++;
        for (Node c:n.getNeighbors())
            dfsHawk(c);
    }

    private void dfsSalmon(Node n){
        if (n==null)
            return;
        if (sVisited.contains(n))
            return;
        sVisited.add(n);
        if (n.getAnimal().equals("S")){
            sValid=true;
            int cnt = cntSalmon(n, 0, new HashSet<>());
            if (sValid){
                if (cnt==3) salmonCScore+=10;
                if (cnt==4) salmonCScore+=12;
                if (cnt>=5) salmonCScore+=15;
                //System.out.println("numSal" +cnt);
            }
        }
        for (Node c:n.getNeighbors())
            dfsSalmon(c);
    }



    private int scoreLand(Node node, String land, HashSet<Node> visited, int sum){
        if (visited.contains(node)){
            return 0;
        }
        if (!node.hasLand(land)){
            return 0;
        }
        visited.add(node);
        landVisited.add(node);
        Node[]lst = node.getNeighbors();
        //System.out.println("curr: "+node+" score: "+sum);
        int s = sum;
        for (int i=0;i<6;i++){
            //System.out.println(lst[i]);
            if (node.getSides()[i]!=null && node.getSides()[i].equals(land)){
                //System.out.println("over here");
                if (lst[i]!= null && lst[i].getSides()[opp.get(i)]!=null && lst[i].getSides()[opp.get(i)].equals(land)){
                    //System.out.println(land);
                    //System.out.println("side: "+i+" "+lst[i].getSides()[opp.get(i)]);
                    //System.out.println("go to: " + i + " " + lst[i] + " score: " + sum);
                    sum += scoreLand(lst[i], land, visited, s);
                    //System.out.println("after go to: " + i + " " + lst[i] + " score: " + sum);

                }
            }
        }
        sum++;
        //System.out.println("before returning curr: "+node+" score: "+sum);
        return sum;
    }


    private int cntBear(Node n, int cnt, HashSet<Node>visited){
        if (!n.getAnimal().equals("B")){
            return 0;
        }
        if (visited.contains(n)){
            return 0;
        }
        visited.add(n);
        bVisited.add(n);
        int s = cnt;
        for (Node c:n.getNeighbors()){
            if (c.getAnimal().equals("B"))
                cnt+=cntBear(c, s, visited);
        }
        cnt++;
        return cnt;
    }

    private int cntElk(Node n, int cnt, HashSet<Node>visited){
        if (!n.getAnimal().equals("E"))
            return 0;
        if (visited.contains(n))
            return 0;
        visited.add(n);
        eVisited.add(n);
        int s = cnt;
        for (Node c:n.getNeighbors()){
            if (c.getAnimal().equals("E")){
                cnt+=cntElk(c, s, visited);
            }
        }
        cnt++;
        return cnt;
    }

    private int cntSalmon(Node n, int cnt, HashSet<Node>visited){
        if (!n.getAnimal().equals("S"))
            return 0;
        if (visited.contains(n))
            return 0;

        visited.add(n);
        sVisited.add(n);
        int a=0;
        int s = cnt;
        for (Node c:n.getNeighbors()){
            if (c.getAnimal().equals("S"))
                a++;
        }
        if (a>2) {
            sValid=false;
            return 0;
        }
        for (Node c:n.getNeighbors()){
            if (c.getAnimal().equals("S"))
                cnt+=cntSalmon(c, s, visited);
        }
        cnt++;
        return cnt;
    }

    private boolean validHawk(Node n){
        if (!n.getAnimal().equals("H"))
            return false;
        for (Node c:n.getNeighbors()){
            if (c.getAnimal().equals("H"))
                return false;
        }
        return true;
    }

}