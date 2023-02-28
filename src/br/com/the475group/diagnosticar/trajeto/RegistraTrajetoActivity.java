package br.com.the475group.diagnosticar.trajeto;

import android.os.Bundle;
import android.widget.EditText;
import br.com.the475group.diagnosticar.R;
import br.com.the475group.diagnosticar.RegistraActivity;
import br.com.the475group.diagnosticar.modelo.Trajeto;

public class RegistraTrajetoActivity extends RegistraActivity<Trajeto> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EditText edtNome;
	private EditText edtOrigem;
	private EditText edtDestino;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registra_trajeto);
		
		edtNome = (EditText) findViewById(R.id.registra_trajeto_edtNome);
		edtOrigem = (EditText) findViewById(R.id.registra_trajeto_edtOrigem);
		edtDestino = (EditText) findViewById(R.id.registra_trajeto_edtDestino);
		
		setTitle(R.string.registra_trajeto_titulo);
		
		if(isNovo){
			setBtnCabecImage(android.R.drawable.ic_menu_agenda);
			destravarCampos();
			setBtnCabecClickListener(salvar);
		} else {
			setBtnCabecImage(android.R.drawable.ic_menu_edit);
			setBtnCabecClickListener(destravar);
			preencherCampos(objeto);
			travarCampos();			
		}
		
		
	}

	@Override
	protected void travarCampos() {
		edtNome.setEnabled(false);
		edtOrigem.setEnabled(false);
		edtDestino.setEnabled(false);
	}

	@Override
	protected void destravarCampos() {
		edtNome.setEnabled(true);
		edtOrigem.setEnabled(true);
		edtDestino.setEnabled(true);
	}

	@Override
	protected boolean validarCampos() {
		// TODO Programar isso (chato)
		return true;
	}

	@Override
	protected void limparCampos() {
		edtNome.setText("");
		edtOrigem.setText("");
		edtDestino.setText("");
	}

	@Override
	protected void preencherCampos(Trajeto objeto) {
		edtNome.setText(objeto.getNome());
		edtOrigem.setText(objeto.getOrigem());
		edtDestino.setText(objeto.getDestino());
	}

	@Override
	protected Trajeto obterObjeto() {
		Trajeto t = new Trajeto();
		t.setNome(edtNome.getText().toString());
		t.setOrigem(edtOrigem.getText().toString());
		t.setDestino(edtDestino.getText().toString());
		return t;
	}

}
