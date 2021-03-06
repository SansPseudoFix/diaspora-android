package com.github.dfa.diaspora_android.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by gsantner on 20.03.16. Part of Diaspora for Android.
 */
public class AppSettings {
    private final SharedPreferences prefApp;
    private final SharedPreferences prefPod;
    private final Context context;

    public AppSettings(Context context) {
        this.context = context.getApplicationContext();
        prefApp = this.context.getSharedPreferences("app", Context.MODE_PRIVATE);
        prefPod = this.context.getSharedPreferences("pod0", Context.MODE_PRIVATE);
    }

    public Context getApplicationContext() {
        return context;
    }

    public void clearPodSettings() {
        prefPod.edit().clear().apply();
    }

    public void clearAppSettings() {
        prefApp.edit().clear().apply();
    }

    private void setString(SharedPreferences pref, String key, String value) {
        pref.edit().putString(key, value).apply();
    }

    private void setInt(SharedPreferences pref, String key, int value) {
        pref.edit().putInt(key, value).apply();
    }

    private void setBool(SharedPreferences pref, String key, boolean value) {
        pref.edit().putBoolean(key, value).apply();
    }

    private void setStringArray(SharedPreferences pref, String key, Object[] values) {
        StringBuffer sb = new StringBuffer();
        for (Object value : values) {
            sb.append("%%%");
            sb.append(value.toString());
        }
        setString(pref, key, sb.toString().replaceFirst("%%%", ""));
    }

    private String[] getStringArray(SharedPreferences pref, String key) {
        String value = pref.getString(key, "%%%");
        if (value.equals("%%%")) {
            return new String[0];
        }
        return value.split("%%%");
    }

    /*
    //   Preferences
     */
    public static class PREF {
        private static final String PREVIOUS_PODLIST = "previousPodlist";
        private static final String IS_LOAD_IMAGES = "loadImages";
        private static final String MINIMUM_FONT_SIZE = "minimumFontSize";
        private static final String PODUSERPROFILE_AVATAR_URL = "podUserProfile_avatar";
        private static final String PODUSERPROFILE_NAME = "podUserProfile_name";
        private static final String PODUSERPROFILE_ID = "podUserProfile_guid";
        private static final String PODDOMAIN = "podDomain";
        private static final String PODUSERPROFILE_ASPECTS = "podUserProfile_aspects";
        private static final String PROXY_ENABLED = "isProxyEnabled";
        private static final String PROXY_HOST = "proxyHost";
        private static final String PROXY_PORT = "proxyPort";
    }


    /*
    //     Setters & Getters
    */
    public String getProfileId() {
        return prefPod.getString(PREF.PODUSERPROFILE_ID, "");
    }

    public void setProfileId(String profileId) {
        setString(prefPod, PREF.PODUSERPROFILE_ID, profileId);
    }


    public boolean isLoadImages() {
        return prefApp.getBoolean(PREF.IS_LOAD_IMAGES, true);
    }

    public void setLoadImages(boolean loadImages) {
        setBool(prefApp, PREF.IS_LOAD_IMAGES, loadImages);
    }


    public int getMinimumFontSize() {
        return prefApp.getInt(PREF.MINIMUM_FONT_SIZE, 8);
    }

    public void setMinimumFontSize(int minimumFontSize) {
        setInt(prefApp, PREF.MINIMUM_FONT_SIZE, minimumFontSize);
    }

    public String getAvatarUrl() {
        return prefPod.getString(PREF.PODUSERPROFILE_AVATAR_URL, "");
    }

    public void setAvatarUrl(String avatarUrl) {
        setString(prefPod, PREF.PODUSERPROFILE_AVATAR_URL, avatarUrl);
    }

    public String getName() {
        return prefPod.getString(PREF.PODUSERPROFILE_NAME, "");
    }

    public void setName(String name) {
        setString(prefPod, PREF.PODUSERPROFILE_NAME, name);
    }

    public String getPodDomain() {
        return prefPod.getString(PREF.PODDOMAIN, "");
    }

    public void setPodDomain(String podDomain) {
        setString(prefPod, PREF.PODDOMAIN, podDomain);
    }

    public boolean hasPodDomain() {
        return !prefPod.getString(PREF.PODDOMAIN, "").equals("");
    }

    public String[] getPreviousPodlist() {
        return getStringArray(prefApp, PREF.PREVIOUS_PODLIST);
    }

    public void setPreviousPodlist(String[] pods) {
        setStringArray(prefApp, PREF.PREVIOUS_PODLIST, pods);
    }

    public void setPodAspects(PodAspect[] aspects) {
        setStringArray(prefPod, PREF.PODUSERPROFILE_ASPECTS, aspects);
    }

    public PodAspect[] getPodAspects() {
        String[] s= getStringArray(prefPod, PREF.PODUSERPROFILE_ASPECTS);
        PodAspect[] aspects = new PodAspect[s.length];
        for(int i=0; i < aspects.length; i++){
            aspects[i] = new PodAspect(s[i]);
        }
        return aspects;
    }

    public void setProxyEnabled(boolean enabled) {
        //commit instead of apply because the app is likely to be killed before apply is called.
        prefApp.edit().putBoolean(PREF.PROXY_ENABLED, enabled).commit();
    }

    /**
     * Default return value: false
     * @return whether proxy is enabled or not
     */
    public boolean isProxyEnabled() {
        return prefApp.getBoolean(PREF.PROXY_ENABLED, false);
    }

    public void setProxyHost(String host) {
        setString(prefApp, PREF.PROXY_HOST, host);
    }

    /**
     * Default value: ""
     * @return proxy host
     */
    public String getProxyHost() {
        return prefApp.getString(PREF.PROXY_HOST, "");
    }

    public void setProxyPort(int port) {
        setInt(prefApp, PREF.PROXY_PORT, port);
    }

    /**
     * Default value: 0
     * @return proxy port
     */
    public int getProxyPort() {
        return prefApp.getInt(PREF.PROXY_PORT, 0);
    }
}
