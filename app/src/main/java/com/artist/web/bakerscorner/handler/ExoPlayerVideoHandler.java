package com.artist.web.bakerscorner.handler;

import android.content.Context;
import android.net.Uri;
import android.view.SurfaceView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by User on 16-Apr-18.
 */

public class ExoPlayerVideoHandler {

    private static final String TAG = ExoPlayerVideoHandler.class.getSimpleName();

    private static ExoPlayerVideoHandler instance;
    private static long playerPosition;
    private TrackSelector trackSelector;
    private SimpleExoPlayer player;
    private Uri playerUri;
    private boolean isPlayerPlaying;

    public ExoPlayerVideoHandler() {
    }


    public void prepareExoPlayerForUri(Context context, Uri uri, SimpleExoPlayerView exoPlayerView) {
        if (context != null && uri != null && exoPlayerView != null) {
            if (!uri.equals(playerUri) || player == null) {
                // 1. Create a default TrackSelector
                // Measures bandwidth during playback. Can be null if not required.
                BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);

                trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

                //2. Create the Player
                player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
                playerUri = uri;

                //3. Creating MediaSource
                // Produces DataSource instances through which media data is loaded.
                String userAgent = Util.getUserAgent(context, TAG);
                DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, userAgent);
                // This is the MediaSource representing the media to be played.
                MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(playerUri);

                // Prepare the player with the source.
                player.prepare(videoSource);
            }
            player.clearVideoSurface();
            player.setVideoSurfaceView((SurfaceView) exoPlayerView.getVideoSurfaceView());
            player.seekTo(playerPosition);
            exoPlayerView.setPlayer(player);
        }
    }

    public void releaseVideoPlayer() {
        if (player != null) {
            playerPosition = player.getCurrentPosition();
            player.stop();
            player.release();
            player = null;

        }
        if (trackSelector != null) {
            trackSelector = null;
        }

    }

    public void goToBackground() {
        if (player != null) {
            isPlayerPlaying = player.getPlayWhenReady();
            playerPosition = player.getCurrentPosition();
        }
    }

    public boolean savePlaybackState() {
        if (player != null) {
            isPlayerPlaying = player.getPlayWhenReady();
            return isPlayerPlaying;
        }
        return false;
    }

    public void receivePlaybackState(boolean isPlayerPlaying) {
        this.isPlayerPlaying = isPlayerPlaying;
    }

    public void goToForeground() {
        if (player != null) {
            player.setPlayWhenReady(isPlayerPlaying);
            player.seekTo(playerPosition);
        }
    }

    public long savePlayerPosition() {
        if (player != null) {
            playerPosition = player.getCurrentPosition();
            return playerPosition;
        }
        return 0;
    }

    public void receivePlayerPosition(Long position) {
        playerPosition = position;
    }
}
