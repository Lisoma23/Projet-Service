package bri;
import java.io.*;
import java.lang.reflect.Constructor;
import java.net.*;

class ServiceBRi implements Runnable {
	private Socket client;

	ServiceBRi(Socket socket) {
		client = socket;
	}

	public void run() {
		try {
			System.out.println("Nouvelle connexion d'un amateur");

			BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);
			
			out.println(ServiceRegistry.toStringue());

			int numberServices = ServiceRegistry.getServicesSize();
			out.println(numberServices);
			if(numberServices > 0) {
				out.println("##Tapez le numero de service desire :");
				int choix = Integer.parseInt(in.readLine());
				Class<? extends Service> classe = ServiceRegistry.getServiceClass(choix);

				try {
					Constructor<? extends Service> niou = classe.getConstructor();
					Service service = classe.getConstructor(java.net.Socket.class).newInstance(this.client);
					service.run();
				} catch (Exception e){	
				}
			}
		}
		catch (IOException e) {
		}
		try {
			client.close();
			System.out.println("Fin de connexion d'un amateur");
		} catch (IOException e2) {}
	}

	protected void finalize() throws Throwable {
		client.close(); 
	}

	public void start() {
		(new Thread(this)).start();		
	}
}