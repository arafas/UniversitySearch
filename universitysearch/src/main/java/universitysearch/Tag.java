package universitysearch;

public class Tag {
private int id;	
	private String tagName;

	public Tag() {
	}
	
	public Tag(String tName) {
		this.tagName = tName;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tName) {
		this.tagName = tName;
	}

}
