
/*
* Problem 1: Escape Room
* 
* V1.0
* 10/10/2019
* Copyright(c) 2019 PLTW to present. All rights reserved
*/
import java.util.Scanner;

/**
 * Create an escape room game where the player must navigate to the other side
 * of the screen in the fewest steps, while avoiding obstacles and collecting
 * prizes.
 */
public class EscapeRoom {

  // describe the game with brief welcome message
  // determine the size (length and width) a player must move to stay within the
  // grid markings
  // Allow game commands:
  // right, left, up, down: if you try to go off grid or bump into wall, score
  // decreases
  // jump over 1 space: you cannot jump over walls
  // if you land on a trap, spring a trap to increase score: you must first check
  // if there is a trap, if none exists, penalty
  // pick up prize: score increases, if there is no prize, penalty
  // help: display all possible commands
  // end: reach the far right wall, score increase, game ends, if game ended
  // without reaching far right wall, penalty
  // replay: shows number of player steps and resets the board, you or another
  // player can play the same board
  // Note that you must adjust the score with any method that returns a score
  // Optional: create a custom image for your player use the file player.png on
  // disk


  // size of move
  static final int m = 60;
  // individual player moves
  static final int px = 0;
  static final int py = 0;

  int score = 0;

  private int lastX = 0;
  private int lastY = 0;

  private GameGUI game;
  boolean play = true;

  public EscapeRoom(GameGUI g) {
    g.createBoard();

    game = g;
  }

  public static void main(String[] args) {
    // welcome message
    System.out.println("Welcome to EscapeRoom!");
    System.out.println("Get to the other side of the room, avoiding walls and invisible traps,");
    System.out.println("pick up all the prizes.\n");

    GameGUI game = new GameGUI();
    

    String[] validCommands = { "right", "left", "up", "down", "r", "l", "u", "d", "jump", "jr", "jumpleft", "jl",
        "jumpup", "ju", "jumpdown", "jd", "pickup", "p", "quit", "q", "replay", "help", "?", "spring", "s" };

    EscapeRoom escapeRoom = new EscapeRoom(game);
    

    // set up game
    escapeRoom.runGame(validCommands);
  }

  public void runGame(String[] validCommands) {
    play = true;
    while (play) {
      System.out.println("Enter Command: ");
      String command = UserInput.getValidInput(validCommands);
      command = command.toLowerCase();

      handleInput(command);
    }

    endGame();
  }

  public void handleInput(String command) {
    switch (command) {
      case "right":
      case "r":
        attemptMove(m, 0);
        break;
      case "left":
      case "l":
        attemptMove(-m, 0);
        break;
      case "down":
      case "d":
        attemptMove(0, m);
        break;
      case "up":
      case "u":
        attemptMove(0, -m);
        break;

      // Jumping
      case "jumpright":
      case "jr":
        attemptMove(m * 2, 0);
        break;
      case "jumpleft":
      case "jl":
        attemptMove(-m * 2, 0);
        break;
      case "jumpdown":
      case "jd":
        attemptMove(0, m * 2);
        break;
      case "jumpup":
      case "ju":
        attemptMove(0, -m * 2);
        break;

      case "jump":
        // jump in the last direction
        score += game.movePlayer(lastX * 2 * m, lastY * 2 * m);
        break;


      case "pickup":
      case "p":
        score += game.pickupPrize();
        break;

      case "spring":
      case "s":
        score += game.springTrap(lastX * m, lastY * m);
        break;

      case "help":
      case "?":
        displayHelp();
        break;

      case "replay":
      case "rep":
        score += game.replay();
        break;

      case "quit":
      case "q":
        play = false;
        break;

      default:
        System.out.println("That is not a recognized command");
    }
  }

  private void displayHelp() {
    System.out.println("Type any of the following commands to navigate the game.");
    System.out.println("right, left, up, down: if you try to go off grid or bump into wall, score decreases");
    System.out.println("Jump over 1 space: you cannot jump over walls");
    System.out.println("if you land on a trap, spring a trap to increase score: you must first check if there is a trap, if none exists, penalty");
    System.out.println("(P)ickup: score increases, if there is no prize, penalty");
    System.out.println("(S)pring: Attempts to spring a trap one space in the direction that you moved last");
    System.out.println("Help: display all possible commands");
    System.out.println("(Q)uit: reach the far right wall, the score increases, and the game ends, if the game ended without reaching far right wall, penalty.");
    System.out.println("(Rep)lay: shows number of player steps and resets the board, you or another player can play the same board");
  }

  private void attemptMove(int x, int y) {
    score += game.movePlayer(x, y); // for some reason this is sort of delayed in updating playerLoc
    
    lastX = sign(x);
    lastY = sign(y);

    // check the spot one in front of the same thing
    game.isTrap(x + lastX * m, y + lastY * m);
  }


  public void endGame() {
    score += game.endGame();

    System.out.println("score=" + score);
    System.out.println("steps=" + game.getSteps());
  }

  // should probably be in a helpers file

  private int sign(int num) {
    if (num > 0) {
      return 1;
    } 

    if (num < 0) {
      return -1;
    }

    return 0;
  }
}
