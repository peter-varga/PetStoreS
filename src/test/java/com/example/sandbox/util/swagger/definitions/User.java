package com.example.sandbox.util.swagger.definitions;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class User {

	public User() {}
	
	@JsonProperty
	private int id;

	@JsonProperty
	private String username;

	@JsonProperty
	private String firstName;

	@JsonProperty
	private String lastName;

	@JsonProperty
	private String email;

	@JsonProperty
	private String password;

	@JsonProperty
	private int userStatus;
	
	@JsonProperty
	private String phone;
}
