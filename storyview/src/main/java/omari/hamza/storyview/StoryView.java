package omari.hamza.storyview;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import omari.hamza.storyview.progress.StoriesProgressView;
import omari.hamza.storyview.utils.StoryViewHeaderInfo;

import static omari.hamza.storyview.utils.Utils.getDurationBetweenDates;

public class StoryView extends DialogFragment implements StoriesProgressView.StoriesListener, StoryCallbacks {

    private static final String TAG = StoryView.class.getSimpleName();

    private ArrayList<String> images; //Images' URL

    private final static String IMAGES_KEY = "IMAGES";

    private long duration = 2000; //Default Duration

    private static final String DURATION_KEY = "DURATION";

    private static final String HEADER_INFO_KEY = "HEADER_INFO";

    private StoriesProgressView storiesProgressView;

    private ViewPager mViewPager;

    private int counter = 0;

    //Heading
    private TextView titleTextView, subtitleTextView;
    private CardView titleCardView;
    private ImageView titleIconImageView;
    private ImageButton closeImageButton;

    //Touch Events
    private boolean isDownClick = false;
    private long elapsedTime = 0;
    private Thread timerThread;
    private boolean isPaused = false;

    private StoryView() {
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
        detectTouchEvents();
    }

    private void setupStories() {
        storiesProgressView.setStoriesCount(images.size());
        storiesProgressView.setStoryDuration(duration);
        displayHeading();
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
        titleTextView = view.findViewById(R.id.title_textView);
        subtitleTextView = view.findViewById(R.id.subtitle_textView);
        titleIconImageView = view.findViewById(R.id.title_imageView);
        titleCardView = view.findViewById(R.id.titleCardView);
        closeImageButton = view.findViewById(R.id.imageButton);
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
        displayHeading();
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
        if (counter - 1 < 0) return;
        mViewPager.setCurrentItem(--counter);
        storiesProgressView.startStories(counter);
    }

    @Override
    public void nextStory() {
        if (counter + 1 >= images.size()) {
            dismissAllowingStateLoss();
            return;
        }
        mViewPager.setCurrentItem(++counter);
        storiesProgressView.startStories(counter);
    }

    @Override
    public void onDestroy() {
        timerThread = null;
        images = null;
        storiesProgressView.destroy();
        super.onDestroy();
    }

    private void displayHeading() {
        StoryViewHeaderInfo currentStory = (StoryViewHeaderInfo) getArguments().getSerializable(HEADER_INFO_KEY);
        if (currentStory == null) return;

        if (currentStory.getTitleIconUrl() != null) {
            titleCardView.setVisibility(View.VISIBLE);
            if (getContext() == null) return;
            Glide.with(getContext())
                    .load(currentStory.getTitleIconUrl())
                    .into(titleIconImageView);
        }
        if (currentStory.getTitle() != null) {
            titleTextView.setVisibility(View.VISIBLE);
            titleTextView.setText(currentStory.getTitle());
        }
        if (currentStory.getSubtitle() != null) {
            subtitleTextView.setVisibility(View.VISIBLE);
            subtitleTextView.setText(currentStory.getSubtitle());
        }
        if (currentStory.getDate() != null) {
            titleTextView.setText(titleTextView.getText()
                    + " "
                    + getDurationBetweenDates(currentStory.getDate(), Calendar.getInstance().getTime())
            );
        }
    }

    private void detectTouchEvents() {
        if (getActivity() == null) return;
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        final int width = displaymetrics.widthPixels;
        //noinspection all
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float xValue = event.getX();
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (!isDownClick) {
                        runTimer();
                    }
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    stopTimer();
                    if (elapsedTime < 500) {
                        if ((int) xValue <= (width / 2)) {
                            //Left
                            previousStory();
                        } else {
                            //Right
                            nextStory();
                        }
                    }
                    elapsedTime = 0;
                }
                return true;
            }
        });
    }

    private void setHeadingVisibility(int visibility) {
        titleTextView.setVisibility(visibility);
        titleCardView.setVisibility(visibility);
        subtitleTextView.setVisibility(visibility);
        closeImageButton.setVisibility(visibility);
    }


    private void createTimer() {
        timerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isDownClick) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    elapsedTime += 100;
                    if (elapsedTime >= 500 && !isPaused) {
                        isPaused = true;
                        if (getActivity() == null) return;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                storiesProgressView.pause();
                                setHeadingVisibility(View.GONE);
                            }
                        });
                    }
                }
                isPaused = false;
                if (getActivity() == null) return;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setHeadingVisibility(View.VISIBLE);
                        storiesProgressView.resume();
                    }
                });
            }
        });
    }

    private void runTimer() {
        isDownClick = true;
        createTimer();
        timerThread.start();
    }

    private void stopTimer() {
        isDownClick = false;
    }

    public static class Builder {

        private StoryView storyView;
        private FragmentManager fragmentManager;
        private Bundle bundle;
        private StoryViewHeaderInfo storyViewHeaderInfo;

        public Builder(FragmentManager fragmentManager) {
            this.fragmentManager = fragmentManager;
            this.bundle = new Bundle();
            this.storyViewHeaderInfo = new StoryViewHeaderInfo();
        }

        public Builder setImages(ArrayList<String> imagesUrls) {
            bundle.putSerializable(IMAGES_KEY, imagesUrls);
            return this;
        }

        public Builder setTitleText(String title) {
            storyViewHeaderInfo.setTitle(title);
            return this;
        }

        public Builder setSubtitleText(String subtitle) {
            storyViewHeaderInfo.setSubtitle(subtitle);
            return this;
        }

        public Builder setTitleLogoUrl(String url) {
            storyViewHeaderInfo.setTitleIconUrl(url);
            return this;
        }

        public Builder setDate(Date date) {
            storyViewHeaderInfo.setDate(date);
            return this;
        }

        public Builder setStoryDuration(long duration) {
            bundle.putLong(DURATION_KEY, duration);
            return this;
        }

        public Builder build() {
            if (storyView != null) {
                try {
                    throw new Exception("The StoryView has already been built!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return this;
            }
            storyView = new StoryView();
            bundle.putSerializable(HEADER_INFO_KEY, storyViewHeaderInfo);
            storyView.setArguments(bundle);
            return this;
        }

        public void show() {
            storyView.show(fragmentManager, TAG);
        }

        public void dismiss() {
            storyView.dismiss();
        }

        public Fragment getFragment() {
            return storyView;
        }

    }

}
