package subtitle_model;

import java.util.ArrayList;

public class Version {
	ArrayList<Subtitle> subtitles = new ArrayList<Subtitle>();
	
	String versionName;
	
	public Version(String versionName){
		this.versionName = versionName;
	}
	
	public void addSubtitle(Subtitle sub){
		subtitles.add(sub);
	}
	
	public ArrayList<Subtitle> getSubtitles(){
		return this.subtitles;
	}
	
	public String toString(){
		String aux = "";
		aux += this.versionName + ":\n";
		for(Subtitle s : subtitles){
			aux += "   Subtitle " + subtitles.indexOf(s) + ":" + "\n";
			aux += s.toString();
			aux += "\n";
		}
		return aux;
		
	}
}
