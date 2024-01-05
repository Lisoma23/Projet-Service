package bri;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ServiceRegistry {
	static {
		servicesClasses = null;
	}
	private static List<Class<? extends Service>> servicesClasses = new ArrayList<Class<? extends Service>>();

	public static void addService(Class<? extends Service> runnableClass) throws ValidationException {
		validation(runnableClass);
		servicesClasses.add(runnableClass);
	}

	public static void replaceService(Class<? extends Service> newRunnableClass, int numService) throws ValidationException {
		validation(newRunnableClass);
		servicesClasses.set(numService - 1, newRunnableClass);		
	}

	private static void validation(Class<? extends Service> classe) throws ValidationException {
		Constructor<? extends Service> c;
		try {
			c = classe.getConstructor();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		try { 
			c = classe.getConstructor(java.net.Socket.class); 
		} catch (NoSuchMethodException e) {
			throw new ValidationException("Il faut un constructeur avec Socket");
		}
		int modifiers = c.getModifiers();
		if (!Modifier.isPublic(modifiers)) 
			throw new ValidationException("Le constructeur (Socket) doit etre public");
		if (c.getExceptionTypes().length != 0)
			throw new ValidationException("Le constructeur (Socket) ne doit pas lever d'exception");		
	}

	public static Class<? extends Service> getServiceClass(int numService) {
		return servicesClasses.get(numService-1);
	}

	public static String toStringue() {
		String result = "Activites presentes :##";
		int i = 1;
		synchronized (servicesClasses) {
			for (Class<? extends Service> s : servicesClasses) {
				try {
					Method toStringue = s.getMethod("toStringue");
					String string = (String) toStringue.invoke(s);
					result = result + i + " " + string + "##";
					i++;
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public static String toStringueProg(String login) {
		String result = "Vos services :##";
		int i = 1;
		synchronized (servicesClasses) {
			for (Class<? extends Service> s : servicesClasses) {
				if(s.getPackageName().equals(login)) {
					try {
						Method toStringue = s.getMethod("toStringue");
						String string = (String) toStringue.invoke(s);
						result = result + i + " " + string + "##";
						i++;
					} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}
		}
		return result;
	}

	public static int getServicesSize()
	{
		return servicesClasses.size();
	}
	
	public static int getServicesProg(String login)
	{
		int services = 0;
		for (Class<? extends Service> s : servicesClasses) {
			if(s.getPackageName().equals(login))
				services++;
		}
		return services;
	}
}
