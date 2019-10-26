package omari.hamza.storyview;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import omari.hamza.storyview.progress.StoriesProgressView;

public class StoryView extends DialogFragment implements StoriesProgressView.StoriesListener, StoryCallbacks {

    private static final String TAG = StoryView.class.getSimpleName();

    private ArrayList<String> images; //Images' URL

    private final static String IMAGES_KEY = "IMAGES";

    private long duration = 2000; //Default Duration

    private static final String DURATION_KEY = "DURATION";

    private StoriesProgressView storiesProgressView;

    private ViewPager mViewPager;

    private int counter = 0;

    public static void showStories(FragmentManager fragmentManager, ArrayList<String> images) {
        StoryView storyView = new StoryView();
        Bundle bundle = new Bundle();
        bundle.putSerializable(IMAGES_KEY, images);
        storyView.setArguments(bundle);
        storyView.show(fragmentManager, TAG);
    }

    public static void showStories(FragmentManager fragmentManager, ArrayList<String> images, long duration) {
        StoryView storyView = new StoryView();
        Bundle bundle = new Bundle();
        bundle.putSerializable(IMAGES_KEY, images);
        bundle.putLong(DURATION_KEY, duration);
        storyView.setArguments(bundle);
        storyView.show(fragmentManager, TAG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_stories, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        findViewsById(view);
        readArguments();
        setupStories();
    }

    private void setupStories() {
        storiesProgressView.setStoriesCount(images.size());
        storiesProgressView.setStoryDuration(duration);
        mViewPager.setAdapter(new ViewPagerAdapter(images, getContext(), this));
    }

    private void readArguments() {
        assert getArguments() != null;
        images = (ArrayList<String>) getArguments().getSerializable(IMAGES_KEY);
        duration = getArguments().getLong(DURATION_KEY, 2000);
    }

    private void findViewsById(View view) {
        storiesProgressView = view.findViewById(R.id.storiesProgressView);
        mViewPager = view.findViewById(R.id.storiesViewPager);
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        storiesProgressView.setStoriesListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(params);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onNext() {
        mViewPager.setCurrentItem(++counter);
    }

    @Override
    public void onPrev() {
        if (counter <= 0) return;
        mViewPager.setCurrentItem(--counter);
    }

    @Override
    public void onComplete() {
        dismissAllowingStateLoss();
    }

    @Override
    public void startStories() {
        storiesProgressView.startStories();
    }

    @Override
    public void pauseStories() {
        storiesProgressView.pause();
    }

    private void previousStory() {
        mViewPager.setCurrentItem(--counter);
        storiesProgressView.startStories(counter);
    }

    @Override
    public void nextStory() {
        mViewPager.setCurrentItem(++counter);
        storiesProgressView.startStories(counter);
    }

    @Override
    public void onDestroy() {
        storiesProgressView.destroy();
        super.onDestroy();
    }
}
