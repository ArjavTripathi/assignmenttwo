
package hw2;

/**
 * Models a simplified baseball-like game called Fuzzball.
 * 
 * @author YOUR_NAME_HERE
 */
public class FuzzballGame {
  /**
   * Number of strikes causing a player to be out.
   */
  public static final int MAX_STRIKES = 2;

  /**
   * Number of balls causing a player to walk.
   */
  public static final int MAX_BALLS = 5;

  /**
   * Number of outs before the teams switch.
   */
  public static final int MAX_OUTS = 3;

  public int currentInnings = 1;

  public int topOrBottom = 1;

  public int ballCount = 0;

  public int calledStrikes = 0;

  public int currentOuts = 0;

  public boolean[] bases = new boolean[3];





  // TODO: EVERTHING ELSE
  // Note that this code will not compile until you have put in stubs for all
  // the required methods.
  


  
  // The methods below are provided for you and you should not modify them.
  // The compile errors will go away after you have written stubs for the
  // rest of the API methods.
  /**
   * Returns a three-character string representing the players on base, in the
   * order first, second, and third, where 'X' indicates a player is present and
   * 'o' indicates no player. For example, the string "oXX" means that there are
   * players on second and third but not on first.
   * 
   * @return three-character string showing players on base
   */
  public String getBases()
  {
    return (runnerOnBase(1) ? "X" : "o") + (runnerOnBase(2) ? "X" : "o")
        + (runnerOnBase(3) ? "X" : "o");
  }

  public void changeInnings(){
    if(currentInnings == 1){
      currentInnings = 2;
    } else {
      currentInnings = 1;
    }

  }

  private int newBatter(){

  }

  private boolean runnerOnBase(int i) {
    return bases[i-1];
  }

  /**
   * Returns a one-line string representation of the current game state. The
   * format is:
   * <pre>
   *      ooo Inning:1 [T] Score:0-0 Balls:0 Strikes:0 Outs:0
   * </pre>
   * The first three characters represent the players on base as returned by the
   * <code>getBases()</code> method. The 'T' after the inning number indicates
   * it's the top of the inning, and a 'B' would indicate the bottom. The score always
   * shows team 0 first.
   * 
   * @return a single line string representation of the state of the game
   */
  public String toString()
  {
    String bases = getBases();
    String topOrBottom = (isTopOfInning() ? "T" : "B");
    String fmt = "%s Inning:%d [%s] Score:%d-%d Balls:%d Strikes:%d Outs:%d";
    return String.format(fmt, bases, whichInning(), topOrBottom, getTeam0Score(),
        getTeam1Score(), getBallCount(), getCalledStrikes(), getCurrentOuts());
  }

  private void strike(boolean strikeValue) {
    if(strikeValue){
      currentOuts++;
    } else {
      calledStrikes++;
      if(calledStrikes%2==0){
        currentOuts++;

      }
    }
  }

  private void ball(){
    ballCount++;
    if(ballCount == MAX_BALLS){
      walk();
    }
  }

  private void walk() {
    String bases = getBases();
    if(bases.charAt(2) == 'X'){
          bases = "X" + bases.charAt(0) + bases.charAt(1);
        } else if(bases.charAt(1) == 'X'){
          bases = "X" + bases.charAt(0) + "X";
        } else if(bases.charAt(0) == 'X'){
          bases = "X" + "X" + "X";
        } else {
          bases = "X" + "o" + "o";
        }
  }

  private void caughtFly(){
    currentOuts++;
  }
  private void hit(int dist){
    if(dist >= 15 && dist < 150) {
      // Single
      // An imaginary runner goes to first base
      if (!runnerOnBase(1)) {
        moveRunnerToBase(1);
      } else if (!runnerOnBase(2)) {
        moveRunnerToBase(2);
      } else if (!runnerOnBase(3)) {
        moveRunnerToBase(3);
      } else {
        // All bases are occupied, so we score a run
        earnPoints(1);
      }
    } else if(dist >= 150 && dist < 200) {
      // Double
      // An imaginary runner goes to second base, leaving first base empty
      // Any runners already on the bases advance twice
      moveRunnerToBase(2);
      if (runnerOnBase(1)) {
        moveRunnerToBase(1);
      }
      if (runnerOnBase(2)) {
        moveRunnerToBase(3);
      }
    } else if(dist >= 200 && dist < 250) {
      // Triple
      // An imaginary runner goes to third base, leaving first and second bases empty
      // Any runners already on the bases advance three times
      moveRunnerToBase(3);
      if (runnerOnBase(2)) {
        moveRunnerToBase(2);
      }
      if (runnerOnBase(1)) {
        moveRunnerToBase(1);
      }
    } else if(dist >= 250) {
      // Home Run
      // All bases end up empty and the team earns up to four points
      for(int i = 1; i <= 3; i++) {
        if (runnerOnBase(i)) {
          moveRunnerToBase(i);
        }
      }
      // Earn points
      earnPoints(4);
    }
    // Reset counts of balls and strikes for a new batter
    resetCounts();
  }

  private int getCurrentOuts() {
    return currentOuts;
  }

  public int getBallCount(){
    return ballCount;
  }

  private int getCalledStrikes() {
    return calledStrikes;
  }


  private int getTeam1Score() {
    return 0;
  }

  private int getTeam0Score() {
    return 0;
  }

  private int whichInning() {
    return currentInnings;
  }


  private boolean isTopOfInning() {
      return topOrBottom == 1;
  }

  public void setBall(int balls){
    ballCount = balls;
  }

  public void setStrikes(int strikes){
    calledStrikes = strikes;
  }
}




