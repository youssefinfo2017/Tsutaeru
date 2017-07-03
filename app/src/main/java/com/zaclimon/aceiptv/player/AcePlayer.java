package com.zaclimon.aceiptv.player;

import android.content.Context;
import android.media.PlaybackParams;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.Surface;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.media.tv.companionlibrary.TvPlayer;
import com.zaclimon.aceiptv.BuildConfig;
import com.zaclimon.aceiptv.R;

import java.util.ArrayList;
import java.util.List;

import static android.R.string.defaultMsisdnAlphaTag;
import static android.R.string.ok;

/**
 * Concrete implementation of the {@link TvPlayer} interface made for A.C.E IPTV.
 *
 * This current implementation is based on ExoPlayer 2.x series.
 *
 * @author zaclimon
 * Creation date: 11/06/17
 *
 */

public class AcePlayer implements TvPlayer {

    private SimpleExoPlayer player;
    private List<TvPlayer.Callback> callbacks;
    private String streamUrl;

    /**
     * Main constructor of the player.
     * @param context the context used to initialize the player
     * @param url the url containing the required stream to be played
     */
    public AcePlayer(Context context, String url) {
        callbacks = new ArrayList<>();
        streamUrl = url;
        init(context);
    }

    /**
     * Initializes and prepares the player but doesn't make it play the content.
     * @param context the context needed to initialize the player.
     */
    private void init(Context context) {
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
        player.prepare(getMediaSource(context));
    }

    /**
     * Gets the required {@link MediaSource} depending on the stream source.
     * @param context The required context to get the correct MediaSource
     * @return the MediaSource used for the playback.
     */
    private MediaSource getMediaSource(Context context) {
        Uri mediaUrl = Uri.parse(streamUrl);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, context.getString(R.string.app_name)));
        MediaSource mediaSource;

         /*
          Use only a HlsMediaSource if we're sure that we're on a HLS stream. Any other one should
          be an ExtractorMediaSource.
         */

        if (streamUrl.endsWith(".m3u8")) {
            mediaSource = new HlsMediaSource(mediaUrl, dataSourceFactory, new Handler(), null);
        } else {
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            mediaSource = new ExtractorMediaSource(mediaUrl, dataSourceFactory, extractorsFactory, new Handler(), null);
        }

        return (mediaSource);
    }

    @Override
    public void play() {
        Log.d(getClass().getSimpleName(), "play() called!");
        player.setPlayWhenReady(true);
    }

    @Override
    public void pause() {
        player.setPlayWhenReady(false);
    }

    @Override
    public void setVolume(float volume) {
        player.setVolume(volume);
    }

    @Override
    public void registerCallback(TvPlayer.Callback callback) {
        callbacks.add(callback);
    }

    @Override
    public void unregisterCallback(TvPlayer.Callback callback) {
        callbacks.remove(callback);
    }

    @Override
    public void seekTo(long positionMs) {
        player.seekTo(positionMs);
    }

    @Override
    public long getCurrentPosition() {
        return (player.getCurrentPosition());
    }

    @Override
    public void setSurface(Surface surface) {
        player.setVideoSurface(surface);
    }

    @Override
    public long getDuration() {
        return (player.getDuration());
    }

    @Override
    public void setPlaybackParams(PlaybackParams params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PlaybackParameters playbackParameters = new PlaybackParameters(params.getSpeed(), params.getPitch());
            player.setPlaybackParameters(playbackParameters);
        }
    }

    /**
     * Stops the player if required
     */
    public void stop() {
        player.stop();
    }

    /**
     * Releases the resources used by the player
     */
    public void release() {
        player.release();
    }

    /**
     * Re-prepares a streaming for the player
     * @param context the context needed to re-prepare the player
     */
    public void restart(Context context) {
        player.prepare(getMediaSource(context));
    }

    /**
     * Used to determine what is the main video format used by the player while streaming
     * @return the video format used while streaming
     */
    public Format getVideoFormat() {
        return (player.getVideoFormat());
    }

    /**
     * Used to determine what is the main audio format used by the player while streaming.
     * @return the audio format used while streaming
     */
    public Format getAudioFormat() {
        return (player.getAudioFormat());
    }

    /**
     * Adds listeners for eventual callbacks
     * @param listener the given listener for callbacks.
     */
    public void addListener(ExoPlayer.EventListener listener) {
        player.addListener(listener);
    }
}
