package parsing;

import java.io.BufferedReader;
import java.io.IOException;

import com.google.gson.Gson;

import objects.DataContainer;

public class Parser {
	private DataContainer data;
	public static Gson gson = new Gson();

	public Parser(BufferedReader br) throws IOException {
		data = gson.fromJson(br, DataContainer.class);
	}

	public DataContainer getData() {
		return data;
	}
}
