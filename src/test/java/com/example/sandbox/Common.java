package com.example.sandbox;

import io.restassured.response.Response;

import java.util.Map;

import com.example.sandbox.util.JsonBody;

import static io.restassured.RestAssured.given;

public class Common extends Endpoints {

	protected JsonBody serializer;
	
	public Common() {
		this.serializer = new JsonBody();
	}
	
    //----------------------------------GET----------------------------------
    public Response getUrl(String endpoint){


        return given()
                .relaxedHTTPSValidation()
                .and()
                .log().everything()
                .when()
                .get(baseUrl+endpoint)
                .then()
                .log()
                .all()
                .extract().response();

    }
    public Response getUrl(String endpoint, Map<String, String> queryParam ){


        return given()
                .relaxedHTTPSValidation()
                .params(queryParam)
                .and()
                .log().everything()
                .when()
                .get(baseUrl+endpoint)
                .then()
                .log()
                .all()
                .extract().response();

    }
    public Response getUrl(String endpoint,Map<String, String> headers,Map<String, String> queryParam ){


        return given()
                .relaxedHTTPSValidation()
                .params(queryParam)
                .headers(headers)
                .and()
                .log().all()
                .when()
                .get(baseUrl+endpoint)
                .then()
                .log()
                .all()
                .extract().response();

    }

    //----------------------------------POST----------------------------------
    public Response postUrl(String endpoint,String body){


        return given()
                .relaxedHTTPSValidation()
                .contentType("application/json; charset=UTF-8")
                .body(body)
                .and()
                .log().everything()
                .when()
                .post(baseUrl+endpoint)
                .then()
                .log()
                .all()
                .extract().response();

    }

    //----------------------------------PUT----------------------------------
    public Response putUrl(String endpoint,String body){


        return given()
                .relaxedHTTPSValidation()
                .contentType("application/json; charset=UTF-8")
                .body(body)
                .and()
                .log().everything()
                .when()
                .put(baseUrl+endpoint)
                .then()
                .log()
                .all()
                .extract().response();

    }

    //----------------------------------DELETE----------------------------------
    public Response deleteUrl(String endpoint){


        return given()
                .relaxedHTTPSValidation()
                .and()
                .log().everything()
                .when()
                .delete(baseUrl+endpoint)
                .then()
                .log()
                .all()
                .extract().response();

    }
}

