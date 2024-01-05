package adg;

import java.io.*;
import java.net.*;

import bri.Service;

public class ServiceUpperText implements Service {
	
	private final Socket client;
	
	public ServiceUpperText() {
		this.client = null;
	}
	
	public ServiceUpperText(Socket socket) {
		client = socket;
	}
@Override
	public void run() {
		try {BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);

			out.println("Tapez un texte a mettre en majuscule");
		
			String line = in.readLine();		
	
			String invLine = new String (line.toUpperCase());
			
			out.println(invLine);
			client.close();
		}
		catch (IOException e) {
		}
	}
	
	protected void finalize() throws Throwable {
		 client.close(); 
	}

	public static String toStringue() {
		return "Met le texte en majuscule";
	}
}
