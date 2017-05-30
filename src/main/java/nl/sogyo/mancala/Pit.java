package nl.sogyo.mancala;


public class Pit extends BoardMember {
    
    public Pit(){
        super(6);
        
    }
    
    public Pit(int howManyPits){
        super(howManyPits);
    }
    
    public Pit(BoardMember firstPit, int totalInstancesCreated, Contestant firstPlayer, int totalPitsPerPlayer){
        super(firstPit, totalInstancesCreated, firstPlayer, totalPitsPerPlayer);
    }
    
    @Override
    public void pickThisBoardMember(){
        if (ownerIsActive() == true){
            playThisPit();
        } else {
            System.out.println("You cannot choose the opponents side..");
        }
    }
    
    void playThisPit(){
        if (getTotalStones() == 0) {
            System.out.println("There are no stones in here. Pick again..");
        } else {
            int stonesToPassOn=getTotalStones();
            emptyStones();
            getNeighbour().takeAndPassStones(stonesToPassOn);
            accessGame();
        }
    }
    
    @Override
    void receiveStones(int stonesReceived){
    }
        
    void subtractStones(int stonesReceived){
        super.receiveStones(-stonesReceived);
    }
    
}
