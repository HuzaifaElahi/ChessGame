/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/
package model;


import java.util.*;

// line 35 "RestoApp v3.ump"
public class Player
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private String name;

  //Player Associations
  private List<SpecificChessPiece> specificChessPieces;
  private Chess chess;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(String aName, Chess aChess)
  {
    name = aName;
    specificChessPieces = new ArrayList<SpecificChessPiece>();
    boolean didAddChess = setChess(aChess);
    if (!didAddChess)
    {
      throw new RuntimeException("Unable to create player due to chess");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public SpecificChessPiece getSpecificChessPiece(int index)
  {
    SpecificChessPiece aSpecificChessPiece = specificChessPieces.get(index);
    return aSpecificChessPiece;
  }

  public List<SpecificChessPiece> getSpecificChessPieces()
  {
    List<SpecificChessPiece> newSpecificChessPieces = Collections.unmodifiableList(specificChessPieces);
    return newSpecificChessPieces;
  }

  public int numberOfSpecificChessPieces()
  {
    int number = specificChessPieces.size();
    return number;
  }

  public boolean hasSpecificChessPieces()
  {
    boolean has = specificChessPieces.size() > 0;
    return has;
  }

  public int indexOfSpecificChessPiece(SpecificChessPiece aSpecificChessPiece)
  {
    int index = specificChessPieces.indexOf(aSpecificChessPiece);
    return index;
  }

  public Chess getChess()
  {
    return chess;
  }

  public boolean isNumberOfSpecificChessPiecesValid()
  {
    boolean isValid = numberOfSpecificChessPieces() >= minimumNumberOfSpecificChessPieces() && numberOfSpecificChessPieces() <= maximumNumberOfSpecificChessPieces();
    return isValid;
  }

  public static int requiredNumberOfSpecificChessPieces()
  {
    return 16;
  }

  public static int minimumNumberOfSpecificChessPieces()
  {
    return 16;
  }

  public static int maximumNumberOfSpecificChessPieces()
  {
    return 16;
  }

  public SpecificChessPiece addSpecificChessPiece(boolean aIsLive, int aCurrentX, int aCurrentY, boolean aPlayer1, ChessPieceGeneral aChessPieceGeneral, BoardSquare aBoardSquare)
  {
    if (numberOfSpecificChessPieces() >= maximumNumberOfSpecificChessPieces())
    {
      return null;
    }
    else
    {
      return new SpecificChessPiece(aIsLive, aCurrentX, aCurrentY, aPlayer1, aChessPieceGeneral, aBoardSquare, this);
    }
  }

  public boolean addSpecificChessPiece(SpecificChessPiece aSpecificChessPiece)
  {
    boolean wasAdded = false;
    if (specificChessPieces.contains(aSpecificChessPiece)) { return false; }
    if (numberOfSpecificChessPieces() >= maximumNumberOfSpecificChessPieces())
    {
      return wasAdded;
    }

    Player existingPlayer = aSpecificChessPiece.getPlayer();
    boolean isNewPlayer = existingPlayer != null && !this.equals(existingPlayer);

    if (isNewPlayer && existingPlayer.numberOfSpecificChessPieces() <= minimumNumberOfSpecificChessPieces())
    {
      return wasAdded;
    }

    if (isNewPlayer)
    {
      aSpecificChessPiece.setPlayer(this);
    }
    else
    {
      specificChessPieces.add(aSpecificChessPiece);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSpecificChessPiece(SpecificChessPiece aSpecificChessPiece)
  {
    boolean wasRemoved = false;
    //Unable to remove aSpecificChessPiece, as it must always have a player
    if (this.equals(aSpecificChessPiece.getPlayer()))
    {
      return wasRemoved;
    }

    //player already at minimum (16)
    if (numberOfSpecificChessPieces() <= minimumNumberOfSpecificChessPieces())
    {
      return wasRemoved;
    }
    specificChessPieces.remove(aSpecificChessPiece);
    wasRemoved = true;
    return wasRemoved;
  }

  public boolean setChess(Chess aChess)
  {
    boolean wasSet = false;
    //Must provide chess to player
    if (aChess == null)
    {
      return wasSet;
    }

    //chess already at maximum (2)
    if (aChess.numberOfPlayers() >= Chess.maximumNumberOfPlayers())
    {
      return wasSet;
    }
    
    Chess existingChess = chess;
    chess = aChess;
    if (existingChess != null && !existingChess.equals(aChess))
    {
      boolean didRemove = existingChess.removePlayer(this);
      if (!didRemove)
      {
        chess = existingChess;
        return wasSet;
      }
    }
    chess.addPlayer(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=specificChessPieces.size(); i > 0; i--)
    {
      SpecificChessPiece aSpecificChessPiece = specificChessPieces.get(i - 1);
      aSpecificChessPiece.delete();
    }
    Chess placeholderChess = chess;
    this.chess = null;
    if(placeholderChess != null)
    {
      placeholderChess.removePlayer(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "chess = "+(getChess()!=null?Integer.toHexString(System.identityHashCode(getChess())):"null");
  }
}