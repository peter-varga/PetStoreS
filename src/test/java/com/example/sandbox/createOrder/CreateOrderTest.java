package com.example.sandbox.createOrder;

import static com.example.sandbox.util.constans.Tags.REGRESSION;
import static com.example.sandbox.util.constans.Tags.SMOKE;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.example.sandbox.Common;
import com.example.sandbox.util.JsonBody;
import com.example.sandbox.util.Tools;
import com.example.sandbox.util.swagger.definitions.Order;
import com.example.sandbox.util.swagger.definitions.Info;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.restassured.response.Response;

public class CreateOrderTest extends Common {

	@Test(enabled = true, groups = { SMOKE, REGRESSION }, description = "create order")
	public void createOrderSuccessfullyTest() throws IOException {
		//create order
		//Arrange
		String expectedDate = Tools.actualTimeToString();
		Order expectedOrder = Order.builder()
				.petId(0)
				.quantity(1)
				.shipDate(expectedDate)
				.status("placed")
				.complete(true)
				.build();
		JsonBody body = new JsonBody();
		String json = body.createOrder(expectedOrder);
		//Act
		Response createResponse = postUrl(order,json);
		Order actualOrder = body.getOrder(createResponse.getBody().asString());
		//Assert
		Assert.assertEquals(createResponse.getStatusCode(),200,"Invalid response code");
		assertThat(actualOrder)
			.usingRecursiveComparison()
			.ignoringFields("id")
			.isEqualTo(expectedOrder);
	}
	
	@Test(enabled = true, groups = { REGRESSION }, description = "can not create order without proper payLoad")
	public void createOrderUnSuccessfullyTest() throws JsonMappingException, JsonProcessingException {
		//try create order
		//Arrange
		String json = "";
		//Act
		Response createResponse = postUrl(order,json);
		JsonBody body = new JsonBody();
		Info data = body.getInfo(createResponse.getBody().asString());
		//Assert
		Assert.assertEquals(createResponse.getStatusCode(),400,"Invalid response code");
		Assert.assertEquals(data.getCode(),1,"Invalid code");
		Assert.assertEquals(data.getType(),"error","Invalid type");
		Assert.assertEquals(data.getMessage(),"No data","Invalid data");
	}
}
