/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/
package model;



// line 14 "RestoApp v3.ump"
public class SpecificChessPiece
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //SpecificChessPiece Attributes
  private boolean isLive;
  private int currentX;
  private int currentY;
  private boolean player1;

  //SpecificChessPiece Associations
  private ChessPieceGeneral chessPieceGeneral;
  private BoardSquare boardSquare;
  private Player player;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SpecificChessPiece(boolean aIsLive, int aCurrentX, int aCurrentY, boolean aPlayer1, ChessPieceGeneral aChessPieceGeneral, BoardSquare aBoardSquare, Player aPlayer)
  {
    isLive = aIsLive;
    currentX = aCurrentX;
    currentY = aCurrentY;
    player1 = aPlayer1;
    boolean didAddChessPieceGeneral = setChessPieceGeneral(aChessPieceGeneral);
    if (!didAddChessPieceGeneral)
    {
      throw new RuntimeException("Unable to create specificChessPiece due to chessPieceGeneral");
    }
    boolean didAddBoardSquare = setBoardSquare(aBoardSquare);
    if (!didAddBoardSquare)
    {
      throw new RuntimeException("Unable to create specificChessPiece due to boardSquare");
    }
    boolean didAddPlayer = setPlayer(aPlayer);
    if (!didAddPlayer)
    {
      throw new RuntimeException("Unable to create specificChessPiece due to player");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsLive(boolean aIsLive)
  {
    boolean wasSet = false;
    isLive = aIsLive;
    wasSet = true;
    return wasSet;
  }

  public boolean setCurrentX(int aCurrentX)
  {
    boolean wasSet = false;
    currentX = aCurrentX;
    wasSet = true;
    return wasSet;
  }

  public boolean setCurrentY(int aCurrentY)
  {
    boolean wasSet = false;
    currentY = aCurrentY;
    wasSet = true;
    return wasSet;
  }

  public boolean setPlayer1(boolean aPlayer1)
  {
    boolean wasSet = false;
    player1 = aPlayer1;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsLive()
  {
    return isLive;
  }

  public int getCurrentX()
  {
    return currentX;
  }

  public int getCurrentY()
  {
    return currentY;
  }

  public boolean getPlayer1()
  {
    return player1;
  }

  public boolean isIsLive()
  {
    return isLive;
  }

  public boolean isPlayer1()
  {
    return player1;
  }

  public ChessPieceGeneral getChessPieceGeneral()
  {
    return chessPieceGeneral;
  }

  public BoardSquare getBoardSquare()
  {
    return boardSquare;
  }

  public Player getPlayer()
  {
    return player;
  }

  public boolean setChessPieceGeneral(ChessPieceGeneral aChessPieceGeneral)
  {
    boolean wasSet = false;
    //Must provide chessPieceGeneral to specificChessPiece
    if (aChessPieceGeneral == null)
    {
      return wasSet;
    }

    if (chessPieceGeneral != null && chessPieceGeneral.numberOfSpecificChessPieces() <= ChessPieceGeneral.minimumNumberOfSpecificChessPieces())
    {
      return wasSet;
    }

    ChessPieceGeneral existingChessPieceGeneral = chessPieceGeneral;
    chessPieceGeneral = aChessPieceGeneral;
    if (existingChessPieceGeneral != null && !existingChessPieceGeneral.equals(aChessPieceGeneral))
    {
      boolean didRemove = existingChessPieceGeneral.removeSpecificChessPiece(this);
      if (!didRemove)
      {
        chessPieceGeneral = existingChessPieceGeneral;
        return wasSet;
      }
    }
    chessPieceGeneral.addSpecificChessPiece(this);
    wasSet = true;
    return wasSet;
  }

  public boolean setBoardSquare(BoardSquare aNewBoardSquare)
  {
    boolean wasSet = false;
    if (aNewBoardSquare == null)
    {
      //Unable to setBoardSquare to null, as specificChessPiece must always be associated to a boardSquare
      return wasSet;
    }
    
    SpecificChessPiece existingSpecificChessPiece = aNewBoardSquare.getSpecificChessPiece();
    if (existingSpecificChessPiece != null && !equals(existingSpecificChessPiece))
    {
      //Unable to setBoardSquare, the current boardSquare already has a specificChessPiece, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    BoardSquare anOldBoardSquare = boardSquare;
    boardSquare = aNewBoardSquare;
    boardSquare.setSpecificChessPiece(this);

    if (anOldBoardSquare != null)
    {
      anOldBoardSquare.setSpecificChessPiece(null);
    }
    wasSet = true;
    return wasSet;
  }

  public boolean setPlayer(Player aPlayer)
  {
    boolean wasSet = false;
    //Must provide player to specificChessPiece
    if (aPlayer == null)
    {
      return wasSet;
    }

    //player already at maximum (16)
    if (aPlayer.numberOfSpecificChessPieces() >= Player.maximumNumberOfSpecificChessPieces())
    {
      return wasSet;
    }
    
    Player existingPlayer = player;
    player = aPlayer;
    if (existingPlayer != null && !existingPlayer.equals(aPlayer))
    {
      boolean didRemove = existingPlayer.removeSpecificChessPiece(this);
      if (!didRemove)
      {
        player = existingPlayer;
        return wasSet;
      }
    }
    player.addSpecificChessPiece(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    ChessPieceGeneral placeholderChessPieceGeneral = chessPieceGeneral;
    this.chessPieceGeneral = null;
    if(placeholderChessPieceGeneral != null)
    {
      placeholderChessPieceGeneral.removeSpecificChessPiece(this);
    }
    BoardSquare existingBoardSquare = boardSquare;
    boardSquare = null;
    if (existingBoardSquare != null)
    {
      existingBoardSquare.setSpecificChessPiece(null);
    }
    Player placeholderPlayer = player;
    this.player = null;
    if(placeholderPlayer != null)
    {
      placeholderPlayer.removeSpecificChessPiece(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "isLive" + ":" + getIsLive()+ "," +
            "currentX" + ":" + getCurrentX()+ "," +
            "currentY" + ":" + getCurrentY()+ "," +
            "player1" + ":" + getPlayer1()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "chessPieceGeneral = "+(getChessPieceGeneral()!=null?Integer.toHexString(System.identityHashCode(getChessPieceGeneral())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "boardSquare = "+(getBoardSquare()!=null?Integer.toHexString(System.identityHashCode(getBoardSquare())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "player = "+(getPlayer()!=null?Integer.toHexString(System.identityHashCode(getPlayer())):"null");
  }
}