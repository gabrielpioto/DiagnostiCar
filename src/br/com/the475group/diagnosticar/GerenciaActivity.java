package br.com.the475group.diagnosticar;

import java.io.Serializable;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.the475group.diagnosticar.daoBanco.DAO;

public class GerenciaActivity<T extends Serializable,ID extends Serializable> extends BaseActivity implements OnClickListener, OnItemClickListener, OnItemLongClickListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ListView listView;
	private Class<? extends RegistraActivity<T>> registraActivityClass;
	private DAO<T,ID> dao;
	private T objetoAntigo;
	private ArrayAdapter<T> adapter;
	
	public static final int REQUEST_NOVO = 1001;
	public static final int REQUEST_EDITA = 1002;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listView = new ListView(this);
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
		setBtnCabecClickListener(this);
		setBtnCabecImage(android.R.drawable.ic_menu_add);
		setContentView(listView);
	}
	
	protected DAO<T, ID> getDao() {
		return dao;
	}

	protected void setDao(DAO<T, ID> dao) {
		this.dao = dao;
	}

	protected void setRegistraActivityClass(Class<? extends RegistraActivity<T>> registraActivityClass) {
		this.registraActivityClass = registraActivityClass;
	}
	
	protected void setArrayAdapter(ArrayAdapter<T> adapter){
		this.adapter = adapter;
		listView.setAdapter(adapter);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int arg2,
			long arg3) {
		
		new AlertDialog.Builder(this)
		.setMessage(R.string.gerencia_base_confirmaExcluir)
		.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				T objeto = adapter.getItem(arg2);
				if(dao.delete(objeto))	
					adapter.remove(objeto);
			}
		}).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).show();
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent it = new Intent(this, registraActivityClass);
		objetoAntigo = adapter.getItem(arg2);
		Log.d("gerencia activity",objetoAntigo.toString());
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
					@SuppressWarnings("unchecked")
					T objetoNovo = (T) data.getSerializableExtra(RegistraActivity.KEY_OBJETO);
					if(dao.insert(objetoNovo))
						adapter.add(objetoNovo);
					break;
				case REQUEST_EDITA:
					@SuppressWarnings("unchecked")
					T objetoEditado = (T) data.getSerializableExtra(RegistraActivity.KEY_OBJETO);
					if(dao.update(objetoEditado, objetoAntigo)){
						int posicaoAntiga = adapter.getPosition(objetoAntigo);
						adapter.remove(objetoAntigo);
						adapter.insert(objetoEditado, posicaoAntiga);
					}
					break;
				}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}	
	
}
