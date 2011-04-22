package eu.chuvash.android.rovar;

import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
/*
 * Settings is an Activity where the user can adjust the settings
 * It extends PreferenceActivity
 * It includes even static methods to get the saved settings
 * Inspired by Ed Burnette's Hello, Android, 1st ed. (106ff)
 */
public class Settings extends PreferenceActivity {
	 private static final String TAG = "Rövarspråksöversättare Settings";
	 private static final String OPT_MUSIC = "music";
	 private static final boolean OPT_MUSIC_DEFAULT = true;
	 
	 private static final String OPT_TRANSLATE_2ROV = "translate_to_rovar";
	 private static final String OPT_TRANSLATE_2ROV_DEFAULT = "";
	 
	 private static final String OPT_TRANSLATE_FROM_ROV = "translate_from_rovar";
	 private static final String OPT_TRANSLATE_FROM_ROV_DEFAULT = "";
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 addPreferencesFromResource(R.xml.settings);
	 }
	 private static SharedPreferences getPref(Context context) {
		 SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);
		 return p;
	 }
	 private static SharedPreferences.Editor getPrefEditor(Context context) {
		 SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);
		 SharedPreferences.Editor editor = p.edit();
		 return editor;
	 }
	 public static boolean isMusicWanted(Context context) {
		 return getPref(context).getBoolean(OPT_MUSIC, OPT_MUSIC_DEFAULT);
	 }
	 public static String getPreviousTranslateToRovar(Context context) {
		 return getPref(context).getString(OPT_TRANSLATE_2ROV, OPT_TRANSLATE_2ROV_DEFAULT);
	 }
	 public static void savePreviousTranslateToRovar(Context context, String translation) {
		 boolean wentItWell = getPrefEditor(context).putString(OPT_TRANSLATE_2ROV, translation).commit();
		 if (!wentItWell) {
			 Log.e(TAG, "Kunde tyvärr inte spara översättningar till rövarspråket");
		 }
	 }
	 public static void savePreviousTranslateFromRovar(Context context, String translation) {
		 boolean wentItWell = getPrefEditor(context).putString(OPT_TRANSLATE_FROM_ROV, translation).commit();
		 if (!wentItWell) {
			 Log.e(TAG, "Kunde tyvärr inte spara översättningar från rövarspråket");
		 }
	 }
	 public static String getPreviousTranslateFromRovar(Context context) {
		 return getPref(context).getString(OPT_TRANSLATE_FROM_ROV, OPT_TRANSLATE_FROM_ROV_DEFAULT);
	 }
	 
}
