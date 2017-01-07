package unittest;

import static org.junit.Assert.*;
import model.MohJong;
import model.game.MohJongDrawingDelegate;
import model.game.MohJongGame;
import model.readable.GameReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import algorithms.MCTSAlgorithm;

public class TestMCTSAlgorithm {
	
	
	private MohJongGame game;
	private MohJongDrawingDelegate virutalDelegate = new MohJongDrawingDelegate() {
			
			@Override
			public void playerDidWin(int playerNum, MohJongGame game) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void gameDidStart(MohJongGame game) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void gameDidFinish(MohJongGame game) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void didFinishDrawing(int playerNum, MohJong m, MohJongGame game) {
				// TODO Auto-generated method stub
				
			}
	};
		

	public TestMCTSAlgorithm() {
	}

	@Before
	public void setUp() throws Exception {
		game = new MohJongGame(virutalDelegate);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		//first draw a card
		game.playerDrawMohJong(0, false, virutalDelegate, true);
		System.out.println(GameReader.getGameStatus(game));
		MCTSAlgorithm alg = new MCTSAlgorithm(game);
		alg.performMCTSNonRandom(0, 1000);
		System.out.println(alg.log());

	}

}
