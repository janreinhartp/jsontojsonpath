package com.reinhart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ResourceUtils;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;

@SpringBootApplication
public class JsonToJsonpathApplication {

	private static final Logger log = LoggerFactory.getLogger(JsonToJsonpathApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(JsonToJsonpathApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			ShowJsonPath(readJsonFile());
			// log.info(readJsonFile());
		};
	}

	public String readJsonFile() {
		try {

			File file = ResourceUtils.getFile("classpath:json/data.json");
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			StringBuilder builder = new StringBuilder();

			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}

			String jsonContent = builder.toString();

			return jsonContent;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public void ShowJsonPath(String json) {
		String jsonData = json;

		log.info(jsonData);
		Configuration conf = Configuration.builder().options(Option.AS_PATH_LIST).build();

		List<String> allPaths = JsonPath.using(conf).parse(json).read("$..*");

		// List<Map<String, Object>> books = JsonPath.parse(jsonData).read("$[?].name");

		allPaths.forEach((temp) -> {
			String jsonpath = temp;

			Object dataObject = JsonPath.parse(jsonData).read(jsonpath);
			String value = dataObject.toString();
			log.info("JsonPath : " + jsonpath + "  " + "Value : " + value);
		});

	}

}
