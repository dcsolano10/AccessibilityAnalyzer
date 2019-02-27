package logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import java.io.StringReader;

public class Helper {

	public static List<String> executeProcess(List<String> commands, String commandName, String onSuccessMessage,
			String onErrorMessage) throws IOException {
		List<String> answer = new ArrayList<String>();
		ProcessBuilder pb = new ProcessBuilder(commands);
		System.out.println("-> " + commandName);
		System.out.println(commands.toString());
		Process spb = pb.start();
		String output = IOUtils.toString(spb.getInputStream());
		answer.add(output);
		String err = IOUtils.toString(spb.getErrorStream());
		answer.add(err);
		System.out.println(err);
		answer.add(commandName);
		System.out.println("- - - - - - - - - - - - - - - - - - -");
		return answer;
	}

	public static Document loadXMLFromString(String xml)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		return builder.parse(is);
	}

}
