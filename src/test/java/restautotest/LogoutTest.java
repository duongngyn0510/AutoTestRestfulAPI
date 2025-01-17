package restautotest;

import static io.restassured.RestAssured.*;
import org.json.JSONObject;
import io.restassured.response.Response;
import org.testng.Assert;

public class LogoutTest extends APINeedTesting{
	

	public String setLogin(String email, String password) {
		LoginHelper lg = new LoginHelper();
		return lg.getAccessToken(email, password);
	}
	
	public void callAPI(String access_token) {
		baseURI = BaseURI.BASEURI;
		Response response = 
				given()
					.header("Authorization", "Bearer" + access_token)
				.when()
					.post("api/logout");
		
		JSONObject rep = new JSONObject(response.getBody().asString());
		this.codeResponse = Integer.parseInt(rep.get("code").toString());
		this.messageResponse = rep.get("message").toString();
		this.dataResponse = rep.get("data").toString();
	}
	
	void test1() {
		System.out.println("Test 1 in LogOut API: The code should be 1000 and message is OK:");
		
		//Unit 1
		try {
			String access_token = this.creRequest(
					"auto@gmail.com"							
					,"123456"
			);
			this.callAPI(access_token);
			Assert.assertEquals(this.codeResponse, 1000);
			Assert.assertEquals(this.messageResponse, "OK");
	        System.out.println("Unit 1: Passed");
		} catch (AssertionError e) {
			System.out.println("Unit 1: Failed");
		}

		
		//Unit 2
		try {
			String request = this.creRequest(
					"d111112@gmail.com"
					,"123"
			);
			this.callAPI(request);
			Assert.assertEquals(this.codeResponse, 1000);
			Assert.assertEquals(this.messageResponse, "OK");
			System.out.println("Unit 2: Passed");
		} catch (AssertionError e) {
			System.out.println("Unit 2: Failed");
		}

	 }
}
