package bri;

import java.util.ArrayList;
import java.util.List;

public class ProgRegistry {
	static {
		progs = null;
	}
	private static List<Prog> progs = new ArrayList<Prog>();

	public static void addProg(Prog prog) {
		progs.add(prog);
	}
	
	public static Prog progExist(Prog prog) {
		for (Prog p : progs) {
			if(p.getLogin().equals(prog.getLogin()) && p.getPassword().equals(prog.getPassword()))
				return p;
		}
		return null;
	}
}