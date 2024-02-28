package hw2;

/**
 * Models a simplified baseball-like game called Fuzzball.
 *
 * @author YOUR_NAME_HERE
 */
public class FuzzballGame {

  public static final int MAX_STRIKES = 2;
  public static final int MAX_BALLS = 5;
  public static final int MAX_OUTS = 3;

  public int currentInnings;
  public boolean isTop;
  public int ballCount;
  public int calledStrikes;
  public int currentOuts;
  public int team1Score;
  public int team0Score;
  public boolean[] bases;
  public int maxInnings;

  public boolean isGameEnded;

  public FuzzballGame(int maxInnings) {
    this.maxInnings = maxInnings;
    this.currentInnings = 1;
    this.isTop = true;
    this.ballCount = 0;
    this.calledStrikes = 0;
    this.currentOuts = 0;
    this.team1Score = 0;
    this.team0Score = 0;
    this.bases = new boolean[3];
    this.isGameEnded = false;
  }

  public String getBases() {
    return (runnerOnBase(1) ? "X" : "o") + (runnerOnBase(2) ? "X" : "o")
            + (runnerOnBase(3) ? "X" : "o");
  }

  public void changeInnings() {
    if (currentInnings < maxInnings) {
      currentInnings++;
    } else {
      isGameEnded = true;
    }
  }

  public boolean runnerOnBase(int i) {
    return bases[i - 1];
  }

  public String toString() {
    String bases = getBases();
    String topOrBottom = (isTopOfInning() ? "T" : "B");
    String fmt = "%s Inning:%d [%s] Score:%d-%d Balls:%d Strikes:%d Outs:%d";
    return String.format(fmt, bases, whichInning(), topOrBottom, getTeam0Score(),
            getTeam1Score(), getBallCount(), getCalledStrikes(), getCurrentOuts());
  }

  public void strike(boolean strikeValue) {
    if (strikeValue) {
      currentOuts++;
      newBatter();
    } else {
      calledStrikes++;
      if (calledStrikes % MAX_STRIKES == 0) {
        currentOuts++;
        newBatter();
      }
    }
  }

  private void changeIsTop() {
    isTop = !isTop;
    if (isTop) {
      changeInnings();
    }
  }

  private void resetBalls() {
    ballCount = 0;
  }

  private void resetStrikes() {
    calledStrikes = 0;
  }

  public void ball() {
    ballCount++;
    if (ballCount == MAX_BALLS) {
      walk();
    }
  }

  public void walk() {
    // Check the current state of the bases
    String bases = getBases();

    moveRunnerToBase(1);

    // If there's a runner on third base, force them to home
    if (bases.charAt(2) == 'X') {
      moveRunnerToBase(3);
    }
    // If there's a runner on second base, move them to third
    else if (bases.charAt(1) == 'X') {
      moveRunnerToBase(2);
      moveRunnerToBase(3);
    }
    // If there's a runner on first base, move them to second
    else if (bases.charAt(0) == 'X') {
      moveRunnerToBase(1);
      moveRunnerToBase(2);
    }
    // If no runners are on base, place a runner on first
    else {
      moveRunnerToBase(1);
    }
  }

  public void caughtFly() {
    currentOuts++;
    newBatter();
  }

  private void moveRunnerToBase(int base) {
    bases[base - 1] = true;
  }

  private void moveRunnersAhead(int basesToAdvance) {
    for (int i = 3; i >= 1; i--) {
      if (runnerOnBase(i)) {
        if (i + basesToAdvance <= 3) {
          moveRunnerToBase(i + basesToAdvance);
        } else {
          earnPoints(1);
        }
        moveRunnerToBase(i);
      }
    }
  }

  public void hit(int dist) {
    if (dist >= 15 && dist < 150) {
      handleSingle();
    } else if (dist >= 150 && dist < 200) {
      handleDouble();
    } else if (dist >= 200 && dist < 250) {
      handleTriple();
    } else if (dist >= 250) {
      handleHomeRun();
    } else {
      strike(true);
    }
    resetCounts();
  }

  private void handleSingle() {
    if (!runnerOnBase(1)) {
      moveRunnerToBase(1);
    } else if (!runnerOnBase(2)) {
      moveRunnerToBase(2);
    } else if (!runnerOnBase(3)) {
      moveRunnerToBase(3);
    } else {
      earnPoints(1);
    }
  }

  private void handleDouble() {
    moveRunnerToBase(2);
    moveRunnersAhead(2);
    if (runnerOnBase(3)) {
      earnPoints(1);
    }
    if (runnerOnBase(1)) {
      moveRunnerToBase(3);
    }
  }

  private void handleTriple() {
    moveRunnerToBase(3);
    moveRunnersAhead(3);
    if (runnerOnBase(3)) {
      earnPoints(1);
    }
  }

  private void handleHomeRun() {
    for (int i = 1; i <= 3; i++) {
      if (runnerOnBase(i)) {
        moveRunnerToBase(i);
      }
    }
    earnPoints(4);
  }

  private void clearBases() {
    for (int i = 0; i < bases.length; i++) {
      bases[i] = false;
    }
  }

  private void newBatter() {
    resetBalls();
    resetStrikes();
    if (getCurrentOuts() >= MAX_OUTS) {
      changeIsTop();
      currentOuts = 0;
      clearBases(); // Clear bases after three outs
    }
  }

  public void earnPoints(int points) {
    if (isTop) {
      team0Score += points;
    } else {
      team1Score += points;
    }
  }

  public void resetCounts() {
    ballCount = 0;
    calledStrikes = 0;
  }

  public int getCurrentOuts() {
    return currentOuts;
  }

  public int getBallCount() {
    return ballCount;
  }

  public int getCalledStrikes() {
    return calledStrikes;
  }

  public int getTeam1Score() {
    return team1Score;
  }

  public int getTeam0Score() {
    return team0Score;
  }

  public int whichInning() {
    return currentInnings;
  }

  public boolean isTopOfInning() {
    return isTop;
  }

  public void setBall(int balls) {
    ballCount = balls;
  }

  public void setStrikes(int strikes) {
    calledStrikes = strikes;
  }

  public boolean gameEnded() {
    return isGameEnded;
  }
}





