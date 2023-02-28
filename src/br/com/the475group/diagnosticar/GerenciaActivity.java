package br.com.the475group.diagnosticar;

import java.io.Serializable;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CursorAdapter;
import android.widget.ListView;
import br.com.the475group.diagnosticar.daoBanco.DAO;

public class GerenciaActivity<T extends Serializable,ID> extends BaseActivity implements OnItemClickListener, OnItemLongClickListener, OnClickListener{
	
	private ListView listView;
	private CursorAdapter adapter;
	protected DAO<T,ID> dao;
	protected Class registraActivityClass;
	private T objetoAntigo;
	
	public static final int REQUEST_NOVO = 1001;
	public static final int REQUEST_EDITA = 1002;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listView = new ListView(this);
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
		setBtnCabecImage(android.R.drawable.ic_menu_add);
		setBtnCabecClickListener(this);
		setContentView(listView);
	}
	
	protected void setCursorAdapter(CursorAdapter adapter){
		this.adapter = adapter;
		listView.setAdapter(adapter);
	}	

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int arg2,
			long arg3) {
		new AlertDialog.Builder(this)
		.setMessage("Deseja realmente excluir este item")
		.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Cursor c = adapter.getCursor();
				c.moveToPosition(arg2);
				dao.delete(dao.getByCursor(c));				
			}
		}).setCancelable(true).show();
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent it = new Intent(this, registraActivityClass);
		Cursor c = adapter.getCursor();
		c.moveToPosition(arg2);
		objetoAntigo = dao.getByCursor(c);
		it.putExtra(RegistraActivity.KEY_OBJETO, objetoAntigo);
		startActivityForResult(it, REQUEST_EDITA);		
	}

	@Override
	public void onClick(View v) {
		Intent it = new Intent(this, registraActivityClass);
		startActivityForResult(it, REQUEST_NOVO);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			switch(requestCode){
				case REQUEST_NOVO:
					T objetoNovo = (T) data.getSerializableExtra(RegistraActivity.KEY_OBJETO);
					dao.insert(objetoNovo);
					adapter.changeCursor(dao.getCursorWithAll());
					break;
				case REQUEST_EDITA:
					T objetoEditado = (T) data.getSerializableExtra(RegistraActivity.KEY_OBJETO);
					dao.update(objetoEditado, objetoAntigo);
					adapter.changeCursor(dao.getCursorWithAll());
					break;
				}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}	
}
