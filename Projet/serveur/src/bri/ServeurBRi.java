package bri;

import java.io.*;
import java.net.*;

public class ServeurBRi implements Runnable {
	private ServerSocket listen_socket;
	
	public ServeurBRi(int port) {
		try {
			listen_socket = new ServerSocket(port);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void run() {
		try {
			while(true)
				if(listen_socket.getLocalPort() == 3000)
					new ServiceBRi(listen_socket.accept()).start();					
				else if(listen_socket.getLocalPort() == 3001) 
					new ProgBRi(listen_socket.accept()).start();				
		}
		catch (IOException e) { 
			try {this.listen_socket.close();} catch (IOException e1) {}
			System.err.println("Pb sur le port d'ecoute :"+e);
		}
	}

	protected void finalize() throws Throwable {
		try {this.listen_socket.close();} catch (IOException e1) {}
	}

	public void lancer() {
		(new Thread(this)).start();		
	}
}