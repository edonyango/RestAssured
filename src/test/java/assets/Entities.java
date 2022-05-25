package assets;

import static io.restassured.RestAssured.given;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import reusableMethods.GenerateTokens;
import reusableMethods.Utility;
import static org.hamcrest.Matchers.*;

public class Entities extends GenerateTokens {

	@DataProvider
	public Object[][] accMgrId() {
		return new Object [][] {{"1", "Abdalla Salim"},{"2", "Christo Oneya"}};
	}
	
	//@Test
	public void filterTrucksBySerial() {
		String query = "query Trucks {\r\n" + "  trucks(serialList: \"" + "KCH047Y" + "\") {\r\n" + "    edges {\r\n"
				+ "      node {\r\n" + "        serial\r\n" + "        createdAt\r\n" + "      }\r\n" + "    }\r\n"
				+ "  }\r\n" + "}";
		Utility util = new Utility();
		String bodyJsonString = util.graphqlToJson(query);
		given().log().all().contentType("application/json").header("x-assets-authorization", generateLIA_DEV_Token())
				.body(bodyJsonString).when().post().then().log().all().assertThat().statusCode(200);
	}

	@Test(dataProvider = "accMgrId", dataProviderClass = Entities.class)
	public void filterTransportersByAccountMgr(String accountMgId, String fullName) {
		RestAssured.baseURI ="https://serengeti.welovetrucks.co/assets-backend/api/graphql";
		String query = "{\r\n" + "	transporters(accountManagerId: "+ accountMgId +") {\r\n" + "		edges {\r\n"
				+ "			node {\r\n" + "                companyName\r\n" + "                accountManagerId\r\n"
				+ "				accountManager {\r\n" + "					fullName\r\n"
				+ "					email\r\n" + "				}\r\n" + "			}\r\n" + "		}\r\n"
				+ "		extra\r\n" + "		totalCount\r\n" + "	}\r\n" + "}\r\n" + "";
		Utility util = new Utility();
		String jsBody = util.graphqlToJson(query);
		String token = generateLIA_DEV_Token();
		given().contentType("application/json").header("X-ASSETS-Authorization", token).body(jsBody).when()
				.post().then().log().all().assertThat()
				.statusCode(200).body("data.transporters.edges.node.accountManager.fullName", hasItem(allOf(equalTo(fullName))));
	}
	
}
