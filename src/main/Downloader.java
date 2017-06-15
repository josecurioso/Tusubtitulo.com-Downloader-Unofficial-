package main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader implements Runnable {
	
	String fileURL;
	String saveDir;
	String filename;
	
	public Downloader(String fileURL, String saveDir, String filename){
		this.fileURL = fileURL;
		this.saveDir = saveDir;
		this.filename = filename;
	}
	
	/**
	 * Downloads a file from a URL
	 * 
	 * @param fileURL HTTP URL of the file to be downloaded
	 * @param saveDir path of the directory to save the file
	 * @param fileName desired name for the file
	 * @throws IOException
	 */	
	public static void downloadFile(String fileURL, String saveDir, String fileName) throws IOException {
		String saveFilePath = saveDir + File.separator + fileName + ".srt";
		
		HttpURLConnection connection = null;

		try {
			// Create connection
			URL url = new URL(fileURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			connection.setRequestProperty("Accept-Encoding", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Accept-Language", "es-US,en-US;q=0.8,en;q=0.6,de-DE;q=0.4,de;q=0.2,es-419;q=0.2,es;q=0.2");
			connection.setRequestProperty("Connection", "keep-alive");
			connection.setRequestProperty("Host", "www.tusubtitulo.com");
			connection.setRequestProperty("Referer", "https://www.tusubtitulo.com/");
			connection.setRequestProperty("Upgrade-Insecure-Requests", "1");
			connection.setRequestProperty("Cookie", "PHPSESSID=b1dcbb5afca1783c5d9b0def2828e7a9");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");

			connection.setUseCaches(false);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes("");
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder(); // or StringBuffer if
															// Java version 5+
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			
			
			FileWriter writer = new FileWriter(saveFilePath);
			
			writer.write(response.toString());
			writer.close();
			
			System.out.println();
		} catch (Exception e) {
			System.out.println("Download error");
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		
		
	}

	@Override
	public void run() {
		try {
			downloadFile(this.fileURL, this.saveDir, this.filename);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}