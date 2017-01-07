package unittest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.MohJong;
import model.MohJongContainer;
import model.game.MohJongValidator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestMohJongValidator {
	
	public static String TEST_STRING = "t3t4t5t9t9b2b4w3w4w5w7w8s5s7";
	
	public MohJongContainer mohJongContainer;

	public TestMohJongValidator() {
	}

	@Before
	public void setUp() throws Exception {
		mohJongContainer = new MohJongContainer(mohJongsFromString(TEST_STRING));
	}

	@After
	public void tearDown() throws Exception {
	}
	
	private ArrayList<MohJong> mohJongsFromString(String str){
		ArrayList<MohJong> result = new ArrayList<MohJong>();
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length / 2; i++) {
			char[] c = new char[]{ chars[2*i], chars[2*i+1]};
			MohJong m = new MohJong(new String(c));
			result.add(m);
		}
		return result;
	}

	@Test
	public void test() {
		assertTrue(! MohJongValidator.validateMohJongs(mohJongsFromString("t3t4t5t9t9b2b4w3w4w5w7w8s5s7")));
		assertTrue(  MohJongValidator.validateMohJongs(mohJongsFromString("t3t4t5t9t9b2b4b3w4w5w6s5s5s5")));
		assertTrue(  MohJongValidator.validateMohJongs(mohJongsFromString("t3t4t5t9t9b2b4b3w3w4w4w6w5w5")));
		assertTrue(  MohJongValidator.validateMohJongs(mohJongsFromString("t4t4t4t9t9b2b4b3w3w4w4w6w5w5")));
	}

}
