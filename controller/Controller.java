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

}
