package nl.Servlet;

import nl.sogyo.mancala.*;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;


public class MancalaServlet extends HttpServlet {

    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int[] gameBoardState;
        GameStates currentGame;
        int pickedHole;

        HttpSession session = req.getSession();
        currentGame = (GameStates) session.getAttribute("gameState");
        Boolean restart = evaluateRestart(req);

        if(currentGame == null || restart == true){
            currentGame = new GameStates();
        }

        pickedHole = evaluatePickedHole(req);

        playGame(pickedHole, currentGame);

        session.setAttribute("gameState", currentGame);
        session.setAttribute("gameBoard", currentGame.stateOfGameBoard());
        session.setAttribute("formatPlayer1", currentGame.formatPlayer1());
        session.setAttribute("formatPlayer2", currentGame.formatPlayer2());
        session.setAttribute("Winner", currentGame.winningPlayer());
        session.setAttribute("gameOver", currentGame.isItOver());

        RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
        rd.forward(req, resp);
/*
        ArrayList<BoardMember> listGameBoard;
        BoardMember startPit;
        int pickedHole;

        HttpSession session = req.getSession();

        startPit = (BoardMember) session.getAttribute("firstPit");
        listGameBoard = (ArrayList<BoardMember>) session.getAttribute("GameBoard");
        if(startPit == null){
            startPit = new Pit();
        }

        if(listGameBoard == null){
            listGameBoard = new ArrayList<>();
        }

        pickedHole = evaluatePickedHole(req);

        playGame(pickedHole, listGameBoard);

        listGameBoard = refreshGameState(startPit);

        session.setAttribute("firstPit", startPit);
        session.setAttribute("GameBoard", listGameBoard);

        RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
        rd.forward(req, resp);
*/
    }

    private Boolean evaluateRestart(HttpServletRequest req){
        try {
            return Boolean.parseBoolean(req.getParameter("restart"));
        } catch (Exception e) {
            return false;
        }
    }

    private int evaluatePickedHole(HttpServletRequest req){
        try {
            return Integer.parseInt(req.getParameter("value"));
        } catch (Exception e) {
            return -1;
        }
    }

    private void playGame(int pickedHole, GameStates currentGame){
        if (pickedHole >= 0 && pickedHole < 14){
            currentGame.playChoice(pickedHole);
        }
    }

    private ArrayList refreshGameState(BoardMember startPit){
        ArrayList<BoardMember> listGameBoard = new ArrayList<>();
        listGameBoard.add(startPit);
        BoardMember usedForLoop = startPit.getNeighbour();
        while (isNotStartingPit(usedForLoop, startPit)){
            listGameBoard.add(usedForLoop);
            usedForLoop = usedForLoop.getNeighbour();
        }
        return listGameBoard;
    }

    private boolean isNotStartingPit(BoardMember current, BoardMember startPit){
            return !current.equals(startPit);
        }


}