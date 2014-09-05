package com.profilekrani;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	
	
	public static final String DATABASE_CREATE="CREATE TABLE"+DatabaseContract.TABLE_NAME+"("+DatabaseContract.Profil.COLUMN_KULLANICI_ADI+"INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ ""+DatabaseContract.Profil.COLUMN_AD+"VARCHAR(20) NOT NULL,"+DatabaseContract.Profil.COLUMN_SOYAD+"VARCHAR(20) NOT NULL,"
					+ ""+DatabaseContract.Profil.COLUMN_TELEFON+"VARCHAR(10),"+DatabaseContract.Profil.COLUMN_EMAIL+"VARCHAR(20) );";
	
	public static final String DATABASE_DROP="DROP TABLE IF EXISTS"+DatabaseContract.TABLE_NAME;
	
	public DatabaseHelper(Context context){
		super(context,DatabaseContract.DATABASE_NAME,null,DatabaseContract.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DATABASE_DROP);
		onCreate(db);
		
	}

}
