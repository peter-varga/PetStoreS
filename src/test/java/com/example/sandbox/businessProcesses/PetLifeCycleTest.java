package com.example.sandbox.businessProcesses;

import static org.assertj.core.api.Assertions.assertThat;
import static com.example.sandbox.util.constans.Tags.SMOKE;
import static com.example.sandbox.util.constans.Tags.REGRESSION;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.example.sandbox.Common;
import com.example.sandbox.util.JsonBody;
import com.example.sandbox.util.Tools;
import com.example.sandbox.util.constans.TestData;
import com.example.sandbox.util.swagger.definitions.Info;
import com.example.sandbox.util.swagger.definitions.Pet;

import io.restassured.response.Response;

public class PetLifeCycleTest extends Common {

	@Test(enabled = true, groups = { SMOKE, REGRESSION }, description = "create pet, update pet, delete pet")
	public void petLifeCycleTest() throws IOException {
		// Create Pet
		// Arrange
		List<String> expectedPhotoUrls = new ArrayList<String>();
		expectedPhotoUrls.add(TestData.HYDRAIMAGE);
		String expectedName = TestData.PETNAME;
		JsonBody body = new JsonBody();
		Pet expectedPet = Pet.builder().name(expectedName).photoUrls(expectedPhotoUrls).build();
		String json = body.createPet(expectedPet);
		// Act
		Response createResponse = postUrl(newPet, json);
		Pet createdPet = body.getPet(createResponse.getBody().asString());
		// Assert
		Assert.assertEquals(createResponse.getStatusCode(), 200, "Invalid response code");
		Assert.assertEquals(createdPet.getName(), expectedName);
		Assert.assertEquals(createdPet.getPhotoUrls(), expectedPhotoUrls);

		// Get Pet
		// Arrange
		long expectedId = createdPet.getId();
		expectedPet.setId(expectedId);
		String urlWithPetId = Tools.replaceVariable("petId").withValue(String.valueOf(expectedId)).inText(petById);
		// Act
		Response getResponse = getUrl(urlWithPetId);
		createdPet = body.getPet(getResponse.getBody().asString());
		// Assert
		assertThat(createdPet).usingRecursiveComparison().isEqualTo(expectedPet);

		// Update Pet
		// Arrange
		String expectedChangedName = TestData.PETNAME_UPDATED;
		expectedPet.setName(expectedChangedName);
		json = body.createPet(expectedPet);
		// Act
		Response updateResponse = putUrl(newPet, json);
		Pet updatedPet = body.getPet(updateResponse.getBody().asString());
		// Assert
		assertThat(updatedPet).usingRecursiveComparison().isEqualTo(expectedPet);

		// Get Pet
		// Arrange
		// Act
		getResponse = getUrl(urlWithPetId);
		updatedPet = body.getPet(getResponse.getBody().asString());
		// Assert
		assertThat(updatedPet).usingRecursiveComparison().isEqualTo(expectedPet);

		// Delete Pet
		// Arrange
		Info expectedData = Info.builder().code(200).type("unknown").message(String.valueOf(expectedId)).build();
		// Act
		Response deleteResponse = deleteUrl(urlWithPetId);
		Info deletedPet = body.getInfo(deleteResponse.getBody().asString());
		// Assert
		assertThat(deletedPet).usingRecursiveComparison().isEqualTo(expectedData);

		// Get Pet
		// Arrange
		expectedData.setCode(1);
		expectedData.setType("error");
		expectedData.setMessage("Pet not found");
		// Act
		getResponse = getUrl(urlWithPetId);
		deletedPet = body.getInfo(getResponse.getBody().asString());
		// Assert
		assertThat(deletedPet).usingRecursiveComparison().isEqualTo(expectedData);
	}
}
