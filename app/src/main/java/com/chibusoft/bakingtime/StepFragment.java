package com.chibusoft.bakingtime;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


public class StepFragment extends Fragment {
    @BindView(R.id.step_description)
    TextView description;
    @BindView(R.id.next_btn)
    ImageButton next_button;
    @BindView(R.id.previous_btn)
    ImageButton previous_button;
    @BindView(R.id.thumbnail_img)
    ImageView thumbnail_img;

    private List<Baking.steps> mStepList;
    private int index;

    public static final String EXTRA_INDEX = "index";

    private SimpleExoPlayer player;
    private PlayerView playerView;
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();

    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;

    boolean loadPlayer = false;
    //boolean initialize_Exo = false;

    private static final String EXTRA_PLAYBACKPOSIITON = "playbackposition";
    private static final String EXTRA_CURRENTWINDOW = "currentwindow";
    private static final String EXTRA_PLAYWHENREADY = "playwhenready";

    public String VideoLink;



    // Define a new interface OnImageClickListener that triggers a callback in the host activity
    ClickListener mCallback;

    // OnImageClickListener interface, calls a method in the host activity named onImageSelected
    public interface ClickListener {
        void next(View view);
        void previous(View view);
    }


    public StepFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //here we load whatever we save in the savedInstanceState
        if(savedInstanceState != null) {
            mStepList = savedInstanceState.getParcelableArrayList(MainActivity.EXTRA_STEPS);
            index = savedInstanceState.getInt(EXTRA_INDEX,0);
            playbackPosition = savedInstanceState.getLong(EXTRA_PLAYBACKPOSIITON,0);
            currentWindow = savedInstanceState.getInt(EXTRA_CURRENTWINDOW,0);
            playWhenReady = savedInstanceState.getBoolean(EXTRA_PLAYWHENREADY, false);
        }



        // Inflate the Android-Me fragment layout
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, rootView);

        playerView = rootView.findViewById(R.id.video_view);


        shouldPlay();

       // if(mStepList != null) {
        //    description.setText(mStepList.get(index).description);

            setContent();
       // }

        navigationLoad();

        return rootView;
    }

    private void setContent()
    {
        if(mStepList != null) {
            description.setText(mStepList.get(index).description);


                if(mStepList.get(index).thumbnailURL.length() > 0) {

                    Picasso.get()
                            .load(mStepList.get(index).thumbnailURL)
                            .into(thumbnail_img, new Callback() {
                                @Override
                                public void onSuccess() {
                                }
                                @Override
                                public void onError(Exception e) {
                                    thumbnail_img.setVisibility(View.GONE);
                                }
                            });
                }
                else
                {
                    thumbnail_img.setVisibility(View.GONE);
                }
            }


    }

    private void navigationLoad()
    {
        if(index == 0) previous_button.setVisibility(View.INVISIBLE);
        if(index > 0) previous_button.setVisibility(View.VISIBLE);
        if(index == mStepList.size() -1) next_button.setVisibility(View.INVISIBLE);
        if(index < mStepList.size() -1 ) next_button.setVisibility(View.VISIBLE);

    }

    public void shouldPlay()
    {
        // VideoLink = mStepList.get(index).videoURL.length() > 0 ?
         //       mStepList.get(index).videoURL : mStepList.get(index).thumbnailURL;

        VideoLink = mStepList.get(index).videoURL;

        loadPlayer = VideoLink.length() > 0;

       // if(!loadPlayer) playerView.setVisibility(View.GONE);
        if(!loadPlayer){
            playerView.setVisibility(View.GONE);
            releasePlayer();
        }
        else
        {
           // if(!initialized_Exo) {
              // initialized_Exo = true;
                playerView.setVisibility(View.VISIBLE);
                initializePlayer();
           // }
        }


    }


    public void setData(List<Baking.steps> steps,int i){
        mStepList = steps;
        index = i;
        playbackPosition = 0;
    }

    public void setloadData(List<Baking.steps> steps,int i)
    {
        index = i;

        description.setText(mStepList.get(index).description);
        playbackPosition = 0;
        shouldPlay();
        navigationLoad();
    }

    public void loadNext()
    {

        if(index < mStepList.size() -1) index++;
       // description.setText(mStepList.get(index).description);
        setContent();

        if(index > 0) previous_button.setVisibility(View.VISIBLE);
        if(index == mStepList.size() -1) next_button.setVisibility(View.INVISIBLE);

        shouldPlay();
    }

    public void loadPrevious()
    {

        if(index > 0) index--;
       // description.setText(mStepList.get(index).description);
        setContent();

        if(index < mStepList.size() - 1) next_button.setVisibility(View.VISIBLE);
        if(index == 0) previous_button.setVisibility(View.INVISIBLE);

        shouldPlay();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // remove the reference to your Activity
        mCallback = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface for onclick
        // If not, it throws an exception
        try {
            mCallback = (ClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(MainActivity.EXTRA_STEPS, (ArrayList) mStepList);
        outState.putInt(EXTRA_INDEX,index);
        if(player != null) {
            outState.putLong(EXTRA_PLAYBACKPOSIITON,  player.getCurrentPosition());
            outState.putInt(EXTRA_CURRENTWINDOW, player.getCurrentWindowIndex());
            outState.putBoolean(EXTRA_PLAYWHENREADY,  player.getPlayWhenReady());
        }
        super.onSaveInstanceState(outState);
    }



    private void initializePlayer() {
            if (loadPlayer) {
              //  VideoLink = mStepList.get(index).videoURL.length() > 0 ?
                 //       mStepList.get(index).videoURL : mStepList.get(index).thumbnailURL;


                if (player == null) {
                    // a factory to create an AdaptiveVideoTrackSelection
                    //this is good for the web url stream to choose adaptive video quality
                    //else use  new DefaultTrackSelector(),
                    TrackSelection.Factory adaptiveTrackSelectionFactory =
                            new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);

                    player = ExoPlayerFactory.newSimpleInstance(
                            new DefaultRenderersFactory(getContext()),
                            new DefaultTrackSelector(adaptiveTrackSelectionFactory),
                            new DefaultLoadControl());
                    playerView.setPlayer(player);

                }

                MediaSource mediaSource = buildMediaSource(Uri.parse(VideoLink));

                if(playbackPosition != 0)
                {
                    //this where you state if you want to reset player position and state or not
                    player.prepare(mediaSource, false, true);
                    player.seekTo(playbackPosition);
                    player.setPlayWhenReady(playWhenReady);
                    player.getPlaybackState();
                }
                else {

                    //this where you state if you want to reset player position and state or not
                    player.prepare(mediaSource, true, false);
                    player.seekTo(playbackPosition);
                    player.setPlayWhenReady(playWhenReady);
                    player.getPlaybackState();
                }

            }
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
           // initialized_Exo = false;

        }
    }

    private MediaSource buildMediaSource(Uri uri) {

        String userAgent = "exoplayer-codelab";

        if (uri.getLastPathSegment().contains("mp3") || uri.getLastPathSegment().contains("mp4")) {
            return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory(userAgent))
                    .createMediaSource(uri);
        } else if (uri.getLastPathSegment().contains("m3u8")) {
            return new HlsMediaSource.Factory(new DefaultHttpDataSourceFactory(userAgent))
                    .createMediaSource(uri);
        } else {
            DashChunkSource.Factory dashChunkSourceFactory = new DefaultDashChunkSource.Factory(
                    new DefaultHttpDataSourceFactory("ua", BANDWIDTH_METER));
            DataSource.Factory manifestDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent);
            return new DashMediaSource.Factory(dashChunkSourceFactory, manifestDataSourceFactory).
                    createMediaSource(uri);
        }
    }

    //Exoplayer Lifecycle stuff
    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23 ) {
           // initializePlayer();
            shouldPlay();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 ) {
            //initializePlayer();
            shouldPlay();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUiFullScreen() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {

            hideSystemUiFullScreen();
        }
        else {
            hideSystemUi();
        }
    }

}
