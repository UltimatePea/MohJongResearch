package algorithms;

import java.util.ArrayList;

import model.MohJong;
import model.game.MohJongDrawingDelegate;
import model.game.MohJongGame;

public class MCTSAlgorithm implements MohJongDrawingDelegate {
	
	private static final int INITIAL_VALUE = 1;
	private int currentSearchPlayerNum;
	private int[] timesSelected;

	MohJongGame game;
	
	DecisionTree tree;
	

	/**
	 * @return the timesSelected
	 */
	public int[] getTimesSelected() {
		return timesSelected;
	}

	public MCTSAlgorithm(MohJongGame game){
		this.game = game;
	}
	
	/**
	 * Returns the current situation of this object
	 * @return
	 */
	public String log(){
		StringBuffer b = new StringBuffer();
		b.append("Times selected are as follows:\n");
		for(int i = 0; i < timesSelected.length; i ++){
			int num = timesSelected[i];
			b.append(num + "(" + i + "), ");
		}
		b.append('\n');
		return b.toString();
	}
	
	public int getPreferredIndex(){
		int max = 0;
		int maxIndex = 0;
		for (int i = 0; i < timesSelected.length; i++) {
			int num = timesSelected[i];
			if (num > max) {
				max = num;
				maxIndex =i;
			}
		}
		return maxIndex;
	}
	
	/**
	 * Before invoking this method, make sure that the designated player has 14 cards
	 * and each other player have 13 cards.
	 * This method will start by playing a card for the designated player
	 * @param playerNum the player to start judgement on
	 * @param numOfSearches the number of traversals MCTS is performing
	 */
	public void performMCTSNonRandom(int playerNum, int numOfSearches){
		
		//set the search focus
		currentSearchPlayerNum = playerNum;
		
		//create the root tree
		tree = new DecisionTree(INITIAL_VALUE);
		
		
		//get array of times selected
		timesSelected = new int[game.getPlayersHands().get(playerNum).size()]; 
		
		//populate the toplevel of decision tree
		populateDecisionTree(tree, game.getPlayersHands().get(playerNum).getMohJongs(), INITIAL_VALUE);
		
		
		for (int i = 0; i < numOfSearches; i++) {
			//create a shallow copy
			MohJongGame game = new MohJongGame(this.game);
			game.setDelegate(this);
			
			//doing search on the decision tree and increase the values
			searchOnTheTreeWithRootNode(tree, playerNum, game, true);
		}

		
		
	}
	
	/**
	 * This method populates the dicision tree with the number of sub-nodes to the number 
	 * of mohJongs, each is given a initial value of 1
	 * This method does not check whether the tree has ever been populated
	 * @param tree the tree to populate
	 * @param mohJongs the mohJongs array
	 * @param initialValue the initial weight given to each node
	 */
	private void populateDecisionTree(DecisionTree tree, ArrayList<MohJong> mohJongs, int initialValue){
		for(int i = 0; i < mohJongs.size(); i ++){
			DecisionTree child = new DecisionTree(initialValue);
			tree.getSubTrees().add(child);
		}
	}
	
	/**
	 * This method starts a search on the given tree already populated with child decisions.
	 * It randomly select a child according to the value distribution, plays that card, see if
	 * the result has came out. If so, increment values on paths. If not, let next player draw a card.
	 * See if (1) the game ended, in which case, do nothing (2) the player has won, if so, increment
	 * values on paths. If not, populate the decision tree and recurse
	 * @param tree
	 */
	private void searchOnTheTreeWithRootNode(DecisionTree tree, int playerNum, MohJongGame game, boolean recordTimesSelected){
		//!!! IMPORTANT THIS METHOD SHOULD NOT USE this.game !!!
		//reset game conditions
		gameEnded = false;
		gameWon = false;
		
		//get a random decision
		int randomChildIndex = tree.randomChildIndex();
		if(recordTimesSelected) timesSelected[randomChildIndex] ++;
		
		//play the card
		game.playerPlayedMohJong(playerNum, randomChildIndex);
		
		//see if any one wins and if this strategy is effective
		if(gameWon ){
			if(gameWonPlayerNum == currentSearchPlayerNum){
				incrementAllValuesOnThePath(tree);
			}
			return;
		}
		
		//if no one wins, continue to let the next player draw a card
		//get the next player number
		playerNum += 1;
		playerNum %= game.getPlayersHands().size();
		
		//draw a card
		game.playerDrawMohJong(playerNum, false, this, true);
		
		//see if the player has won
		if(gameWon){
			if(gameWonPlayerNum == currentSearchPlayerNum){
				incrementAllValuesOnThePath(tree);
			}
			return;
		}
		//see if the card has run out
		if(gameEnded){
			return;
		}
		
		//continue the game
		
		//populate the decision tree if tree is empty
		if(tree.getSubTrees().get(randomChildIndex).getSubTrees().isEmpty()){
			populateDecisionTree(tree.getSubTrees().get(randomChildIndex), game.getPlayersHands().get(playerNum).getMohJongs(), INITIAL_VALUE);
		}
		
		//continue with the decision
		searchOnTheTreeWithRootNode(tree.getSubTrees().get(randomChildIndex), playerNum, game, false);
		
	}
	
	/**
	 * Increases all values by 1 along the decision tree between the passed in child
	 * and root node of this algorithm class
	 * @param child
	 */
	private void incrementAllValuesOnThePath(DecisionTree child){
		child.setValue(child.getValue() + 1);
		if(child.getParent() != null){
			incrementAllValuesOnThePath(child.getParent());
		}
		
	}
	
	private boolean gameEnded, gameWon;
	private int gameWonPlayerNum;

	@Override
	public void didFinishDrawing(int playerNum, MohJong m, MohJongGame game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameDidStart(MohJongGame game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerDidWin(int playerNum, MohJongGame game) {
		// TODO Auto-generated method stub
		gameWon = true;
		gameWonPlayerNum = playerNum;
	}

	@Override
	public void gameDidFinish(MohJongGame game) {
		// TODO Auto-generated method stub
		
		gameEnded = true;
	}

}
