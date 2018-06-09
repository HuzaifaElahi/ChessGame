/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3728.d139ed893 modeling language!*/
package model;


import java.util.*;

// line 1 "RestoApp v3.ump"
public class Chess
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Chess Associations
  private List<ChessPieceGeneral> chessPieceGenerals;
  private Board board;
  private List<Player> players;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Chess(Board aBoard)
  {
    chessPieceGenerals = new ArrayList<ChessPieceGeneral>();
    if (aBoard == null || aBoard.getChess() != null)
    {
      throw new RuntimeException("Unable to create Chess due to aBoard");
    }
    board = aBoard;
    players = new ArrayList<Player>();
  }

  public Chess()
  {
    chessPieceGenerals = new ArrayList<ChessPieceGeneral>();
    board = new Board(this);
    players = new ArrayList<Player>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public ChessPieceGeneral getChessPieceGeneral(int index)
  {
    ChessPieceGeneral aChessPieceGeneral = chessPieceGenerals.get(index);
    return aChessPieceGeneral;
  }

  public List<ChessPieceGeneral> getChessPieceGenerals()
  {
    List<ChessPieceGeneral> newChessPieceGenerals = Collections.unmodifiableList(chessPieceGenerals);
    return newChessPieceGenerals;
  }

  public int numberOfChessPieceGenerals()
  {
    int number = chessPieceGenerals.size();
    return number;
  }

  public boolean hasChessPieceGenerals()
  {
    boolean has = chessPieceGenerals.size() > 0;
    return has;
  }

  public int indexOfChessPieceGeneral(ChessPieceGeneral aChessPieceGeneral)
  {
    int index = chessPieceGenerals.indexOf(aChessPieceGeneral);
    return index;
  }

  public Board getBoard()
  {
    return board;
  }

  public Player getPlayer(int index)
  {
    Player aPlayer = players.get(index);
    return aPlayer;
  }

  public List<Player> getPlayers()
  {
    List<Player> newPlayers = Collections.unmodifiableList(players);
    return newPlayers;
  }

  public int numberOfPlayers()
  {
    int number = players.size();
    return number;
  }

  public boolean hasPlayers()
  {
    boolean has = players.size() > 0;
    return has;
  }

  public int indexOfPlayer(Player aPlayer)
  {
    int index = players.indexOf(aPlayer);
    return index;
  }

  public boolean isNumberOfChessPieceGeneralsValid()
  {
    boolean isValid = numberOfChessPieceGenerals() >= minimumNumberOfChessPieceGenerals() && numberOfChessPieceGenerals() <= maximumNumberOfChessPieceGenerals();
    return isValid;
  }

  public static int requiredNumberOfChessPieceGenerals()
  {
    return 8;
  }

  public static int minimumNumberOfChessPieceGenerals()
  {
    return 8;
  }

  public static int maximumNumberOfChessPieceGenerals()
  {
    return 8;
  }

  public ChessPieceGeneral addChessPieceGeneral(String aName)
  {
    if (numberOfChessPieceGenerals() >= maximumNumberOfChessPieceGenerals())
    {
      return null;
    }
    else
    {
      return new ChessPieceGeneral(aName, this);
    }
  }

  public boolean addChessPieceGeneral(ChessPieceGeneral aChessPieceGeneral)
  {
    boolean wasAdded = false;
    if (chessPieceGenerals.contains(aChessPieceGeneral)) { return false; }
    if (numberOfChessPieceGenerals() >= maximumNumberOfChessPieceGenerals())
    {
      return wasAdded;
    }

    Chess existingChess = aChessPieceGeneral.getChess();
    boolean isNewChess = existingChess != null && !this.equals(existingChess);

    if (isNewChess && existingChess.numberOfChessPieceGenerals() <= minimumNumberOfChessPieceGenerals())
    {
      return wasAdded;
    }

    if (isNewChess)
    {
      aChessPieceGeneral.setChess(this);
    }
    else
    {
      chessPieceGenerals.add(aChessPieceGeneral);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeChessPieceGeneral(ChessPieceGeneral aChessPieceGeneral)
  {
    boolean wasRemoved = false;
    //Unable to remove aChessPieceGeneral, as it must always have a chess
    if (this.equals(aChessPieceGeneral.getChess()))
    {
      return wasRemoved;
    }

    //chess already at minimum (8)
    if (numberOfChessPieceGenerals() <= minimumNumberOfChessPieceGenerals())
    {
      return wasRemoved;
    }
    chessPieceGenerals.remove(aChessPieceGeneral);
    wasRemoved = true;
    return wasRemoved;
  }

  public boolean isNumberOfPlayersValid()
  {
    boolean isValid = numberOfPlayers() >= minimumNumberOfPlayers() && numberOfPlayers() <= maximumNumberOfPlayers();
    return isValid;
  }

  public static int requiredNumberOfPlayers()
  {
    return 2;
  }

  public static int minimumNumberOfPlayers()
  {
    return 2;
  }

  public static int maximumNumberOfPlayers()
  {
    return 2;
  }

  public Player addPlayer(String aName)
  {
    if (numberOfPlayers() >= maximumNumberOfPlayers())
    {
      return null;
    }
    else
    {
      return new Player(aName, this);
    }
  }

  public boolean addPlayer(Player aPlayer)
  {
    boolean wasAdded = false;
    if (players.contains(aPlayer)) { return false; }
    if (numberOfPlayers() >= maximumNumberOfPlayers())
    {
      return wasAdded;
    }

    Chess existingChess = aPlayer.getChess();
    boolean isNewChess = existingChess != null && !this.equals(existingChess);

    if (isNewChess && existingChess.numberOfPlayers() <= minimumNumberOfPlayers())
    {
      return wasAdded;
    }

    if (isNewChess)
    {
      aPlayer.setChess(this);
    }
    else
    {
      players.add(aPlayer);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePlayer(Player aPlayer)
  {
    boolean wasRemoved = false;
    //Unable to remove aPlayer, as it must always have a chess
    if (this.equals(aPlayer.getChess()))
    {
      return wasRemoved;
    }

    //chess already at minimum (2)
    if (numberOfPlayers() <= minimumNumberOfPlayers())
    {
      return wasRemoved;
    }
    players.remove(aPlayer);
    wasRemoved = true;
    return wasRemoved;
  }

  public void delete()
  {
    for(int i=chessPieceGenerals.size(); i > 0; i--)
    {
      ChessPieceGeneral aChessPieceGeneral = chessPieceGenerals.get(i - 1);
      aChessPieceGeneral.delete();
    }
    Board existingBoard = board;
    board = null;
    if (existingBoard != null)
    {
      existingBoard.delete();
    }
    for(int i=players.size(); i > 0; i--)
    {
      Player aPlayer = players.get(i - 1);
      aPlayer.delete();
    }
  }

}