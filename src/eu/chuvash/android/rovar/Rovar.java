package eu.chuvash.android.rovar;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class Rovar extends Activity {
	private static final String TAG = "Rovar";
	private MediaPlayer mp = null;

	private Button buttonTranslate2Rovar;
	private Button buttonTranslateFromRovar;
	private Button buttonAbout;
	private Button buttonExit;

	private Animation anim1;
	private Animation anim2;
	private Animation anim3;
	private Animation anim4;

	private int playerPosition = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		initializeButtons();
		initializeAnimations();
		animateFadeIn();
	}

	public void onPause() {
		super.onPause();
		if (mp != null) {
			mp.pause();
			// mp.getCurrentPosition()
		}
	}

	public void onResume() {
		super.onResume();
		setupMediaPlayer(); // check if Settings have changed
		animateFadeIn();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mp != null) {
			mp.release();
			mp = null;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.settings:
			startActivity(new Intent(this, Settings.class));
			break;
		}
		return false;
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		if (mp != null && mp.isPlaying()) {
			playerPosition = mp.getCurrentPosition();
			Log.d(TAG, "playerPosition är " + playerPosition);
			savedInstanceState.putInt("playerPosition", playerPosition);
		}
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		playerPosition = savedInstanceState.getInt("playerPosition", 0);
		Log.d(TAG, "playerPosition onRestore " + playerPosition);
		setupMediaPlayer();
		super.onRestoreInstanceState(savedInstanceState);
	}

	public void setupMediaPlayer() {
		if (Settings.isMusicWanted(this)) {
			// init mp if null
			if (mp == null) {
				mp = MediaPlayer.create(this, R.raw.idas_sommarvisa);
				mp.setLooping(true);
				mp.seekTo(playerPosition);
				Log.d(TAG, "setupMediaPlayer playerPosition " + playerPosition);
			}
			mp.start();
		}
	}

	public void initializeButtons() {
		buttonTranslate2Rovar = (Button) findViewById(R.id.buttonTranslate2Rovar);
		buttonTranslateFromRovar = (Button) findViewById(R.id.buttonTranslateFromRovar);
		buttonAbout = (Button) findViewById(R.id.buttonAbout);
		buttonExit = (Button) findViewById(R.id.buttonExit);
	}

	public void initializeAnimations() {
		// get animations - all the same, kan inte återanvända
		anim1 = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		anim2 = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		anim3 = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		anim4 = AnimationUtils.loadAnimation(this, R.anim.fade_in);

		// setStartOffset
		anim2.setStartOffset(300);
		anim3.setStartOffset(500);
		anim4.setStartOffset(700);
	}

	public void animateFadeIn() {
		buttonTranslate2Rovar.startAnimation(anim1);
		buttonTranslateFromRovar.startAnimation(anim2);
		buttonAbout.startAnimation(anim3);
		buttonExit.startAnimation(anim4);
	}

	public void buttonTranslate2Rovar_click(View v) {
		Intent i = new Intent(this, Translate2RovarActivity.class);
		i.putExtra("translateToRovar", true);
		startActivity(i);
	}

	public void buttonTranslateFromRovar_click(View v) {
		Intent i = new Intent(this, Translate2RovarActivity.class);
		i.putExtra("translateToRovar", false);
		startActivity(i);
	}

	public void buttonAbout_click(View v) {
		Intent i = new Intent(this, About.class);
		startActivity(i);
		// Context context = getApplicationContext();
		// CharSequence text = getString(R.string.app_name) + "\n\n"
		// + getString(R.string.aboutTheProgram);
		// int duration = Toast.LENGTH_LONG;
		//
		// Toast toast = Toast.makeText(context, text, duration);
		// toast.show();
	}

	public void buttonExit_click(View v) {
		finish();
	}
}