package universitysearch;

/**
 * Created by zubairbaig on 3/24/16.
 */
public class Tags {
    public String tagName;
    public int id, fileId;

    public Tags() {
    }

    public Tags(String tagName, int fileId) {
        this.tagName = tagName;
        this.fileId = fileId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
}
