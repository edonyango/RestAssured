package assets;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import apiData.AssetsData;
import apiReusableMethods.GenerateTokens;
import apiReusableMethods.Utility;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class FilterAssets extends GenerateTokens {
	private static String token = generateLIA_DEV_Token();

	private static Response httpRequest(String body) {
		Response httpReq = given().log().all().header("Content-Type", "application/json")
				.header("x-assets-authorization", token).body(body).when().post();
		return httpReq;
	}

	@BeforeClass
	public void environmentSetUp() {
		RestAssured.baseURI = "https://serengeti.welovetrucks.co/assets-backend/api/graphql";
	}

	@Test(dataProvider = "truckDetails", dataProviderClass = AssetsData.class)
	public void filterTrucksBySerial(String truckReg) {
		String query = "query Trucks {\r\n" + "  trucks(serialList: \"" + truckReg + "\") {\r\n" + "    edges {\r\n"
				+ "      node {\r\n" + "        serial\r\n" + "        createdAt\r\n" + "      }\r\n" + "    }\r\n"
				+ "  }\r\n" + "}";
		Utility util = new Utility();
		String bodyJsonString = util.graphqlToJson(query);
		Response httpReq = httpRequest(bodyJsonString);
		httpReq.then().log().all().assertThat().statusCode(200).and()
				.body("apiData.trucks.edges[0].node.serial", equalTo(truckReg)).and()
				.body("apiData.trucks.edges.node", hasSize(1));
	}

	@Test(dataProvider = "driverDetails", dataProviderClass = AssetsData.class)
	public void filterDriversBySerial(String serial) {
		String query = "query Drivers {\r\n" + "  drivers(idNumberList:\"" + serial + "\") {\r\n" + "    edges {\r\n"
				+ "      node {\r\n" + "        serial\r\n" + "        idNumber\r\n" + "      }\r\n" + "    }\r\n"
				+ "  }\r\n" + "}";
		Utility util = new Utility();
		String bodyJsonString = util.graphqlToJson(query);
		Response httpReq = httpRequest(bodyJsonString);
		httpReq.then().log().all().assertThat().statusCode(200).and()
				.body("apiData.drivers.edges[0].node.idNumber", equalTo(serial)).and()
				.body("apiData.drivers.edges.node", hasSize(1));
	}

	@Test(dataProvider = "trailerDetails", dataProviderClass = AssetsData.class)
	public void filterTrailersBySerial(String trailerReg) {
		String query = "query Trailers {\r\n" + "  trailers(trailerReg:[\"" + trailerReg + "\"]){\r\n"
				+ "    edges {\r\n" + "      node{\r\n" + "        trailerReg\r\n" + "        serial\r\n"
				+ "        id\r\n" + "      }\r\n" + "    }\r\n" + "  }\r\n" + "}";
		Utility util = new Utility();
		String bodyJsonString = util.graphqlToJson(query);
		Response httpReq = httpRequest(bodyJsonString);
		httpReq.then().log().all().assertThat().statusCode(200).and()
				.body("apiData.trailers.edges[0].node.trailerReg", equalTo(trailerReg)).and()
				.body("apiData.trailers.edges.node", hasSize(1));
	}

	@Test(dataProvider = "countryOfOrigin", dataProviderClass = AssetsData.class)
	public void filterTrucksByCountry(String country, String countValue) {
		String query = "query Trucks {\r\n" + "  trucks(country: \"" + countValue + "\") {\r\n" + "    edges {\r\n"
				+ "      node {\r\n" + "        serial\r\n" + "        createdAt\r\n" + "        country\r\n"
				+ "      }\r\n" + "    }\r\n" + "  }\r\n" + "}";
		Utility util = new Utility();
		String bodyJsonString = util.graphqlToJson(query);
		Response httpReq = httpRequest(bodyJsonString);
		httpReq.then().log().all().assertThat().statusCode(200).and().body("apiData.trucks.edges.node",
				hasItem(allOf(hasEntry(country, countValue))));
	}

	@Test(dataProvider = "countryOfOrigin", dataProviderClass = AssetsData.class)
	public void filterDriversByCountry(String country, String countValue) {
		String query = "query Drivers {\r\n" + "  drivers(country: \"" + countValue + "\") {\r\n" + "    edges {\r\n"
				+ "      node {\r\n" + "        serial\r\n" + "        names\r\n" + "        createdAt\r\n"
				+ "        country\r\n" + "      }\r\n" + "    }\r\n" + "  }\r\n" + "}";
		Utility util = new Utility();
		String bodyJsonString = util.graphqlToJson(query);
		Response httpReq = httpRequest(bodyJsonString);
		httpReq.then().log().all().assertThat().statusCode(200).and().body("apiData.drivers.edges.node",
				hasItem(allOf(hasEntry(country, countValue))));
	}

	@Test(dataProvider = "countryOfOrigin", dataProviderClass = AssetsData.class)
	public void filterTrailersByCountry(String country, String countValue) {
		String query = "query Trailers {\r\n" + "  trailers(country: \"" + countValue + "\"){\r\n" + "    edges {\r\n"
				+ "      node{\r\n" + "        trailerReg\r\n" + "        serial\r\n" + "        id\r\n"
				+ "        country\r\n" + "      }\r\n" + "    }\r\n" + "  }\r\n" + "}";
		Utility util = new Utility();
		String bodyJsonString = util.graphqlToJson(query);
		Response httpReq = httpRequest(bodyJsonString);
		httpReq.then().log().all().assertThat().statusCode(200).and().body("apiData.trailers.edges.node",
				hasItem(allOf(hasEntry(country, countValue))));
	}
}
