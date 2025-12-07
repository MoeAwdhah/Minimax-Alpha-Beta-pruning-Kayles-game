import java.util.List;

public class AI {
	
	public int nodesMinimax;
	public int nodesAlphaBeta;
	
	public Move minimaxDecision(GameState state) {
		
		nodesMinimax = 0;
		int bestValue = Integer.MIN_VALUE;
		List<Move> moves = state.getLegalMoves();
		Move bestMove = null;
        
		
        for(int i = 0; i < moves.size(); i++) {
        	
        	Move m = moves.get(i);
        	GameState child = state.applyMove(m);
        	int value = minValue(child);
        	
        	if (value > bestValue) {
        		
                bestValue = value;
                bestMove = m;
            }
        }
        
        return bestMove;
	}
	
	private int minValue(GameState state) {
		
		nodesMinimax++;
		
		if(state.isTerminal())
			return state.evaluate();
		
		int v = Integer.MAX_VALUE;
		List<Move> moves = state.getLegalMoves();
		
		for(int i = 0; i < moves.size(); i++) {
			
			Move m = moves.get(i);
			GameState child = state.applyMove(m);
			int value = maxValue(child);
			
			if(value < v)
				v = value;
			
		}
		
		return v;
	}
	
	private int maxValue(GameState state) {
		
		nodesMinimax++;
		
		if(state.isTerminal())
			return state.evaluate();
		
		int v = Integer.MIN_VALUE;
		List<Move> moves = state.getLegalMoves();
		
		for(int i = 0; i < moves.size(); i++) {
			
			Move m = moves.get(i);
			GameState child = state.applyMove(m);
			int value = minValue(child);
			
			if(value > v)
				v = value;
		}
		
		return v;
	}
	
	public Move alphaBetaDecision(GameState state) {
		
		nodesAlphaBeta = 0;
		
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		List<Move> moves = state.getLegalMoves();
		Move bestMove = null;
		
		for(int i = 0; i < moves.size(); i++) {
			
			Move m = moves.get(i);
			GameState child = state.applyMove(m);
			int value = minValueAB(child, alpha, beta);
			
			if(value > alpha) {
				
				alpha = value;
				bestMove = m;
			}
			
		}
		
		return bestMove;
	}
	
	private int minValueAB(GameState state, int alpha, int beta) {
		
		nodesAlphaBeta++;
		
		if(state.isTerminal())
			return state.evaluate();
		
		int v = Integer.MAX_VALUE;
		List<Move> moves = state.getLegalMoves();
		
		for(int i = 0; i < moves.size(); i++) {
			
			Move m = moves.get(i);
			GameState child = state.applyMove(m);
			
			int value = maxValueAB(child, alpha, beta);
			
			if(value < v)
				v = value;
			
			if(v < beta)
				beta = v;
			
			if(alpha >= beta)
				break;
		}
		
		return v;
	}
	
	private int maxValueAB(GameState state, int alpha, int beta) {
		
		nodesAlphaBeta++;
		
		if(state.isTerminal())
			return state.evaluate();
		
		int v = Integer.MIN_VALUE;
		List<Move> moves = state.getLegalMoves();
		
		for(int i = 0; i < moves.size(); i++) {
			
			Move m = moves.get(i);
			GameState child = state.applyMove(m);
			
			int value = minValueAB(child, alpha, beta);
			
			if(value > v)
				v = value;
			
			if(v > alpha)
				alpha = v;
			
			if(alpha >= beta)
				break;
		}
		
		return v;
	}
}