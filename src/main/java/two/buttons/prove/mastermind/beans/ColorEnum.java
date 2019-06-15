package two.buttons.prove.mastermind.beans;


import java.util.Random;

public enum ColorEnum {
    RED,
    GREEN,
    PURPLE,
    YELLOW,
    BROWN,
    ORANGE,
    BLACK,
    WHITE;


    public static ColorEnum getRandomColor() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
