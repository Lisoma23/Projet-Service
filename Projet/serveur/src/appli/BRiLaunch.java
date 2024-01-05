package appli;

import bri.ServeurBRi;

public class BRiLaunch {
	private final static int PORT_SERVICE = 3000;
	private final static int PORT_PROG = 3001;

	public static void main(String[] args) {
		System.out.println("Serveur lancé");
		
		new Thread(new ServeurBRi(PORT_SERVICE)).start();
		new Thread(new ServeurBRi(PORT_PROG)).start();
	}
}