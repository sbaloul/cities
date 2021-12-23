package com.codest.cities;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@ComponentScan({ "com.codest.cities.controller" })
public class CitiesApplication {

	@Value("${server.port}")
	private String port;

	public static void main(String[] args) {
		SpringApplication.run(CitiesApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	private void executeAtSarting() {
		String uriStr = "http://localhost:" + port;

		if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
			System.out.println("Opening the browser with url: " + uriStr);

			try {
				Desktop.getDesktop().browse(new URI(uriStr));
			} catch (IOException | URISyntaxException e) {
				System.out.println("Opening the browser with url '" + uriStr + "' failed!");
			}
		} else {
			System.out.println("Open the flowing url in browser to execute the app: " + uriStr);
		}
	}
}
