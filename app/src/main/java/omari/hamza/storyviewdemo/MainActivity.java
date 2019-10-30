package omari.hamza.storyviewdemo;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import omari.hamza.storyview.StoryView;
import omari.hamza.storyview.callback.OnStoryChangedCallback;
import omari.hamza.storyview.callback.StoryClickListeners;
import omari.hamza.storyview.model.MyStory;
import omari.hamza.storyviewdemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setActivity(this);
    }

    public void showStories() {

        final ArrayList<MyStory> myStories = new ArrayList<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        try {
            MyStory story1 = new MyStory(
                    "https://media.pri.org/s3fs-public/styles/story_main/public/images/2019/09/092419-germany-climate.jpg?itok=P3FbPOp-",
                    simpleDateFormat.parse("20-10-2019 10:00:00")
            );
            myStories.add(story1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            MyStory story2 = new MyStory(
                    "http://i.imgur.com/0BfsmUd.jpg",
                    simpleDateFormat.parse("26-10-2019 15:00:00"),
                    "#TEAM_STANNIS"
            );
            myStories.add(story2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        MyStory story3 = new MyStory(
                "https://mfiles.alphacoders.com/681/681242.jpg"
        );
        myStories.add(story3);

        new StoryView.Builder(getSupportFragmentManager())
                .setStoriesList(myStories)
                .setStoryDuration(5000)
                .setTitleText("Hamza Al-Omari")
                .setSubtitleText("Damascus")
                .setStoryClickListeners(new StoryClickListeners() {
                    @Override
                    public void onDescriptionClickListener(int position) {
                        Toast.makeText(MainActivity.this, "Clicked: " + myStories.get(position).getDescription(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onTitleIconClickListener(int position) {
                    }
                })
                .setOnStoryChangedCallback(new OnStoryChangedCallback() {
                    @Override
                    public void storyChanged(int position) {
                        Toast.makeText(MainActivity.this, position + "", Toast.LENGTH_SHORT).show();
                    }
                })
                .setStartingIndex(0)
                .build()
                .show();

    }
}
