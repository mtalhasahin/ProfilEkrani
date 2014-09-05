package com.profilekrani;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class DatabaseManager {
	public static final int BASARILI=1;
	public static final int BASARISIZ=-1;
	public static final int PROFIL_VALIDASYON_HATASI=2;
	private DatabaseHelper helper;
	private Context context;
	public DatabaseManager(Context context){
		this.context=context;
		helper=new DatabaseHelper(context);
	}
	
	public Profil profilSorgula(String kullaniciAdi){
		String where=null;
		String[] whereArgs=null;
		
		if(!TextUtils.isEmpty(kullaniciAdi)){
			where=DatabaseContract.Profil.COLUMN_KULLANICI_ADI+"=?";
			whereArgs=new String[]{kullaniciAdi};
		}
		SQLiteDatabase db=helper.getReadableDatabase();
		Cursor cursor=db.query(DatabaseContract.TABLE_NAME, DatabaseContract.Profil.FULL_PROJECTION, where, whereArgs, null, null, null);
		return buildProfil(cursor);
	}

	private Profil buildProfil(Cursor cursor) {
		if(cursor==null || cursor.getCount()!=1 || !cursor.moveToNext())
			return null;
		Profil profil=new Profil();
		
		int Idindex=cursor.getColumnIndex(DatabaseContract.Profil._ID);
		profil.setId(cursor.getInt(Idindex));
		
		int kullaniciAdiIndex=cursor.getColumnIndex(DatabaseContract.Profil.COLUMN_KULLANICI_ADI);
		profil.setKullaniciAdi(cursor.getString(kullaniciAdiIndex));
		
		int adIndex=cursor.getColumnIndex(DatabaseContract.Profil.COLUMN_AD);
		profil.setAd(cursor.getString(adIndex));
		
		int soyadIndex=cursor.getColumnIndex(DatabaseContract.Profil.COLUMN_SOYAD);
		profil.setSoyad(cursor.getString(soyadIndex));
		
		int telefonIndex=cursor.getColumnIndex(DatabaseContract.Profil.COLUMN_TELEFON);
		profil.setTelefon(cursor.getString(telefonIndex));
		
		int emailIndex=cursor.getColumnIndex(DatabaseContract.Profil.COLUMN_EMAIL);
		profil.setEmail(cursor.getString(emailIndex));
		
		return profil;
	}
	public int profilKaydetGuncelle(Profil profil){
		
		ContentValues satir=new ContentValues();
		satir.put(DatabaseContract.Profil.COLUMN_KULLANICI_ADI, profil.getKullaniciAdi());
		satir.put(DatabaseContract.Profil.COLUMN_AD, profil.getAd());
		satir.put(DatabaseContract.Profil.COLUMN_SOYAD, profil.getSoyad());
		satir.put(DatabaseContract.Profil.COLUMN_TELEFON, profil.getTelefon());
		satir.put(DatabaseContract.Profil.COLUMN_EMAIL, profil.getEmail());
		
		Profil kayitliProfil=profilSorgula(profil.getKullaniciAdi());
		if(kayitliProfil!=null)
			return profilGuncelle(kayitliProfil.getId(),satir);
		return profilKaydet(satir);
	}

	public int profilGuncelle(int id, ContentValues satir) {
		SQLiteDatabase db=helper.getWritableDatabase();
		String where=DatabaseContract.Profil._ID+"="+id;
		int guncellenenSatir=db.update(DatabaseContract.TABLE_NAME, satir, where,null);
		if(guncellenenSatir!=1)
			return BASARISIZ;
		return BASARILI;
	}

	public int profilKaydet(ContentValues satir) {
		SQLiteDatabase db=helper.getWritableDatabase();
		long profilId=db.insert(DatabaseContract.TABLE_NAME, null, satir);
		
		if(profilId==-1)
			return BASARISIZ;
		
		return BASARILI;
	}
	

}
