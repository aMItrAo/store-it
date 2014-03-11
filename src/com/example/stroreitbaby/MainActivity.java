package com.example.stroreitbaby;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	EditText edt1;
	TextView txt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btn = (Button) findViewById(R.id.button1);

		edt1 = (EditText) findViewById(R.id.editText1);

		database db = new database(MainActivity.this);

		db.open();

		final String code = db.getcode();

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final String get = edt1.getText().toString();
				//if (code.equals(get)) {
					Intent i = new Intent(getApplicationContext(), view.class);
					startActivity(i);
				//} else {
					(Toast.makeText(MainActivity.this, "Wrong Password",
							Toast.LENGTH_SHORT)).show();
				//}
			}
		});
	}
}
