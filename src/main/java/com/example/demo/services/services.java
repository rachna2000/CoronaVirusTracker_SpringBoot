package com.example.demo.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.model.localmodel;
@Service //will make a instance of it while building
public class services {
	private List<localmodel>model =new ArrayList<>();
	private static String url = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	@PostConstruct   //while constructing the instance this will be executed
	@Scheduled(cron= "* * 1 * * *")
	public void fetchdata() throws IOException, InterruptedException {
		List<localmodel>newmodel =new ArrayList<>();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request =HttpRequest.newBuilder().uri(URI.create(url)).build();
		HttpResponse<String>httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
		System.out.println(httpResponse.body());
		StringReader csvreader=new StringReader(httpResponse.body());
		Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(csvreader);
		for (CSVRecord record : records) {
			localmodel mod =new localmodel();
			
			mod.setState(record.get("Province/State"));
			mod.setCountry(record.get("Country/Region"));
			mod.setLatestTotal(Integer.parseInt(record.get(record.size()-1)));
			System.out.println(mod);
			newmodel.add(mod);
			
			
			
		   
		    
		}
		this.model=newmodel;
		        
	}
}
