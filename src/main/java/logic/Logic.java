package logic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class Logic {

	private final boolean runLint=false;

	public Logic(String projectPathLint, String projectPathLayout) throws Exception {


		Path pathLint = Paths.get(projectPathLint);

		if (Files.notExists(pathLint)) {
			throw new Exception("Project path does not exist");
		}

		Path pathLayout = null;

		String layoutPath=displayDirectoryContents(pathLint.toFile() );
		if(layoutPath==null) {
			throw new Exception("Project path layout does not exist");
		}else {
			pathLayout = Paths.get(layoutPath);
		}
		

//		if (projectPathLayout == null) {
//
//			String layoutPath = null;
//
//			try {
//				File settingsFile = new File(pathLint.toString() + File.separator + "settings.gradle");
//				String content = FileUtils.readFileToString(settingsFile);
//				String folderName = content.replaceAll("include ':+", "").replace("'", "").replaceAll("\n", "");
//				layoutPath = pathLint.toString() + File.separator + folderName + File.separator + "src"+ File.separator + "main" + File.separator + "res" + File.separator + "layout";
//			} catch (FileNotFoundException e) {
//
//				//Project does not contain settings.gradle
//
//			}
//
//
//			pathLayout = Paths.get(layoutPath);
//		} else {
//			pathLayout = Paths.get(projectPathLayout);
//		}

		if (Files.notExists(pathLayout) ) {
			System.out.println(pathLayout);
			throw new Exception("Project path layout does not exist");
		}

		if(runLint) {

			Path gradlePath = Paths.get(projectPathLint.concat(File.separator).concat("gradlew"));

			if (Files.notExists(gradlePath)) {
				throw new Exception("Project does not contain Gradle wrapper");
			}

			List<String> commands = Arrays.asList(gradlePath.toString(), "lint", "-p", pathLint.toString());
			try {
				// Run Lint
				Helper.executeProcess(commands, "Lint Android Project", "success", "error");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Read java files and find accessibility assignments
		JVParser.listMethodCalls(pathLint.toFile());

		// Read xml and finf EditText, ImageView and ImageButton
		ReadXML.readXML(pathLayout.toFile());

		double pWT=Statistics.getAverage();
		int nel=Statistics.getNumElements();
		int elwt=Statistics.getElWtDesc();

		ArrayList<String> services = Statistics.getServices();



		BufferedWriter br = new BufferedWriter(
				new FileWriter(pathLint.toString() + File.separator + "statistics.csv"));
		StringBuilder sb = new StringBuilder();
		sb.append("Percentage_of_elements_without_description");
		sb.append(",");
		sb.append(pWT);
		sb.append("\n");
		sb.append("Number_of_elements");
		sb.append(",");
		sb.append(nel);
		sb.append("\n");
		sb.append("Number_of_elements_without_description");
		sb.append(",");
		sb.append(elwt);
		sb.append("\n");
		sb.append("Number_of_accessibility_services_imported");
		sb.append(",");
		sb.append(services.size());
		sb.append("\n");
		for (int i = 0; i < services.size(); i++) {
			sb.append("Accessibility_service_imported");
			sb.append(",");
			sb.append(services.get(i));
			sb.append("\n");
		}
		br.write(sb.toString());
		br.close();


		/**
		ArrayList<RowJCode> javaArray = JVParser.getJavaArray();
		ArrayList<RowRXML> xmlArray = ReadXML.getXMLArray();

		BufferedWriter br = new BufferedWriter(
				new FileWriter(pathLint.toString() + File.separator + "javaDescription.csv"));
		StringBuilder sb = new StringBuilder();
		for (RowJCode row : javaArray) {
			sb.append(row.getRid());
			sb.append(",");
			sb.append(row.getLine());
			sb.append(",");
			sb.append(row.getType());
			sb.append(",");
			sb.append(row.getDescription());
			sb.append("\n");
		}
		br.write(sb.toString());
		br.close();

		br = new BufferedWriter(new FileWriter(pathLint.toString() + File.separator + "xmlDescription.csv"));
		sb = new StringBuilder();
		for (RowRXML row : xmlArray) {
			sb.append(row.getElementID());
			sb.append(",");
			sb.append(row.getHasDescription());
			sb.append(",");
			sb.append(row.getXmlName());
			sb.append(",");
			sb.append(row.getElementType());
			sb.append(",");
			sb.append(row.getDescription());
			sb.append("\n");

		}
		br.write(sb.toString());
		br.close();

		 **/

	}

	public String displayDirectoryContents(File dir) {
		String layoutPathName=null;
		File[] files = dir.listFiles();
		for (File file : files) {
			String fileName=file.getName();
			if (file.isDirectory()) {
				if(fileName.equals("layout")) {
					layoutPathName=file.getAbsolutePath();
					System.out.println("Layout directory: " + layoutPathName);

					return layoutPathName;
				}else if(layoutPathName==null){
					layoutPathName=displayDirectoryContents(file);
				}
			}
		}
		return layoutPathName;
	}

}
