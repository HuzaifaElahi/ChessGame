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
			System.out.println(square.getX() + " " + square.getY());
			if((square.getX() == x) && (square.getY() == y)) {
				return square;
			}
		}
		System.out.println("NO SUCH SQUARE");
		return null;
	}

}
