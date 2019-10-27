package omari.hamza.storyview.utils;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class StoryViewHeaderInfo implements Serializable {

    @Nullable
    private String title;

    @Nullable
    private String subtitle;

    @Nullable
    private String titleIconUrl;


    public StoryViewHeaderInfo() {
    }


    public StoryViewHeaderInfo(@Nullable String title, @Nullable String subtitle, @Nullable String titleIconUrl) {
        this.title = title;
        this.subtitle = subtitle;
        this.titleIconUrl = titleIconUrl;
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
    public String getTitleIconUrl() {
        return titleIconUrl;
    }

    public void setTitleIconUrl(@Nullable String titleIconUrl) {
        this.titleIconUrl = titleIconUrl;
    }


}
