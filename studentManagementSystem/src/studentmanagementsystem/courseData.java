package studentmanagementsystem;

public class courseData {
    
    private String id;
    private String dep_name;
    private String code;
    private String title;
    
    public courseData(String id, String code, String title, String dep_name){
        this.id = id;
        this.code = code;
        this.title = title;
        this.dep_name =dep_name;
    }

    public String getId() {
        return id;
    }

    public String getDep_name() {
        return dep_name;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "courseData{" +
                "id='" + id + '\'' +
                ", dep_name='" + dep_name + '\'' +
                ", code='" + code + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
