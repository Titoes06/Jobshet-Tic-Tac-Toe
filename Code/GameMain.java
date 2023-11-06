
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class GameMain extends JPanel {
 // Named-constants for the game board
 public static final int ROWS = 4; // ROWS by COLS cells
 public static final int COLS = 4;
public static final String TITLE = "Tic Tac Toe Andika";
 // Name-constants for the various dimensions used for graphics drawing
 public static final int CELL_SIZE = 120; // cell width and height (square)
 public static final int CANVAS_WIDTH = CELL_SIZE * COLS; // the drawing canvas
 public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
 public static final int GRID_WIDTH = 8; // Grid-line's width
 public static final int GRID_WIDHT_HALF = GRID_WIDTH / 2; // Grid-line's half-width
 // Symbols (cross/nought) are displayed inside a cell, with padding from border
 
 public static final int CELL_PADDING = CELL_SIZE / 6;
 public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
 public static final int SYMBOL_STROKE_WIDTH = 8; // pen's stroke width
 private Board board; // the game board
 private GameState currentState;//the current state of the game
 private Seed currentPlayer; // the current player
 private JLabel statusBar; // for displaying status message
 /** Constructor to setup the UI and game components */
 public GameMain() {
 // This JPanel fires MouseEvent
 this.addMouseListener(new MouseAdapter() {
 @Override
 public void mouseClicked(MouseEvent e) { // mouse-clicked handler
 int mouseX = e.getX();
 int mouseY = e.getY();
 // Get the row and column clicked
 int rowSelected = mouseY / CELL_SIZE;
 int colSelected = mouseX / CELL_SIZE;
 if (currentState == GameState.PLAYING) {
 if (rowSelected >= 0 && rowSelected < ROWS
 && colSelected >= 0 && colSelected < COLS
&& board.cells[rowSelected][colSelected].content == 
Seed.EMPTY) {
 board.cells[rowSelected][colSelected].content = currentPlayer; // move
 updateGame(currentPlayer, rowSelected, colSelected); // update currentState
 // Switch player
currentPlayer = (currentPlayer == Seed.CROSS) ? 
Seed.NOUGHT : Seed.CROSS;
 }
 } else { // game over
 initGame(); // restart the game
 }
 // Refresh the drawing canvas
 repaint(); // Call-back paintComponent().
 }
 });
 
// Setup the status bar (JLabel) to display status message
 statusBar = new JLabel(" ");
 statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 12));
 statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
 statusBar.setOpaque(true);
 statusBar.setBackground(Color.BLUE);
 setLayout(new BorderLayout());
 add(statusBar, BorderLayout.PAGE_END); // same as SOUTH
 setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));
 // account for statusBar in height
 board = new Board(); // allocate the game-board
 initGame(); // Initialize the game variables
 }

/** Initialize the game-board contents and the current-state */
 public void initGame() {
 for (int row = 0; row < ROWS; ++row) {
 for (int col = 0; col < COLS; ++col) {
 board.cells[row][col].content = Seed.EMPTY; // all cells empty
 }
 }
 currentState = GameState.PLAYING; // ready to play
 currentPlayer = Seed.CROSS; // cross plays first
 }
 /** Update the currentState after the player with "theSeed" has placed on (row, col) */
 public void updateGame(Seed theSeed, int row, int col) {
 if (board.hasWon(theSeed, row, col)) { // check for win
 currentState = (theSeed == Seed.CROSS) ? GameState.CROSS_WON : GameState.NOUGHT_WON;
 } else if (board.isDraw()) { // check for draw
 currentState = GameState.DRAW;
 }
 // Otherwise, no change to current state (PLAYING).
 }

/** Custom painting codes on this JPanel */
 @Override
 public void paintComponent(Graphics g){
 //invoke via repaint()
 super.paintComponent(g); // fill background
 setBackground(Color.WHITE); // set its background color
 board.paint(g); // ask the game board to paint itself
 // Print status-bar message
 if (currentState == GameState.PLAYING) {
 statusBar.setForeground(Color.BLACK);
 if (currentPlayer == Seed.CROSS) {
 statusBar.setText("Giliran X");
 } else {
 statusBar.setText("Giliran O");
 }
 } else if (currentState == GameState.DRAW) {
 statusBar.setForeground(Color.RED);
 statusBar.setText("Permainan seri, silahkan tekan permainan untuk bermain lagi.");
 } else if (currentState == GameState.CROSS_WON) {
 statusBar.setForeground(Color.RED);
 statusBar.setText("'X' menang! silahkan tekan permainan untuk bermain lagi.");
 } else if (currentState == GameState.NOUGHT_WON) {
 statusBar.setForeground(Color.RED);
 statusBar.setText("'O' menang! silahkan tekan permainan untuk bermain lagi.");
 }
 }
 /** The entry "main" method */
 public static void main(String[] args) {
 // Run GUI construction codes in Event-Dispatching thread for thread safety
 javax.swing.SwingUtilities.invokeLater(new Runnable() {
public void run() {
 JFrame frame = new JFrame(TITLE);
 // Set the content-pane of the JFrame to an instance of main JPanel
 frame.setContentPane(new GameMain());
 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 frame.pack();
 // center the application window
 frame.setLocationRelativeTo(null); 
 frame.setVisible(true); // show it
 }
 });
 }
}

