/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dandan2000.metrics;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.util.Random;

/**
 *
 * @author daniel
 */
public class MainClass {

    final static int PORT = 9090;
    
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/metrics", new MetricsHandler());
        server.setExecutor(null); 
        System.out.println("Starting server at " + PORT);
        server.start();
    }

    static class MetricsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            Random rand = new Random();
            
            String response = "# HELP apache_accesses_total Current total apache accesses (*)\n" +
                "# TYPE apache_accesses_total counter\n" +
                "apache_accesses_total " + rand.nextInt(100) + "\n";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}
