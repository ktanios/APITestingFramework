package com.w2a.APITestingFramework.testcases.paypal;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.w2a.APITestingFramework.APIs.paypal.OrderAPI;
import com.w2a.APITestingFramework.setUp.BaseTest;

import io.restassured.response.Response;

public class GetOrderTest extends BaseTest{
	
	
	@Test
	public void getOrder() {
		
		String accessToken = OrderAPI.getAccessToken();
		Response response = OrderAPI.getOrder(accessToken);
		response.prettyPrint();
		
		Assert.assertEquals(response.getStatusCode(),200);
	}

}
