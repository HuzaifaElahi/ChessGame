/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/
package model;


import java.util.*;

// line 8 "RestoApp v3.ump"
public class ChessPieceGeneral
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ChessPieceGeneral Attributes
  private String name;

  //ChessPieceGeneral Associations
  private List<SpecificChessPiece> specificChessPieces;
  private Chess chess;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ChessPieceGeneral(String aName, Chess aChess)
  {
    name = aName;
    specificChessPieces = new ArrayList<SpecificChessPiece>();
    boolean didAddChess = setChess(aChess);
    if (!didAddChess)
    {
      throw new RuntimeException("Unable to create chessPieceGeneral due to chess");
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
    boolean isValid = numberOfSpecificChessPieces() >= minimumNumberOfSpecificChessPieces();
    return isValid;
  }

  public static int minimumNumberOfSpecificChessPieces()
  {
    return 1;
  }

  public SpecificChessPiece addSpecificChessPiece(boolean aIsLive, int aCurrentX, int aCurrentY, boolean aPlayer1, BoardSquare aBoardSquare, Player aPlayer)
  {
    SpecificChessPiece aNewSpecificChessPiece = new SpecificChessPiece(aIsLive, aCurrentX, aCurrentY, aPlayer1, this, aBoardSquare, aPlayer);
    return aNewSpecificChessPiece;
  }

  public boolean addSpecificChessPiece(SpecificChessPiece aSpecificChessPiece)
  {
    boolean wasAdded = false;
    if (specificChessPieces.contains(aSpecificChessPiece)) { return false; }
    ChessPieceGeneral existingChessPieceGeneral = aSpecificChessPiece.getChessPieceGeneral();
    boolean isNewChessPieceGeneral = existingChessPieceGeneral != null && !this.equals(existingChessPieceGeneral);

    if (isNewChessPieceGeneral && existingChessPieceGeneral.numberOfSpecificChessPieces() <= minimumNumberOfSpecificChessPieces())
    {
      return wasAdded;
    }
    if (isNewChessPieceGeneral)
    {
      aSpecificChessPiece.setChessPieceGeneral(this);
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
    //Unable to remove aSpecificChessPiece, as it must always have a chessPieceGeneral
    if (this.equals(aSpecificChessPiece.getChessPieceGeneral()))
    {
      return wasRemoved;
    }

    //chessPieceGeneral already at minimum (1)
    if (numberOfSpecificChessPieces() <= minimumNumberOfSpecificChessPieces())
    {
      return wasRemoved;
    }

    specificChessPieces.remove(aSpecificChessPiece);
    wasRemoved = true;
    return wasRemoved;
  }

  public boolean addSpecificChessPieceAt(SpecificChessPiece aSpecificChessPiece, int index)
  {  
    boolean wasAdded = false;
    if(addSpecificChessPiece(aSpecificChessPiece))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSpecificChessPieces()) { index = numberOfSpecificChessPieces() - 1; }
      specificChessPieces.remove(aSpecificChessPiece);
      specificChessPieces.add(index, aSpecificChessPiece);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSpecificChessPieceAt(SpecificChessPiece aSpecificChessPiece, int index)
  {
    boolean wasAdded = false;
    if(specificChessPieces.contains(aSpecificChessPiece))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSpecificChessPieces()) { index = numberOfSpecificChessPieces() - 1; }
      specificChessPieces.remove(aSpecificChessPiece);
      specificChessPieces.add(index, aSpecificChessPiece);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSpecificChessPieceAt(aSpecificChessPiece, index);
    }
    return wasAdded;
  }

  public boolean setChess(Chess aChess)
  {
    boolean wasSet = false;
    //Must provide chess to chessPieceGeneral
    if (aChess == null)
    {
      return wasSet;
    }

    //chess already at maximum (8)
    if (aChess.numberOfChessPieceGenerals() >= Chess.maximumNumberOfChessPieceGenerals())
    {
      return wasSet;
    }
    
    Chess existingChess = chess;
    chess = aChess;
    if (existingChess != null && !existingChess.equals(aChess))
    {
      boolean didRemove = existingChess.removeChessPieceGeneral(this);
      if (!didRemove)
      {
        chess = existingChess;
        return wasSet;
      }
    }
    chess.addChessPieceGeneral(this);
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
      placeholderChess.removeChessPieceGeneral(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "chess = "+(getChess()!=null?Integer.toHexString(System.identityHashCode(getChess())):"null");
  }
}