package gui;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import main.*;
import subtitle_model.Episode;
import subtitle_model.Lang;

import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Font;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JComboBox;

public class TuSubtitulo {

	private JFrame frmTusubtitulo;
	private JTextField search_bar;
	private JLabel season_label;
	JComboBox season_combo;
	JComboBox episode_combo;
	JButton download_button;
	JRadioButton download_all;
	JLabel series_label;
	JLabel episode_label;
	JButton search_button;
	API api;
	JSONObject searchResult;
	JSONObject episodes;
	int season;
	int episode;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TuSubtitulo window = new TuSubtitulo();
					window.frmTusubtitulo.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TuSubtitulo() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		api = new API();
		frmTusubtitulo = new JFrame();
		frmTusubtitulo.setTitle("TuSubtitulo");
		frmTusubtitulo.setFont(new Font("Droid Sans", Font.PLAIN, 19));
		frmTusubtitulo.setIconImage(Toolkit.getDefaultToolkit().getImage("/home/carlos/Repositorios/Tusubtitulo.com-Downloader-Unofficial-/res/icon.png"));
		frmTusubtitulo.setBounds(100, 100, 500, 370);
		frmTusubtitulo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		series_label = new JLabel("Serie");
		series_label.setBounds(45, 57, 96, 14);
		
		search_button = new JButton("Buscar");
		search_button.setBounds(387, 53, 65, 23);
		search_button.addActionListener(this::search_button);
		
		search_bar = new JTextField();
		search_bar.setBounds(151, 54, 226, 20);
		search_bar.setColumns(10);
		
		season_label = new JLabel("Temporada");
		season_label.setBounds(45, 97, 93, 14);
		season_label.setToolTipText("");
				
		episode_label = new JLabel("Episodio");
		episode_label.setBounds(45, 135, 93, 14);
		
		download_all = new JRadioButton("Descargar todos");
		download_all.setBounds(347, 154, 105, 23);
		
		download_button = new JButton("Descargas");
		download_button.addActionListener(e -> {
			try {
				downloadButton(e);
			} catch (JSONException | IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		});
		download_button.setBounds(214, 195, 83, 23);
		
		frmTusubtitulo.getContentPane().setLayout(null);
		frmTusubtitulo.getContentPane().add(download_all);
		frmTusubtitulo.getContentPane().add(series_label);
		frmTusubtitulo.getContentPane().add(search_bar);
		frmTusubtitulo.getContentPane().add(search_button);
		frmTusubtitulo.getContentPane().add(season_label);
		frmTusubtitulo.getContentPane().add(episode_label);
		
		season_combo = new JComboBox();
		season_combo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox spinner = (JComboBox) e.getSource();
				try {
					updateEpisodes(spinner.getSelectedItem());
				} catch (NumberFormatException | IOException | JSONException e1) {
					e1.printStackTrace();
				}
			}
		});
		season_combo.setBounds(185, 94, 267, 20);
		frmTusubtitulo.getContentPane().add(season_combo);
		
		episode_combo = new JComboBox();
		episode_combo.setBounds(185, 132, 267, 20);
		frmTusubtitulo.getContentPane().add(episode_combo);
		frmTusubtitulo.getContentPane().add(download_button);
	}
	
	public void search_button(final ActionEvent e){
		try{
			searchResult = api.searchShow(search_bar.getText());
			search_bar.setText(searchResult.getString("showName"));
			updateSeasons();
		}
		catch(Exception i){
			System.out.println(i);
			System.out.println("Search failed");
		}
	}
	
	public void updateSeasons() throws IOException, JSONException{
		ArrayList<Integer> seasons = api.getSeasons(searchResult.getString("showLink"));
		for(Integer i : seasons){
			season_combo.addItem(i);
		}
	}
	
	public void updateEpisodes(Object content) throws NumberFormatException, IOException, JSONException{
		String season = "";
		season += content;
		episodes = api.getEpisodes(Integer.parseInt(season), searchResult.getString("showId"));
		JSONArray array = (JSONArray) episodes.get("titles");
		ArrayList<String> episodes = new ArrayList<String>();
		episode_combo.removeAllItems();
		for(int i=0; i<array.length(); i++){
			episodes.add(array.getString(i));
			episode_combo.addItem(array.getString(i));
		}
	}
	
	public void downloadButton(final ActionEvent e) throws JSONException, IOException{
		if(download_all.isSelected()){
			JSONArray array = (JSONArray) episodes.get("titles");
			ArrayList<String> episodesTitles = new ArrayList<String>();
			for(int i=0; i<array.length(); i++){
				episodesTitles.add(array.getString(i));
			}
			for(String i: episodesTitles){
				JSONObject relations = (JSONObject) episodes.get("relations");
				String link = relations.getString(i);
				Episode episode = api.loadEpisode(link);
				api.download(api.getClosestMatch(Lang.es_ESP, episode), episode);
			}
		}
		else{
			JSONObject relations = (JSONObject) episodes.get("relations");
			String link = relations.getString((String) episode_combo.getSelectedItem());
			Episode episode = api.loadEpisode(link);
			api.download(api.getClosestMatch(Lang.es_ESP, episode), episode);
		}
	}
}
