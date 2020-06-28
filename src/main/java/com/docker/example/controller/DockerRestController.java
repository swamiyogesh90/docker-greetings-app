package com.docker.example.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
    private Environment env;
	
	@Autowired
	RestTemplate restTemplate;
	
	@RequestMapping(value="/greetings", method=RequestMethod.GET)
	public String displayName(@PathParam("name") String name) {
		
		HttpEntity<String> requestEntity = null;
		
		String displayNameAppHost = env.getProperty("DISPLAYNAME_APP_NAME");
		String displayNameAppPort = env.getProperty("DISPLAYNAME_APP_PORT");
		
		String URL = "http://"+ displayNameAppHost + ":" + displayNameAppPort + "/displayName?name=" + name;
		URI url = null;
		try {
			url = new URI(URL);
		} catch (URISyntaxException e) {
			System.out.println("Error while making call to displayName app");
		}
		
		ResponseEntity<String> exchangeResponse = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
		
		SimpleDateFormat sd = new SimpleDateFormat("dd/mm/yyyy 'at' HH:mm:ss z");
	        Date date = new Date();
	        sd.setTimeZone(TimeZone.getTimeZone("IST"));
		
		return exchangeResponse.getBody().toString() + "\n\nRequest made at - " + sd.format(date);
	}
	
}
