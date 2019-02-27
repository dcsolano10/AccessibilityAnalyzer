package logic;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class Statistics {

	private static int numElements = 0;
	private static int elWtDesc = 0;
	private static double average = 0;
	private static Hashtable<String, String> services = new Hashtable<String, String>();

	public static int getNumElements() {
		return numElements;
	}

	public static int getElWtDesc() {
		return elWtDesc;
	}

	public static double getAverage() {
		if(numElements==0) {
			return 0;
		}
		average = (double) elWtDesc / numElements;
		if (average > 1) {
			average = 1;
		}
		// System.out.println("average: "+average+ ":::"+numElements+":::"+elWtDesc);
		return average;
	}

	public static void addNumElements() {
		numElements++;
		// System.out.println("addNumElements");
	}

	public static void addElWtDesc() {
		elWtDesc++;
		// System.out.println("addElWtDesc---------------------");
	}

	public static void setNumElements(int nElements) {
		numElements += nElements;
		// System.out.println("setNumElements:::::::::::: "+nElements);
	}

	public static void setElWtDesc(int eWtDesc) {
		elWtDesc += eWtDesc;
	}

	public static void addService(String service) {

		if (services.get(service) == null) {

			services.put(service, service);
		}
		
	}
	
	public static ArrayList<String> getServices() {
		ArrayList<String> servs= new ArrayList<String>();
		Enumeration<String> keys= services.keys();
		while(keys.hasMoreElements()) {
			servs.add(keys.nextElement());
		}
		return servs;
	}

}
