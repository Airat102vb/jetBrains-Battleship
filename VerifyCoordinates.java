package battleship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.IntStream;

public class VerifyCoordinates {

  private static Character[][] battleShipField;
  private static Map<String, List<String>> existsShips;

  public VerifyCoordinates(Character[][] battleShipField, Map<String, List<String>> existsShips) {
    this.battleShipField = battleShipField;
    this.existsShips = existsShips;
  }

  public boolean checkCoordinates(int[][] coordinates, Ship ship) {
    if(!isLengthCorrect(coordinates[0], coordinates[1], ship)) {
      System.out.println("\nError! Wrong length of the " + ship.getName() + "! Try again:");
      return false;
    }

    if(!isLocationRight(coordinates[0], coordinates[1])) {
      System.out.println("\nError! Wrong ship location! Try again:");
      return false;
    }

    try {
      if(!isNotClosToAnother(coordinates[0], coordinates[1])) {
        System.out.println("\nError! You placed it too close to another one. Try again:");
        return false;
      }
    } catch (IndexOutOfBoundsException e) {}

    addShip(coordinates[0], coordinates[1], ship);
    return true;
  }

  private void addShip(int[] c1, int[] c2, Ship ship) {
    List<String> shipPositions = new ArrayList<>();
    if(isHorizontal(c1, c2)) {
      for(int p = Integer.parseInt(String.valueOf(c1[1])); p <= Integer.parseInt(String.valueOf(c2[1])); p++) {
        battleShipField[c1[0]][p] = 'O';
        shipPositions.add(String.valueOf(c1[0]) + p);
      }
    } else {
      int startRow = c1[0];
      int endRow = c2[0];
      for(int p = startRow; p <= endRow; p++) {
        battleShipField[p][Integer.parseInt(String.valueOf(c1[1]))] = 'O';
        shipPositions.add(String.valueOf(p) + c1[1]);
      }
    }
    existsShips.put(ship.getName(), shipPositions);
  }

  private boolean isNotClosToAnother(int[] c1, int[] c2) throws IndexOutOfBoundsException {
    if(isHorizontal(c1, c2)) {
      for (int i = c1[1]; i <= c2[1]; i++) {
        for (int row = 0; row < 3; row++) {
          for (int field = 0; field < 3; field++) {
            Optional<Character> f = Optional.ofNullable(battleShipField[(c1[0] - 1 + row)][i - 1 + field]);
            if (f.orElse(' ') == 'O') {
              return false;
            }
          }
        }
      }
    } else {
      int startRow = c1[0];
      int endRow = c2[0];
      for (int i = startRow; i <= endRow; i++) {
        for (int row = 0; row < 3; row++) {
          for (int field = 0; field < 3; field++) {
            Optional<Character> f = Optional.ofNullable(battleShipField[i - 1 + row][c1[1] - 1 + field]);
            if (f.orElse(' ') == 'O') {
              return false;
            }
          }
        }
      }
    }
    return true;
  }

  private boolean isLocationRight(int[] c1, int[] c2) {
    if(isHorizontal(c1, c2)) {
      return c1[0] == c2[0] ? true : false;
    } else {
      return c1[1] == c2[1] ? true : false;
    }
  }

  private boolean isLengthCorrect(int[] c1, int[] c2, Ship ship) {
    if (isHorizontal(c1, c2)) {
      if (Math.abs(c1[1] - c2[1]) + 1 == ship.getLength()) {
        return true;
      }
    } else {
      if (Math.abs(c1[0] - c2[0]) + 1 == ship.getLength()) {
        return true;
      }
    }
    return false;
  }

  private static boolean isHorizontal(char[] c1, char[] c2) {
    return c1[0] == c2[0] ? true : false;
  }

  private boolean isHorizontal(int[] c1, int[] c2) {
    return c1[0] == c2[0] ? true : false;
  }

  public static int[][] coordinateRequest() {
    Scanner scanner = new Scanner(System.in);
    String[] pos =  scanner.nextLine().split(" ");
    char[] c1 = fixStraight(pos)[0].toCharArray();
    char[] c2 = fixStraight(pos)[1].toCharArray();
    int[][] coords = new int[2][2];
    coords[0][0] = c1[0] - 65;
    coords[0][1] = c1.length == 3 ? Integer.parseInt(String.valueOf(c1[1]) + c1[2]) : Integer.parseInt(String.valueOf(c1[1]));
    coords[1][0] = c2[0] - 65;
    coords[1][1] = c2.length == 3 ? Integer.parseInt(String.valueOf(c2[1]) + c2[2]): Integer.parseInt(String.valueOf(c2[1]));
    return coords;
  }

  public static String[] fixStraight(String[] coords) {
    String[] rightDirect;
    if(isHorizontal(coords[0].toCharArray(), coords[1].toCharArray())) {
      char[] f = coords[0].toCharArray();
      char[] s = coords[1].toCharArray();
      int first = f.length == 3 ? Integer.parseInt(String.valueOf(f[1]) + f[2]) : Integer.parseInt(String.valueOf(f[1]));
      int second = s.length == 3 ? Integer.parseInt(String.valueOf(s[1]) + s[2]) : Integer.parseInt(String.valueOf(s[1]));
      if(first > second) {
        rightDirect = IntStream.rangeClosed(1, coords.length)
            .mapToObj(i -> coords[coords.length - i]).toArray(String[]::new);
      } else {
        rightDirect = coords.clone();
      }
    } else {
      if(coords[0].toCharArray()[0] > coords[1].toCharArray()[0]) {
        rightDirect = IntStream.rangeClosed(1, coords.length)
            .mapToObj(i -> coords[coords.length - i]).toArray(String[]::new);
      } else {
        rightDirect = coords.clone();
      }
    }
    return rightDirect;
  }
}
