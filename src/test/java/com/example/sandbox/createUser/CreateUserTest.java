package com.example.sandbox.createUser;

import static com.example.sandbox.util.constans.Tags.REGRESSION;
import static com.example.sandbox.util.constans.Tags.SMOKE;

import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.example.sandbox.Common;
import com.example.sandbox.util.Tools;
import com.example.sandbox.util.constans.TestData;
import com.example.sandbox.util.swagger.definitions.Info;
import com.example.sandbox.util.swagger.definitions.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.restassured.response.Response;
import org.testng.Assert;

public class CreateUserTest extends Common {

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
				.username(String.format(TestData.USERNAME_TEMPLATE, Tools.generateRandomString()))
				.firstName(TestData.USER_FIRSTNAME)
				.lastName(TestData.USER_LASTNAME)
				.email(TestData.EMAIL)
				.password(TestData.PASSWORD)
				.phone(TestData.PHONE)
				.userStatus(TestData.USER_STATUS)
				.build();
		User user2 = User.builder().id(Tools.generateRandomNumber())
				.username(String.format(TestData.USERNAME_TEMPLATE, Tools.generateRandomString()))
				.firstName(TestData.USER_FIRSTNAME)
				.lastName(TestData.USER_LASTNAME)
				.email(TestData.EMAIL)
				.password(TestData.PASSWORD)
				.phone(TestData.PHONE)
				.userStatus(TestData.USER_STATUS)
				.build();
		users.add(user);
		users.add(user2);
		String json = serializer.createUsers(users);
		// Act
		Response createResponse = postUrl(endPoint, json);
		Info info = serializer.getInfo(createResponse.getBody().asString());
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
		Info info = serializer.getInfo(createResponse.getBody().asString());
		// Assert
		Assert.assertEquals(createResponse.getStatusCode(), 405, "Invalid response code");
		Assert.assertEquals(info.getCode(), 405, "Invalid code");
		Assert.assertEquals(info.getType(), "unknown", "Invalid type");
		Assert.assertEquals(info.getMessage(), "no data", "Invalid message");
	}
}
