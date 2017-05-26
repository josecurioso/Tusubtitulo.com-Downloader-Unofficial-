package main;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import subtitle_model.Episode;
import java.io.IOException;

public class Logic {
	Episode episode;
	String APIKey;
	String CSX;
	String googleCSE; //separe spaces with +
	//String TVDBJwtToken;
	//String TVDBAPIKey;
	//String TVDBUserKey;
	String showLink;
	String showIdWord;
	String showName;
	int seasonSelected;
	int episodeSelected;	
	String episodeFetch;
	String showId;
	
	
	public Logic(){
		APIKey = "AIzaSyAm6QlezxEd4N2flR2QO6aVYQ3cx_K4xsw";
		CSX = "006098004307864223219:fwks5vba0co";
		googleCSE = "https://www.googleapis.com/customsearch/v1?key=" + APIKey + "&cx=" + CSX + "&fields=items(title,link)&q="; //separe spaces with +
		
		//TVDBJwtToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE0OTU2MzUzOTEsImlkIjoiVHVzdWJ0aXR1bG8gRG93bmxvYWRlciIsIm9yaWdfaWF0IjoxNDk1NTQ4OTkxLCJ1c2VyaWQiOjQ0NTYwMiwidXNlcm5hbWUiOiJqb3NlY3VyaW9zbyJ9.ibkrMQGL0uDEXQFQjnMEhdL1-WtpnpGN6W0qvOE0PrVXxXXcbxMEqDBJrvIWwtF1zE63hecZgIIkLoEUFU0ef_xDmWYsmCVfQwKbhEPQtDQmrFYY0IUcQ3lFF593n9zGdBx4Koe1vcfJKptygrLa_sbEN856GT90IyJhpVhAJqbbafK68TNKrAljQ853aAnzD1pdAb_xHMZuGV6aCYiOl-mIT_izBLkGbZI4MS-WqhXrfUlYfYbDuNsVX-bXdhch3KvittIlroAsRGSydi7mYl8jJGIKr9vI34rx0EqdbPdgb-tK8m2Jj3GAlif5i74QL5X2LpoOFyoHCKG5XyYmEA";
		//TVDBAPIKey = "7F6E9F14865ED25B";
		//TVDBUserKey = "FADADD0D4E3A4CC7";
		
		showLink = null;
		showIdWord = null;
		showName = null;	
		episodeFetch = "https://www.tusubtitulo.com/showsub.php?ushow=" + showIdWord + "&useason=" + seasonSelected + "&uepisode=" + episodeSelected;
		
	}
	
	
	public void download(String choiceIn) throws IOException{
		String[] parts = choiceIn.split("");
		int version = Integer.parseInt(parts[0]);
		int sub = Integer.parseInt(parts[1]);	    
		
		Downloader.downloadFile(episode.getVersions().get(version).getSubtitles().get(sub).getLink(), "D:\\Escritorio\\Prueba", episode.getFilename());
	}
	
	
	public int getSeasons() throws IOException{
		int seasons;
		seasons = Parser.parseSeasons(showLink);
		return seasons;
	}
	
	
	public void selectSeason(int season){
		this.seasonSelected = season;
	}
	
	
	public int getEpisodes() throws IOException{
		int episodes;
		episodes = Parser.parseEpisodes(seasonSelected, showLink.substring(33).trim());
		return episodes;
	}
	
	
	public void selectEpisode(int episode){
		this.episodeSelected = episode;
	}
	
	
	public Episode loadEpisode() throws IOException{
		episode = Parser.parseEpisodePage(episodeFetch);
		return episode;
	}
	
	
	public JSONObject searchShow(String enteredName) throws JSONException, SeriesNotFoundException{
		JSONObject searchResult = null;
		try{
	        searchResult = getProbableLink(enteredName);
			showLink = (String) searchResult.get("link");
			showIdWord = (String) searchResult.get("title");
			showName = (String) searchResult.get("name"); 
	        showId = showLink.substring(33).trim();
		}
		catch(Exception e){
			throw new SeriesNotFoundException();
		}
		return searchResult;
		
	}
	
	/**
	 * Method that checks if the selected version and subtitle exist
	 * 
	 * @param choice Version chosen
	 * @param episode Subtitle chosen
	 * @return true or false
	 */
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
	
	
	/**
	 * Method that uses a google CSX to search tusubtitulo.com for a suitable series
	 * 
	 * @param keyWord Series name entered by the user
	 * @return JSON object with the information about the first result
	 * @throws JSONException
	 */
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
