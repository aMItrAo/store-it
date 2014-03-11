package com.example.stroreitbaby;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class settings extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);

		final EditText edt = (EditText) findViewById(R.id.editText1);

		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String password = edt.getText().toString();
				database db = new database(settings.this);
				db.open();
				db.create();				
				db.updatecode(password);
				db.close();
				Toast.makeText(getApplicationContext(), "Changed",
						Toast.LENGTH_SHORT).show();
				Intent i=new Intent(settings.this,MainActivity.class);
				startActivity(i);
			}
		});
	}
}
