package br.com.the475group.diagnosticar;

import java.io.Serializable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class RegistraActivity<T extends Serializable> extends BaseActivity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String KEY_OBJETO = "objeto";
	protected T objeto;
	protected boolean isNovo;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		objeto = (T) getIntent().getSerializableExtra(KEY_OBJETO);
		isNovo = (objeto == null);
	}
	
	protected abstract void travarCampos();
	
	protected abstract void destravarCampos();
	
	protected abstract boolean validarCampos();
	
	protected abstract void limparCampos();
	
	protected abstract void preencherCampos(T objeto);
	
	protected abstract T obterObjeto();
	
	protected final OnClickListener salvar = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(validarCampos()){
				Intent it = new Intent();
				it.putExtra(KEY_OBJETO, obterObjeto());
				setResult(RESULT_OK,it);
				finish();
			}			
			
		}
	};
	
	protected final OnClickListener destravar = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			destravarCampos();
			setBtnCabecImage(android.R.drawable.ic_menu_agenda);
			setBtnCabecClickListener(salvar);			
		}
	};
	
}
