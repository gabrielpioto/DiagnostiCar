package br.com.the475group.diagnosticar.gerenciadorDeCarro;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import br.com.the475group.diagnosticar.GerenciaActivity;
import br.com.the475group.diagnosticar.bluetooth.Bluetooth;
import br.com.the475group.diagnosticar.daoBanco.CarroDao;
import br.com.the475group.diagnosticar.modelo.Carro;

public class GerenciaCarrosActivity extends GerenciaActivity<Carro, String> {
	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("testeGerenciaCarro", "criou activity");
		dao = new CarroDao(this);
		//dao.insert(new Carro("aaa","bbb","ccc"));
		Bluetooth.prepararDisponiveis((CarroDao)dao);
		Cursor c = dao.getCursorWithAll();
	
		registraActivityClass = RegistraCarroActivity.class;
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, 
				c, new String[]{CarroDao.COL_NOME,CarroDao.COL_ID} , 
				new int[]{android.R.id.text1,android.R.id.text2});
		
		setCursorAdapter(adapter);
		setTxtCabecText("Gerencia Carro"); //TODO: mudar isso depois
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		super.onItemClick(arg0, arg1, arg2, arg3);
	}
	
}
