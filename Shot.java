package battleship;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Shot {

  PlayerField player_1;
  PlayerField player_2;
  int queue = 1;
  boolean finished = false;

  public Shot(PlayerField player_1, PlayerField player_2) {
    this.player_1 = player_1;
    this.player_2 = player_2;
  }

  public void startAttack() {
    while (!finished) {
      if (queue % 2 != 0) {
        takeShot(player_1, player_2);
      } else {
        takeShot(player_2, player_1);
      }
      queue++;

    }
  }

  public void takeShot(PlayerField attackPlayer, PlayerField otherPlayer) {
    otherPlayer.printOpponentField();
    System.out.println("---------------------");
    attackPlayer.printOwnField();
    System.out.println("Player " + attackPlayer.getName() + ", it's your turn:");
    Scanner scanner = new Scanner(System.in);
    String shot = scanner.nextLine();
    int[] coords = new int[2];
    if (shot.length() == 3) {
      coords[0] = shot.charAt(0) - 65;
      coords[1] = Integer.parseInt(String.valueOf(shot.charAt(1)) + shot.charAt(2));
    } else {
      coords[0] = shot.charAt(0) - 65;
      coords[1] = Integer.parseInt(String.valueOf(shot.charAt(1)));
    }
    makeShot(coords, attackPlayer, otherPlayer);
  }

  private void makeShot(int[] coords, PlayerField attackPlayer, PlayerField otherPlayer) {
    Character[][] otherPlayerField = otherPlayer.getBattleShipField();
    if (coords[0] > 9 || coords[1] > 10) {
      System.out.println("\nError! You entered the wrong coordinates! Try again:");
      takeShot(attackPlayer, otherPlayer);
    } else if (otherPlayerField[coords[0]][coords[1]] == null) {
      otherPlayerField[coords[0]][coords[1]] = 'M';
      missedAndPassTheMove();
    }else if (otherPlayerField[coords[0]][coords[1]] == 'O') {
      otherPlayerField[coords[0]][coords[1]] = 'X';
      removeWreckedShip(otherPlayer, coords, attackPlayer);
    } else if (otherPlayerField[coords[0]][coords[1]] == 'X') {
      hitAndPassTheMove();
    } else if (otherPlayerField[coords[0]][coords[1]] == 'M') {
      missedAndPassTheMove();
    } else {
      otherPlayerField[coords[0]][coords[1]] = 'M';
      missedAndPassTheMove();
    }
  }

  private void removeWreckedShip(PlayerField otherPlayer, int[] coords, PlayerField attackPlayer) {
    Map<String, List<String>> existsShips = otherPlayer.getExistsShips();
    String sh = existsShips.entrySet().stream()
        .filter(s -> s.getValue().contains(String.valueOf(coords[0]) + coords[1])).findFirst().get().getKey();
    if (existsShips.entrySet().stream()
        .filter(s -> s.getValue().contains(String.valueOf(coords[0]) + coords[1])).findFirst()
        .get().getValue().remove((String.valueOf(coords[0]) + coords[1]))) {
      if (existsShips.get(sh).size() == 0) {
        existsShips.remove(sh);
        if (existsShips.size() == 0) {
          System.out.println("You sank the last ship. You won. Congratulations!");
          finished = true;
          return;
        }
        System.out.println("You sank a ship! Specify a new target:");
        hitAndPassTheMove();
      } else {
        hitAndPassTheMove();
      }
    }
  }

  private void hitAndPassTheMove() {
    System.out.println("You hit a ship!\nPress Enter and pass the move to another player");
    new Scanner(System.in).nextLine();
    return;
  }

  private void missedAndPassTheMove() {
    System.out.println("You missed!\nPress Enter and pass the move to another player");
    new Scanner(System.in).nextLine();
    return;
  }
}
