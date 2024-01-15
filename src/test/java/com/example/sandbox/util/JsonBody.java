package com.example.sandbox.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.example.sandbox.util.swagger.definitions.Info;
import com.example.sandbox.util.swagger.definitions.Order;
import com.example.sandbox.util.swagger.definitions.Pet;
import com.example.sandbox.util.swagger.definitions.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonBody {

	private ObjectMapper mapper;

	public JsonBody() {
		this.mapper = new ObjectMapper();
	}

	public String createPet(Pet pet) throws IOException {
		String body = mapper.writeValueAsString(pet);
		return body;
	}

	public Pet getPet(String json) throws JsonMappingException, JsonProcessingException {
		Pet pet = mapper.readValue(json, Pet.class);
		return pet;
	}

	public Info getInfo(String json) throws JsonMappingException, JsonProcessingException {
		Info data = mapper.readValue(json, Info.class);
		return data;
	}

	public List<Pet> getPetList(String json) throws JsonMappingException, JsonProcessingException {
		ArrayList<Pet> list = mapper.readValue(json,
				mapper.getTypeFactory().constructCollectionType(ArrayList.class, Pet.class));
		return list;
	}

	public String createOrder(Order order) throws IOException {
		String body = mapper.writeValueAsString(order);
		return body;
	}

	public Order getOrder(String json) throws JsonMappingException, JsonProcessingException {
		Order order = mapper.readValue(json, Order.class);
		return order;
	}

	public Map<String, Object> getInventory(String json) throws JsonMappingException, JsonProcessingException {
		TreeMap<String, Object> value = mapper.readValue(json, TreeMap.class);
		Map<String, Object> result = value;
		return result;
	}

	public String createUsers(List<User> users) throws JsonProcessingException {
		String body = mapper.writeValueAsString(users);
		return body;
	}

	public User getUser(String json) throws JsonMappingException, JsonProcessingException {
		User user = mapper.readValue(json, User.class);
		return user;
	}
}
