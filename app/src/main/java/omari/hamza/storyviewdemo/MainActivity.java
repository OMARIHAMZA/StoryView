package omari.hamza.storyviewdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import omari.hamza.storyview.StoryView;
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
                    "https://www.spruch-des-tages.org/images/sprueche/nimm-dir-zeit-fuer-die-dinge-die-dich-gluecklich-machen.jpg",
                    simpleDateFormat.parse("26-10-2019 10:00:00")
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
                .setTitleText("Hamza Al-Omari")
                .setSubtitleText("Damascus")
                .setTitleLogoUrl("https://scontent-amt2-1.xx.fbcdn.net/v/t1.0-1/p160x160/39992298_1709076109202448_8167947883299995648_n.jpg?_nc_cat=106&_nc_oc=AQmES4AmTCqzNzXatJvOBc5U2ZyU8SNxwkeZmxUmZIt96pNdKjPCsHG1MJfbN_SJ6eU&_nc_ht=scontent-amt2-1.xx&oh=8063cb8bdd3c01b71cb920f22dc8c081&oe=5E201FFB")
                .setStoryDuration(5000)
                .setStoryClickListeners(new StoryClickListeners() {
                    @Override
                    public void onDescriptionClickListener(int position) {
                        Toast.makeText(MainActivity.this, "Clicked: " + myStories.get(position).getDescription(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onTitleIconClickListener(int position) {
                    }
                })
                .build()
                .show();

    }
}
