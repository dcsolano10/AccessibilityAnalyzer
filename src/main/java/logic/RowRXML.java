package logic;

public class RowRXML {
	
	private String xmlName;
	private String elementID;
	private String elementType;
	private String description;
	private boolean hasDescription;
	
	
	public RowRXML(String xmlName, String elementID, String elementType, String description, boolean hasDescription) {
		super();
		this.xmlName = xmlName;
		this.elementID = elementID;
		this.elementType = elementType;
		this.description = description;
		this.hasDescription = hasDescription;
	}


	public String getXmlName() {
		return xmlName;
	}


	public String getElementID() {
		return elementID;
	}


	public String getElementType() {
		return elementType;
	}


	public String getDescription() {
		return description;
	}


	public boolean getHasDescription() {
		return hasDescription;
	}
	
	
	

}
