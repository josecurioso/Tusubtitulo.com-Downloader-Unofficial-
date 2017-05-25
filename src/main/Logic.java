package main;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.omertron.thetvdbapi.TheTVDBApi;
import com.omertron.thetvdbapi.TvDbException;
import com.omertron.thetvdbapi.model.Series;
import com.zenkey.net.prowser.Prowser;
import com.zenkey.net.prowser.Request;
import com.zenkey.net.prowser.Response;
import com.zenkey.net.prowser.Tab;

import subtitle_model.Episode;
import subtitle_model.State;
import subtitle_model.Subtitle;
import subtitle_model.Version;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class Logic {
	
	String APIKey = "AIzaSyAm6QlezxEd4N2flR2QO6aVYQ3cx_K4xsw";
	String CSX = "006098004307864223219:fwks5vba0co";

	String googleCSE = "https://www.googleapis.com/customsearch/v1?key=" + APIKey + "&cx=" + CSX + "&fields=items(title,link)&q="; //separe spaces with +
		
	String TVDBJwtToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE0OTU2MzUzOTEsImlkIjoiVHVzdWJ0aXR1bG8gRG93bmxvYWRlciIsIm9yaWdfaWF0IjoxNDk1NTQ4OTkxLCJ1c2VyaWQiOjQ0NTYwMiwidXNlcm5hbWUiOiJqb3NlY3VyaW9zbyJ9.ibkrMQGL0uDEXQFQjnMEhdL1-WtpnpGN6W0qvOE0PrVXxXXcbxMEqDBJrvIWwtF1zE63hecZgIIkLoEUFU0ef_xDmWYsmCVfQwKbhEPQtDQmrFYY0IUcQ3lFF593n9zGdBx4Koe1vcfJKptygrLa_sbEN856GT90IyJhpVhAJqbbafK68TNKrAljQ853aAnzD1pdAb_xHMZuGV6aCYiOl-mIT_izBLkGbZI4MS-WqhXrfUlYfYbDuNsVX-bXdhch3KvittIlroAsRGSydi7mYl8jJGIKr9vI34rx0EqdbPdgb-tK8m2Jj3GAlif5i74QL5X2LpoOFyoHCKG5XyYmEA";
	String TVDBAPIKey = "7F6E9F14865ED25B";
	String TVDBUserKey = "FADADD0D4E3A4CC7";
	
	

	public void start() throws IOException, JSONException, TvDbException, URISyntaxException{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
		System.out.print("Enter series name: ");
        String enteredName = br.readLine();
        
        
        JSONObject searchResult = getProbableLink(enteredName);
		String showLink = (String) searchResult.get("link");
		String showIdWord = (String) searchResult.get("title");
		String showName = (String) searchResult.get("name"); 
		String episodeFetch;
//		HashMap<String, String> TVDBInfo = getTVDBInfo(showName);
		int seasonNum = Parser.parseSeasons(showLink);
		int seasonSelected;
		int episodeSelected;
		
		
        System.out.print("Choose season (1-" + seasonNum + "): ");
        String seasonIn = br.readLine();
        seasonSelected = Integer.parseInt(seasonIn);
        
        String showId = showLink.substring(33).trim();
		int episodeNum = Parser.parseEpisodes(seasonSelected, showLink.substring(33).trim());
		
		
        System.out.print("Choose episode (1-" + episodeNum + "): ");
        String episodeIn = br.readLine();
        episodeSelected = Integer.parseInt(episodeIn);
        
        
    	episodeFetch = "https://www.tusubtitulo.com/showsub.php?ushow=" + showIdWord + "&useason=" + seasonSelected + "&uepisode=" + episodeSelected;
		
		Episode episode = Parser.parseEpisodePage(episodeFetch);
		System.out.println(episode.toString());
		
		
		String choiceIn = "";
		do{
			System.out.print("Specify a version and subtitle (11 being version 1 sub 1) : ");
	    	choiceIn = br.readLine();
		}
		while(!isChoiceValid(choiceIn, episode));
		

		String[] parts = choiceIn.split("");
		int version = Integer.parseInt(parts[0]);
		int sub = Integer.parseInt(parts[1]);	    
		
		Downloader.downloadFile(episode.getVersions().get(version).getSubtitles().get(sub).getLink(), "D:\\Escritorio\\Prueba", episode.getFilename());
		
		
	}
	
	public boolean isChoiceValid(String choice, Episode episode){
		try{
			String[] parts = choice.split("");
			int version = Integer.parseInt(parts[0]);
			int sub = Integer.parseInt(parts[1]);
			
			episode.getVersions().get(version).getSubtitles().get(sub);
			return true;			
		}
		catch(Exception e){
			return false;
		}
		
	}
	
	public JSONObject getProbableLink(String keyWord) throws JSONException{
		String searchTerm = keyWord.replace(" ", "+");
		JSONObject obj = JSON.GET(googleCSE + searchTerm);
		JSONArray aux = obj.getJSONArray("items");
		JSONObject temp = (JSONObject) aux.get(0);
		String title = (String) temp.get("title");
		temp.put("name", title.substring(0, title.length()-14));
		temp.put("title", title.substring(0, title.length()-14).replace(" ", "-").toLowerCase());
		
		return temp;
	}
	
	/*  DEPRECATED
	public HashMap<String, String> getTVDBInfo(String showName){
		HashMap<String, String> info = new HashMap<String, String>();
		
		
		TheTVDBApi tvDB = new TheTVDBApi(TVDBAPIKey);
		List<Series> results = tvDB.searchSeries(showName, "en");
		
		
		Series series = tvDB.getSeries(results.get(0).getId(), "en");
		String seriesId = series.getId();
		List<com.omertron.thetvdbapi.model.Episode> episodes = tvDB.getAllEpisodes(seriesId, "en");		
		System.out.println(seriesId);
		
		
		
		return info;
	}
	*/
	
	
}
