package nl.sogyo.mancala;

public class Contestant {
    private String playerName;
    private Contestant opponent;
    private boolean isActiveTurn;
    
    public Contestant(){
        playerName = "Player 1";
        isActiveTurn = true;
        opponent = new Contestant(this);
    }
    
    public Contestant(Contestant firstOpponent){
        playerName = "Player 2";
        isActiveTurn = false;
        opponent = firstOpponent;
    }

    public String getPlayerName(){
        return playerName;
    }
    
    public Contestant getOpponent(){
        return opponent;
    }
    
    public boolean getIsActiveTurn(){
        return isActiveTurn;
    }
    
    public void switchIsActiveTurn(){
        isActiveTurn = !isActiveTurn;
    }
}
