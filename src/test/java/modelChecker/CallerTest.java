package modelChecker;

import java.io.IOException;

import org.junit.Test;

public class CallerTest {

	@Test
	public void passingModelTest() throws IOException {
		String path = "src/test/resources/integration/";
		String[] args = {path+"m1.json", path+"true.json", path+"true.json"};
		new Main();
		Main.main(args);
	}
	
	@Test
	public void failingModelTest() throws IOException {
		String path = "src/test/resources/integration/";
		String[] args = {path+"m1.json", path+"false.json", path+"true.json"};
		Main.main(args);
	}
	
}
