package nl.sogyo.mancala;

public abstract class BoardMember {
    int TotalPitsPerPlayer;
    private int totalStones = 4;
    private BoardMember neighbour;
    private Contestant owner;
       
    public BoardMember(int howManyPitsPerPlayer){
        BoardMember firstPit = this;
        TotalPitsPerPlayer = howManyPitsPerPlayer;
        
        Contestant firstPlayer = new Contestant();
        owner = firstPlayer;
        neighbour = new Pit(firstPit, 2, firstPlayer, TotalPitsPerPlayer);
        
    }

    public BoardMember(BoardMember firstPit, int instancesOfPitsCreated, Contestant firstPlayer, int numberPitsPerPlayer){
        owner = firstPlayer;
        TotalPitsPerPlayer = numberPitsPerPlayer;
        if (instancesOfPitsCreated == TotalPitsPerPlayer  || instancesOfPitsCreated == (TotalPitsPerPlayer*2)+1 ) {
            neighbour = new Kalaha(firstPit, instancesOfPitsCreated+1, firstPlayer, TotalPitsPerPlayer);
        } else if (instancesOfPitsCreated == TotalPitsPerPlayer+1 ) {
            //First Kalaha created. Creates new pits with opponent as their owner
            neighbour = new Pit(firstPit, instancesOfPitsCreated+1, firstPlayer.getOpponent(), TotalPitsPerPlayer);
        } else if (instancesOfPitsCreated == (TotalPitsPerPlayer+1)*2 ) {
             //Reached Second Kalaha, stop chain and link kalaha to first pit
            neighbour = firstPit;
        } else {
            neighbour = new Pit(firstPit, instancesOfPitsCreated+1, firstPlayer, TotalPitsPerPlayer);
        }
    }

    public int getTotalStones(){
        return totalStones;
    }
    
    public Contestant getOwner(){
        return owner;
    }     
    
    public BoardMember getNeighbour(){
        return neighbour; 
    }
    
    public BoardMember getOpposingBoardMember(){
        BoardMember myNeighboursOpposingBoardMember = neighbour.getOpposingBoardMember();
        return myNeighboursOpposingBoardMember.getNeighbour();
    }
    
    private String getMyClass(BoardMember toCheckForType){
        return toCheckForType.getClass().getSimpleName();
    }
        
    public boolean ownerIsActive(){
        return getOwner().getIsActiveTurn() == true;
    }
    
    public BoardMember findFirstPitOfActivePlayer(){
        if (ownerIsNOTActive(this) && getMyClass(this).equals("Kalaha")) {
            return this.getNeighbour();
        }
        return getNeighbour().findFirstPitOfActivePlayer();
    }
         
    private boolean ownerIsNOTActive(BoardMember toCheckForActiveState){
        return toCheckForActiveState.ownerIsActive() == false;
    }

    void takeAndPassStones(int stonesToPassOn) {
        totalStones++;
        stonesToPassOn--;
        if (stonesToPassOn == 0) {
           endTurnSequence();
        } else {
            neighbour.takeAndPassStones(stonesToPassOn);
        }
    }
         
    void endTurnSequence(){
        if (wasEmpty() && ownerIsActive()) {
            BoardMember findTheActiveKalaha = getActiveKalaha();
            captureMe(findTheActiveKalaha);
            getOpposingBoardMember().captureMe(findTheActiveKalaha);
        }
        
        owner.switchIsActiveTurn();
        owner.getOpponent().switchIsActiveTurn();
        accessGame();
    }

    public BoardMember getActiveKalaha(){
        if (getMyClass(this).equals("Kalaha") == true && ownerIsActive()){
            return this;
        }
        return neighbour.getActiveKalaha();
    }
    
    private boolean wasEmpty(){
        return totalStones == 1;
    }

    void captureMe(BoardMember activeKalaha){
        int stonesToGiveAway = totalStones;
        activeKalaha.receiveStones(stonesToGiveAway);
        emptyStones();
    }
    
    void receiveStones(int stonesReceived){
        totalStones += stonesReceived;
    }        
    
    void emptyStones(){
        totalStones = 0;
    }
    
    void accessGame(){
        BoardMember firstPitOfActivePlayer = findFirstPitOfActivePlayer();
        int TotalStonesOfPlayerInPits = firstPitOfActivePlayer.howManyStonesOnPlayerSide();
        
        if (TotalStonesOfPlayerInPits == 0){
            sweepRemainingStonesToKalaha(firstPitOfActivePlayer.getActiveKalaha());
            System.out.println("THE GAME IS OVER");
        }
    }
    
    public int howManyStonesOnPlayerSide(){
        int thisManySofar = getTotalStones() + neighbour.howManyStonesOnPlayerSide();
        return thisManySofar;
    }
    
    public BoardMember getNextKalaha(){
        return neighbour.getNextKalaha();
    }
    
    void sweepRemainingStonesToKalaha(BoardMember theActivePlayersKalaha){
        
        BoardMember firstPitOfInactivePlayer = theActivePlayersKalaha.getNeighbour();
        int TotalStonesLeft = firstPitOfInactivePlayer.howManyStonesOnPlayerSide();
        firstPitOfInactivePlayer.emptyAllPits();

        BoardMember theInactivePlayersKalaha = firstPitOfInactivePlayer.getNextKalaha();
        theInactivePlayersKalaha.receiveStones(TotalStonesLeft);
        decideVictoriousPlayer(theInactivePlayersKalaha);
    }
    
    void emptyAllPits(){
        emptyStones();
        neighbour.emptyAllPits();
    }
    
    void decideVictoriousPlayer(BoardMember kalahaInactivePlayer){
        int AllStonesInactivePlayer = kalahaInactivePlayer.getTotalStones();
        BoardMember kalahaActivePlayer = kalahaInactivePlayer.getActiveKalaha();
        int allStonesActivePlayer = kalahaActivePlayer.getTotalStones();
        
        System.out.println(); //for new line 
        
        String weHaveAWinner = null;
        
        if (AllStonesInactivePlayer > allStonesActivePlayer) {
            weHaveAWinner = getPlayerName(kalahaInactivePlayer);
        } else if (AllStonesInactivePlayer < allStonesActivePlayer){
             weHaveAWinner = getPlayerName(kalahaActivePlayer);
        }
        
        if (weHaveAWinner != null){
             System.out.println(weHaveAWinner + " has won the game.");
        }else {
            System.out.println("We have a draw! No winners or losers..");
        }
    }
    
    private String getPlayerName (BoardMember whoseOwnerToCheck) {
        return whoseOwnerToCheck.getOwner().getPlayerName();
    }
    
    void pickThisBoardMember(){
    }
}