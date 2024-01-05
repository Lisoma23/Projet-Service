package prog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class Application {
	private final static int PORT_PROG = 3001;
	private final static String HOST = "localhost"; 

	public static void main(String[] args) {
		Socket s = null;		
		try {
			s = new Socket(HOST, PORT_PROG);

			BufferedReader sin = new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter sout = new PrintWriter(s.getOutputStream(), true);
			BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Connecte au serveur " + s.getInetAddress() + ":"+ s.getPort());

			//login
			String line = sin.readLine();
			System.out.println(line.replaceAll("##", "\n"));
			String login = clavier.readLine();
			sout.println(login);

			//password
			line = sin.readLine();
			System.out.println(line.replaceAll("##", "\n"));
			sout.println(clavier.readLine());

			line = sin.readLine();
			if(line.contains("##Creation de votre compte")) {
				System.out.println(line.replaceAll("##", "\n"));

				line = sin.readLine();
				System.out.println(line.replaceAll("##", "\n"));
				sout.println(clavier.readLine());		

				line = sin.readLine();
				System.out.println(line.replaceAll("##", "\n"));
			}
			else
				System.out.println(line.replaceAll("##", "\n"));

			while(true) {
				line = sin.readLine();
				System.out.println(line.replaceAll("##", "\n"));

				int numberServices = Integer.parseInt(sin.readLine());
				if(numberServices > 0) {
					line = sin.readLine();
					System.out.println(line.replaceAll("##", "\n"));
				}
				line = sin.readLine();
				System.out.println(line.replaceAll("##", "\n"));
				line = sin.readLine();
				System.out.println(line.replaceAll("##", "\n"));

				String choixString = clavier.readLine();
				while(true) {
					if(choixString.equals("1") || (choixString.equals("2") && numberServices > 0) || choixString.equals("3") || choixString.equals("4"))
						break;
					
					System.out.println("Selectionnez un choix existant");
					choixString = clavier.readLine();
				}
				
				int choix = Integer.parseInt(choixString);
				sout.println(choix);

				if(choix == 4)
					break;

				line = sin.readLine();
				System.out.println(line.replaceAll("##", "\n"));

				if (choix == 1)
					sout.println(verifyPath(login, clavier));

				if (choix == 2 && numberServices > 0) {
					line = sin.readLine();
					System.out.println(line.replaceAll("##", "\n"));

					sout.println(clavier.readLine());

					line = sin.readLine();
					System.out.println(line.replaceAll("##", "\n"));

					sout.println(verifyPath(login, clavier));
				}
				if(choix == 3)
					sout.println(clavier.readLine());

				line = sin.readLine();
				System.out.println(line.replaceAll("##", "\n"));
			}
		}
		catch (IOException e) { System.err.println("Fin de la connexion");}
		// Refermer dans tous les cas la socket
		try { if (s != null) s.close(); } 
		catch (IOException e2) { ; }		
	}

	private static String verifyPath(String login, BufferedReader clavier) throws IOException {
		String path = clavier.readLine();
		String[] word = path.split("\\.");

		while(!word[0].equals(login)) {
			System.out.println("Il faut que le nom de votre package soit celui de votre login");
			path = clavier.readLine();
			word = path.split("\\.");
		}
		return path;
	}
}