package com.example.sandbox.getUserLogin;

import static com.example.sandbox.util.constans.Tags.REGRESSION;
import static com.example.sandbox.util.constans.Tags.SMOKE;

import java.util.Map;
import java.util.TreeMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.example.sandbox.Common;
import com.example.sandbox.util.JsonBody;
import com.example.sandbox.util.swagger.definitions.Info;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.restassured.response.Response;

public class getUserLoginTest extends Common {

	@Test(enabled = true, groups = { SMOKE, REGRESSION }, description = "get userlogin always returns same data")
	public void getUserLoginSuccessfullyTest() throws JsonMappingException, JsonProcessingException {
		// Arrange
		Map<String, String> queryParams = new TreeMap<>();
		queryParams.put("username", "myTestUserNameUVgpoaimRt");
		queryParams.put("password", "*****");
		// Act
		Response getResponse = getUrl(login, queryParams);
		JsonBody body = new JsonBody();
		Info info = body.getInfo(getResponse.getBody().asString());
		// Assert
		Assert.assertEquals(getResponse.getStatusCode(), 200, "Invalid response code");
		Assert.assertEquals(info.getCode(), 200, "Invalid code");
		Assert.assertEquals(info.getType(), "unknown", "Invalid type");
		Assert.assertTrue(info.getMessage().contains("logged in user session:"), "Invalid message");
	}
}
