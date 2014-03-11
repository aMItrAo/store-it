package com.example.stroreitbaby;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class view extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		database db = new database(view.this);
		db.open();
		ArrayList<String> data = db.getArray();
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item,
				R.id.textView1, data));
		final ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				// String product = ((TextView) view).getText().toString();
				String product = (String) (lv.getItemAtPosition(arg2));
				Intent i = new Intent(getApplicationContext(), newview.class);
				i.putExtra("product", product);
				startActivity(i);
			}
		});
		db.close();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuinflater = getMenuInflater();
		menuinflater.inflate(R.layout.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_add:
			Intent i = new Intent(getApplicationContext(), add.class);
			startActivity(i);
			return true;

		case R.id.menu_settings:
			Intent i1 = new Intent(getApplicationContext(), settings.class);
			startActivity(i1);
			return true;

		case R.id.menu_refresh:
			Intent i2 = new Intent(getApplicationContext(), view.class);
			startActivity(i2);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
}