package com.artist.web.bakerscorner.fragments;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artist.web.bakerscorner.R;
import com.artist.web.bakerscorner.data.Steps;
import com.artist.web.bakerscorner.handler.ExoPlayerVideoHandler;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.util.ArrayList;

/**
 * Created by User on 14-Apr-18.
 */

public class StepVideoFragment extends Fragment {

    private static final String ARG_STEP = "step_selected";
    private static final String ARG_STEP_LIST = "step_list";
    View displayView;
    String videoUrl;
    private boolean destroyVideo = true;
    private ArrayList<Steps> displayStep;
    private int position;
    private SimpleExoPlayerView mPlayerView;

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
        displayStep = getArguments().getParcelableArrayList(ARG_STEP_LIST);
        position = getArguments().getInt(ARG_STEP);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        displayView = inflater.inflate(R.layout.fragment_steps_video, container, false);

        mPlayerView = displayView.findViewById(R.id.playerView);
        TextView mTextViewDescription = displayView.findViewById(R.id.step_instruction);

        Steps stepDisplay = displayStep.get(position);

        if (stepDisplay.getDescription().length() != 0) {
            mTextViewDescription.setText(stepDisplay.getDescription());
        } else {
            mTextViewDescription.setText(R.string.no_instructions);
        }

        videoUrl = stepDisplay.getVideoUrl();
        if (TextUtils.isEmpty(videoUrl)) {
            videoUrl = stepDisplay.getThumbnailUrl();
        }
        // Initialize the player.
        initializePlayer(Uri.parse(videoUrl));

        return displayView;
    }

    private void initializePlayer(Uri videoUri) {

        if (videoUri == null)
            mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(displayView.getContext().getResources(),
                    R.drawable.cake));
        if (videoUri != null && mPlayerView != null) {
            ExoPlayerVideoHandler.getInstance().prepareExoPlayerForUri(displayView.getContext(), videoUri, mPlayerView);
            destroyVideo = false;
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
        if (videoUrl != null && mPlayerView != null) {
            ExoPlayerVideoHandler.getInstance().prepareExoPlayerForUri(displayView.getContext(), Uri.parse(videoUrl), mPlayerView);
        }
        ExoPlayerVideoHandler.getInstance().goToForeground();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (destroyVideo) {
            ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
        }
    }
}