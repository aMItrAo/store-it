package com.example.stroreitbaby;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class newview extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view);
		TextView txt1, txt2, txt3;
		txt1 = (TextView) findViewById(R.id.textView2);
		txt2 = (TextView) findViewById(R.id.textView4);
		txt3 = (TextView) findViewById(R.id.textView6);
		Button btn = (Button) findViewById(R.id.button1);
		Intent i = getIntent();
		String product = i.getStringExtra("product");
		txt3.setText(product);
		final database db = new database(newview.this);
		db.open();
		final String name = db.getUsername(product);
		try {

			String correctpass = "";

			String pass = db.getPassword(product);
			//correctpass = db.decrypt("secure", pass);
			txt1.setText(name);
			txt2.setText(pass);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			txt1.setText(e.toString());
		}
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				db.deleteEntry(name);
				Toast.makeText(getApplicationContext(), "Entry Deleted",
						Toast.LENGTH_SHORT).show();
				Intent i = new Intent(newview.this, view.class);
				startActivity(i);
			}
		});
	}
}
