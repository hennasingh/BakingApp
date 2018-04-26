package com.artist.web.bakerscorner.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artist.web.bakerscorner.R;
import com.artist.web.bakerscorner.models.Steps;
import com.artist.web.bakerscorner.handler.ExoPlayerVideoHandler;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by User on 14-Apr-18.
 */

public class StepVideoFragment extends Fragment {

    private static final String ARG_STEP = "step_selected";
    private static final String ARG_STEP_LIST = "step_list";
    private static final String PLAYER_STATE = "player_state";
    private static final String PLAYER_POSITION = "player_position";

    View displayView;
    @BindView(R.id.playerView)
    SimpleExoPlayerView mPlayerView;
    @Nullable
    @BindView(R.id.step_instruction)
    TextView mTextViewDescription;
    @BindView(R.id.imageViewNoVideo)
    ImageView mImageViewNoVideo;
    private boolean destroyVideo = true;
    private ArrayList<Steps> displayStepList;
    private int position;
    private Steps displayStep;
    private String mStepInstruction;
    private String mVideoUrl;
    private String mImageUrl;
    private Unbinder unbinder;

    public static StepVideoFragment newInstance(int position, ArrayList<Steps> stepList) {
        Bundle args = new Bundle();
        args.putInt(ARG_STEP, position);
        args.putParcelableArrayList(ARG_STEP_LIST, stepList);
        StepVideoFragment fragment = new StepVideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        displayStepList = getArguments().getParcelableArrayList(ARG_STEP_LIST);
        position = getArguments().getInt(ARG_STEP);
        getStepDetails();

        if (savedInstanceState != null) {
            ExoPlayerVideoHandler.getInstance().receivePlaybackState(
                    savedInstanceState.getBoolean(PLAYER_STATE)
            );
            ExoPlayerVideoHandler.getInstance().receivePlayerPosition(
                    savedInstanceState.getLong(PLAYER_POSITION)
            );
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        displayView = inflater.inflate(R.layout.fragment_steps_video, container, false);

        unbinder = ButterKnife.bind(this, displayView);

        if (savedInstanceState == null) {
            createMediaPlayer();
        }

        displaySteps();
        return displayView;
    }

    private void displaySteps() {

        if (mTextViewDescription != null) {
            if (Character.isDigit(mStepInstruction.charAt(0))) {
                mTextViewDescription.setText(mStepInstruction.substring(2));
            } else {
                mTextViewDescription.setText(mStepInstruction);
            }
        }
    }


    private void createMediaPlayer() {

        if (!TextUtils.isEmpty(mVideoUrl)) {

            //hide the overlay imageView
            mImageViewNoVideo.setVisibility(View.GONE);
            // Initialize the player.
            initializePlayer(Uri.parse(mVideoUrl));
        } else {
            mPlayerView.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(mImageUrl)) {
                Picasso.with(displayView.getContext())
                        .load(mImageUrl)
                        .placeholder(R.drawable.cake)
                        .error(R.drawable.cake)
                        .into(mImageViewNoVideo);
            } else {
                mImageViewNoVideo.setImageResource(R.drawable.cake);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(PLAYER_STATE, ExoPlayerVideoHandler.getInstance().savePlaybackState());
        outState.putLong(PLAYER_POSITION, ExoPlayerVideoHandler.getInstance().savePlayerPosition());
    }

    private void initializePlayer(Uri videoUri) {

        if (videoUri != null) {
            ExoPlayerVideoHandler.getInstance().prepareExoPlayerForUri(displayView.getContext(),
                    videoUri, mPlayerView);
            destroyVideo = false;
            ExoPlayerVideoHandler.getInstance().goToForeground();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ExoPlayerVideoHandler.getInstance().goToBackground();

    }

    @Override
    public void onResume() {
        super.onResume();
        createMediaPlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (destroyVideo) {
            ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
        }

    }

    public void getStepDetails() {
        displayStep = displayStepList.get(position);
        mStepInstruction = displayStep.getDescription();
        mVideoUrl = displayStep.getVideoUrl();
        mImageUrl = displayStep.getThumbnailUrl();
    }
}