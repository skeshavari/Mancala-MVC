package nl.sogyo.mancala;
import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class GameStateOriginal {
    JFrame frame;
    JPanel gamePanel;

    Pit StartGameBoard = new Pit(6);
    int totalNumberBoardMembers = getTotalNumberBoardMembers();
    int totalPitsBeforeKalaha1 = (totalNumberBoardMembers/2)-1;
    int totalPitsBeforeKalaha2 = (totalNumberBoardMembers)-1;
    JButton [] buttons = new JButton[totalNumberBoardMembers];
    int frameWidth = 450;
    int frameHeight = 250 + (25 * totalNumberBoardMembers);  
    
    void createGUIFrame(){
        frame = new JFrame("Mancala");
        frame.setSize(frameWidth,frameHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        CapturePane capturePane = new CapturePane();
        System.setOut(new PrintStream(new StreamCapturer("Game Referee: ", capturePane, System.out)));
        capturePane.setPreferredSize(new Dimension(100,100));
        
        JPanel background = new JPanel(new BorderLayout());
        background.setBorder(BorderFactory.createEmptyBorder(20,10,10,10));
        
        gamePanel = new JPanel();
        background.add(BorderLayout.CENTER, gamePanel);

        background.add(BorderLayout.SOUTH, capturePane);
        frame.getContentPane().add(background);

        startNewGame();
        frame.setVisible(true);
        
    }
    
    void startNewGame(){
        JButton newGameButton = new JButton("New Game");
        newGameButton.setBounds(50, 50, 100, 50);
        newGameButton.setLayout(null);
        newGameButton.addActionListener(new NewGameButtonListener());
        gamePanel.add(newGameButton);
    }
    

    
    void CreateAndUpdateAllbuttons(){
        gamePanel.removeAll(); 
        BoardMember loopThroughBoard = StartGameBoard;
        JLabel name;
        int buttonXPosition = 150;
        int buttonYPosition = 50;
        int labelXPosition = 50;
        int numberOfBoardMembers = 0;
        
        while (numberOfBoardMembers < totalNumberBoardMembers){
            buttons[numberOfBoardMembers] = new JButton(Integer.toString(loopThroughBoard.getTotalStones()));
            buttons[numberOfBoardMembers].putClientProperty("boardMember", loopThroughBoard);
            buttons[numberOfBoardMembers].addActionListener(new ButtonClickListener());
            buttons[numberOfBoardMembers].setBounds(buttonXPosition, buttonYPosition, 50, 50);
            buttons[numberOfBoardMembers].setLayout(null);
            
            if (numberOfBoardMembers == totalPitsBeforeKalaha1){
                name = new JLabel(loopThroughBoard.getClass().getSimpleName() + " Player 1");
            } else if (numberOfBoardMembers == totalPitsBeforeKalaha2){
                name = new JLabel(loopThroughBoard.getClass().getSimpleName() + " Player 2");
            } else {
                name = new JLabel(loopThroughBoard.getClass().getSimpleName() + " " + (numberOfBoardMembers+1));
            }
            
            //reCollor active Players buttons
            if (loopThroughBoard.getOwner().getIsActiveTurn() == false){
                buttons[numberOfBoardMembers].setBackground(Color.GRAY);
            } else if (loopThroughBoard.getTotalStones() == 0){
                buttons[numberOfBoardMembers].setBackground(Color.LIGHT_GRAY);
            }
            
            name.setBounds(labelXPosition, buttonYPosition, 100, 50);
            name.setLayout(null);
            gamePanel.add(name);
            gamePanel.add(buttons[numberOfBoardMembers]);
            
            loopThroughBoard = loopThroughBoard.getNeighbour();
            numberOfBoardMembers++;
            if (numberOfBoardMembers == (totalNumberBoardMembers/2)){
                buttonXPosition += 52;                   
                buttonYPosition -= 104;
                labelXPosition = buttonXPosition + 75;
            }
            if (numberOfBoardMembers > (totalNumberBoardMembers/2)){
                buttonYPosition -= 52;
            } else {
                buttonYPosition +=52;
            }
        }
        frame.repaint();
    }

    private int getTotalNumberBoardMembers(){
        int amountOfPits = 1;
        BoardMember checking = StartGameBoard.getNeighbour();
        while (StartGameBoard.equals(checking) == false){
            amountOfPits++;
            checking = checking.getNeighbour();
        }
        return amountOfPits;
    }
    
    class ButtonClickListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event) {
        JButton pitSelected = (JButton) event.getSource();
        BoardMember buttonsLinkedTo = (BoardMember) pitSelected.getClientProperty("boardMember");
        buttonsLinkedTo.pickThisBoardMember();
        CreateAndUpdateAllbuttons();
        frame.repaint();
        }
    }
        
    class NewGameButtonListener implements ActionListener{

        public void actionPerformed(ActionEvent a) {
            CreateAndUpdateAllbuttons();
            frame.repaint();
        }

    }

    public class CapturePane extends JPanel implements Consumer {

        private JTextArea output;

        public CapturePane() {
            setLayout(new BorderLayout());
            output = new JTextArea();
            add(new JScrollPane(output));
        }

        @Override
        public void appendText(final String text) {
            if (EventQueue.isDispatchThread()) {
                output.append(text);
                output.setCaretPosition(output.getText().length());
            } else {

                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        appendText(text);
                    }
                });

            }
        }        
    }

    public interface Consumer {        
        public void appendText(String text);        
    }

    public class StreamCapturer extends OutputStream {

        private StringBuilder buffer;
        private String prefix;
        private Consumer consumer;
        private PrintStream old;

        public StreamCapturer(String prefix, Consumer consumer, PrintStream old) {
            this.prefix = prefix;
            buffer = new StringBuilder(128);
            buffer.append("[").append(prefix).append("] ");
            this.old = old;
            this.consumer = consumer;
        }

        @Override
        public void write(int b) throws IOException {
            char c = (char) b;
            String value = Character.toString(c);
            buffer.append(value);
            if (value.equals("\n")) {
                consumer.appendText(buffer.toString());
                buffer.delete(0, buffer.length());
                buffer.append("[").append(prefix).append("] ");
            }
            old.print(c);
        }        
    }    
}