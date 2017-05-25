package subtitle_model;

import java.util.ArrayList;
import java.util.HashMap;

public class Episode {
	ArrayList<Version> versions = new ArrayList<Version>();
	
	HashMap<String, String> info = new HashMap<String, String>();
	
	public Episode(String series, String season, String title, String episode){
		info.put("Series", series);
		info.put("Season", season);
		info.put("Title", title);
		info.put("Episode", episode);
		info.put("Filename", info.get("Series") + " " + info.get("Season") + "x" + info.get("Episode") + " - " + info.get("Title"));
	}
	
	public HashMap<String, String> getInfo(){
		return this.info;
	}
	
	public void addVersion(Version ver){
		versions.add(ver);
	}
	
	public ArrayList<Version> getVersions(){
		return this.versions;
	}
	
	public String toString(){
		String aux = "";
		
		aux += info.get("Filename") + "\n";
		for(Version v : versions){
			aux += v.toString();
			aux += "\n";
		}
		
		return aux;
	}
	
	public String getFilename(){
		return info.get("Filename");
	}
}
