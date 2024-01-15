package com.example.sandbox.businessProcesses;

import static com.example.sandbox.util.constans.Tags.REGRESSION;
import static com.example.sandbox.util.constans.Tags.SMOKE;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.example.sandbox.Common;
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
		Pet expectedPet = Pet.builder().name(TestData.PETNAME).photoUrl(TestData.HYDRAIMAGE).build();
		String json = serializer.createPet(expectedPet);
		// Act
		Response createResponse = postUrl(newPet, json);
		Pet createdPet = serializer.getPet(createResponse.getBody().asString());
		// Assert
		Assert.assertEquals(createResponse.getStatusCode(), 200, "Invalid response code");
		Assert.assertEquals(createdPet.getName(), expectedPet.getName());
		Assert.assertEquals(createdPet.getPhotoUrls(), expectedPet.getPhotoUrls());

		// Get Pet
		// Arrange
		long expectedId = createdPet.getId();
		expectedPet.setId(expectedId);
		String urlWithPetId = Tools.replaceVariable("petId").withValue(String.valueOf(expectedId)).inText(petById);
		// Act
		Response getResponse = getUrl(urlWithPetId);
		Pet actualPet = serializer.getPet(getResponse.getBody().asString());
		// Assert
		assertThat(actualPet).usingRecursiveComparison().isEqualTo(expectedPet);

		// Update Pet
		// Arrange
		expectedPet.setName(TestData.PETNAME_UPDATED);
		json = serializer.createPet(expectedPet);
		// Act
		Response updateResponse = putUrl(newPet, json);
		Pet updatedPet = serializer.getPet(updateResponse.getBody().asString());
		// Assert
		assertThat(updatedPet).usingRecursiveComparison().isEqualTo(expectedPet);

		// Get Pet
		// Arrange
		// Act
		getResponse = getUrl(urlWithPetId);
		actualPet = serializer.getPet(getResponse.getBody().asString());
		// Assert
		assertThat(actualPet).usingRecursiveComparison().isEqualTo(expectedPet);

		// Delete Pet
		// Arrange
		Info expectedData = Info.builder().code(200).type("unknown").message(String.valueOf(expectedId)).build();
		// Act
		Response deleteResponse = deleteUrl(urlWithPetId);
		Info deletedPet = serializer.getInfo(deleteResponse.getBody().asString());
		// Assert
		assertThat(deletedPet).usingRecursiveComparison().isEqualTo(expectedData);

		// Get Pet
		// Arrange
		expectedData.setCode(1);
		expectedData.setType("error");
		expectedData.setMessage("Pet not found");
		// Act
		getResponse = getUrl(urlWithPetId);
		deletedPet = serializer.getInfo(getResponse.getBody().asString());
		// Assert
		assertThat(deletedPet).usingRecursiveComparison().isEqualTo(expectedData);
	}
}
