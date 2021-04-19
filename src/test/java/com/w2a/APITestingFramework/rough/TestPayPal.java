package com.w2a.APITestingFramework.rough;


import static io.restassured.RestAssured.given;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.w2a.APITestingFramework.pojo.Orders;
import com.w2a.APITestingFramework.pojo.PurchaseUnits;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class TestPayPal {

	static String access_token;
	static String client_id="Aafrf-nQ-kr6xW2XoVwoPL6DwWkFw2Og5lFJP47RkTh53nXLBW_kEss2XcAwuyCL6ASmAJ3oEf_RWoAM";
	static String secret="EF933duGm91qYVklp6lHYu_7QZosqA4IPGt24PeoZgA7AY0VK9LipWJExIz4YRxxyYIvm6ndsdJ5RGl5";
	static String orderId;

	@Test(priority=1)
	public void getAuthKey() {
		
		RestAssured.baseURI="https://api.sandbox.paypal.com/";
		
		
		Response response = given().param("grant_type", "client_credentials")
		.auth().preemptive()
		.basic(client_id, secret).post("/v1/oauth2/token");
		
		
		response.prettyPrint();
		
		access_token = response.jsonPath().get("access_token").toString();
		System.out.println(access_token);
		
		
		
	}

	
	@Test(priority=2, dependsOnMethods="getAuthKey")
	public void createOrder() {
		
		ArrayList<PurchaseUnits> list = new ArrayList<PurchaseUnits>();
		list.add(new PurchaseUnits("USD","500.00"));
		Orders order = new Orders("CAPTURE",list);
		/*	
		String jsonBody="{\r\n" + 
				"  \"intent\": \"CAPTURE\",\r\n" + 
				"  \"purchase_units\": [\r\n" + 
				"    {\r\n" + 
				"      \"amount\": {\r\n" + 
				"        \"currency_code\": \"USD\",\r\n" + 
				"        \"value\": \"150.00\"\r\n" + 
				"      }\r\n" + 
				"    }\r\n" + 
				"  ]\r\n" + 
				"}";*/
		
		RestAssured.baseURI="https://api.sandbox.paypal.com/";
		Response resposne = given()
		.contentType(ContentType.JSON)
		.auth().oauth2(access_token)
		.body(order)
		.post("/v2/checkout/orders");
		
	
		resposne.prettyPrint();
		

		Assert.assertEquals(resposne.jsonPath().get("status").toString(), "CREATED");
		
		orderId = resposne.jsonPath().get("id").toString();
	
	}
	
	
	@Test(priority=3,dependsOnMethods= {"getAuthKey","createOrder"})
	public void getOrder() {
		
		System.out.println("----Getting the ORDER-------");
		
		RestAssured.baseURI="https://api.sandbox.paypal.com/";
		Response resposne = given()
		.contentType(ContentType.JSON)
		.auth().oauth2(access_token)
		
		.get("/v2/checkout/orders"+"/"+orderId);
		
	
		resposne.prettyPrint();
		
		Assert.assertEquals(resposne.getStatusCode(),200);
		
	}
	
	

}

