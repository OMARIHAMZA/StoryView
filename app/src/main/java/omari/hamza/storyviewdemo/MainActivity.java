package omari.hamza.storyviewdemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import omari.hamza.storyview.StoryView;
import omari.hamza.storyviewdemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setActivity(this);
    }

    public void showStories() {
        ArrayList<String> images = new ArrayList<>(Arrays.asList(
                "https://i.pinimg.com/originals/6a/45/3d/6a453dcf8af8d13d648743aad173e317.jpg",
                "https://wallpaperaccess.com/full/755169.jpg",
                "https://mfiles.alphacoders.com/681/681242.jpg")
        );

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, -2);

        new StoryView.Builder(getSupportFragmentManager())
                .setImages(images)
                .setTitleText("Hamza Al-Omari")
                .setSubtitleText("Berlin")
                .setDate(calendar.getTime())
                .setTitleLogoUrl("https://scontent-amt2-1.xx.fbcdn.net/v/t1.0-1/p160x160/39992298_1709076109202448_8167947883299995648_n.jpg?_nc_cat=106&_nc_oc=AQmES4AmTCqzNzXatJvOBc5U2ZyU8SNxwkeZmxUmZIt96pNdKjPCsHG1MJfbN_SJ6eU&_nc_ht=scontent-amt2-1.xx&oh=8063cb8bdd3c01b71cb920f22dc8c081&oe=5E201FFB")
                .setStoryDuration(5000)
                .build()
                .show();

    }
}
