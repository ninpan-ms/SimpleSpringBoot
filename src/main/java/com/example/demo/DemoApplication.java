package com.example.demo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.JSONException;
import org.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(DemoApplication.class, args);
		Thread.sleep(2 * 3600 * 1000);
	}

	@Scheduled(fixedRateString = "60000")
	public void printHeatBeat() throws FileNotFoundException {
		String pathToResources = "missing.log";
		InputStream input = DemoApplication.class.getResourceAsStream("/resources/" + pathToResources);

		if (input == null) {
			input = DemoApplication.class.getClassLoader().getResourceAsStream(pathToResources);
		}

		try (BufferedReader br = new BufferedReader(new InputStreamReader(input))) {
			String line;
			while ((line = br.readLine()) != null) {
				try {
					JSONObject object = new JSONObject(line);
					log.info(object.getString("message"));
				} catch (JSONException e){
					System.out.println(line);
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
