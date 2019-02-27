package logic;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;

public class ReadXML {

	private static ArrayList<RowRXML> rows;

	public static void readXML(File projectDir) {

		rows = new ArrayList<RowRXML>();

		try {

			//Files from layout dir
			File[] files=projectDir.listFiles();

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			for (int i = 0; i < files.length; i++) {
				File filei=files[i];
				String fileName=filei.getName();

				if(filei.isFile() && FilenameUtils.getExtension(fileName).equals("xml")) {

					//Parse and normalize xml file
					Document doc = dBuilder.parse(filei);
					doc.getDocumentElement().normalize();

					//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

					//Lists all EditText elements in xml file
					NodeList nListET = doc.getElementsByTagName("EditText");
					
					//Add number of edit texts to statistics
					if(nListET.getLength()>0) {
					Statistics.setNumElements(nListET.getLength());
					}

					//For each EditText element gets it's attributes
					for (int temp = 0; temp < nListET.getLength(); temp++) {

						Node nNode = nListET.item(temp);
						Element eElement = (Element) nNode;

						String androidID = eElement.getAttribute("android:id");
						String hint = eElement.getAttribute("android:hint");
						boolean hasDesc = !hint.isEmpty();
						
						if(!hasDesc) {
							Statistics.addElWtDesc();
						}

						//System.out.println("android  id: " + androidID +" -- " + hint);

						//Add to report
						RowRXML row= new RowRXML(fileName, androidID, "EditText", hint, hasDesc);
						rows.add(row);

					}

					//Lists all ImageView elements in xml file
					NodeList nListIV = doc.getElementsByTagName("ImageView");
					
					//Add number of ImageView to statistics
					if(nListIV.getLength()>0) {
					Statistics.setNumElements(nListIV.getLength());
					}

					//For each ImageView element gets it's attributes
					for (int temp = 0; temp < nListIV.getLength(); temp++) {

						Node nNode = nListIV.item(temp);
						Element eElement = (Element) nNode;
						
						String androidID = eElement.getAttribute("android:id");
						String contDesc = eElement.getAttribute("android:contentDescription");
						boolean hasDesc = !contDesc.isEmpty();
						
						if(!hasDesc) {
							Statistics.addElWtDesc();
						}
						
						//System.out.println("android  id: " + androidID +" -- " + contDesc);

						//Add to report
						RowRXML row= new RowRXML(fileName, androidID, "ImageView", contDesc, hasDesc);
						rows.add(row);

					}

					//Lists all ImageButton elements in xml file
					NodeList nListIB = doc.getElementsByTagName("ImageButton");
					
					//Add number of ImageButton to statistics
					if(nListIB.getLength()>0) {
					Statistics.setNumElements(nListIB.getLength());
					}

					//For each ImageButton element gets it's attributes
					for (int temp = 0; temp < nListIB.getLength(); temp++) {

						Node nNode = nListIB.item(temp);
						Element eElement = (Element) nNode;
						
						String androidID = eElement.getAttribute("android:id");
						String contDesc = eElement.getAttribute("android:contentDescription");
						boolean hasDesc = !contDesc.isEmpty();
						
						if(!hasDesc) {
							Statistics.addElWtDesc();
						}

						//System.out.println("android  id: " + androidID +" -- " + contDesc);

						//Add to report
						RowRXML row= new RowRXML(fileName, androidID, "ImageButton", contDesc, hasDesc);
						rows.add(row);
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static ArrayList<RowRXML> getXMLArray() {
		return rows;
	}


}
