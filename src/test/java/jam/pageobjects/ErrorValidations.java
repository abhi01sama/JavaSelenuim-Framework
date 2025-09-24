package jam.pageobjects;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import jam.TestComponents.BaseTest;

public class ErrorValidations extends BaseTest {

	@Test
	public void Submitorder() throws IOException, InterruptedException {

		//String productname = "ZARA COAT 3";
		landingpage.loginApplication("abhi01sama@gmail.com", "sR1234567");
		Assert.assertEquals("Incorrect email or password.", landingpage.getErrorMessage());
	}

}
