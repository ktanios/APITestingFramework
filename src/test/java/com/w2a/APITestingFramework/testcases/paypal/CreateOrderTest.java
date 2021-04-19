package com.w2a.APITestingFramework.testcases.paypal;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.w2a.APITestingFramework.APIs.paypal.OrderAPI;
import com.w2a.APITestingFramework.setUp.BaseTest;

import io.restassured.response.Response;

public class CreateOrderTest extends BaseTest{
	
	
	@Test
	public void createOrder() {
		
		String accessToken = OrderAPI.getAccessToken();
		Response response = OrderAPI.createOrder(accessToken);
		Assert.assertEquals(response.jsonPath().get("status").toString(), "CREATED");
		response.prettyPrint();
	}

}
