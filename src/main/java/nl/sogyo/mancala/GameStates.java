package nl.sogyo.mancala;

import java.util.ArrayList;

public class GameStates {

    private Pit StartGameBoard = new Pit();
    private ArrayList<BoardMember> allMembers = new ArrayList<>();

    public GameStates() {
        prepareGameBoard();
    }


    private void prepareGameBoard() {
        allMembers.add(StartGameBoard);
        BoardMember checking = StartGameBoard.getNeighbour();
        while (StartGameBoard.equals(checking) == false) {
            allMembers.add(checking);
            checking = checking.getNeighbour();
        }
    }

    public void playChoice(int choice){
        allMembers.get(choice).pickThisBoardMember();
    }

    public String formatPlayer1(){
        if (StartGameBoard.ownerIsActive()){
            return "playerActive";
        }
        return "playerInactive";
    }

    public String formatPlayer2(){
        if (StartGameBoard.ownerIsActive()){
            return "playerInactive";
        }
        return "playerActive";
    }

    public int[] stateOfGameBoard(){
        int[] totalStones = new int[allMembers.size()];
        totalStones[0] = StartGameBoard.getTotalStones();
        BoardMember nextBoardMember = StartGameBoard.getNeighbour();
        for (int iterator = 1; iterator < allMembers.size(); iterator++){
            totalStones[iterator] = nextBoardMember.getTotalStones();
            nextBoardMember = nextBoardMember.getNeighbour();
        }
        return totalStones;
    }

    public String winningPlayer(){
        int playerOneStones = StartGameBoard.getNextKalaha().getTotalStones();
        int playerTwoStones = StartGameBoard.getOpposingBoardMember().getNextKalaha().getTotalStones();

        if (playerOneStones > playerTwoStones){
            return "Player 1 is in the Lead!";
        } else if (playerOneStones < playerTwoStones){
        return "Player 2 is in the Lead!";
        }
        return "It's a Draw..";
    }

    public String isItOver(){
        BoardMember firstPitOpponent = StartGameBoard.getNextKalaha().getNeighbour();

        int AllStones1 = StartGameBoard.howManyStonesOnPlayerSide();
        int allStones2 = firstPitOpponent.howManyStonesOnPlayerSide();
        if(AllStones1 == 0 && allStones2 == 0){
            return "GameOver";
        }
        return "GameOngoing";
    }
}