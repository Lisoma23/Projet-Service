package bri;
import java.io.*;
import java.net.*;

class ProgBRi implements Runnable {
	private Socket client;

	ProgBRi(Socket socket) {
		client = socket;
	}

	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			
			out.println("##Veuillez saisir votre login :");
			String login = in.readLine();

			out.println("##Veuillez saisir votre mot de passe:");
			String password = in.readLine();

			Prog prog = new Prog(login,password);
			Prog currentProg = ProgRegistry.progExist(prog);

			if(currentProg == null) {
				out.println("##Creation de votre compte");	
				out.println("##Veuillez saisir votre url ftp :");
				prog.setFtpUrl(in.readLine());
				ProgRegistry.addProg(prog);
				currentProg = prog;
			}

			out.println("##Vous etes connecter en tant que programmeur");
			System.out.println("Nouvelle connexion d'un programmeur");

			while(true) {
				out.println("##1 - Ajout d'un service");
				
				int numberServices = ServiceRegistry.getServicesProg(login);
				out.println(numberServices);
				
				if(numberServices > 0) {
					out.println("##2 - Modifier un service");					
				}
				out.println("##3 - Changer votre url ftp");
				out.println("##4 - Se déconnecter");

				int choix = Integer.parseInt(in.readLine());
				if (choix == 1) {
					out.println("##Ajouter un service");

					URLClassLoader urlcl = URLClassLoader.newInstance(new URL[] {new URL(currentProg.getFtpUrl())});
					try {
						ServiceRegistry.addService(urlcl.loadClass(in.readLine()).asSubclass(Service.class));
					} catch (ValidationException e) {
						e.printStackTrace();
					} catch (ClassCastException e) {
						System.out.println("La classe doit implementer bri.Service");
					} catch (ClassNotFoundException e) {
						System.out.println("La classe n'est pas sur le serveur ftp dans home");
					}
					out.println("Service bien ajouté");
				}

				if (choix == 2 && numberServices > 0) {
					out.println("Choissisez quel service modifier");
					out.println(ServiceRegistry.toStringueProg(prog.getLogin()));
					int choixService = Integer.parseInt(in.readLine());
					out.println("Choissisez le nouveau service");

					URLClassLoader urlcl = URLClassLoader.newInstance(new URL[] {new URL(currentProg.getFtpUrl())});
					try {
						ServiceRegistry.replaceService(urlcl.loadClass(in.readLine()).asSubclass(Service.class),choixService);
					} catch (ValidationException e) {
						e.printStackTrace();
					} catch (ClassCastException e) {
						System.out.println("La classe doit implementer bri.Service");
					} catch (ClassNotFoundException e) {
						System.out.println("La classe n'est pas sur le serveur ftp dans home");
					}
					out.println("Service bien remplacé");
				}

				if (choix == 3) {
					out.println("##Changer l'url ftp");
					prog.setFtpUrl(in.readLine());
					out.println("##Url du ftp changé");
				}

				if (choix == 4) {
					break;
				}
			}
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		try {
			client.close();
			System.out.println("Fin de connexion d'un programmeur");
		} 
		catch (IOException e2) {}
	}

	protected void finalize() throws Throwable {
		client.close(); 
	}

	public void start() {
		(new Thread(this)).start();		
	}
}