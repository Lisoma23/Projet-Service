package ama;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class Application {
	private final static int PORT_AMA = 3000;
	private final static String HOST = "localhost"; 

	public static void main(String[] args) {
		Socket s = null;		
		try {
			s = new Socket(HOST, PORT_AMA);

			BufferedReader sin = new BufferedReader (new InputStreamReader(s.getInputStream ( )));
			PrintWriter sout = new PrintWriter (s.getOutputStream ( ), true);
			BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));			
			System.out.println("Connecte au serveur " + s.getInetAddress() + ":"+ s.getPort());

			String line;
			line = sin.readLine();
			System.out.println(line.replaceAll("##", "\n"));
			int numberServices = Integer.parseInt(sin.readLine());
			if(numberServices > 0) {
				line = sin.readLine();
				System.out.println(line.replaceAll("##", "\n"));
				sout.println(clavier.readLine());
				System.out.println(sin.readLine());
				sout.println(clavier.readLine());
				System.out.println(sin.readLine());
			}

		}
		catch (IOException e) { System.err.println("Fin de la connexion"); }
		try { if (s != null) s.close(); } 
		catch (IOException e2) { ; }		
	}
}
