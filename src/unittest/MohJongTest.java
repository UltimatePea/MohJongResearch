package unittest;

import static org.junit.Assert.*;
import model.MohJong;
import model.readable.MohJongReader;
import model.readable.MohJongReaderLanguageEnum;

import org.junit.Test;

public class MohJongTest {

	@Test
	public void test() {
		MohJong mjw3 = new MohJong("w3");
		int rank = mjw3.getRank();
		String cat = mjw3.getCategory();
		assertEquals( 3, rank);
		assertEquals("w", cat);
		mjw3.setCategory("s");
		mjw3.setRank(9);
		String uniqueID = mjw3.getUniqueIdentifier();
		assertEquals( "s9",uniqueID);
		
		MohJongReader reader = new MohJongReader(mjw3, MohJongReaderLanguageEnum.Chinese);
		String readable = reader.getReadablePresentation();
		assertEquals("花朵", readable);
		mjw3.setCategory("b");
		 readable = reader.getReadablePresentation();
		assertEquals("九饼", readable);
	}
	
	public void testMohJongW3(){
		
	}

}
