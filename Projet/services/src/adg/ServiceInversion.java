package adg;

import java.io.*;
import java.net.*;

import bri.Service;

public class ServiceInversion implements Service {
	
	private final Socket client;
	
	public ServiceInversion() {
		this.client = null;
	}
	
	public ServiceInversion(Socket socket) {
		client = socket;
	}
@Override
	public void run() {
		try {BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);

			out.println("Tapez un texte a inverser");
		
			String line = in.readLine();		
	
			String invLine = new String (new StringBuffer(line).reverse());
			
			out.println(invLine);
			
			client.close();
		}
		catch (IOException e) {
			//Fin du service d'inversion
		}
	}
	
	protected void finalize() throws Throwable {
		 client.close(); 
	}

	public static String toStringue() {
		return "Inversion de texte";
	}
}
