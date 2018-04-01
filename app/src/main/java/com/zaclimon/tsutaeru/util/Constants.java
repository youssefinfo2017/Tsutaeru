package com.zaclimon.tsutaeru.util;

import android.content.ComponentName;

import com.zaclimon.xipl.service.ProviderTvInputService;

/**
 * List of constants to be used throughout the application's lifecycle.
 *
 * @author zaclimon
 * Creation date: 17/06/17
 */

public class Constants {

    /**
     * {@link ComponentName} used for defining easily an input id for the application
     */
    public static final ComponentName TV_INPUT_SERVICE_COMPONENT = new ComponentName("com.zaclimon.tsutaeru", ProviderTvInputService.class.getName());

    // Preferences stuff
    public static final String TSUTAERU_PREFERENCES = "TsutaeruSharedPreferences";
    public static final String PROVIDER_URL_PREFERENCE = "provider_url";
    public static final String USERNAME_PREFERENCE = "username";
    public static final String PASSWORD_PREFERENCE = "password";
    public static final String STREAM_TYPE_PREFERENCE = "stream_type";
    public static final String STREAM_TYPE_MPEG_TS = "ts";
    public static final String STREAM_TYPE_HLS = "hls";
    public static final String CHANNEL_LOGO_PREFERENCE = "channel_logo";
    public static final String VIDEO_FIT_SCREEN_PREFERENCE = "video_fit_screen";
    public static final String UK_REGION_PREFERENCE = "uk_region";
    public static final String NA_REGION_PREFERENCE = "na_region";
    public static final String INTERNATIONAL_REGION_PREFERENCE = "int_region";
    public static final String EPG_OFFSET_PREFERENCE = "epg_offset";
    public static final String EXTERNAL_PLAYER_PREFERENCE = "external_player";
    public static final String CHANNEL_GENRE_PREFERENCE = "genre_";

    // xipl stuff
    public static final String[] CHANNEL_GENRES = com.zaclimon.xipl.Constants.CHANNEL_GENRES;
    public static final String CHANNEL_GENRES_PROVIDER = com.zaclimon.xipl.Constants.CHANNEL_GENRES_PROVIDER;
}