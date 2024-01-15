package com.example.sandbox.getUser;

import static com.example.sandbox.util.constans.Tags.SMOKE;
import static com.example.sandbox.util.constans.Tags.REGRESSION;
import static org.assertj.core.api.Assertions.assertThat;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.example.sandbox.Common;
import com.example.sandbox.util.JsonBody;
import com.example.sandbox.util.Tools;
import com.example.sandbox.util.swagger.definitions.Info;
import com.example.sandbox.util.swagger.definitions.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.restassured.response.Response;

public class getUserTest extends Common {

	@Test(enabled = true, groups = { SMOKE, REGRESSION }, description = "get existing testuser returns correct data")
	public void getUserSuccessfullyTest() throws JsonMappingException, JsonProcessingException {
		//Arrange
		User expectedUser = User.builder()
				.id(76)
				.username("myTestUserNameUVgpoaimRt")
				.email("johndoe@mail.com")
				.firstName("John")
				.lastName("Doe")
				.password("*****")
				.phone("06809874563")
				.userStatus(0)
				.build();
		//Act
		Response getResponse = getUrl(Tools.replaceVariable("username").withValue(expectedUser.getUsername()).inText(user));
		JsonBody body = new JsonBody();
		User actualUser = body.getUser(getResponse.getBody().asString());
		//Assert
		assertThat(actualUser).usingRecursiveComparison().isEqualTo(expectedUser);
	}
	
	@Test(enabled = true, groups = { REGRESSION }, description = "get nonexisting testuser returns correct error")
	public void getUserUnSuccessfullyTest() throws JsonMappingException, JsonProcessingException {
		//Arrange
		String testUserName = "user1";
		//Act
		Response getResponse = getUrl(Tools.replaceVariable("username").withValue(testUserName).inText(user));
		JsonBody body = new JsonBody();
		Info info = body.getInfo(getResponse.getBody().asString());
		//Assert
		Assert.assertEquals(getResponse.getStatusCode(), 404, "Invalid response code");
		Assert.assertEquals(info.getCode(), 1, "Invalid code");
		Assert.assertEquals(info.getType(), "error", "Invalid type");
		Assert.assertEquals(info.getMessage(), "User not found", "Invalid message");
		
	}
}
