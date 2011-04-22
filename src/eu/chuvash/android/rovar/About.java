package eu.chuvash.android.rovar;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author Anatoly Mironov
 * 2010-07-22
 * This activity is an about box
 * Inspired of Burnette (2008:47) Hello, Android
 * This activity is shown with the predefined "@android:style/Theme.Dialog"
 *
 */
public class About extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
	}
}
