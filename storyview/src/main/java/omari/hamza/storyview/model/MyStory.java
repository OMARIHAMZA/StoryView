package omari.hamza.storyview.model;

import java.io.Serializable;
import java.util.Date;

public class MyStory implements Serializable {

    private String url;

    private Date date;

    private String description;

    public MyStory(String url, Date date, String description) {
        this.url = url;
        this.date = date;
        this.description = description;
    }

    public MyStory(String url, Date date) {
        this.url = url;
        this.date = date;
    }

    public MyStory(String url) {
        this.url = url;
    }

    public MyStory() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
