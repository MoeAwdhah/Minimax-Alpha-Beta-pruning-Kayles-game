import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

public class GameState {
	
	boolean[] pins = {true, true, true, true, true, true, true};
	int currentPlayer;
	Move lastmove;
	
	GameState(boolean[] pins, int currentPlayer, Move lastmove){
		
		this.pins = pins;
		this.currentPlayer = currentPlayer;
		this.lastmove = lastmove;
	}
	
	public List<Move> getLegalMoves() {
		
		List<Move> legalMoves = new ArrayList<>();
		
		for(int i = 0; i < pins.length; i++) {
			
			if(pins[i]) {
				
				legalMoves.add(new Move(i,i));
			}
					
			if(i < pins.length - 1 && pins[i] && pins[i+1]) {
					
				legalMoves.add(new Move(i,i+1));
				}
		}
		
		return legalMoves;
	}
	
	public GameState applyMove(Move move) {
		
		boolean[] newPins = new boolean[pins.length];
		
		for (int i = 0; i < pins.length; i++) {
			
		    newPins[i] = pins[i];
		}
		
		newPins[move.first] = false;

		if (move.last != move.first) {
			
		    newPins[move.last] = false;
		}
		
		int player;
		
		if (currentPlayer == 0)
			player = 1;
		else
			player = 0;
		
		return new GameState(newPins, player, move);
	}
	
	public boolean isTerminal() {
		
		return getLegalMoves().isEmpty();
	}
	
	public int evaluate() {
			
		// 1 is AI, 0 is Human
		if(currentPlayer == 1)
			return -1;
		else
			return 1;	
	}
	
	public void play() {
		
		Scanner input = new Scanner(System.in);
		
		while(!isTerminal()) {
			
			for(int i = 0; i < pins.length; i++) {
				
				if(pins[i])
					System.out.printf("%3s", "|");
				else
					System.out.printf("%3s", ".");
			}
			
			System.out.println();
			
			for(int i = 1; i < pins.length + 1; i++) {
				
				System.out.printf("%3d", i);
				
			}
			
			System.out.println();
			System.out.println();
			
			if(currentPlayer == 0) {
				
				System.out.print("Pick a move to play: ");
				
				try {
					
					int firstChoice = input.nextInt() - 1;
					int lastChoice = input.nextInt() - 1;
					
					if(firstChoice >= pins.length || firstChoice < 0 || lastChoice >= pins.length || lastChoice < 0) {
						
						System.out.println("Invalid Move, your move's numbers should not be smaller than 1 nor larger than " 
						+ pins.length);
						System.out.println();
						continue;
					}
					
					if(!(lastChoice == firstChoice || lastChoice == firstChoice + 1)) {
						
						System.out.println("Invalid Move, you can't pick two pins that are not next to each other");
						System.out.println();
						continue;
					}

					
					if(!pins[firstChoice] || !pins[lastChoice]) {
						
						System.out.println("Invalid Move, you can't pick pins that have already been removed");
						System.out.println();
						continue;
					}
					
					System.out.println("You played: " + (firstChoice + 1) + " to " + (lastChoice + 1));
					System.out.println();
					
					Move humanMove = new Move(firstChoice, lastChoice);
					GameState newState = applyMove(humanMove);
					this.pins = newState.pins;
					this.currentPlayer = newState.currentPlayer;
					this.lastmove = newState.lastmove;
					
				} catch(InputMismatchException m) {
					
					System.out.println("Invalid Input, you should only input whole numbers");
					System.out.println();
					input.next();
					continue;
				}
				
			}
			
			else {
				
				AI ai = new AI();
				
				Move minimax = ai.minimaxDecision(this);
				int nodesMinimax = ai.nodesMinimax;
				
				Move alphaBeta = ai.alphaBetaDecision(this);
				int nodesAlphaBeta = ai.nodesAlphaBeta;
				
				if(alphaBeta.first != minimax.first || alphaBeta.last != minimax.last)
					System.out.println("AlphaBeta and Minimax did not pick the same move!!");
				
				System.out.println("AI plays: " + (alphaBeta.first + 1) + " to " + (alphaBeta.last + 1));
			    System.out.println("Nodes (Minimax): " + nodesMinimax);
			    System.out.println("Nodes (Alpha-Beta): " + nodesAlphaBeta);
			    System.out.println();
			    
			    GameState newState = applyMove(alphaBeta);
			    this.pins = newState.pins;
			    this.currentPlayer = newState.currentPlayer;
			    this.lastmove = newState.lastmove;
			}
			
		}
		
		input.close();
		
		if(currentPlayer == 0)
			System.out.print("AI wins!!");
		else
			System.out.print("You won!!");
		
	}
}