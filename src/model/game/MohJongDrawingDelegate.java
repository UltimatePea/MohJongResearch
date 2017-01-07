package model.game;

import model.MohJong;

public interface MohJongDrawingDelegate {
	
	/**
	 * Did finish drawing may be called multiple times, before the game start (when every player is given 13 cards)
	 * It may also be called multiple times if a single draw cannot satisfy the need, e.g. 补花
	 * in such cases, the delegate must notify the game object to draw the card again
	 * @param game
	 */
	public void didFinishDrawing(int playerNum, MohJong m, MohJongGame game);
	
	/**
	 * This delegate is called after the game has finished the initialization, after when each player is given thirteen cards
	 * @param game
	 */
	public void gameDidStart(MohJongGame game);
	/**
	 * This may be called multiple times if multiple person win the game because of a single play out
	 * @param playerNum
	 * @param game
	 */
	public void playerDidWin(int playerNum, MohJongGame game);

	/**
	 * This method indicates that the game has finished, and comes to a tie.
	 */
	public void gameDidFinish(MohJongGame game);
}
