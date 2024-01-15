package com.example.sandbox.createPet;

import static com.example.sandbox.util.constans.Tags.REGRESSION;
import static com.example.sandbox.util.constans.Tags.SMOKE;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.example.sandbox.Common;
import com.example.sandbox.util.JsonBody;
import com.example.sandbox.util.constans.TestData;
import com.example.sandbox.util.swagger.definitions.Item;
import com.example.sandbox.util.swagger.definitions.Pet;
import com.example.sandbox.util.swagger.definitions.Info;

import io.restassured.response.Response;

public class CreatePetTest extends Common {

	@Test(enabled = true, groups = { SMOKE, REGRESSION }, description = "create pet")
	public void createPetSuccessfullyTest() throws IOException {
		//create Pet
		//Arrange
		Item item = Item.builder()
				.name("myItem")
				.build();
		List<Item> tags = new ArrayList<Item>();
		tags.add(item);
		List<String> photoUrls = new ArrayList<String>();
		photoUrls.add(TestData.HYDRAIMAGE);
		String name = "MyTestDog";
		String status = "available";
		JsonBody body = new JsonBody();
		Pet expectedPet = Pet.builder()
				.name(name)
				.photoUrls(photoUrls)
				.category(item)
				.status(status)
				.tags(tags)
				.build();
		String json = body.createPet(expectedPet);
		//Act
		Response createResponse = postUrl(newPet,json);
		Pet actualPet = body.getPet(createResponse.getBody().asString());
		//Assert
		Assert.assertEquals(createResponse.getStatusCode(),200,"Invalid response code");
		assertThat(actualPet)
			.usingRecursiveComparison()
			.ignoringFields("id")
			.isEqualTo(expectedPet);
	}
	
	@Test(enabled = true, groups = { REGRESSION }, description = "can not create pet without proper payLoad")
	public void createPetUnsuccessfullyTest() throws IOException {
		//Arrange
		String json = "";
		//Act
		Response createResponse = postUrl(newPet,json);
		JsonBody body = new JsonBody();
		Info data = body.getInfo(createResponse.getBody().asString());
		//Assert
		Assert.assertEquals(createResponse.getStatusCode(),405,"Invalid response code");
		Assert.assertEquals(data.getCode(),405,"Invalid response code");
		Assert.assertEquals(data.getType(),"unknown","Invalid type");
		Assert.assertEquals(data.getMessage(),"no data","Invalid data");
	}
}
