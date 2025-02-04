package com.example.sandbox.getUserLogout;

import static com.example.sandbox.util.constans.Tags.REGRESSION;
import static com.example.sandbox.util.constans.Tags.SMOKE;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.example.sandbox.Common;
import com.example.sandbox.util.swagger.definitions.Info;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.restassured.response.Response;

public class GetUserLogoutTest extends Common {
	
	@Test(enabled = true, groups = { SMOKE, REGRESSION }, description = "get userlogout alwayys returns correct data")
	public void getUserLogoutSuccessfullyTest() throws JsonMappingException, JsonProcessingException {
		// Arrange
		// Act
		Response getResponse = getUrl(logout);
		Info info = serializer.getInfo(getResponse.getBody().asString());
		// Assert
		Assert.assertEquals(getResponse.getStatusCode(), 200, "Invalid response code");
		Assert.assertEquals(info.getCode(), 200, "Invalid code");
		Assert.assertEquals(info.getType(), "unknown", "Invalid type");
		Assert.assertEquals(info.getMessage(), "ok", "Invalid message");
	}
}
