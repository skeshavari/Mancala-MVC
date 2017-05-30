package nl.sogyo.mancala;
import org.junit.*;

public class MancalaTest {
    Pit pit1;
    Pit pit2;
    Pit pit3;
    Pit pit4;
    Pit pit5;
    Pit pit6;
    Kalaha kalaha1;
    Pit pit8;
    Pit pit9;
    Pit pit10;
    Pit pit11;
    Pit pit12;
    Pit pit13;
    Kalaha kalaha2;

    @Before
    public void SetUp()
    {
        pit1 = new Pit();
        pit2 = (Pit) pit1.getNeighbour();
        pit3 = (Pit) pit2.getNeighbour();
        pit4 = (Pit) pit3.getNeighbour();
        pit5 = (Pit) pit4.getNeighbour();
        pit6 = (Pit) pit5.getNeighbour();
        kalaha1 = (Kalaha) pit6.getNeighbour();
        pit8 = (Pit) kalaha1.getNeighbour();
        pit9 = (Pit) pit8.getNeighbour();
        pit10 = (Pit) pit9.getNeighbour();
        pit11 = (Pit) pit10.getNeighbour();
        pit12 = (Pit) pit11.getNeighbour();
        pit13 = (Pit) pit12.getNeighbour();
        kalaha2 = (Kalaha) pit13.getNeighbour();
    }
    
    @Test
    public void testIfNewPitHasFourStones() {
        int result = pit1.getTotalStones();
        int expResult = 4;
        Assert.assertEquals(expResult, result);
    }
    
    @Test
    public void testIfPitPassesStonesOnAndSetsOwnStonesOnZero() {
        pit1.pickThisBoardMember();
        int result = pit1.getTotalStones();
        int expResult = 0;
        Assert.assertEquals(expResult, result);
    }
    
    @Test
    public void testIfNeighbourReceivesStonesAfterMethodpickAndPlayPit() {
        pit1.pickThisBoardMember();
        int result = pit2.getTotalStones();
        int expResult = 5;
        
        Assert.assertEquals(expResult, result);
    }
    
    @Test
    public void testIfFirstKalahaIsEmpty() {
        pit1.pickThisBoardMember();
        int stonesInKalahaShouldBeOne = kalaha1.getTotalStones();
        
        Assert.assertEquals(0, stonesInKalahaShouldBeOne);
    }
     
    @Test
    public void testIfPitKnowsItsOwner() {
        Contestant playerOfFirstPit = pit1.getOwner();
        
        Assert.assertNotNull(playerOfFirstPit);
    }    
    
    @Test
    public void testIfPitOwnerKnowsItsOpponent() {
        Contestant opponentOfFirstOneOwner = pit1.getOwner().getOpponent();
        
        Assert.assertNotNull(opponentOfFirstOneOwner);
    }
    
    @Test
    public void testIfSecondKalahaIsEmpty() {
        pit1.pickThisBoardMember();
        int stonesInKalahaShouldBeNone = kalaha2.getTotalStones();
        
        Assert.assertEquals(0, stonesInKalahaShouldBeNone);
    }
    
    @Test
    public void testIfFirstPitIs14thPit() {
        Assert.assertEquals(pit1, kalaha2.getNeighbour());
    }
    
    @Test
    public void testIfFirstSIxPitsAndKalahaHaveSameOwner() {
        Assert.assertEquals(pit1.getOwner(), pit2.getOwner());
        Assert.assertEquals(pit2.getOwner(), pit3.getOwner());
        Assert.assertEquals(pit3.getOwner(), pit4.getOwner());
        Assert.assertEquals(pit4.getOwner(), pit5.getOwner());
        Assert.assertEquals(pit5.getOwner(), pit6.getOwner());
        Assert.assertEquals(pit6.getOwner(), kalaha1.getOwner());
        Assert.assertNotEquals(kalaha1.getOwner(), kalaha1.getNeighbour().getOwner());
        
    }
    
    @Test
    public void testIfSecondSIxPitsAndKalahaHaveSameOwner() {
        Assert.assertEquals(pit8.getOwner(), pit9.getOwner());
        Assert.assertEquals(pit9.getOwner(), pit10.getOwner());
        Assert.assertEquals(pit10.getOwner(), pit11.getOwner());
        Assert.assertEquals(pit11.getOwner(), pit12.getOwner());
        Assert.assertEquals(pit12.getOwner(), pit13.getOwner());
        Assert.assertEquals(pit13.getOwner(), kalaha2.getOwner());
        Assert.assertNotEquals(kalaha2.getOwner(), kalaha2.getNeighbour().getOwner());
    }
    
    @Test
    public void testIfKalahaGetsOneStoneAfterPlayingPitFive() {
        pit5.pickThisBoardMember();
        Assert.assertEquals(1, kalaha1.getTotalStones());
    }
    
    @Test
    public void testIfPitTwoCanFindOppositeField() {
        Assert.assertEquals(pit2.getOpposingBoardMember(), pit12);
        Assert.assertEquals(pit1.getOpposingBoardMember(), pit13);
        Assert.assertEquals(kalaha2.getOpposingBoardMember(), kalaha2);
    }
    
    @Test
    public void testIfPitFourCanCaptureOpposingBoardMember() {
        pit5.emptyStones();
        pit1.pickThisBoardMember();
        
        Assert.assertEquals(0, pit5.getOpposingBoardMember().getTotalStones());
    }
    
    @Test
    public void testIfKalahaOneReceivesStonesFromCapturedMembers() {
        int stonesReceivedByKalahaOneShouldBe = 5;
        pit5.emptyStones();
        pit1.pickThisBoardMember();
        
        Assert.assertEquals(stonesReceivedByKalahaOneShouldBe, kalaha1.getTotalStones());
    }
    
    @Test
    public void testIfPlayerTurnChangesAfterPlaying() {
        pit1.pickThisBoardMember();
        boolean playerOneTurn = pit1.getOwner().getIsActiveTurn();
        boolean playerTwoTurn = pit8.getOwner().getIsActiveTurn();
        
        Assert.assertEquals(playerOneTurn, false);
        Assert.assertEquals(playerTwoTurn, true);
    }
    
    @Test
    public void testIfPlayerWithActiveTurnCANNOTChooseOpponentField() {
        pit8.pickThisBoardMember();
        boolean playerOneTurn = pit1.getOwner().getIsActiveTurn();
        boolean playerTwoTurn = pit8.getOwner().getIsActiveTurn();
        
        Assert.assertEquals(4, pit8.getTotalStones());
        Assert.assertEquals(4, pit12.getTotalStones());
        Assert.assertEquals(playerOneTurn, true);
        Assert.assertEquals(playerTwoTurn, false);
    }
    
    @Test
    public void testIfKalahaTwoReceivesNoStonesWhenPlayerOneIsActive() {
        int AfterTheseSessionsKalahaTwoShouldHave = 4;
        pit2.pickThisBoardMember();
        pit13.pickThisBoardMember();
        pit3.pickThisBoardMember();
        pit12.pickThisBoardMember();
        pit4.pickThisBoardMember();
        pit11.pickThisBoardMember();
        pit5.pickThisBoardMember();
        pit10.pickThisBoardMember();
        pit6.pickThisBoardMember();
        
        Assert.assertEquals(AfterTheseSessionsKalahaTwoShouldHave,kalaha2.getTotalStones());
    }
    
    @Test
    public void testIfYouCanPlayAgainAfterFinishingInKalaha() {
        pit4.pickThisBoardMember();
        pit10.pickThisBoardMember();
        boolean playerTwoturn = pit10.getOwner().getIsActiveTurn();
        
        Assert.assertEquals(playerTwoturn,true);
    }

    
    @Test
    public void testIfSweepFunctionClearsAllPits() {
        printAllStones();
        pit1.emptyStones();
        pit2.emptyStones();
        pit3.emptyStones();
        pit4.emptyStones();
        pit5.emptyStones();
        pit6.pickThisBoardMember();
        pit8.pickThisBoardMember();
        printAllStones();
        
        Assert.assertEquals(0, pit8.getTotalStones());
        Assert.assertEquals(0, pit9.getTotalStones());
        Assert.assertEquals(0, pit10.getTotalStones());
        Assert.assertEquals(0, pit11.getTotalStones());
        Assert.assertEquals(0, pit12.getTotalStones());
        Assert.assertEquals(0, pit13.getTotalStones());
    }
    
    @Test
    public void testIfEndGameSequenceIsInitiatedWithStonesInRightKalaha() {
        printAllStones();
        pit1.emptyStones();
        pit2.emptyStones();
        pit3.emptyStones();
        pit4.emptyStones();
        pit5.emptyStones();
        pit6.pickThisBoardMember();
        pit8.pickThisBoardMember();
        printAllStones();
        
        Assert.assertEquals(1, kalaha1.getTotalStones());
        Assert.assertEquals(27, kalaha2.getTotalStones());
    }
    
    @Test
    public void testIfEndGameSequenceIsInitiatedWhenEndingInOwnKalaha() {
        printAllStones();
        pit1.emptyStones();
        pit2.emptyStones();
        pit3.emptyStones();
        pit4.emptyStones();
        pit5.emptyStones();
        pit6.subtractStones(3);
        pit6.pickThisBoardMember();
        printAllStones();
        
        Assert.assertEquals(1, kalaha1.getTotalStones());
        Assert.assertEquals(24, kalaha2.getTotalStones());
    }
    
    @Test
    public void testIfDrawIsInitiatedWhenBothKalahaHaveSameAmountAtEndGame() {
        printAllStones();
        pit1.emptyStones();
        pit2.emptyStones();
        pit3.emptyStones();
        pit4.emptyStones();
        pit6.emptyStones();

        pit8.emptyStones();
        pit9.emptyStones();
        pit10.emptyStones();
        pit11.emptyStones();
        pit13.emptyStones();
        
        pit5.subtractStones(3);
        pit12.subtractStones(3);
        
        pit5.pickThisBoardMember();
        pit12.pickThisBoardMember();

        Assert.assertEquals(1, kalaha1.getTotalStones());
        Assert.assertEquals(1, kalaha2.getTotalStones());
        
    }
    
    
    void printAllStones(){
        System.out.println("             Kalaha2 stones " + kalaha2.getTotalStones());
        System.out.println("pit1 stones    " + pit1.getTotalStones() + " || " + pit13.getTotalStones() + "  pit13 stones");
        System.out.println("pit2 stones    " + pit2.getTotalStones() + " || " + pit12.getTotalStones() + "  pit12 stones");
        System.out.println("pit3 stones    " + pit3.getTotalStones() + " || " + pit11.getTotalStones() + "  pit11 stones");
        System.out.println("pit4 stones    " + pit4.getTotalStones() + " || " + pit10.getTotalStones() + "  pit10 stones");
        System.out.println("pit5 stones    " + pit5.getTotalStones() + " || " + pit9.getTotalStones() + "  pit9 stones");
        System.out.println("pit6 stones    " + pit6.getTotalStones() + " || " + pit8.getTotalStones() + "  pit8 stones");
        System.out.println("   Kalaha1 stones " + kalaha1.getTotalStones());
    }
}
