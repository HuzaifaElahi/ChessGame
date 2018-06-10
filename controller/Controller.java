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
}
