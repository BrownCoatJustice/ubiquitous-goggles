package main;

import java.net.HttpURLConnection;

import java.net.URL;
import java.util.ArrayList;

import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class DBInteracter {
	/*
	 * Program to interact with Google count DB through Google count API
	 * 
	 * @author Habis Muhammed
	 */

	public static String inline = "";

	public static void main(String[] args) throws Exception {
		// inline will store the JSON data streamed in string format

		// Get the bar code..code.
		System.out.println(
				"Type ISBN Code (The code above the bar code, usually written as: ISBN-10/13 xxxx-xxxxx-xxxx)");

		Scanner bar = new Scanner(System.in);
		long code = bar.nextLong();
		System.out.println(code);
		String setURL = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + code;
		bar.close();
		contact(setURL);
	}

	private static void contact(String getURL) throws Exception {
		// Actual API Communication
		URL url = new URL(getURL);

		// Type casting into HTTP URL Connection to get em gud features.
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.connect();
		int responsecode = conn.getResponseCode();

		if (responsecode != 200) {
			// 200 means success
			throw new RuntimeException("HttpResponseCode: " + responsecode);
		} else {
			Scanner sc = new Scanner(url.openStream());
			while (sc.hasNext()) {
				inline += sc.nextLine();
			}
			System.out.println("\nJSON data in string format");
			System.out.println(inline);
			sc.close();

			JSONParser parse = new JSONParser();
			JSONObject data = (JSONObject) parse.parse(inline);
			long count = (long) data.get("totalItems");

			try {
				Runtime.getRuntime().exec("clear");
			} catch (Exception e) {
				Runtime.getRuntime().exec("cmd cls");
			}

			System.out.println("********************************");
			System.out.println("There is/are " + count + " book(s) matching your ISBN.");
			if (count > 1 || count <= 0) {
				System.out.println("There are multiple books matching your ISBN Code!");
				System.exit(1);
			}
			System.out.println("Getting book info...");
			JSONArray items = (JSONArray) data.get("items");

			for (Object item : items) {
				JSONObject jsonItem = (JSONObject) item;
				String type = (String) jsonItem.get("kind");
				JSONObject volumeInfo = (JSONObject) jsonItem.get("volumeInfo");
				JSONArray authors = (JSONArray) volumeInfo.get("authors");
				
				for (int i = 0; i < authors.size(); i++) {
					ArrayList<String> authorList = new ArrayList<>();
					authorList.add((String) authors.get(i));
					for (int j = 0; j < authorList.size(); j++) {
						System.out.println(authorList.get(j));
					}
					
				}
				
				System.out.println(volumeInfo.get("title"));
				System.out.println(type);
			}
		}
	}

}
