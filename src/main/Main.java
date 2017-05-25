package main;

import java.io.IOException;
import java.net.URISyntaxException;

import org.json.JSONException;

import com.omertron.thetvdbapi.TvDbException;

public class Main {

	public static void main(String[] args) throws TvDbException, IOException, JSONException, URISyntaxException {
		Logic program = new Logic();
		program.start();
	}

}
