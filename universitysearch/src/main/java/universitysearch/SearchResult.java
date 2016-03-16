package universitysearch;

import java.util.ArrayList;

/**
 * Created by zubairbaig on 3/13/16.
 */
public class SearchResult {
    public ArrayList<File> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<File> files) {
        this.files = files;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    private ArrayList<File> files = new ArrayList<File>();
    private ArrayList<Course> courses = new ArrayList<Course>();

    public void addFile(File file) {
        files.add(file);
    }

    public void addCourse(Course course) {
        courses.add(course);
    }
}
