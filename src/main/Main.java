package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.json.JSONException;
import subtitle_model.Episode;

public class Main {

	public static void main(String[] args) throws IOException, JSONException {
		Logic program = new Logic();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        		
        String success = "";
        do{
	        try{
	        	System.out.print("Enter series name: ");
	            String enteredName = br.readLine();
	            program.searchShow(enteredName);
	        }
	        catch(SeriesNotFoundException e){
				System.out.println("The series you entered is not in our database, sorry for that");
	        }
        }
        while(success.equals(""));
        
        int seasonNum = program.getSeasons();
        int selectedSeason = getSeason(seasonNum, br);
		program.selectSeason(selectedSeason);
        

        int episodeNum = program.getEpisodes();
        int selectedEpisode = getEpisode(episodeNum, br);
		program.selectEpisode(selectedEpisode);
		
		
		Episode episode = program.loadEpisode();

		System.out.println(episode.toString());
		
		
		String choiceIn = "";
		do{
			System.out.print("Specify a version and subtitle (11 being version 1 sub 1) : ");
	    	choiceIn = br.readLine();
		}
		while(!program.isChoiceValid(choiceIn, episode));
		
		
		
	}
		
	/**
	 * @author Carlos Manrique Enguita
	 * 
	 * User input controller for the seasons
	 * 
	 * @param maxSeasons The maximum number of seasons we have
	 * @param br A way of input
	 * @return seasonSelected The season selected
	 * @throws IOException DAAAAAMN IT
	 */
	private static int getSeason(int maxSeasons, BufferedReader br) throws IOException{
		boolean isValid = false;
		int seasonSelected = 0;
		
		while(!isValid){
			
			System.out.print("Choose season (1-" + maxSeasons + "): ");
			String seasonIn = br.readLine();
			seasonSelected = Integer.parseInt(seasonIn);
			
			if(seasonSelected>1 && seasonSelected <= maxSeasons){
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

}
