package universitysearch;

public class File {
private int id;
	
	private String fileName, filePath, fileDesc, fileHash;
	private int fileSize, fileOwner;

	public File() {
	}
	
	public File(String fName, String fPath, String fDesc, String fHash, int fSize, int fOwn) {
		this.fileName = fName;
		this.filePath = fPath;
		this.fileDesc = fDesc;
		this.fileHash = fHash;
		this.fileSize = fSize;
		this.fileOwner = fOwn;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fName) {
		this.fileName = fName;
	}

	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String fPath) {
		this.filePath = fPath;
	}

	public String getFileDesc() {
		return fileDesc;
	}
	public void setFileDesc(String fDesc) {
		this.fileDesc = fDesc;
	}

	public String getFileHash() {
		return fileHash;
	}
	public void setFileHash(String fHash) {
		this.fileHash = fHash;
	}
	
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fSize) {
		this.fileSize = fSize;
	}
	
	public int getFileOwner() {
		return fileOwner;
	}
	public void setFileOwner(int fOwn) {
		this.fileOwner = fOwn;
	}
}
