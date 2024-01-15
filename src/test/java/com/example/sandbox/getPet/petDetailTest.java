package com.example.sandbox.getPet;

import static com.example.sandbox.util.constans.Tags.SMOKE;
import static com.example.sandbox.util.constans.Tags.REGRESSION;
import java.util.Map;
import java.util.TreeMap;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.example.sandbox.Common;
import com.example.sandbox.util.JsonBody;
import com.example.sandbox.util.Tools;
import com.example.sandbox.util.swagger.definitions.Info;
import com.example.sandbox.util.swagger.definitions.Pet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.restassured.response.Response;

public class petDetailTest extends Common {

	@DataProvider(name="Unsuccessful getPetById")
	public Object[][] invalidIds(){
		return new Object[][] {{"-666",1,"error","Pet not found"},{"null",404,"unknown","java.lang.NumberFormatException: For input string: \"null\""}};
	}
	
	@Test(enabled = true, groups = { SMOKE }, description = "get pet by id return HTTP OK")
	public void getPetListByStatusTest() throws JsonMappingException, JsonProcessingException {
		Map<String, String> queryParams = new TreeMap<>();
		queryParams.put("status", "available");

		Response response = getUrl(findByStatus, queryParams);
		Assert.assertEquals(response.getStatusCode(), 200, "Invalid response code");

		String id = response.jsonPath().get("[0].id").toString();

		Response getResponse = getUrl(Tools.replaceVariable("petId").withValue(id).inText(petById));
		Assert.assertEquals(getResponse.getStatusCode(), 200, "Invalid response code");
	}

	@Test(enabled = true, groups = { REGRESSION }, description = "get pet by id returns correct pet")
	public void GetPetSuccessfullyTest() throws JsonMappingException, JsonProcessingException {
		Map<String, String> queryParams = new TreeMap<>();
		queryParams.put("status", "available");

		Response response = getUrl(findByStatus, queryParams);
		Assert.assertEquals(response.getStatusCode(), 200, "Invalid response code");

		String id = response.jsonPath().get("[0].id").toString();
		Response getResponse = getUrl(Tools.replaceVariable("petId").withValue(id).inText(petById));
		JsonBody json = new JsonBody();
		Pet pet = json.getPet(getResponse.getBody().asString());

		Assert.assertEquals(getResponse.getStatusCode(), 200, "Invalid response code");
		Assert.assertNotNull(pet.getId(), "Missing id");
		Assert.assertNotNull(pet.getName(), "Missing name");
		Assert.assertNotNull(pet.getPhotoUrls(), "Missing photourls");
	}
	
	@Test(enabled = true,groups = {REGRESSION},description ="non existing pet id returns HTTP not found and correct error message", dataProvider = "Unsuccessful getPetById")
	public void GetPetUnsuccessfullyTest(String id,int expectedCode,String expectedType,String expectedMessage) throws JsonMappingException, JsonProcessingException {
	    	Response  response = getUrl(Tools.replaceVariable("petId").withValue(id).inText(petById));
	    	JsonBody json = new JsonBody();
	    	Info error = json.getInfo(response.getBody().asString());
   	
	    	Assert.assertEquals(response.getStatusCode(),404,"Invalid response code");
	    	Assert.assertEquals(error.getCode(), expectedCode);
	    	Assert.assertEquals(error.getType(), expectedType);
	    	Assert.assertEquals(error.getMessage(), expectedMessage);
	 }
}
