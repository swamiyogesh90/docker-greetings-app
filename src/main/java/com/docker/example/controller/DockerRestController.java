package com.docker.example.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DockerRestController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@RequestMapping(value="/greetings", method=RequestMethod.GET)
	public String displayName(@PathParam("name") String name) {
		
		HttpEntity<String> requestEntity = null;
		String URL = "http://displayNameApp:8085/displayName?name=" + name;
		URI url = null;
		try {
			url = new URI(URL);
		} catch (URISyntaxException e) {
			System.out.println("Error while making call to displayName app");
		}
		
		ResponseEntity<String> exchangeResponse = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		
		return exchangeResponse.getBody().toString() + "\n\n" + dateFormat.format(date);
	}

}
