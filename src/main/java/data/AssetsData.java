package data;

import org.testng.annotations.DataProvider;

public class AssetsData {
	
	@DataProvider
	public Object[][] trailerDetails() {
		return new Object[][] { { "KHH007" }, { "ZZZ505" } };
	}

	@DataProvider
	public Object[][] truckDetails() {
		return new Object[][] { { "KCH047Y" }, { "KAJ128U" } };
	}

	@DataProvider
	public Object[][] countryOfOrigin() {
		return new Object[][] { { "country", "KE" }, { "country", "UG" } };
	}

	@DataProvider
	public Object[][] driverDetails() {
		return new Object[][] { { "23468920" }, { "722546589" } };
	}

}
