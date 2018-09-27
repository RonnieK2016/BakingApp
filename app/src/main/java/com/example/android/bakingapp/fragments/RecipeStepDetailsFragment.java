package com.example.android.bakingapp.fragments;

import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.media.VolumeShaper;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Constraints;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.RecipeStepsAdapter;
import com.example.android.bakingapp.domain.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by angelov on 9/23/2018.
 */

public class RecipeStepDetailsFragment extends Fragment implements ExoPlayer.EventListener {

    private static final String TAG = RecipeStepDetailsFragment.class.getSimpleName();

    @Getter
    @Setter
    private Step step;
    private Unbinder unbinder;
    @BindView(R.id.step_video_player)
    public SimpleExoPlayerView mStepVideoPlayerView;
    @BindView(R.id.step_description)
    public TextView mStepDescriptionView;
    @BindBool(R.bool.is_tablet)
    public boolean isTwoPane;
    SimpleExoPlayer mSimpleExoPlayer;
    private MediaSessionCompat mMediaSessionCompat;
    private PlaybackStateCompat.Builder mSBuilder;
    private long currentPlaybackPosition = 0;
    private static final String CURRENT_STEP_SAVED_TAG =  "CURRENT_STEP_SAVED_TAG";
    private static final String PLAYBACK_POSITION_SAVED_TAG =  "PLAYBACK_POSITION_SAVED_TAG";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.step_details_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        if(savedInstanceState != null) {
            step = savedInstanceState.getParcelable(CURRENT_STEP_SAVED_TAG);
            currentPlaybackPosition = savedInstanceState.getLong(PLAYBACK_POSITION_SAVED_TAG);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStepDescriptionView.setText(step.getDescription());

        if(!StringUtils.isEmpty(step.getVideoURL()) && (mSimpleExoPlayer == null)) {
            mStepVideoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                    (getResources(), R.drawable.ic_cupcake_2));
            mStepVideoPlayerView.setVisibility(View.VISIBLE);
            setupExoPlayer();
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE && !isTwoPane) {
                showPlayerFullScreen();
                hideUIElements();
            }
        }
        else {
            mStepVideoPlayerView.setVisibility(View.GONE);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            setupExoPlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23) {
            setupExoPlayer();
        }
    }

    private void hideUIElements() {
        mStepDescriptionView.setVisibility(View.GONE);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                (View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN)
        );
    }

    private void showPlayerFullScreen() {
        mStepVideoPlayerView.getLayoutParams().height = Constraints.LayoutParams.MATCH_PARENT;
        mStepVideoPlayerView.getLayoutParams().width = Constraints.LayoutParams.MATCH_PARENT;
    }

    private void setupExoPlayer() {
        if (mSimpleExoPlayer == null && !StringUtils.isEmpty(step.getVideoURL())) {

            initializeMediaSession();

            Uri videoUri = Uri.parse(step.getVideoURL());
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mStepVideoPlayerView.setPlayer(mSimpleExoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            mSimpleExoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "BakingAppVideoPlayer");
            MediaSource mediaSource = new ExtractorMediaSource(videoUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);

            mSimpleExoPlayer.seekTo(currentPlaybackPosition);

            mSimpleExoPlayer.prepare(mediaSource);
            mSimpleExoPlayer.setPlayWhenReady(true);
        }
    }

    private void initializeMediaSession() {

        if(mMediaSessionCompat != null) {
            return;
        }

        // Create a MediaSessionCompat.
        mMediaSessionCompat = new MediaSessionCompat(getContext(), TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSessionCompat.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSessionCompat.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mSBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSessionCompat.setPlaybackState(mSBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSessionCompat.setCallback(new MediaSessionCompat.Callback(){
            @Override
            public void onPlay() {
                mSimpleExoPlayer.setPlayWhenReady(true);
            }

            @Override
            public void onPause() {
                mSimpleExoPlayer.setPlayWhenReady(false);
            }

            @Override
            public void onSkipToPrevious() {
                mSimpleExoPlayer.seekTo(0);
            }

        });

        // Start the Media Session since the activity is active.
        mMediaSessionCompat.setActive(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CURRENT_STEP_SAVED_TAG, step);
        outState.putLong(PLAYBACK_POSITION_SAVED_TAG, currentPlaybackPosition);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        //rememeber position
        if(mSimpleExoPlayer != null) {
            currentPlaybackPosition = mSimpleExoPlayer.getCurrentPosition();
        }

        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            mSBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mSimpleExoPlayer.getCurrentPosition(), 1f);
        } else if((playbackState == ExoPlayer.STATE_READY)){
            mSBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mSimpleExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSessionCompat.setPlaybackState(mSBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }
    private void releasePlayer() {
        if (mSimpleExoPlayer != null) {
            mSimpleExoPlayer.stop();
            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;
            mMediaSessionCompat.setActive(false);
        }
    }
}
