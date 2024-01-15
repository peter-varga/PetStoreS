package com.example.sandbox.createPet;

import static com.example.sandbox.util.constans.Tags.REGRESSION;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.example.sandbox.Common;
import com.example.sandbox.util.constans.TestData;
import com.example.sandbox.util.swagger.definitions.Info;
import com.example.sandbox.util.swagger.definitions.Item;
import com.example.sandbox.util.swagger.definitions.Pet;

import io.restassured.response.Response;

public class CreatePetTest extends Common {

	@DataProvider(name="Successful create Pet")
	public Object[] statuses(){
		return new Object[] {"available","pending","sold"};
	}
	
	@Test(enabled = true, groups = { REGRESSION }, description = "create pet", dataProvider = "Successful create Pet")
	public void createPetSuccessfullyTest(String expectedStatus) throws IOException {
		// create Pet
		// Arrange
		Item item = Item.builder().name(TestData.ITEMNAME).build();
		Pet expectedPet = Pet.builder().name(TestData.PETNAME).photoUrl(TestData.HYDRAIMAGE).category(item).status(expectedStatus).tag(item)
				.build();
		String json = serializer.createPet(expectedPet);
		// Act
		Response createResponse = postUrl(newPet, json);
		Pet actualPet = serializer.getPet(createResponse.getBody().asString());
		// Assert
		Assert.assertEquals(createResponse.getStatusCode(), 200, "Invalid response code");
		assertThat(actualPet).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedPet);
	}

	@Test(enabled = true, groups = { REGRESSION }, description = "can not create pet without proper payLoad")
	public void createPetUnsuccessfullyTest() throws IOException {
		// Arrange
		String json = "";
		// Act
		Response createResponse = postUrl(newPet, json);
		Info data = serializer.getInfo(createResponse.getBody().asString());
		// Assert
		Assert.assertEquals(createResponse.getStatusCode(), 405, "Invalid response code");
		Assert.assertEquals(data.getCode(), 405, "Invalid response code");
		Assert.assertEquals(data.getType(), "unknown", "Invalid type");
		Assert.assertEquals(data.getMessage(), "no data", "Invalid data");
	}
}
