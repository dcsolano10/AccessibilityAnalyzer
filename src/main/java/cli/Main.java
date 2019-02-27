package cli;

import logic.Logic;

public class Main {
	
	public Main(String pathLint, String pathLayout) {
		
		try {
			new Logic(pathLint, pathLayout);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) {
		String pathLayouts = null;
		try {
			pathLayouts = args[1];
		}
		catch(Exception e) {
			//Layout paths is null
		}
		
		new Main(args[0], pathLayouts);
	}

}
