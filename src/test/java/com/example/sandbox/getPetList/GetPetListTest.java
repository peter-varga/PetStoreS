package com.example.sandbox.getPetList;

import com.example.sandbox.Common;
import com.example.sandbox.util.JsonBody;
import com.example.sandbox.util.swagger.definitions.Pet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.restassured.response.Response;


import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.example.sandbox.util.constans.Tags.SMOKE;
import static com.example.sandbox.util.constans.Tags.REGRESSION;

public class GetPetListTest extends Common {

	@Test(enabled = true, groups = { SMOKE }, description = "find pet by status returns HTTP OK")
	public void findPetsByStatusReturnsOKTest() {
		Map<String, String> queryParams = new TreeMap<>();
		queryParams.put("status", "available");

		Response response = getUrl(findByStatus, queryParams);
		Assert.assertEquals(response.getStatusCode(), 200, "Invalid response code");
	}

	@Test(enabled = true, groups = { SMOKE }, description = "find pet by status with header parameters returns HTTP OK")
	public void findPetsByStatusWithHeaderReturnsOKTest() {
		Map<String, String> queryParams = new TreeMap<>();
		queryParams.put("status", "available");
		Map<String, String> headers = new TreeMap<>();
		headers.put("Mandatoryheader", "BFG");

		Response response = getUrl(findByStatus, headers, queryParams);
		Assert.assertEquals(response.getStatusCode(), 200, "Invalid response code");

	}

	@Test(enabled = true, groups = { REGRESSION }, description = "find pet by status has some pets")
	public void FindPetsByStatusSuccessfullyTest() throws JsonMappingException, JsonProcessingException {
		Map<String, String> queryParams = new TreeMap<>();
		queryParams.put("status", "available");
		Map<String, String> headers = new TreeMap<>();
		headers.put("Mandatoryheader", "BFG");

		Response response = getUrl(findByStatus, headers, queryParams);
		JsonBody body = new JsonBody();
		List<Pet> petList = body.getPetList(response.getBody().asString());

		Assert.assertEquals(response.getStatusCode(), 200, "Invalid response code");
		Assert.assertNotEquals(petList.size(), 0);
	}

	@Test(enabled = true, groups = {
			REGRESSION }, description = "find pet by status without status parameter returns empty list")
	public void FindPetsByStatusUnsuccessfullyTest() throws JsonMappingException, JsonProcessingException {
		Response response = getUrl(findByStatus);
		JsonBody body = new JsonBody();
		List<Pet> petList = body.getPetList(response.getBody().asString());

		Assert.assertEquals(response.getStatusCode(), 200, "Invalid response code");
		Assert.assertEquals(petList.size(), 0);

	}
}
