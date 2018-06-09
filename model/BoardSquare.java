/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/

package model;


// line 26 "RestoApp v3.ump"
public class BoardSquare
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //BoardSquare Attributes
  private int x;
  private int y;
  private String color;

  //BoardSquare Associations
  private SpecificChessPiece specificChessPiece;
  private Board board;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BoardSquare(int aX, int aY, String aColor, Board aBoard)
  {
    x = aX;
    y = aY;
    color = aColor;
    boolean didAddBoard = setBoard(aBoard);
    if (!didAddBoard)
    {
      throw new RuntimeException("Unable to create boardSquare due to board");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setX(int aX)
  {
    boolean wasSet = false;
    x = aX;
    wasSet = true;
    return wasSet;
  }

  public boolean setY(int aY)
  {
    boolean wasSet = false;
    y = aY;
    wasSet = true;
    return wasSet;
  }

  public boolean setColor(String aColor)
  {
    boolean wasSet = false;
    color = aColor;
    wasSet = true;
    return wasSet;
  }

  public int getX()
  {
    return x;
  }

  public int getY()
  {
    return y;
  }

  public String getColor()
  {
    return color;
  }

  public SpecificChessPiece getSpecificChessPiece()
  {
    return specificChessPiece;
  }

  public boolean hasSpecificChessPiece()
  {
    boolean has = specificChessPiece != null;
    return has;
  }

  public Board getBoard()
  {
    return board;
  }

  public boolean setSpecificChessPiece(SpecificChessPiece aNewSpecificChessPiece)
  {
    boolean wasSet = false;
    if (specificChessPiece != null && !specificChessPiece.equals(aNewSpecificChessPiece) && equals(specificChessPiece.getBoardSquare()))
    {
      //Unable to setSpecificChessPiece, as existing specificChessPiece would become an orphan
      return wasSet;
    }

    specificChessPiece = aNewSpecificChessPiece;
    BoardSquare anOldBoardSquare = aNewSpecificChessPiece != null ? aNewSpecificChessPiece.getBoardSquare() : null;

    if (!this.equals(anOldBoardSquare))
    {
      if (anOldBoardSquare != null)
      {
        anOldBoardSquare.specificChessPiece = null;
      }
      if (specificChessPiece != null)
      {
        specificChessPiece.setBoardSquare(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public boolean setBoard(Board aBoard)
  {
    boolean wasSet = false;
    //Must provide board to boardSquare
    if (aBoard == null)
    {
      return wasSet;
    }

    //board already at maximum (64)
    if (aBoard.numberOfBoardSquares() >= Board.maximumNumberOfBoardSquares())
    {
      return wasSet;
    }
    
    Board existingBoard = board;
    board = aBoard;
    if (existingBoard != null && !existingBoard.equals(aBoard))
    {
      boolean didRemove = existingBoard.removeBoardSquare(this);
      if (!didRemove)
      {
        board = existingBoard;
        return wasSet;
      }
    }
    board.addBoardSquare(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    SpecificChessPiece existingSpecificChessPiece = specificChessPiece;
    specificChessPiece = null;
    if (existingSpecificChessPiece != null)
    {
      existingSpecificChessPiece.delete();
    }
    Board placeholderBoard = board;
    this.board = null;
    if(placeholderBoard != null)
    {
      placeholderBoard.removeBoardSquare(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "x" + ":" + getX()+ "," +
            "y" + ":" + getY()+ "," +
            "color" + ":" + getColor()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "specificChessPiece = "+(getSpecificChessPiece()!=null?Integer.toHexString(System.identityHashCode(getSpecificChessPiece())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "board = "+(getBoard()!=null?Integer.toHexString(System.identityHashCode(getBoard())):"null");
  }
}