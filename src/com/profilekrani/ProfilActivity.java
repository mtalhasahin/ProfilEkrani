package com.profilekrani;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ProfilActivity extends Activity {
    private DatabaseManager manager;
    
    private ImageButton profilImageButton;
    private EditText kullaniciAdiEditText;
    private EditText adEditText;
    private EditText soyadEditText;
    private EditText telefonEditText;
    private EditText emailEditText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profil);
		ekranKontrolleriniOlustur();
	}
	private void ekranKontrolleriniOlustur() {
		manager=new DatabaseManager(this);
		final Profil profil=manager.profilSorgula(null);
		
		profilImageButton=(ImageButton) findViewById(R.id.profilImageButton);
		kullaniciAdiEditText=(EditText) findViewById(R.id.kullaniciAdiEditText);
		adEditText=(EditText) findViewById(R.id.adEditText);
		soyadEditText=(EditText) findViewById(R.id.soyadEditText);
		telefonEditText=(EditText) findViewById(R.id.telefonEditText);
		emailEditText=(EditText) findViewById(R.id.emailEditText);
		
		ekranGuncelle(profil);
		
		Button kaydetButton=(Button) findViewById(R.id.kaydetButton);
		kaydetButton.setOnClickListener(new View.OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				Profil kaydedilecekProfil=ekranDegerleriniOku();
				int sonuc=manager.profilKaydetGuncelle(kaydedilecekProfil);
				String profilKaydetSonucMessage=getProfilKaydetSonucMessage(sonuc);
				Toast.makeText(getApplicationContext(), profilKaydetSonucMessage, Toast.LENGTH_LONG).show();
				
			}
		});
		
	}
	private void ekranGuncelle(Profil profil) {
		kullaniciAdiEditText.setText(profil.getKullaniciAdi());
		kullaniciAdiEditText.setEnabled(false);
		
		adEditText.setText(profil.getAd());
		soyadEditText.setText(profil.getSoyad());
		telefonEditText.setText(profil.getTelefon());
		emailEditText.setText(profil.getEmail());
		
	}
	private String getProfilKaydetSonucMessage(int sonuc) {
		if(DatabaseManager.BASARILI==sonuc)
			return getResources().getString(R.string.toast_profil_kaydet_basarili);
		if(DatabaseManager.BASARISIZ==sonuc)
			return getResources().getString(R.string.toast_bilinmeyen_hata);
		return getResources().getString(R.string.toast_profil_validasyon_hatasi);
	}

	private Profil ekranDegerleriniOku() {
		Profil profil=new Profil();
		if(kullaniciAdiEditText.getText()!=null)
			profil.setKullaniciAdi(kullaniciAdiEditText.getText().toString());
		if(adEditText.getText()!=null)
			profil.setAd(adEditText.getText().toString());
		if(soyadEditText.getText()!=null)
			profil.setSoyad(soyadEditText.getText().toString());
		if(telefonEditText.getText()!=null)
			profil.setTelefon(telefonEditText.getText().toString());
		if(emailEditText.getText()!=null)
			profil.setEmail(emailEditText.getText().toString());
		
		return null;
	}
}
