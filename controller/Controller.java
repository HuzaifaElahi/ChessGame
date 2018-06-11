package controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import model.Board;
import model.BoardSquare;
import model.Chess;
import model.ChessPieceGeneral;
import model.Player;
import model.SpecificChessPiece;
import view.BoardView;

public class Controller {
	
	//Finds the board square associated with the given coordinates on the board
	public static BoardSquare getButtonWithCoords(int x, int y, Board board){
		List<BoardSquare> squareHolder = new ArrayList<BoardSquare>();
		squareHolder = board.getBoardSquares();
		for(BoardSquare square : squareHolder) {
			if((square.getX() == x) && (square.getY() == y)) {
				return square;
			}
		}
		System.out.println("NO SUCH SQUARE");
		return null;
	}

	//Finds the player associated with the given boardSquare
	public static Player getPlayerByButton(BoardSquare boardSquare) {
		if(boardSquare.hasSpecificChessPiece()) {
			return boardSquare.getSpecificChessPiece().getPlayer();
		}
		System.out.println("NO PLAYER");
		return null;
	}

	//Helper method for finding valid diagonal moves
	private static Boolean diagonalBooleanDecisionHelper(Boolean isUp, Boolean isRight, int x, int y, int xRef, int yRef) {
		Boolean pickBooleanEq = false;
		//Check conditions to ensure within bounds of specific boundaries
		Boolean right =  (xRef + x <= 8);
		Boolean left =  (xRef + x >= 1);
		Boolean bottom = (yRef + y >= 1);
		Boolean top = (yRef + y <= 8);
		//Assign boolean to appropriate diagonal direction
		if(isUp && isRight)
			pickBooleanEq = right&&top;
		else if(isUp && !isRight)
			pickBooleanEq = left&&top;
		else if(!isUp && isRight)
			pickBooleanEq = bottom&&right;
		else {
			pickBooleanEq = bottom&&left;
		}
		return pickBooleanEq;
	}

	//Helper method to negative value of y where we must go down
	private static int diagonalVerticalSignHelper(Boolean isUp, int y) {
		if(!isUp) {
			return -y;
		}
		else {
			return y;
		}
	}
	
	//Helper method to negative value of x where we must go left
	private static int diagonalHorizontalSignHelper(Boolean isRight, int x) {
		if(!isRight) {
			return -x;
		}
		else {
			return x;
		}
	}
	
	//Method to find all valid moves for a diagonal line
	public static ArrayList<BoardSquare> diagonalMove(SpecificChessPiece piece, Player currentPlayer, Boolean isUp, Boolean isRight) {
		ArrayList<BoardSquare> finalListSquares = new ArrayList<BoardSquare>();
		int xRef = piece.getCurrentX();
		int yRef = piece.getCurrentY();
		int x = 0 ; int y = 0;
		Boolean pickBooleanEq = diagonalBooleanDecisionHelper(isUp, isRight, x, y, xRef, yRef);
		for(x = 1, y = 1; pickBooleanEq; x++, y++) {
			y=diagonalVerticalSignHelper(isUp, y);
			x=diagonalHorizontalSignHelper(isRight,x);
			pickBooleanEq = diagonalBooleanDecisionHelper(isUp, isRight, x, y, xRef, yRef);
			BoardSquare possibleSquare = Controller.getButtonWithCoords(xRef + x, yRef + y, piece.getBoardSquare().getBoard());
			if(possibleSquare!=null) {
				//If piece in the way
				if(possibleSquare.hasSpecificChessPiece()) {
					//If opposition piece, add as target
					if(possibleSquare.getSpecificChessPiece().getPlayer() != currentPlayer) {
						finalListSquares.add(possibleSquare);
						break;
					}
					//If own piece, don't add as option
					else {
						break;
					}
				}
				//Else add empty spot as option
				else {
					finalListSquares.add(possibleSquare);
				}
			}
			y=diagonalVerticalSignHelper(isUp, y);
			x=diagonalHorizontalSignHelper(isRight,x);
		}
		return finalListSquares;
	}
	
	//Method to find all valid moves for a straight line
	public static ArrayList<BoardSquare> validStraightLineMoves(SpecificChessPiece piece, Boolean isUpDown, ArrayList<BoardSquare> squares, Player currentPlayer) {
		ArrayList<BoardSquare> finalListSquares = new ArrayList<BoardSquare>();
		BoardSquare viableSquare;
		int currentReference; int squareReference; int offsetY = 0; int offsetX = 0; int currentOffset = 0;
		for(BoardSquare square : squares ) {
			//Check if meant to run for up and down moves or sideways moves
			if(!isUpDown) {
				currentReference = piece.getCurrentX();
				squareReference = square.getX();
			}
			else {
				currentReference = piece.getCurrentY();
				squareReference = square.getY();
			}

			if(squareReference == currentReference){
				//Moving right or up
				for(currentOffset = 1 ; currentOffset <= 8 - currentReference ; currentOffset++) {
					if(isUpDown) {
						offsetY = currentOffset;
					}
					else {
						offsetX = currentOffset;
					}
					//if at right or top edge, can't move
					if(currentReference == 8) {
						break;
					}
					//check square above or to right of current piece location
					viableSquare = Controller.getButtonWithCoords(piece.getCurrentX()+offsetX, piece.getCurrentY()+offsetY, square.getBoard());
					if(viableSquare==null) {
						continue;
					}
					//if another piece in the way
					if(viableSquare.hasSpecificChessPiece()) {
						//if piece belongs to opponent, add as target
						if(viableSquare.getSpecificChessPiece().getPlayer() != currentPlayer) {
							finalListSquares.add(viableSquare);
							break;
						}
						//if piece belongs to same player, don't add as target
						else {
							break;
						}
					}
					//if no piece in the way, add as option
					else {
						finalListSquares.add(viableSquare);
					}
				}
				//Moving left or down
				for(currentOffset = 1 ; currentOffset <= currentReference ; currentOffset++) {
					if(isUpDown) {
						offsetY = currentOffset;
					}
					else {
						offsetX = currentOffset;
					}
					//if at bottom or left edge, break
					if(currentReference == 1) {
						break;
					}
					//check square below or to left of current piece location
					viableSquare = Controller.getButtonWithCoords(piece.getCurrentX()-offsetX, piece.getCurrentY()-offsetY, square.getBoard());
					if(viableSquare ==null) {
						continue;
					}
					//if another piece in the way
					if(viableSquare.hasSpecificChessPiece()) {
						//if piece belongs to opponent, add as target
						if(viableSquare.getSpecificChessPiece().getPlayer() != currentPlayer) {
							finalListSquares.add(viableSquare);
							break;
						}
						//if piece belongs to same player, don't add as target
						else {
							break;
						}
					}
					//if no piece in the way, add as option
					else {
						finalListSquares.add(viableSquare);
					}
				}
			}
		}
		return finalListSquares;
	}

	//Method to find all valid moves for a given pawn
	public static ArrayList<BoardSquare> validPawnMoves(SpecificChessPiece piece, Player currentPlayer, Chess chess) {
		ArrayList<BoardSquare> finalListSquares = new ArrayList<BoardSquare>();
		int startingPos = 7; int yOffset = -1;
		if(piece.getPlayer().getName() == chess.getPlayer(0).getName()) {
			yOffset = 1;
			startingPos = 2;
		}
		if(piece.getCurrentY() < 8 && piece.getCurrentY() > 1) {
			//Options to kill using pawn
			if(piece.getCurrentX() < 8) {
				//right corner move
				BoardSquare killSquare1 = Controller.getButtonWithCoords(piece.getCurrentX()+1, piece.getCurrentY()+yOffset, piece.getBoardSquare().getBoard());
				if(killSquare1.hasSpecificChessPiece()) {
					if(killSquare1.getSpecificChessPiece().getPlayer() != currentPlayer) {
						finalListSquares.add(killSquare1);
					}
				}
			}
			if(piece.getCurrentX() > 1) {
				//left corner move
				BoardSquare killSquare2 = Controller.getButtonWithCoords(piece.getCurrentX()-1, piece.getCurrentY()+yOffset, piece.getBoardSquare().getBoard());
				if(killSquare2.hasSpecificChessPiece()) {
					if(killSquare2.getSpecificChessPiece().getPlayer() != currentPlayer) {
						finalListSquares.add(killSquare2);
					}
				}
			}
			//Options to move forward using pawn
			BoardSquare thisSquareFrontOne = Controller.getButtonWithCoords(piece.getCurrentX(), piece.getCurrentY()+yOffset, piece.getBoardSquare().getBoard());
			if(!thisSquareFrontOne.hasSpecificChessPiece()) {
				finalListSquares.add(thisSquareFrontOne);
			}
			//Initial move can be two steps
			if(piece.getCurrentY() == startingPos) {
				//Make sure pawn can move two steps in front
				BoardSquare thisSquareFrontTwo = Controller.getButtonWithCoords(piece.getCurrentX(), piece.getCurrentY()+yOffset*2, piece.getBoardSquare().getBoard());
				if(!thisSquareFrontTwo.hasSpecificChessPiece() && finalListSquares.contains(thisSquareFrontOne)) {
					finalListSquares.add(thisSquareFrontTwo);
				}
			}
		}
		return finalListSquares;
	}


}
