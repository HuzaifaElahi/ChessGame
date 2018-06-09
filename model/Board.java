/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/
package model;

import java.util.*;

// line 21 "RestoApp v3.ump"
public class Board
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Board Associations
  private List<BoardSquare> boardSquares;
  private Chess chess;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Board(Chess aChess)
  {
    boardSquares = new ArrayList<BoardSquare>();
    if (aChess == null || aChess.getBoard() != null)
    {
      throw new RuntimeException("Unable to create Board due to aChess");
    }
    chess = aChess;
  }

  public Board()
  {
    boardSquares = new ArrayList<BoardSquare>();
    chess = new Chess(this);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public BoardSquare getBoardSquare(int index)
  {
    BoardSquare aBoardSquare = boardSquares.get(index);
    return aBoardSquare;
  }

  public List<BoardSquare> getBoardSquares()
  {
    List<BoardSquare> newBoardSquares = Collections.unmodifiableList(boardSquares);
    return newBoardSquares;
  }

  public int numberOfBoardSquares()
  {
    int number = boardSquares.size();
    return number;
  }

  public boolean hasBoardSquares()
  {
    boolean has = boardSquares.size() > 0;
    return has;
  }

  public int indexOfBoardSquare(BoardSquare aBoardSquare)
  {
    int index = boardSquares.indexOf(aBoardSquare);
    return index;
  }

  public Chess getChess()
  {
    return chess;
  }

  public boolean isNumberOfBoardSquaresValid()
  {
    boolean isValid = numberOfBoardSquares() >= minimumNumberOfBoardSquares() && numberOfBoardSquares() <= maximumNumberOfBoardSquares();
    return isValid;
  }

  public static int requiredNumberOfBoardSquares()
  {
    return 64;
  }

  public static int minimumNumberOfBoardSquares()
  {
    return 64;
  }

  public static int maximumNumberOfBoardSquares()
  {
    return 64;
  }

  public BoardSquare addBoardSquare(int aX, int aY, String aColor)
  {
    if (numberOfBoardSquares() >= maximumNumberOfBoardSquares())
    {
      return null;
    }
    else
    {
      return new BoardSquare(aX, aY, aColor, this);
    }
  }

  public boolean addBoardSquare(BoardSquare aBoardSquare)
  {
    boolean wasAdded = false;
    if (boardSquares.contains(aBoardSquare)) { return false; }
    if (numberOfBoardSquares() >= maximumNumberOfBoardSquares())
    {
      return wasAdded;
    }

    Board existingBoard = aBoardSquare.getBoard();
    boolean isNewBoard = existingBoard != null && !this.equals(existingBoard);

    if (isNewBoard && existingBoard.numberOfBoardSquares() <= minimumNumberOfBoardSquares())
    {
      return wasAdded;
    }

    if (isNewBoard)
    {
      aBoardSquare.setBoard(this);
    }
    else
    {
      boardSquares.add(aBoardSquare);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBoardSquare(BoardSquare aBoardSquare)
  {
    boolean wasRemoved = false;
    //Unable to remove aBoardSquare, as it must always have a board
    if (this.equals(aBoardSquare.getBoard()))
    {
      return wasRemoved;
    }

    //board already at minimum (64)
    if (numberOfBoardSquares() <= minimumNumberOfBoardSquares())
    {
      return wasRemoved;
    }
    boardSquares.remove(aBoardSquare);
    wasRemoved = true;
    return wasRemoved;
  }

  public void delete()
  {
    for(int i=boardSquares.size(); i > 0; i--)
    {
      BoardSquare aBoardSquare = boardSquares.get(i - 1);
      aBoardSquare.delete();
    }
    Chess existingChess = chess;
    chess = null;
    if (existingChess != null)
    {
      existingChess.delete();
    }
  }

}