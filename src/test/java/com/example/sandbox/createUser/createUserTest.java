package com.example.sandbox.createUser;

import static com.example.sandbox.util.constans.Tags.REGRESSION;
import static com.example.sandbox.util.constans.Tags.SMOKE;

import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.example.sandbox.Common;
import com.example.sandbox.util.JsonBody;
import com.example.sandbox.util.Tools;
import com.example.sandbox.util.swagger.definitions.Info;
import com.example.sandbox.util.swagger.definitions.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.restassured.response.Response;
import org.testng.Assert;

public class createUserTest extends Common {

	@DataProvider(name = "user creation endpoints")
	public Object[] userEndpoints() {
		return new Object[] { createWithArray, createWithList };
	}

	@Test(enabled = true, groups = { SMOKE,
			REGRESSION }, description = "create user", dataProvider = "user creation endpoints")
	public void createUserSuccessfullyTest(String endPoint) throws JsonProcessingException {
		// Arrange
		List<User> users = new ArrayList<User>();
		User user = User.builder().id(Tools.generateRandomNumber())
				.username(String.format("myTestUserName%s", Tools.generateRandomString())).firstName("John")
				.lastName("Doe").email("johndoe@mail.com").password("*****").phone("06809874563").userStatus(0).build();
		User user2 = User.builder().id(Tools.generateRandomNumber())
				.username(String.format("myTestUserName%s", Tools.generateRandomString())).firstName("John")
				.lastName("Doe").email("johndoe@mail.com").password("*****").phone("06809874563").userStatus(0).build();
		users.add(user);
		users.add(user2);

		JsonBody body = new JsonBody();
		String json = body.createUsers(users);
		// Act
		Response createResponse = postUrl(endPoint, json);
		Info info = body.getInfo(createResponse.getBody().asString());
		// Assert
		Assert.assertEquals(createResponse.getStatusCode(), 200, "Invalid response code");
		Assert.assertEquals(info.getCode(), 200, "Invalid code");
		Assert.assertEquals(info.getType(), "unknown", "Invalid type");
		Assert.assertEquals(info.getMessage(), "ok", "Invalid message");
	}

	@Test(enabled = true, groups = {
			REGRESSION }, description = "can not create user without proper payLoad", dataProvider = "user creation endpoints")
	public void createUserUnSuccessfullyTest(String endPoint) throws JsonMappingException, JsonProcessingException {
		// Arrange
		// Act
		Response createResponse = postUrl(endPoint, "");
		JsonBody body = new JsonBody();
		Info info = body.getInfo(createResponse.getBody().asString());
		// Assert
		Assert.assertEquals(createResponse.getStatusCode(), 405, "Invalid response code");
		Assert.assertEquals(info.getCode(), 405, "Invalid code");
		Assert.assertEquals(info.getType(), "unknown", "Invalid type");
		Assert.assertEquals(info.getMessage(), "no data", "Invalid message");
	}
}
