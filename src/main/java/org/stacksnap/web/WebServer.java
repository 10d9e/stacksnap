package org.stacksnap.web;

import static spark.Spark.get;
import static spark.Spark.port;

public class WebServer {
	
	static DebugScreen screen = new DebugScreen();

	public static void start() {
		port(4567);
		
		get("/snap/:name", (request, response) -> {
			return screen.handle(request.params(":name"), request, response);
		});

	}

	public static void main(String[] args) {
		start();
	}
}