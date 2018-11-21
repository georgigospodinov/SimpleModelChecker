package modelChecker;

import java.io.IOException;

import org.junit.Test;

public class CallerTest {

	@Test
	public void passingModelTest() throws IOException {
		//checks no obvious errors are thrown by runner 
		String path = "src/test/resources/integration/";
		String[] args = {path+"m1.json", path+"true.json", path+"true.json"};
		new Main();
		Main.main(args);
	}
	
	@Test
	public void failingModelTest() throws IOException {
		//checks no obvious errors are thrown by runner 
		String path = "src/test/resources/integration/";
		String[] args = {path+"m1.json", path+"false.json", path+"true.json"};
		new Main();
		Main.main(args);
	}
}
