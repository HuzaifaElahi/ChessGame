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

	public static Player getPlayerByButton(BoardSquare boardSquare) {
		if(boardSquare.hasSpecificChessPiece()) {
			return boardSquare.getSpecificChessPiece().getPlayer();
		}
		System.out.println("NO PLAYER");
		return null;
	}

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
			BoardSquare thisSquare = Controller.getButtonWithCoords(piece.getCurrentX(), piece.getCurrentY()+yOffset, piece.getBoardSquare().getBoard());
			if(!thisSquare.hasSpecificChessPiece()) {
				finalListSquares.add(thisSquare);
			}
			//Initial move can be two steps
			if(piece.getCurrentY() == startingPos) {
				thisSquare = Controller.getButtonWithCoords(piece.getCurrentX(), piece.getCurrentY()+yOffset*2, piece.getBoardSquare().getBoard());
				finalListSquares.add(thisSquare);
			}
		}
		return finalListSquares;
	}


}
