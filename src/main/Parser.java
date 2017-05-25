package main;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import subtitle_model.Episode;
import subtitle_model.State;
import subtitle_model.Subtitle;
import subtitle_model.Version;

public class Parser {
	
	public static int parseEpisodes(int season, String showId) throws IOException {
		String link = "https://www.tusubtitulo.com/ajax_loadShow.php?show=" + showId + "&season=" + season;
		Document doc = Jsoup.connect(link).get();
		
		int aux = doc.children().get(0).children().get(1).children().size();
		return aux;
	}
	
	public static int parseSeasons(String link) throws IOException{
		Document doc = Jsoup.connect(link).get();
		
		Element aux = doc.getElementById("content");
		Elements temp = aux.child(1).child(0).child(1).child(3).child(0).getAllElements();
		return temp.size()-1;
		
	}
	
	public static Episode parseEpisodePage(String link) throws IOException{

		System.out.println(link);
		String title;
		String season;
		String episode;
		String series;
		Episode currentEpisode;
		
		
		Document doc = Jsoup.connect(link).get();
		Element content = doc.body().getElementById("content");
		Element cabecera = content.getElementById("cabecera-subtitulo");
		String info = cabecera.text().substring(14);
		title = info.split("-")[1];
		String[] med = info.split("-")[0].split(" ");
		season = med[med.length-1].split("x")[0];
		episode = med[med.length-1].split("x")[1];		
		med[med.length-1] = "";
		series = "";
		for(String i : med){
			series += " ";
			series += i;
		}
		title = title.trim();
		System.out.println(title);
		series = series.trim();
		season = season.trim();
		episode = episode.trim();

//		System.out.println(title);
//		System.out.println(season);
//		System.out.println(episode);
//		System.out.println(series);
		
		currentEpisode = new Episode(series, season, title, episode);
		
		
		Elements versions = content.getElementsByClass("ssdiv");
		Elements versions2 = new Elements();
		for(Element e : versions){
			if(!e.id().equals("ssheader")){
				versions2.add(e);
			}
		}
		
		for(Element e : versions2){
			String versionName;
			Elements aux = e.getElementsByClass("bubble");
			versionName = aux.get(0).getElementsByClass("title-sub").text();
			Version temp = new Version(versionName);
			
			Elements subtitleEntries = e.getElementsByClass("sslist");
			for(Element i : subtitleEntries){
				String subLink = "";
				String lang;
				State state;
				
				
				Elements in = i.getElementsByClass("li-idioma");
				lang = in.get(0).text();
				
				String stateText = in.get(0).nextElementSibling().text().trim();
				if(stateText.equals("Completado")){
					state = State.COMPLETE;
				}
				else if(stateText.contains("%")){
					state = State.TRANSLATING;
				}
				else{
					state = State.REVISION;
				}
				
				if(state == State.COMPLETE){
					Elements temp2 = i.children().get(2).children();
					Element actualLink = temp2.get(temp2.size()-2);
					String linkText = actualLink.attr("href");
					subLink = "https://www.tusubtitulo.com" + "/" + linkText;
				}
				
				Subtitle sub = new Subtitle(subLink, lang, state);
				temp.addSubtitle(sub);
			}
			currentEpisode.addVersion(temp);
			
		}
		
		return currentEpisode;
		
	}
}
