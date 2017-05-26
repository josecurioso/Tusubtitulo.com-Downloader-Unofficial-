package subtitle_model;

import java.util.ArrayList;
import java.util.HashMap;

public class Episode {
	ArrayList<Version> versions = new ArrayList<Version>();
	String series;
	String season;
	String episode;
	String title;
	String filename;
	
	HashMap<String, String> info = new HashMap<String, String>();
	
	public Episode(String series, String season, String title, String episode){
		this.series = series;
		this.season = season;
		this.episode = episode;
		this.title = title;
		this.filename = series + " " + season + "x" + episode + " - " + title;
	}
	
	public void addVersion(Version ver){
		versions.add(ver);
	}
	
	public ArrayList<Version> getVersions(){
		return this.versions;
	}
	
	public String getShow(){
		return this.series;
	}
	
	public String getSeason(){
		return this.season;
	}
	
	public String getEpisode(){
		return this.episode;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String getFilename(){
		return this.filename;
	}
	
	public String toString(){
		String aux = "";
		
		aux += this.filename + "\n";
		for(Version v : versions){
			aux += v.toString();
			aux += "\n";
		}
		
		return aux;
	}
}
