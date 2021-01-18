package com.tamil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Editor {
	private static final String INPUT_FILE = "E:\\OneDrive\\M3U\\OneDrive.m3u";
	private static final String OUTPUT_FILE = "E:\\OneDrive\\M3U\\Playlist.m3u";
	private static final String IP = "127.0.0.1";
	private static final int PORT = 8080;
	private static final String PROTOCOL = "http";
	private static final String LOCAL_FOLDER = "E:\\OneDrive\\Music";
	
	public static void main(String[] args) {
		List<String> lines = readFile(INPUT_FILE);
		List<String> convertedLines = convertWintoHttp(lines);
		writeFile(convertedLines, OUTPUT_FILE);
	}
	
	private static void writeFile(List<String> convertedLines, String outputFile) {
		try {
			FileWriter fileWriter = new FileWriter(new File(outputFile));
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			for(String convertedLine : convertedLines) {
				bufferedWriter.append(convertedLine);
				bufferedWriter.append("\n");
			}
			bufferedWriter.flush();
			bufferedWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static List<String> convertWintoHttp(List<String> lines) {
		List<String> convertedLines = new ArrayList<String>();
		
		for(String line : lines) {
			if(line.startsWith(LOCAL_FOLDER)) {
				
				line = line.replace(LOCAL_FOLDER, "");
				line = line.replace("\\", "/");
				
				try {
					URI uri = new URI(PROTOCOL, null, IP, PORT, line, null, null);
					line = uri.toString();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}
			System.out.println(line);
			convertedLines.add(line);
		}
		return convertedLines;
	}

	private static List<String> readFile(String fileName) {
		List<String> lines = new ArrayList<String>();
		FileReader fileReader;
		try {
			fileReader = new FileReader(new File(fileName));
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String s;
			while((s = bufferedReader.readLine()) != null) {
				lines.add(s);
			}
			bufferedReader.close();
			fileReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
}
