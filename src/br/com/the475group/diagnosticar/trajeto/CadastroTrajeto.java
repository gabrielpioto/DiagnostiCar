/**
 * 
 */
package br.com.the475group.diagnosticar.trajeto;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import br.com.the475group.diagnosticar.R;
import br.com.the475group.diagnosticar.daoBanco.TrajetoDAO;
import br.com.the475group.diagnosticar.utilitarias.Trajeto;

/**
 * @author Yves
 *
 */
public class CadastroTrajeto extends Activity {
	
	private TrajetoDAO tjtdb;
	private EditText txtFieldNome;
	private EditText txtFieldOrigem;
	private EditText txtFieldDestino;
	private Button btnCadastrarTrajeto;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_trajeto);
        this.tjtdb = new TrajetoDAO(this);
        this.txtFieldNome = (EditText) findViewById(R.id.txtFieldNome);
        this.txtFieldOrigem = (EditText) findViewById(R.id.txtFieldOrigem);
        this.txtFieldDestino = (EditText) findViewById(R.id.txtFieldDestino);
        this.btnCadastrarTrajeto = (Button) findViewById(R.id.btnCadastrarTrajeto);
        this.btnCadastrarTrajeto.setOnClickListener(new View.OnClickListener() {
        	
			@Override
			public void onClick(View v) {
				Trajeto tjt = new Trajeto(txtFieldNome.getText().toString(), txtFieldOrigem.getText().toString(), txtFieldDestino.getText().toString());
				tjtdb.insert(tjt);
				finish();
			}
		});
	}
}
