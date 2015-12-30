

public class TestHomework {
	@Test
	public void testCrack() {
		Verifier v = new Verifier("keys");
		boolean souhlasi = v.verify("Message".getBytes(), new byte[]{1,5,5,4,8});
		
		// posielam spravu a chcem zistit aky je jej hash na zaklade timing erroru
		// hash kluca ma dlzku 25B
	}
	

}

java -Ddifficulty=HARD
