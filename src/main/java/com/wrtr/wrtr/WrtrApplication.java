package com.wrtr.wrtr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main app launcher
 */
@SpringBootApplication
public class WrtrApplication {

	/**
	 * Launches the app
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(WrtrApplication.class, args);
	}

}
