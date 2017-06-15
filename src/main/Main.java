package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import subtitle_model.Episode;

public class Main {

	public static void main(String[] args) throws IOException, JSONException {
		API api = new API();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		JSONObject searchResult = null; 
        
		//Get the series name
        boolean success = false;
        do{
	        try{
	        	System.out.print("Enter series name: ");
	            String enteredName = br.readLine();
	            searchResult = api.searchShow(enteredName);
	            success = true;
	        }
	        catch(SeriesNotFoundException e){
				System.out.println("The series you entered is not in our database, sorry for that");
	        }
        }
        while(!success);        
        
        //Get the season
        ArrayList<Integer> seasonNum = api.getSeasons((String) searchResult.getString("showLink"));
        int selectedSeason = getSeason(seasonNum, br);
        
		//Get the episode
        int episodeNum = api.getEpisodes(selectedSeason, searchResult.getString("showId"));
        int selectedEpisode = getEpisode(episodeNum, br);
		
		//Load the episode
		Episode episode = api.loadEpisode(searchResult.getString("showIdWord"), selectedSeason, selectedEpisode);

		//Print all the info
		System.out.println(episode.toString());
		
		//Get the final choice of version and subtitle
		String choice = getChoice(episode, br);

		String[] parts = choice.split("");
		int version = Integer.parseInt(parts[0]);
		int sub = Integer.parseInt(parts[1]);	
		
		//Download the subtitle
		api.download(version, sub, episode);
		
		
		
	}
		
	/**
	 * @author Carlos Manrique Enguita
	 * 
	 * User input controller for the seasons
	 * 
	 * @param seasons An array with the seasons we have
	 * @param br A way of input
	 * @return seasonSelected The season selected
	 * @throws IOException DAAAAAMN IT
	 */
	private static int getSeason(ArrayList<Integer> seasons, BufferedReader br) throws IOException{
		boolean isValid = false;
		int seasonSelected = 0;
		
		String result = "";
		
		for(Integer i : seasons){
			result += i;
			if(seasons.indexOf(i)<seasons.size()-1){
				result += ", ";
			}
		}
		
		while(!isValid){
			
			System.out.print("Choose season (" + result + "): ");
			String seasonIn = br.readLine();
			seasonSelected = Integer.parseInt(seasonIn);
			
			if(seasons.contains(seasonSelected)){
				isValid = true;}
			else{
				System.out.println("Please enter a valid season");}
        }
		return seasonSelected;
	}
	
	
	/**
	 * @author Carlos Manrique Enguita
	 * 
	 * User input controller for the episodes
	 * 
	 * @param maxEpisodes The maximum number of seasons we have
	 * @param br A way of input
	 * @return episodeSelected The season selected
	 * @throws IOException DAAAAAMN IT
	 */
	public static int getEpisode(int maxEpisodes, BufferedReader br) throws IOException{
		boolean isValid = false;
		int episodeSelected = 0;
		
		while(!isValid){
			
			System.out.print("Choose episode (1-" + maxEpisodes + "): ");
			String episodeIn = br.readLine();
			episodeSelected = Integer.parseInt(episodeIn);
			
			if(episodeSelected>1 && episodeSelected <= maxEpisodes){
				isValid = true;}
			else{
				System.out.println("Please enter a valid episode");}
        }
		return episodeSelected;
	}
	
	
	/**
	 * @author Jose Manuel Estrada-Nora Muñoz
	 * 
	 * User input controller for the version/subtitle
	 * 
	 * @param episode Episode object to check
	 * @param br A way of input
	 * @return choiceIn The version/subtitle selected
	 * @throws IOException DAAAAAMN IT
	 */
	public static String getChoice(Episode episode, BufferedReader br) throws IOException{
		String choiceIn = "";
		Boolean state;
		
		do{
			System.out.print("Specify a version and subtitle (11 being version 1 sub 1) : ");
	    	choiceIn = br.readLine();
	    	
	    	try{
				String[] parts = choiceIn.split("");
				int version = Integer.parseInt(parts[0]);
				int sub = Integer.parseInt(parts[1]);
				
				episode.getVersions().get(version).getSubtitles().get(sub);
				state = true;			
			}
			catch(Exception e){
				state = false;
			}
		}
		while(!state);
		return choiceIn;
	}
	

}
