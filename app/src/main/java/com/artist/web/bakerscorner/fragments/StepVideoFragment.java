package com.artist.web.bakerscorner.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artist.web.bakerscorner.R;
import com.artist.web.bakerscorner.data.Steps;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

/**
 * Created by User on 14-Apr-18.
 */

public class StepVideoFragment extends Fragment {

    private static final String ARG_STEP = "step_selected";
    private static final String ARG_STEP_LIST = "step_list";

    private ArrayList<Steps> displayStep;
    private int position;
    private SimpleExoPlayer mExoPlayer;
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

        View displayView = inflater.inflate(R.layout.fragment_steps_video, container, false);

        mPlayerView = displayView.findViewById(R.id.playerView);
        TextView mTextViewDescription = displayView.findViewById(R.id.step_instruction);

        Steps stepDisplay = displayStep.get(position);

        if (stepDisplay.getDescription().length() != 0) {
            mTextViewDescription.setText(stepDisplay.getDescription());
        } else {
            mTextViewDescription.setText(R.string.no_instructions);
        }

        String videoUrl = stepDisplay.getVideoUrl();
        if (videoUrl == null) {
            videoUrl = stepDisplay.getThumbnailUrl();
        }

        // Initialize the player.
        initializePlayer(Uri.parse(videoUrl));

        return displayView;
    }

    private void initializePlayer(Uri videoUri) {

        if (mExoPlayer == null) {
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), new DefaultTrackSelector(),
                    new DefaultLoadControl());
            mPlayerView.setPlayer(mExoPlayer);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity(), "Baking Videos");
            MediaSource mediaSource = new ExtractorMediaSource(videoUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }

    }
}