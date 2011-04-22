package eu.chuvash.android.rovar;

import eu.chuvash.android.rovar.translator.Translator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.EditText;
import android.widget.Toast;

public class Translate2RovarActivity extends Activity {
	private static final int CLEAR = 0;
	private boolean translateToRovar;
	private Translator translator;
	private EditText editTextEntry;
	private EditText editTextOutputTranslation;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.torovar);
		
		defineIfTranslateToOrFromRovar();
		initializeTranslator();
		initializeViews();
	}

	/** initializing */
	public void defineIfTranslateToOrFromRovar() {
		Intent i = getIntent();
		translateToRovar = i.getBooleanExtra("translateToRovar", true);
		if(translateToRovar) {
			this.setTitle(R.string.titleToRovar);
		}
		else {
			this.setTitle(R.string.titleFromRovar);
		}
	}
	public void initializeTranslator() {
		translator = new Translator();
	}

	public void initializeViews() {
		editTextEntry = (EditText) findViewById(R.id.entry);
		editTextOutputTranslation = (EditText) findViewById(R.id.outputTranslation);
		setPreviousTranslatedText();
		registerForContextMenu(editTextOutputTranslation);
	}
	public void setPreviousTranslatedText() {
		String previousTranslations = "";
		if (translateToRovar) {
			previousTranslations = Settings.getPreviousTranslateToRovar(this);
		}
		else {
			previousTranslations = Settings.getPreviousTranslateFromRovar(this);
		}
		editTextOutputTranslation.setText(previousTranslations);
	}
	/** onPause we must save the translations */
	public void onPause() {
		super.onPause();
		savePreviousTranslations();
	}
	public void savePreviousTranslations() {
		String translation = editTextOutputTranslation.getText().toString();
		if (translateToRovar) {
			Settings.savePreviousTranslateToRovar(this, translation);
		}
		else {
			Settings.savePreviousTranslateFromRovar(this, translation);
		}
	}
	/** click handler */
	public void buttonTranslate_click(View v) {
		String input = editTextEntry.getText().toString().trim();
		
		StringBuilder output = new StringBuilder("");
		int amountOfLetters = editTextOutputTranslation.getText().length();
		if (amountOfLetters != 0) {
			output.append("\n");
		}
		
		if (input.length() != 0) {
			String translation;
			if (translateToRovar) {
				translation = translator.translate2Rovar(input);
			}
			else {
				translation = translator.translateFromRovar(input);
			}
			output.append(translation);
			editTextOutputTranslation.append(output.toString());
		}
		else {
			Context context = getApplicationContext();
			CharSequence text = getString(R.string.nothingToTranslate);
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
	}
	/** Context menu */
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v.getId() == R.id.outputTranslation) {
			CharSequence menuText = getString(R.string.contextMenuClear);
			menu.add(0, CLEAR, 0, menuText);
			// menu.add(menuText);
		}
	}
	public boolean onContextItemSelected(MenuItem item) {
		//AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
		case CLEAR:
			editTextOutputTranslation.setText("");
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
}
