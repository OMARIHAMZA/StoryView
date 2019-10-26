package omari.hamza.storyviewdemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import omari.hamza.storyview.StoryView;
import omari.hamza.storyview.utils.MyStory;
import omari.hamza.storyviewdemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setActivity(this);
    }

    public void showStories(){
        ArrayList<String> images = new ArrayList<>(Arrays.asList(
                "https://i.pinimg.com/originals/6a/45/3d/6a453dcf8af8d13d648743aad173e317.jpg",
                "https://wallpaperaccess.com/full/755169.jpg",
                "https://mfiles.alphacoders.com/681/681242.jpg")
        );
        StoryView.showStories(getSupportFragmentManager(), MyStory.toStories(images));
    }
}
