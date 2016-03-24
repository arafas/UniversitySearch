package universitysearch;

public class File {
private int id;
	
	private String fileName, filePath, fileDesc, fileHash, blurb;


	private int fileOwner, courseId;
	private long fileSize;

	public File() {
	}

	public File(String fName, String fPath, String fDesc, String fHash, long fSize, int fOwn, int courseId) {
		this.fileName = fName;
		this.filePath = fPath;
		this.fileDesc = fDesc;
		this.fileHash = fHash;
		this.fileSize = fSize;
		this.fileOwner = fOwn;
		this.courseId = courseId;
	}

	public String getBlurb() {
		return blurb;
	}

	public void setBlurb(String blurb) {
		this.blurb = blurb;
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
	
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fSize) {
		this.fileSize = fSize;
	}
	
	public int getFileOwner() {
		return fileOwner;
	}
	public void setFileOwner(int fOwn) {
		this.fileOwner = fOwn;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
}
