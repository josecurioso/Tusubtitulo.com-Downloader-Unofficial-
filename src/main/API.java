package main;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import subtitle_model.Episode;
import subtitle_model.Lang;
import subtitle_model.Subtitle;
import subtitle_model.Version;

import java.io.IOException;
import java.util.ArrayList;

public class API {
	String APIKey;
	String CSX;
	String googleCSE;
	//String TVDBJwtToken;
	//String TVDBAPIKey;
	//String TVDBUserKey;
	String downloadPath;
	
	
	public API(){
		//Google custom search
		APIKey = "AIzaSyAm6QlezxEd4N2flR2QO6aVYQ3cx_K4xsw";
		CSX = "006098004307864223219:fwks5vba0co";
		googleCSE = "https://www.googleapis.com/customsearch/v1?key=" + APIKey + "&cx=" + CSX + "&fields=items(title,link)&q="; //separe spaces with +
		
		//TVDB API
		//TVDBJwtToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE0OTU2MzUzOTEsImlkIjoiVHVzdWJ0aXR1bG8gRG93bmxvYWRlciIsIm9yaWdfaWF0IjoxNDk1NTQ4OTkxLCJ1c2VyaWQiOjQ0NTYwMiwidXNlcm5hbWUiOiJqb3NlY3VyaW9zbyJ9.ibkrMQGL0uDEXQFQjnMEhdL1-WtpnpGN6W0qvOE0PrVXxXXcbxMEqDBJrvIWwtF1zE63hecZgIIkLoEUFU0ef_xDmWYsmCVfQwKbhEPQtDQmrFYY0IUcQ3lFF593n9zGdBx4Koe1vcfJKptygrLa_sbEN856GT90IyJhpVhAJqbbafK68TNKrAljQ853aAnzD1pdAb_xHMZuGV6aCYiOl-mIT_izBLkGbZI4MS-WqhXrfUlYfYbDuNsVX-bXdhch3KvittIlroAsRGSydi7mYl8jJGIKr9vI34rx0EqdbPdgb-tK8m2Jj3GAlif5i74QL5X2LpoOFyoHCKG5XyYmEA";
		//TVDBAPIKey = "7F6E9F14865ED25B";
		//TVDBUserKey = "FADADD0D4E3A4CC7";

		//Download path
		String testPath = "D:\\Escritorio\\Prueba";
		downloadPath = testPath;
	}
	
	
	/**
	 * Method that uses a google CSX to search tusubtitulo.com for a suitable series
	 * 
	 * @param keyWord Series name entered by the user
	 * @return JSON object with the information about the first result
	 * @throws JSONException
	 */
	private JSONObject getProbableLink(String keyWord) throws JSONException{
		String searchTerm = keyWord.replace(" ", "+");
		JSONObject obj = JSON.GET(googleCSE + searchTerm);
		JSONArray aux = obj.getJSONArray("items");
		JSONObject temp = (JSONObject) aux.get(0);
		String title = (String) temp.get("title");
		temp.put("name", title.substring(0, title.length()-14));
		temp.put("title", title.substring(0, title.length()-14).replace(" ", "-").toLowerCase());
		
		return temp;
	}
	
	
	/**
	 * Performs a google custom search on tusubtitulo.com for a given name. The result of the search is stored as well as returned.
	 * Throws SeriesNotFoundException if the search yields 0 results.
	 *
	 * @param enteredName, search string to pass on to google
	 * @return finalResult, a JSONObject containing the first search result and the infor of that series
	 * @throws JSONException
	 * @throws SeriesNotFoundException
	 */
	public JSONObject searchShow(String enteredName) throws JSONException, SeriesNotFoundException{
		JSONObject searchResult = null;
		JSONObject finalResult = new JSONObject();
		try{
	        searchResult = getProbableLink(enteredName);
	        finalResult.put("showLink", (String) searchResult.get("link"));
	        finalResult.put("showIdWord", (String) searchResult.get("title"));
	        finalResult.put("showName", (String) searchResult.get("name"));
	        finalResult.put("showId", finalResult.getString("showLink").substring(33).trim());
		}
		catch(Exception e){
			throw new SeriesNotFoundException();
		}
		return finalResult;
		
	}
	
	
	/**
	 * Method that checks a show page to see which seasons are available
	 * 
	 * @param showLink, link to the show page to get the seasons from
	 * @return seasons, ArrayList containing the available seasons
	 * @throws IOException
	 */
	public ArrayList<Integer> getSeasons(String showLink) throws IOException{
		ArrayList<Integer> seasons;
		seasons = Parser.parseSeasons(showLink);
		return seasons;
	}
	
	
	/**
	 * Method that returns the amount of episodes in the currently selected season
	 * 
	 * @param season, season we want to check
	 * @param showId, id of the show which season we want to check
	 * @return episodes, amount of episodes
	 * @throws IOException
	 * @throws JSONException 
	 */
	public JSONObject getEpisodes(int season, String showId) throws IOException, JSONException{
		JSONObject episodes;
		episodes = Parser.parseEpisodes(season, showId);
		return episodes;
	}
	
	
	/**
	 * Method that loads an episode page into the model by calling the parser
	 * 
	 * @param showIdWord, id of the show we want to load from
	 * @param season, season we want to load from
	 * @param episode, episode we want to load
	 * @return episode, an Episode object with all the information and subtitles of that episode
	 * @throws IOException
	 */
	public Episode loadEpisode(String showIdWord, int season, int episode) throws IOException{
		String episodeFetch = "https://www.tusubtitulo.com/showsub.php?ushow=" + showIdWord + "&useason=" + season + "&uepisode=" + episode;
		return Parser.parseEpisodePage(episodeFetch);
	}
	
	
	/**
	 * Method that loads an episode page into the model by calling the parser
	 * 
	 * @param episodeFetch, direct url to the episode page
	 * @return episode, an Episode object with all the information and subtitles of that episode
	 * @throws IOException
	 */
	public Episode loadEpisode(String episodeFetch) throws IOException{
		return Parser.parseEpisodePage(episodeFetch);
	}
	
	
	/**
	 * Method that downloads a subtitle given the version index and the subtitle index
	 * 
	 * @param version, version of preference
	 * @param sub, sub we want to download inside the version
	 * @param episode, Episode object to download from
	 * @throws IOException
	 */
	public void download(int version, int sub, Episode episode) throws IOException{ 
		Thread t = new Thread(new Downloader(episode.getVersions().get(version).getSubtitles().get(sub).getLink(), downloadPath, episode.getFilename()));
		t.start();
	}
	
	
	/**
	 * Method that downloads a subtitle from the currently selected episode based on a code like "[version]-[subtitle]"
	 * 
	 * @param version, version of preference
	 * @param sub, sub we want to download inside the version
	 * @param episode, Episode object to download from
	 * @throws IOException
	 */
	public void download(Subtitle sub, Episode episode) throws IOException{
		Thread t = new Thread(new Downloader(sub.getLink(), downloadPath, episode.getFilename()));
		t.start();
	}
	
	
	/**
	 * Method that looks for the best Subtitle match in an Episode given a language, if not found with that language, proceeds to find for all the others by priority
	 * 
	 * @param lang, preferred language
	 * @param episode, Episode object
	 * @return
	 */
	public Subtitle getClosestMatch(Lang lang, Episode episode){
		Subtitle aux = null; 
		for(Version v : episode.getVersions()){
			for(Subtitle s : v.getSubtitles()){
				if(s.getLang() == lang){
					return s;
				}
			}
		}
		getClosestMatch(episode);
		return aux;
	}
	
	
	/**
	 * Method that looks for the best Subtitle match in an Episode given a language, if not found with that language, proceeds to find for all the others by priority
	 * 
	 * @param lang, preferred language
	 * @param episode, Episode object
	 * @return
	 */
	public Subtitle getClosestMatch(Episode episode){
		Subtitle aux = null; 
		for(int i=1; i<4; i++){
			Lang lang = Lang.getByPriority(i);
			for(Version v : episode.getVersions()){
				for(Subtitle s : v.getSubtitles()){
					if(s.getLang() == lang){
						return s;
					}
				}
			}
		}
		return aux;
	}
}
