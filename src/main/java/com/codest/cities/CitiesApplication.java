package com.codest.cities;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@ComponentScan({ "com.codest.cities.controller", "com.codest.cities.security" })
public class CitiesApplication {
	private static final Logger LOG = LoggerFactory.getLogger(CitiesApplication.class);
	
	@Value("${server.port}")
	private String port;

	public static void main(String[] args) {
		SpringApplication.run(CitiesApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	private void executeAtSarting() {
		String uriStr = "http://localhost:" + port;

		if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
			LOG.info("Opening the browser with url: {}", uriStr);

			try {
				Desktop.getDesktop().browse(new URI(uriStr));
			} catch (IOException | URISyntaxException e) {
				LOG.warn("Opening the browser with url '{}' failed!", uriStr);
			}
		} else {
			String command = null;

			if (SystemUtils.IS_OS_LINUX) {
				command = "xdg-open " + uriStr;
			} else if (SystemUtils.IS_OS_WINDOWS) {
				command = "explorer " + uriStr;
			} else if (SystemUtils.IS_OS_MAC) {
				command = "open " + uriStr;
			}

			if (command != null) {
				try {
					Runtime.getRuntime().exec(command);
				} catch (IOException e) {
					// We don't need to log anything
				}
			}

			LOG.info("Open the flowing url in browser to execute the app if the browser doesn't open automatically: {}",
					uriStr);
		}
	}
}
