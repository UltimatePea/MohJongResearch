package model.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import model.MohJong;
import model.MohJongContainer;

public class MohJongGame {
	
	MohJongContainer centerGarbage, sourceRepo;
	ArrayList<MohJongContainer> playersHands;
	
	MohJongDrawingDelegate delegate;
	
	/**
	 * @param delegate the delegate to set
	 */
	public void setDelegate(MohJongDrawingDelegate delegate) {
		this.delegate = delegate;
	}

	/**
	 * This method creates a game's object. The delegate cannot be null;
	 * This method fills the source and each player draws 13 cards from the source.
	 * Player did draw card will be invoked during this method.
	 * It is your responsibility to let the first player draw a card.
	 * @param delegate
	 */
	public MohJongGame(MohJongDrawingDelegate delegate){
		this.delegate = delegate;
		inflateSource();
		centerGarbage = new MohJongContainer(new ArrayList<MohJong>());
		playersHands = new ArrayList<MohJongContainer>();
		for (int i = 0; i < 4; i++) {
			playersHands.add(new MohJongContainer(new ArrayList<MohJong>()));
			for(int j = 0; j < 13; j ++){
				playerDrawMohJong(i, false, delegate, false);
			}
		}
	}
	
	/**
	 * Performs a shallow copy
	 * @param game
	 */
	@SuppressWarnings("unchecked")
	public MohJongGame(MohJongGame game) {
		this.centerGarbage = new MohJongContainer((ArrayList<MohJong>) game.centerGarbage.getMohJongs().clone());
		this.delegate = game.delegate;
		ArrayList<MohJongContainer> hands = new ArrayList<MohJongContainer>();
		for (MohJongContainer mohJongContainer : game.playersHands) {
			hands.add(new MohJongContainer((ArrayList<MohJong>) mohJongContainer.getMohJongs().clone()));
		}
		this.playersHands = hands;
		this.sourceRepo = new MohJongContainer((ArrayList<MohJong>) game.sourceRepo.getMohJongs().clone());
	}

	/**
	 * @return the centerGarbage
	 */
	public MohJongContainer getCenterGarbage() {
		return centerGarbage;
	}

	/**
	 * @return the sourceRepo
	 */
	public MohJongContainer getSourceRepo() {
		return sourceRepo;
	}

	/**
	 * @return the playersHands
	 */
	public ArrayList<MohJongContainer> getPlayersHands() {
		return playersHands;
	}

	/**
	 * @return the delegate
	 */
	public MohJongDrawingDelegate getDelegate() {
		return delegate;
	}

	/**
	 * Let player designated by player number draw a card from sourceRepo
	 * This method may invoke game win 
	 * In case all cards are drawn, game did finish is invoked on the delegate
	 * @param playerNumber
	 * @param random
	 */
	public void playerDrawMohJong(int playerNumber, boolean random, MohJongDrawingDelegate delegate, boolean checksForWinning){
		
		if(sourceRepo.isEmpty()){
			delegate.gameDidFinish(this);
			return;
		}
		int drawingIndex = 0;
		if(random){
			drawingIndex = (int) Math.floor(sourceRepo.size() * Math.random());
		}
		
		MohJong m = sourceRepo.remove(drawingIndex);
		playersHands.get(playerNumber).add(m);
		//checks if the player wins

		if(checksForWinning && MohJongValidator.validateMohJongs(playersHands.get(playerNumber))){
			delegate.playerDidWin(playerNumber, this);
		}

		delegate.didFinishDrawing(playerNumber, m, this);
	}
	
	/**
	 * Invoking this method to let the game to let one player play a card, given the player number and card index
	 * A player has played a card, check if any one wins by that card, 
	 * This method will remove card at the designated index, do not remove card by yourself
	 * @param playerNumber
	 * @param cardNumber
	 */
	public void playerPlayedMohJong(int playerNumber, int cardNumber){
		MohJong m = playersHands.get(playerNumber).remove(cardNumber);
		for(int i = 0; i < playersHands.size(); i ++){
			if (i != playerNumber){
				ArrayList<MohJong> otherHand = playersHands.get(i).clone();
				otherHand.add(m);
				if(MohJongValidator.validateMohJongs(otherHand)){
					delegate.playerDidWin(i, this);
				}
			}
		}
		
	}
	
	/**
	 * This method will inflate the source i.e. erase all contents of the source 
	 * and place a new array of shuffled cards into the source
	 */
	public void inflateSource(){
		ArrayList<MohJong> mohJongs= new ArrayList<MohJong>();
		for (int i = 1; i < 10; i++) {
			
			//each card is repeated four times
			for(int j = 0; j < 4; j ++){
				mohJongs.add(new MohJong(i, "w"));
				mohJongs.add(new MohJong(i, "t"));
				mohJongs.add(new MohJong(i, "b"));
				mohJongs.add(new MohJong(i, "s"));
			}
		}
		
		
		sourceRepo = new MohJongContainer(mohJongs);
		sourceRepo.shuffle();
	}

	/**
	 * Sort all player's hands, invoke this method before displaying the game status
	 */
	public void sortHands() {
		for (Iterator<MohJongContainer> iterator = playersHands.iterator(); iterator.hasNext();) {
			MohJongContainer mohJongContainer = (MohJongContainer) iterator
					.next();
			sort(mohJongContainer.getMohJongs());
		}
	}

	/**
	 * Duplicate of Validator.sort
	 * @param arr
	 */
	private static void sort(ArrayList<MohJong> arr){
		Collections.sort(arr, new Comparator<MohJong>(){
			@Override
			public int compare(MohJong m1, MohJong m2){
				return m1.getUniqueIdentifier().compareTo(m2.getUniqueIdentifier());
			}
		});
	}
}
