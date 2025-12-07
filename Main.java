import java.util.Arrays;

public class Main {
	
public static void main(String[] args) {
    	
        boolean[] pins = new boolean[7]; // How many pins are in play, can be changed to any number but can 
        								 // cause misalignment when the pins are over 99
        Arrays.fill(pins, true);
        int Player = 0; // change if you want to change the starting player (0 -> Human | 1 -> AI)
        
        GameState start = new GameState(pins, Player, null);
        
        System.out.println("Welcome to Kayles Game");
        System.out.println("The rules are simple: ");
        System.out.println("1- you can remove one pin or two adjacent pins per move");
        System.out.println("2- if it's your turn and there no legal moves (all pins are removed) you lose");
        System.out.println("Moves are entered as a pair example : 2 3");
        System.out.println("if you want to remove one you enter the same number twice example: 1 1");
        System.out.println("Notice: don't forget to put a space between the two numbers");
        System.out.println();
        
        start.play();
    }
}