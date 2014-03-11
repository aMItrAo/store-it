package com.example.stroreitbaby;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class add extends Activity {
	Button add;
	EditText username, password, about;
	String user, pass, abut;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);
		add = (Button) findViewById(R.id.add);
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		about = (EditText) findViewById(R.id.about);
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				user = username.getText().toString();
				pass = password.getText().toString();
				abut = about.getText().toString();
				if (user == "" && pass == "" && abut == "") {
					(Toast.makeText(add.this,
							"Please Fill Up The Blank Spaces",
							Toast.LENGTH_LONG)).show();
				} else {
					try {
						database db = new database(add.this);
						db.open();
						//String crypto = db.encrypt("secure", pass);
						//Toast.makeText(getApplicationContext(), crypto, Toast.LENGTH_LONG).show();
						db.createEntry(user, pass, abut);
						db.close();
						(Toast.makeText(add.this, "Entered successfully",
								Toast.LENGTH_LONG)).show();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						username.setText(e.toString());
					}	
				}
				username.setText("");
				about.setText("");
				password.setText("");
			}
		});

	}
}