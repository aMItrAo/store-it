package com.example.stroreitbaby;

import java.security.SecureRandom;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class database extends Activity {

	public static final String ROWID = "_id";
	public static final String NAME = "_name";
	public static final String PASSWORD = "_password";
	private static final String AMT_DB = "AMOUNTDB";
	private static final String ABOUT = "_about";
	private static final String TABLE = "DATAKEEPER";
	private static final String TABLE1 = "PASSWORDSTORAGE";
	private static final String CODE = "_code";
	private static final int DBVERSION = 1;

	private Dbmaker1 maker;
	private final Context context;
	private SQLiteDatabase database;

	private static class Dbmaker1 extends SQLiteOpenHelper {

		public Dbmaker1(Context context) {
			super(context, AMT_DB, null, DBVERSION);
			// TODO Auto-generated constructor stub
		}

		String CREATE_TABLE = "CREATE TABLE " + TABLE + " (" + ROWID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME
				+ " TEXT NOT NULL," + PASSWORD + " TEXT NOT NULL," + ABOUT
				+ " TEXT NOT NULL" + ")";

		String CREATE_TABLE1 = "CREATE TABLE " + TABLE1 + " (" + CODE
				+ " TEXT NOT NULL" + ")";

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(CREATE_TABLE);
			db.execSQL(CREATE_TABLE1);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTES" + TABLE);
			onCreate(db);
		}
	}

	public database(Context r) {
		context = r;

	}

	public database open() {
		maker = new Dbmaker1(context);

		database = maker.getWritableDatabase();
		return this;
	}

	public void close() {
		maker.close();
	}

	public long createEntry(String name, String password, String about) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(NAME, name);
		cv.put(PASSWORD, password);
		cv.put(ABOUT, about);
		return database.insert(TABLE, null, cv);
	}

	public void create() {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(CODE, "amit");
		database.insert(TABLE1, null, cv);
	}

	public ArrayList<String> getArray() {
		// TODO Auto-generated method stub
		Cursor c = database.rawQuery("SELECT _about FROM " + TABLE,
				new String[] {});
		ArrayList<String> result = new ArrayList<String>();

		while (c.moveToNext()) {
			String uname = c.getString(c.getColumnIndex("_about"));
			result.add(uname);
		}

		return result;
	}

	public String getUsername(String s) {
		// TODO Auto-generated method stub

		Cursor c = database.rawQuery("SELECT * FROM " + TABLE + " WHERE "
				+ ABOUT + " = \'" + s + "\'", null);
		if (c != null) {
			c.moveToFirst();
		}
		return c.getString(1);
	}

	public String getPassword(String s) {
		// TODO Auto-generated method stub

		Cursor c = database.rawQuery("SELECT * FROM " + TABLE + " WHERE "
				+ ABOUT + " = \'" + s + "\'", null);
		if (c != null) {
			c.moveToFirst();
		}
		return c.getString(2);
	}

	public String getcode() {
		String code = "SELECT * FROM " + TABLE1;
		Cursor c = database.rawQuery(code, null);
		String result = "";
		if (c.moveToFirst()) {
			result = result + c.getString(0);
		}
		return result;
	}

	public void updatecode(String a) {
		ContentValues args = new ContentValues();
		args.put(CODE, a);
		database.update(TABLE1, args, null, null);

	}

	public void deleteEntry(String name) {
		// TODO Auto-generated method stub
		database.delete(TABLE, NAME + "=?", new String[] { name });
	}

	public static String encrypt(String seed, String cleartext)
			throws Exception {
		byte[] rawKey = getRawKey(seed.getBytes());
		byte[] result = encrypt(rawKey, cleartext.getBytes());
		return toHex(result);
	}

	public static String decrypt(String seed, String encrypted)
			throws Exception {
		byte[] rawKey = getRawKey(fromHexString(seed));
		byte[] enc = toByte(encrypted);
		byte[] result = decrypt(rawKey, enc);
		return new String(result);
	}
	public static byte[] fromHexString(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	private static byte[] getRawKey(byte[] seed) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		sr.setSeed(seed);
		kgen.init(128, sr); // 192 and 256 bits may not be available
		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();
		return raw;
	}

	private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}

	private static byte[] decrypt(byte[] raw, byte[] encrypted)
			throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}

	public static String toHex(String txt) {
		return toHex(txt.getBytes());
	}

	public static String fromHex(String hex) {
		return new String(toByte(hex));
	}

	public static byte[] toByte(String hexString) {
		int len = hexString.length() / 2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++)
			result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
					16).byteValue();
		return result;
	}

	public static String toHex(byte[] buf) {
		if (buf == null)
			return "";
		StringBuffer result = new StringBuffer(2 * buf.length);
		for (int i = 0; i < buf.length; i++) {
			appendHex(result, buf[i]);
		}
		return result.toString();
	}

	private final static String HEX = "0123456789ABCDEF";

	private static void appendHex(StringBuffer sb, byte b) {
		sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
	}
}