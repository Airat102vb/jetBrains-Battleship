package battleship;

public class Main {

    public static void main(String[] args) {
        // Write your code here
        new Shot(new PlayerField("1"), new PlayerField("2"))
            .startAttack();
    }
}
