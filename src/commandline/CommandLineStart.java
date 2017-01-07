package commandline;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.PrimitiveIterator;
import java.util.Scanner;

import javax.print.attribute.standard.OutputDeviceAssigned;

import algorithms.MCTSAlgorithm;
import model.MohJong;
import model.game.MohJongDrawingDelegate;
import model.game.MohJongGame;
import model.readable.GameReader;
import model.readable.MohJongReader;
import model.readable.MohJongReaderLanguageEnum;

public class CommandLineStart implements MohJongDrawingDelegate {
	
	
	MohJongGame game;
	Scanner scanner;
	
	public CommandLineStart(){
		scanner = new Scanner(System.in);

			interactive();
		
	}
	
	/**
	 * Outputs a specific prompt to the user
	 * printf
	 * @param string
	 */
	public void output(String string){
		
		System.out.print(string);
		
	}
	
	/**
	 * Outputs a specific prompt to the user
	 * println
	 * @param string
	 */
	public void outputLn(String string){
		
		System.out.println(string);
		
	}
	
	
	/**
	 * Enters the interactive mode, and proceeds forever
	 */
	public void interactive(){
		while(true){
			try {
				outputLn("");
				output(">>> ");
				String str = scanner. nextLine();
				processCommand(str);
			} catch(QuitException e){
				break;
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	public void processCommand(String str) throws QuitException{
		String[] strings = str.trim().split("\\s");
		
		//game start
		if(strings[0].equals("game")){
			if(strings[1].equals("start")){
				game = new MohJongGame(this);
				return;
			}
		}
		
		//help
		if(strings[0].equals("help")){
			help(strings);
			return;
		}
		
		//status
		if(strings[0].equals("status")){
			outputLn(GameReader.getGameStatus(game));
			return;
		}
		
		//draw
		if(strings[0].equals("draw")){
			game.playerDrawMohJong(Integer.parseInt(strings[1]), false, this, true);
			return;
		}

		//drawrnd
		if(strings[0].equals("drawrnd")){
			game.playerDrawMohJong(Integer.parseInt(strings[1]), true, this, true);
			return;
		}
		
		
		
		//play
		if(strings[0].equals("play")){
			game.playerPlayedMohJong(Integer.parseInt(strings[1]), Integer.parseInt(strings[2]));
			return;
		}
		
		//mcts
		if(strings[0].equals("mcts")){
			game.sortHands();
			int playerNumber = Integer.parseInt(strings[1]);
			runMCTSAlgorithm(playerNumber);
			return;
		}
		
		//autoplay plays the card returned by running mcts on the current player
		if(strings[0].equals("autoplay")){
			int playerNumber = Integer.parseInt(strings[1]);
			int preferredIndex = runMCTSAlgorithm(playerNumber);
			outputLn("Player " + playerNumber + " has played out card " 
			+ readableStringForAMohJong(game.getPlayersHands().get(playerNumber).get(preferredIndex)) 
			+ "(" + preferredIndex + ")");
			game.playerPlayedMohJong(playerNumber, preferredIndex);
			return;
		}
		
		
		//quit
		if(strings[0].equals("quit")){
			throw new QuitException();
		}
		
		//unrecognized commmands
		outputLn("Unrecognized commands, type help to display a list of avaialble commands");
		
	}

	
	/**
	 * Runs a MCTS algorithm on player Number x, assuming 14 cards on him
	 * Will output to command line
	 * @param playerNumber
	 * @return the preferred Index.
	 */
	private int runMCTSAlgorithm(int playerNumber){
			MCTSAlgorithm algorithm = new MCTSAlgorithm(game);
			algorithm.performMCTSNonRandom(playerNumber, 1000);
			outputLn("Performing MCTS algorithm on player " + playerNumber);
			//outputLn("The Algorithm returned the following result:");
			output(algorithm.log());
			int preferredIndex = algorithm.getPreferredIndex();
			
				
			
			outputLn(GameReader.readableStringForPlayerHand(playerNumber, game));
			
			//display the result
			outputLn("Above all, the algorithm recommends that card "
			+ readableStringForAMohJong(game.getPlayersHands().get(playerNumber).get(preferredIndex)) 
			+ "("+ preferredIndex + ")"
			+ " to be played out");
			
			return preferredIndex;
	}
	
	
	
	/**
	 * Display a help menu, 
	 * @param strings the original command line arguments
	 */
	private void help(String[] strings){
		if(strings.length == 1){
			outputLn("List of available commands:");
			outputLn("game start");
			outputLn("status");
			outputLn("draw [num]");
			outputLn("play [num] [num]");
			outputLn("mcts [num]");
		}
		
	}
	
	

	public static void main(String[] args) {
		new CommandLineStart();

	}

	

	@Override
	public void gameDidStart(MohJongGame game) {
		// TODO Auto-generated method stub
		outputLn("Game started");
		
	}

	@Override
	public void playerDidWin(int playerNum, MohJongGame game) {
		// TODO Auto-generated method stub
		outputLn("Player " + playerNum + " won the game");
		
	}

	@Override
	public void didFinishDrawing(int playerNum, MohJong m, MohJongGame game) {
		outputLn("Player " + playerNum + " just drawed a MohJong " + readableStringForAMohJong(m));
	}
	
	private String readableStringForAMohJong(MohJong m){
		MohJongReader reader = new MohJongReader(m, MohJongReaderLanguageEnum.Chinese);
		return reader.getReadablePresentation();
	}

	@Override
	public void gameDidFinish(MohJongGame game) {
		outputLn("Game has finished. No one wins");
		
	}

}
