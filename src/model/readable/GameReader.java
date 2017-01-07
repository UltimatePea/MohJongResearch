package model.readable;

import model.MohJong;
import model.game.MohJongGame;

public class GameReader {
	
	
	/**
	 * Gets a string representation for a player's hand in a given game
	 * @param playerNumber
	 * @param game
	 * @return
	 */
	public static String readableStringForPlayerHand(int playerNumber, MohJongGame game){
		StringBuffer b = new StringBuffer();
		
		for(int j = 0; j < game.getPlayersHands().get(playerNumber).getMohJongs().size(); j++){
			MohJong m = game.getPlayersHands().get(playerNumber).getMohJongs().get(j);
			b.append(readableStringForAMohJong(m) + "(" + j + ") ");
		}
		return b.toString();
	}

	/**
	 * Gets a string representation of the current game status
	 * This method is mutative, it will sort player's hands.
	 * @return
	 */
	public static String getGameStatus(MohJongGame game){
		game.sortHands();
		
		StringBuffer b = new StringBuffer();
		b.append("Current Number of Undrawn MohJongs : " + game.getSourceRepo().size() + " \n");
		for(int i = 0; i < game.getPlayersHands().size(); i ++){
			b.append("Player " + i + " 's hand: \n");
			for(int j = 0; j < game.getPlayersHands().get(i).getMohJongs().size(); j++){
				MohJong m = game.getPlayersHands().get(i).getMohJongs().get(j);
				b.append(readableStringForAMohJong(m) + "(" + j + ") ");
			}
			b.append("\n");
		}

		return b.toString();
		
	}

	private static String readableStringForAMohJong(MohJong m){
		MohJongReader reader = new MohJongReader(m, MohJongReaderLanguageEnum.Chinese);
		return reader.getReadablePresentation();
	}
}
