package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Board;
import model.BoardSquare;
import model.Chess;
import model.ChessPieceGeneral;
import model.Player;
import model.SpecificChessPiece;
import controller.Controller;

public class BoardView extends JFrame {
	Board board = new Board();
	ArrayList<BoardSquare> squares = new ArrayList<BoardSquare>();
	Chess chess = board.getChess();
	Player player1 = new Player("p1", chess);
	Player player2 = new Player("p2", chess);
	Player currentPlayer = player1;
	JPanel panel;
	Boolean pieceSelected = false; 
	SpecificChessPiece selectedPiece;

	HashMap<JButton, BoardSquare> tiles = new HashMap<JButton, BoardSquare>();
	HashMap<BoardSquare, JButton> buttons = new HashMap<BoardSquare, JButton>(); 
	ArrayList<JButton> buttonList = new ArrayList<JButton>();
	ArrayList<JButton> validButtons = new ArrayList<JButton>();

	//Make Piece Templates
	ChessPieceGeneral generalPawn = new ChessPieceGeneral("Pawn", chess);
	ChessPieceGeneral generalKnight = new ChessPieceGeneral("Knight", chess);
	ChessPieceGeneral generalBishop = new ChessPieceGeneral("Bishop", chess);
	ChessPieceGeneral generalCastle = new ChessPieceGeneral("Castle", chess);
	ChessPieceGeneral generalKing = new ChessPieceGeneral("King", chess);
	ChessPieceGeneral generalQueen = new ChessPieceGeneral("Queen", chess);


	public BoardView(){
		initComponents();
		setPieces();
		refreshData();
	}

	private void setPieces() {
		//Set Pieces for Players
		initPieces(player1);	
		initPieces(player2);

	}

	private void initPieces(Player player) {
		Boolean isPlayer1 = false;
		if (player==player1) {
			isPlayer1 = true;
		}
		for(JButton button: buttonList) {
			BoardSquare square = tiles.get(button);
			//Set Pawns
			if(((square.getY() == 2) && (player == player1)) || ((square.getY() == 7) && (player == player2))) {
				new SpecificChessPiece(true, square.getX(), square.getY(), isPlayer1, generalPawn, square, player);
			}
			//Set Castles
			else if(((square.getY() == 1) && (square.getX() == 1) && (player == player1) || (square.getY() == 1) && (square.getX() == 8) &&(player == player1))||
					((square.getY() == 8) && (square.getX() == 1) && (player == player2) || (square.getY() == 8) && (square.getX() == 8) &&(player == player2))){
				new SpecificChessPiece(true, square.getX(), square.getY(), isPlayer1, generalCastle, square, player);
			}
			//Set Bishops
			else if(((square.getY() == 1) && (square.getX() == 2) && (player == player1) || (square.getY() == 1) && (square.getX() == 7) &&(player == player1))||
					((square.getY() == 8) && (square.getX() == 2) && (player == player2) || (square.getY() == 8) && (square.getX() == 7) &&(player == player2))) {
				new SpecificChessPiece(true, square.getX(), square.getY(), isPlayer1, generalBishop, square, player);
			}
			//Set Knights
			else if(((square.getY() == 1) && (square.getX() == 3) && (player == player1) || (square.getY() == 1) && (square.getX() == 6) &&(player == player1))||
					((square.getY() == 8) && (square.getX() == 3) && (player == player2) || (square.getY() == 8) && (square.getX() == 6) &&(player == player2))) {
				new SpecificChessPiece(true, square.getX(), square.getY(), isPlayer1, generalKnight, square, player);
			}
			//Set King
			else if(((square.getY() == 1) && (square.getX() == 4) && (player == player1)||((square.getY() == 8) && (square.getX() == 5) && (player == player2)))) {
				new SpecificChessPiece(true, square.getX(), square.getY(), isPlayer1, generalKing, square, player);
			} 
			//Set Queen
			else if(((square.getY() == 1) && (square.getX() == 5) && (player == player1)||((square.getY() == 8) && (square.getX() == 4) && (player == player2)))) {
				new SpecificChessPiece(true, square.getX(), square.getY(), isPlayer1, generalQueen, square, player);
			}

			if(square.hasSpecificChessPiece()) {
				button.setText(square.getSpecificChessPiece().getChessPieceGeneral().getName());
			}
		}		
	}

	//Button action listener
	ActionListener boardListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			System.out.println(tiles.get(button).getX() + " " + tiles.get(button).getY());
			//Selected a piece to determine move
			if(tiles.get(button).hasSpecificChessPiece() && pieceSelected == false && currentPlayer == Controller.getPlayerByButton(tiles.get(button))){
				highlightOptions(button);
			}
			//If you pick a valid option to move upon
			else if((pieceSelected == true) && (!tiles.get(button).hasSpecificChessPiece()) && validButtons.contains(button) && (selectedPiece.getPlayer() == currentPlayer)) {
				movePiece(selectedPiece, button, false);
			}
			//If you pick a valid option and kill
			else if((pieceSelected == true) && (tiles.get(button).hasSpecificChessPiece()) && ((tiles.get(button).getSpecificChessPiece().getPlayer() != currentPlayer))&& validButtons.contains(button) && (selectedPiece.getPlayer() == currentPlayer)) {
				movePiece(selectedPiece, button, true);
			}			
			//Reset everything
			else{
				pieceSelected = false;
				resetSelected();
				validButtons.clear();
			}
		}

	};
	//Highlight selected piece
	private void highlightOptions(JButton selectedButton) {
		selectedButton.setBackground(Color.YELLOW);
		pieceSelected = true;
		SpecificChessPiece piece = tiles.get(selectedButton).getSpecificChessPiece();
		moveAlgorithm(piece);
		selectedPiece = piece;
		paintSelect();		
	}

	//Move piece onto vacant square and/or kill opponent piece
	private void movePiece(SpecificChessPiece selectedPiece, JButton newButton, Boolean toKill) {
		selectedPiece.getBoardSquare().setSpecificChessPiece(null);
		if(toKill) {
			tiles.get(newButton).getSpecificChessPiece().delete();
		}
		tiles.get(newButton).setSpecificChessPiece(selectedPiece);
		selectedPiece.setCurrentX(tiles.get(newButton).getX());
		selectedPiece.setCurrentY(tiles.get(newButton).getY());
		newButton.setText(selectedPiece.getChessPieceGeneral().getName());
		selectedPiece.setPlayer(currentPlayer);
		pieceSelected = false;
		refreshData();
	}

	//Toggle current player
	private void changePlayer() {
		if (currentPlayer == player1) {
			currentPlayer = player2;
		}
		else {
			currentPlayer = player1;
		}

	}

	//Reset colors
	private void resetSelected() {
		for(JButton button : buttonList) {
			String color = tiles.get(button).getColor();
			if(color == "black") {
				button.setBackground(Color.BLACK);
			}
			else {
				button.setBackground(Color.WHITE);
			}
		}
	}

	//Add text and color viable squares
	private void paintSelect() {
		for(JButton button: validButtons) {
			button.setBackground(Color.GREEN);
		}
		for(JButton button: buttonList) {
			if(tiles.get(button).hasSpecificChessPiece()) {
				button.setText(tiles.get(button).getSpecificChessPiece().getChessPieceGeneral().getName());
			}
			else {
				button.setText(" ");
			}
		}
	}

	//Logic for valid movements
	private void moveAlgorithm(SpecificChessPiece piece) {
		switch(piece.getChessPieceGeneral().getName()) {
		case "Pawn":
			validPawnMoves(piece);
			break;
		case "Castle":
			validCastleMoves(piece);
			break;
		}
	}

	//Method to find all valid pawn moves
	private void validPawnMoves(SpecificChessPiece piece) {
		addToValidButtons(Controller.validPawnMoves(piece, currentPlayer, chess));
	}

	//Method to find all valid castle moves
	private void validCastleMoves(SpecificChessPiece piece) {		
		addToValidButtons(Controller.validStraightLineMoves(piece, true, squares, currentPlayer));
		addToValidButtons(Controller.validStraightLineMoves(piece, false, squares, currentPlayer));

	}
	
	//Method to find all valid bishop moves
	private void validBishopMoves(SpecificChessPiece piece) {
		
	}
	
	//Method to add all squares to valid buttons
	private void addToValidButtons(ArrayList<BoardSquare> targetSquares) {
		for(BoardSquare square : targetSquares) {
			validButtons.add(buttons.get(square));
		}
	}

	//Re-populate data
	private void refreshData() {
		resetSelected();
		validButtons.clear();
		changePlayer();
		paintSelect();
	}

	//Create Buttons and Squares and add to HashMaps and ArrayList
	private void generateTiles() {
		for(int i=1;i<=8;i++) {
			for(int j=1;j<=8;j++) {
				JButton button = new JButton();	
				String color = getColor(i, j);
				BoardSquare square = new BoardSquare(i, j, color, board);
				tiles.put(button, square);
				buttons.put(square, button);
				buttonList.add(button);
				squares.add(square);
			}
		}
	}

	//Retrieve Color of Square based on row and column
	private String getColor(int i, int j) {
		if(((i+j)%2)==0){
			return "white";
		}
		else {
			return "black";
		}
	}

	private void initComponents() {
		// Setting JFrame
		setSize(2000, 1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		generateTiles();
		for(int i=0;i<64;i++) {
			// Set Square Colors
			if(board.getBoardSquare(i).getColor() == "black") {
				buttons.get(board.getBoardSquare(i)).setBackground(Color.BLACK);
			}
			else {
				buttons.get(board.getBoardSquare(i)).setBackground(Color.WHITE);
			}
			// Set Square Size
			buttons.get(board.getBoardSquare(i)).setPreferredSize(new Dimension(90, 90));
		}

		// Add Action Listener to Buttons
		for(JButton button: buttonList) {
			button.addActionListener(boardListener);
		}

		// Set Group Layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		panel = new JPanel(layout);
		panel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(buttons.get(board.getBoardSquare(0)))
						.addComponent(buttons.get(board.getBoardSquare(1)))
						.addComponent(buttons.get(board.getBoardSquare(2)))
						.addComponent(buttons.get(board.getBoardSquare(3)))
						.addComponent(buttons.get(board.getBoardSquare(4)))
						.addComponent(buttons.get(board.getBoardSquare(5)))
						.addComponent(buttons.get(board.getBoardSquare(6)))
						.addComponent(buttons.get(board.getBoardSquare(7))))
				.addGroup(layout.createParallelGroup()
						.addComponent(buttons.get(board.getBoardSquare(8)))
						.addComponent(buttons.get(board.getBoardSquare(9)))
						.addComponent(buttons.get(board.getBoardSquare(10)))
						.addComponent(buttons.get(board.getBoardSquare(11)))
						.addComponent(buttons.get(board.getBoardSquare(12)))
						.addComponent(buttons.get(board.getBoardSquare(13)))
						.addComponent(buttons.get(board.getBoardSquare(14)))
						.addComponent(buttons.get(board.getBoardSquare(15))))
				.addGroup(layout.createParallelGroup()
						.addComponent(buttons.get(board.getBoardSquare(16)))
						.addComponent(buttons.get(board.getBoardSquare(17)))
						.addComponent(buttons.get(board.getBoardSquare(18)))
						.addComponent(buttons.get(board.getBoardSquare(19)))
						.addComponent(buttons.get(board.getBoardSquare(20)))
						.addComponent(buttons.get(board.getBoardSquare(21)))
						.addComponent(buttons.get(board.getBoardSquare(22)))
						.addComponent(buttons.get(board.getBoardSquare(23))))
				.addGroup(layout.createParallelGroup()
						.addComponent(buttons.get(board.getBoardSquare(24)))
						.addComponent(buttons.get(board.getBoardSquare(25)))
						.addComponent(buttons.get(board.getBoardSquare(26)))
						.addComponent(buttons.get(board.getBoardSquare(27)))
						.addComponent(buttons.get(board.getBoardSquare(28)))
						.addComponent(buttons.get(board.getBoardSquare(29)))
						.addComponent(buttons.get(board.getBoardSquare(30)))
						.addComponent(buttons.get(board.getBoardSquare(31))))
				.addGroup(layout.createParallelGroup()
						.addComponent(buttons.get(board.getBoardSquare(32)))
						.addComponent(buttons.get(board.getBoardSquare(33)))
						.addComponent(buttons.get(board.getBoardSquare(34)))
						.addComponent(buttons.get(board.getBoardSquare(35)))
						.addComponent(buttons.get(board.getBoardSquare(36)))
						.addComponent(buttons.get(board.getBoardSquare(37)))
						.addComponent(buttons.get(board.getBoardSquare(38)))
						.addComponent(buttons.get(board.getBoardSquare(39))))
				.addGroup(layout.createParallelGroup()
						.addComponent(buttons.get(board.getBoardSquare(40)))
						.addComponent(buttons.get(board.getBoardSquare(41)))
						.addComponent(buttons.get(board.getBoardSquare(42)))
						.addComponent(buttons.get(board.getBoardSquare(43)))
						.addComponent(buttons.get(board.getBoardSquare(44)))
						.addComponent(buttons.get(board.getBoardSquare(45)))
						.addComponent(buttons.get(board.getBoardSquare(46)))
						.addComponent(buttons.get(board.getBoardSquare(47))))
				.addGroup(layout.createParallelGroup()
						.addComponent(buttons.get(board.getBoardSquare(48)))
						.addComponent(buttons.get(board.getBoardSquare(49)))
						.addComponent(buttons.get(board.getBoardSquare(50)))
						.addComponent(buttons.get(board.getBoardSquare(51)))
						.addComponent(buttons.get(board.getBoardSquare(52)))
						.addComponent(buttons.get(board.getBoardSquare(53)))
						.addComponent(buttons.get(board.getBoardSquare(54)))
						.addComponent(buttons.get(board.getBoardSquare(55))))
				.addGroup(layout.createParallelGroup()
						.addComponent(buttons.get(board.getBoardSquare(56)))
						.addComponent(buttons.get(board.getBoardSquare(57)))
						.addComponent(buttons.get(board.getBoardSquare(58)))
						.addComponent(buttons.get(board.getBoardSquare(59)))
						.addComponent(buttons.get(board.getBoardSquare(60)))
						.addComponent(buttons.get(board.getBoardSquare(61)))
						.addComponent(buttons.get(board.getBoardSquare(62)))
						.addComponent(buttons.get(board.getBoardSquare(63)))));
		layout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] {
				buttons.get(board.getBoardSquare(0)),buttons.get(board.getBoardSquare(1)),buttons.get(board.getBoardSquare(2)),buttons.get(board.getBoardSquare(3)), buttons.get(board.getBoardSquare(4)), buttons.get(board.getBoardSquare(5)), buttons.get(board.getBoardSquare(6)), buttons.get(board.getBoardSquare(7)),
				buttons.get(board.getBoardSquare(8)),buttons.get(board.getBoardSquare(9)),buttons.get(board.getBoardSquare(10)),buttons.get(board.getBoardSquare(11)), buttons.get(board.getBoardSquare(12)), buttons.get(board.getBoardSquare(13)), buttons.get(board.getBoardSquare(14)), buttons.get(board.getBoardSquare(15)),
				buttons.get(board.getBoardSquare(16)),buttons.get(board.getBoardSquare(17)),buttons.get(board.getBoardSquare(18)),buttons.get(board.getBoardSquare(19)), buttons.get(board.getBoardSquare(20)), buttons.get(board.getBoardSquare(21)), buttons.get(board.getBoardSquare(22)), buttons.get(board.getBoardSquare(23)),
				buttons.get(board.getBoardSquare(24)),buttons.get(board.getBoardSquare(25)),buttons.get(board.getBoardSquare(26)),buttons.get(board.getBoardSquare(27)), buttons.get(board.getBoardSquare(28)), buttons.get(board.getBoardSquare(29)), buttons.get(board.getBoardSquare(30)), buttons.get(board.getBoardSquare(31)),
				buttons.get(board.getBoardSquare(32)),buttons.get(board.getBoardSquare(33)),buttons.get(board.getBoardSquare(34)),buttons.get(board.getBoardSquare(35)), buttons.get(board.getBoardSquare(36)), buttons.get(board.getBoardSquare(37)), buttons.get(board.getBoardSquare(38)), buttons.get(board.getBoardSquare(39)),
				buttons.get(board.getBoardSquare(40)),buttons.get(board.getBoardSquare(41)),buttons.get(board.getBoardSquare(42)),buttons.get(board.getBoardSquare(43)), buttons.get(board.getBoardSquare(44)), buttons.get(board.getBoardSquare(45)), buttons.get(board.getBoardSquare(46)), buttons.get(board.getBoardSquare(47)),
				buttons.get(board.getBoardSquare(48)),buttons.get(board.getBoardSquare(49)),buttons.get(board.getBoardSquare(50)),buttons.get(board.getBoardSquare(51)), buttons.get(board.getBoardSquare(52)), buttons.get(board.getBoardSquare(53)), buttons.get(board.getBoardSquare(54)), buttons.get(board.getBoardSquare(55)),
				buttons.get(board.getBoardSquare(56)),buttons.get(board.getBoardSquare(57)),buttons.get(board.getBoardSquare(58)),buttons.get(board.getBoardSquare(59)), buttons.get(board.getBoardSquare(60)), buttons.get(board.getBoardSquare(61)), buttons.get(board.getBoardSquare(62)), buttons.get(board.getBoardSquare(63))});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {buttons.get(board.getBoardSquare(0)),buttons.get(board.getBoardSquare(1)),buttons.get(board.getBoardSquare(2)),buttons.get(board.getBoardSquare(3)), buttons.get(board.getBoardSquare(4)), buttons.get(board.getBoardSquare(5)), buttons.get(board.getBoardSquare(6)), buttons.get(board.getBoardSquare(7)),
				buttons.get(board.getBoardSquare(8)),buttons.get(board.getBoardSquare(9)),buttons.get(board.getBoardSquare(10)),buttons.get(board.getBoardSquare(11)), buttons.get(board.getBoardSquare(12)), buttons.get(board.getBoardSquare(13)), buttons.get(board.getBoardSquare(14)), buttons.get(board.getBoardSquare(15)),
				buttons.get(board.getBoardSquare(16)),buttons.get(board.getBoardSquare(17)),buttons.get(board.getBoardSquare(18)),buttons.get(board.getBoardSquare(19)), buttons.get(board.getBoardSquare(20)), buttons.get(board.getBoardSquare(21)), buttons.get(board.getBoardSquare(22)), buttons.get(board.getBoardSquare(23)),
				buttons.get(board.getBoardSquare(24)),buttons.get(board.getBoardSquare(25)),buttons.get(board.getBoardSquare(26)),buttons.get(board.getBoardSquare(27)), buttons.get(board.getBoardSquare(28)), buttons.get(board.getBoardSquare(29)), buttons.get(board.getBoardSquare(30)), buttons.get(board.getBoardSquare(31)),
				buttons.get(board.getBoardSquare(32)),buttons.get(board.getBoardSquare(33)),buttons.get(board.getBoardSquare(34)),buttons.get(board.getBoardSquare(35)), buttons.get(board.getBoardSquare(36)), buttons.get(board.getBoardSquare(37)), buttons.get(board.getBoardSquare(38)), buttons.get(board.getBoardSquare(39)),
				buttons.get(board.getBoardSquare(40)),buttons.get(board.getBoardSquare(41)),buttons.get(board.getBoardSquare(42)),buttons.get(board.getBoardSquare(43)), buttons.get(board.getBoardSquare(44)), buttons.get(board.getBoardSquare(45)), buttons.get(board.getBoardSquare(46)), buttons.get(board.getBoardSquare(47)),
				buttons.get(board.getBoardSquare(48)),buttons.get(board.getBoardSquare(49)),buttons.get(board.getBoardSquare(50)),buttons.get(board.getBoardSquare(51)), buttons.get(board.getBoardSquare(52)), buttons.get(board.getBoardSquare(53)), buttons.get(board.getBoardSquare(54)), buttons.get(board.getBoardSquare(55)),
				buttons.get(board.getBoardSquare(56)),buttons.get(board.getBoardSquare(57)),buttons.get(board.getBoardSquare(58)),buttons.get(board.getBoardSquare(59)), buttons.get(board.getBoardSquare(60)), buttons.get(board.getBoardSquare(61)), buttons.get(board.getBoardSquare(62)), buttons.get(board.getBoardSquare(63))});
		layout.setVerticalGroup(
				layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addComponent(buttons.get(board.getBoardSquare(0)))
						.addComponent(buttons.get(board.getBoardSquare(1)))
						.addComponent(buttons.get(board.getBoardSquare(2)))
						.addComponent(buttons.get(board.getBoardSquare(3)))
						.addComponent(buttons.get(board.getBoardSquare(4)))
						.addComponent(buttons.get(board.getBoardSquare(5)))
						.addComponent(buttons.get(board.getBoardSquare(6)))
						.addComponent(buttons.get(board.getBoardSquare(7))))
				.addGroup(layout.createSequentialGroup()
						.addComponent(buttons.get(board.getBoardSquare(8)))
						.addComponent(buttons.get(board.getBoardSquare(9)))
						.addComponent(buttons.get(board.getBoardSquare(10)))
						.addComponent(buttons.get(board.getBoardSquare(11)))
						.addComponent(buttons.get(board.getBoardSquare(12)))
						.addComponent(buttons.get(board.getBoardSquare(13)))
						.addComponent(buttons.get(board.getBoardSquare(14)))
						.addComponent(buttons.get(board.getBoardSquare(15))))
				.addGroup(layout.createSequentialGroup()
						.addComponent(buttons.get(board.getBoardSquare(16)))
						.addComponent(buttons.get(board.getBoardSquare(17)))
						.addComponent(buttons.get(board.getBoardSquare(18)))
						.addComponent(buttons.get(board.getBoardSquare(19)))
						.addComponent(buttons.get(board.getBoardSquare(20)))
						.addComponent(buttons.get(board.getBoardSquare(21)))
						.addComponent(buttons.get(board.getBoardSquare(22)))
						.addComponent(buttons.get(board.getBoardSquare(23))))
				.addGroup(layout.createSequentialGroup()
						.addComponent(buttons.get(board.getBoardSquare(24)))
						.addComponent(buttons.get(board.getBoardSquare(25)))
						.addComponent(buttons.get(board.getBoardSquare(26)))
						.addComponent(buttons.get(board.getBoardSquare(27)))
						.addComponent(buttons.get(board.getBoardSquare(28)))
						.addComponent(buttons.get(board.getBoardSquare(29)))
						.addComponent(buttons.get(board.getBoardSquare(30)))
						.addComponent(buttons.get(board.getBoardSquare(31))))
				.addGroup(layout.createSequentialGroup()
						.addComponent(buttons.get(board.getBoardSquare(32)))
						.addComponent(buttons.get(board.getBoardSquare(33)))
						.addComponent(buttons.get(board.getBoardSquare(34)))
						.addComponent(buttons.get(board.getBoardSquare(35)))
						.addComponent(buttons.get(board.getBoardSquare(36)))
						.addComponent(buttons.get(board.getBoardSquare(37)))
						.addComponent(buttons.get(board.getBoardSquare(38)))
						.addComponent(buttons.get(board.getBoardSquare(39))))
				.addGroup(layout.createSequentialGroup()
						.addComponent(buttons.get(board.getBoardSquare(40)))
						.addComponent(buttons.get(board.getBoardSquare(41)))
						.addComponent(buttons.get(board.getBoardSquare(42)))
						.addComponent(buttons.get(board.getBoardSquare(43)))
						.addComponent(buttons.get(board.getBoardSquare(44)))
						.addComponent(buttons.get(board.getBoardSquare(45)))
						.addComponent(buttons.get(board.getBoardSquare(46)))
						.addComponent(buttons.get(board.getBoardSquare(47))))
				.addGroup(layout.createSequentialGroup()
						.addComponent(buttons.get(board.getBoardSquare(48)))
						.addComponent(buttons.get(board.getBoardSquare(49)))
						.addComponent(buttons.get(board.getBoardSquare(50)))
						.addComponent(buttons.get(board.getBoardSquare(51)))
						.addComponent(buttons.get(board.getBoardSquare(52)))
						.addComponent(buttons.get(board.getBoardSquare(53)))
						.addComponent(buttons.get(board.getBoardSquare(54)))
						.addComponent(buttons.get(board.getBoardSquare(55))))
				.addGroup(layout.createSequentialGroup()
						.addComponent(buttons.get(board.getBoardSquare(56)))
						.addComponent(buttons.get(board.getBoardSquare(57)))
						.addComponent(buttons.get(board.getBoardSquare(58)))
						.addComponent(buttons.get(board.getBoardSquare(59)))
						.addComponent(buttons.get(board.getBoardSquare(60)))
						.addComponent(buttons.get(board.getBoardSquare(61)))
						.addComponent(buttons.get(board.getBoardSquare(62)))
						.addComponent(buttons.get(board.getBoardSquare(63)))));
		pack();
	}
}
