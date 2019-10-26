package omari.hamza.storyview.utils;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class MyStory implements Serializable {

    @Nullable
    private String title;

    @Nullable
    private String subtitle;

    @Nullable
    private Date date;

    private String imageUrl;

    @Nullable
    private String titleIconUrl;


    public MyStory() {
    }

    public MyStory(@Nullable String title, @Nullable String subtitle, @Nullable Date date, String imageUrl, @Nullable String titleIconUrl) {
        this.title = title;
        this.subtitle = subtitle;
        this.date = date;
        this.imageUrl = imageUrl;
        this.titleIconUrl = titleIconUrl;
    }

    public MyStory(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Nullable
    public String getTitle() {
        return title;
    }


    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    @Nullable
    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(@Nullable String subtitle) {
        this.subtitle = subtitle;
    }

    @Nullable
    public Date getDate() {
        return date;
    }

    public void setDate(@Nullable Date date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Nullable
    public String getTitleIconUrl() {
        return titleIconUrl;
    }

    public void setTitleIconUrl(@Nullable String titleIconUrl) {
        this.titleIconUrl = titleIconUrl;
    }


    /**
     * ~ HelperMethod
     * @param urls: list of images to be displayed
     * @return list of MyStories with the urls to be used in the library
     *
     */
    public static ArrayList<MyStory> toStories(ArrayList<String> urls) {
        ArrayList<MyStory> myStories = new ArrayList<>();
        for (String url : urls) {
            myStories.add(new MyStory(url));
        }
        return myStories;
    }


    /**
     * ~ HelperMethod
     * @param titleIcon: the url of the icon to be displayed in the top-left corner
     * @param title: the title of the story (example: username)
     * @param subtitle: the small-second title of the story (example: location)
     * @param date: the date of the story
     * @param urls: the urls of the stories
     * @return list of MyStory with the heading to be displayed in all images, to use different heading for each image_
     *         construct this list manually and pass it to the StoryView
     */
    public static ArrayList<MyStory> toStories(String titleIcon,
                                               String title,
                                               String subtitle, Date date, ArrayList<String> urls){
        ArrayList<MyStory> myStories = new ArrayList<>();
        for (String url : urls) {
            myStories.add(new MyStory(title, subtitle, date, url, titleIcon));
        }
        return myStories;
    }
}
