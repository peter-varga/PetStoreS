package com.example.sandbox.getInventory;

import static com.example.sandbox.util.constans.Tags.REGRESSION;
import static com.example.sandbox.util.constans.Tags.SMOKE;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.example.sandbox.Common;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.restassured.response.Response;

public class GetInventoryTest extends Common {

	@Test(enabled = true, groups = { SMOKE, REGRESSION }, description = "get inventory is always successful")
	public void getInventorSuccessfullyTest() throws JsonMappingException, JsonProcessingException {
		// get Inventory
		// Arrange
		// Act
		Response getResponse = getUrl(inventory);
		Map<String, Object> inventory = serializer.getInventory(getResponse.getBody().asString());
		// Assert
		Assert.assertEquals(getResponse.getStatusCode(), 200, "Invalid response code");
		Assert.assertNotEquals(inventory.size(), 0);
		Assert.assertTrue(inventory.values().stream().findFirst().get() instanceof Integer);
	}
}
