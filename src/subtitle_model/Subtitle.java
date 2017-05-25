package subtitle_model;


public class Subtitle {
	String link;
	String lang;
	State state;
	
	public Subtitle(String link, String lang, State state){
		this.link = link;
		this.lang = lang;
		this.state = state;
	}
	
	public String getLang(){
		return this.lang;
	}
	
	public String getLink(){
		return this.link;
	}
	
	public State getCompleted(){
		return this.state;
	}
	
	public String toString(){
	String aux = "";
	
	aux += "     Lang: " + this.lang + "\n";
	aux += "     State: " + State.toString(state) + "\n";
	aux += "     Link: " + this.link;
	
	return aux;
	}

}
