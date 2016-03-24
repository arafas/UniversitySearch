package universitysearch;

public class TagFile {
	private int id;
	private int tagID, fileID;

	public TagFile() {
	}
	
	public TagFile(int tID, int fileID) {
	    this.tagID = tID;
		this.fileID = fileID;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
    public int getTagID() {
      return tagID;
    }
    public void setTagID(int tID) {
      this.tagID = tID;
    }
	
	public int getFileID() {
		return fileID;
	}
	public void setFileID(int fileID) {
		this.fileID = fileID;
	}
}
