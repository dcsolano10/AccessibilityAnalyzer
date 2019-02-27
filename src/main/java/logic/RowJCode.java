package logic;

public class RowJCode {
	
	private String rid;
	private String line;
	private String type;
	private String description;
	
	
	public RowJCode(String rid, String line, String type, String description) {
		super();
		this.rid = rid;
		this.line = line;
		this.type = type;
		this.description = description;
	}
	
	
	public String getRid() {
		return rid;
	}


	public String getLine() {
		return line;
	}


	public String getType() {
		return type;
	}


	public String getDescription() {
		return description;
	}


	public void setRid(String rid) {
		this.rid = rid;
	}


	public void setLine(String line) {
		this.line = line;
	}


	public void setType(String type) {
		this.type = type;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	
	
	
	

}
