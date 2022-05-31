package apiReusableMethods;

import static io.restassured.RestAssured.given;

public class GenerateTokens {
	
	public static String generateLIA_DEV_Token() {
		String dev_token_url = "https://one.welovetrucks.co/auth/realms/LIA-dev/protocol/openid-connect/token";
		String token = given().log().all().contentType("application/x-www-form-urlencoded")
				.formParam("grant_type", "password").formParam("username", "lta-svc")
				.formParam("password", "NwAPn521JYSmNPSWme2Me82vvJYHOua5").formParam("client_id", "asset-service")
				.formParam("client_secret", "8691153c-09d6-4e6c-be6d-c686b1ee4aae").when().post(dev_token_url).then()
				.extract().path("access_token").toString();
		return token;
	}
}
