package battleship;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static battleship.VerifyCoordinates.coordinateRequest;

public class PlayerField {

    private Character[][] battleShipField;
    private Map<String, List<String>> existsShips = new HashMap<>();
    private String name;
    private VerifyCoordinates verifyCoordinates;

    public PlayerField(String name) {
        this.name = name;
        initField();
        this.verifyCoordinates = new VerifyCoordinates(battleShipField, existsShips);
        fillField();
    }

    public Character[][] getBattleShipField() {
        return battleShipField;
    }

    public Map<String, List<String>> getExistsShips() {
        return existsShips;
    }

    public String getName() {
        return name;
    }

    //Заполнение поля
    public void fillField() {
        for (Ship ship : Ship.values()) {
            System.out.println(String.format("Enter the coordinates of the %s (%s cells)", ship.getName(), ship.getLength()));
            int[][] coordinates = coordinateRequest();
            while (!verifyCoordinates.checkCoordinates(coordinates, ship)) {
                coordinates = coordinateRequest();
            }
            printOwnField();
        }
        System.out.println("Press Enter and pass the move to another player");
        new Scanner(System.in).nextLine();
    }

    private void initField() {
        battleShipField = new Character[10][11];
        char ch = 'A';
        for(int i = 0; i < 10; i++) {
            battleShipField[i][0] = ch;
            ch++;
        }
        System.out.println("Player " + name + ", place your ships on the game field");
        printOwnField();
    }

    public void printOwnField() {
        Character[][] charField = this.getBattleShipField();
        System.out.println("\n  1 2 3 4 5 6 7 8 9 10");
        int i = 0;
        while(i < 10) {
            System.out.print(charField[i][0] + " ");
            for (int field = 1; field <= 10; field++) {
                if(charField[i][field] == null) {
                    System.out.print("~ ");
                } else {
                    System.out.print(charField[i][field] + " ");
                }
            }
            System.out.println();
            i++;
        }
        System.out.println();
    }

    public void printOpponentField() {
        Character[][] charField = this.getBattleShipField();
        System.out.println("\n  1 2 3 4 5 6 7 8 9 10");
        int i = 0;
        while(i < 10) {
            System.out.print(charField[i][0] + " ");
            for (int field = 1; field <= 10; field++) {
                if(charField[i][field] == null || charField[i][field] == 'O') {
                    System.out.print("~ ");
                } else if (charField[i][field] == 'M') {
                    System.out.print(charField[i][field] + "M");
                }else {
                    System.out.print(charField[i][field] + " ");
                }
            }
            System.out.println();
            i++;
        }
        System.out.println();
    }
}
