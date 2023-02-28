package br.com.the475group.diagnosticar.status;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import br.com.the475group.diagnosticar.BaseActivity;
import br.com.the475group.diagnosticar.R;

public class StatusActivity extends BaseActivity{
	
	private TextView txtCondicao, txtVelocidade, txtTemperatura;
	private ProgressBar pgbCombustivel, pgbAgua;
	//private Informacao info;
	//private RequisicaoThread thrOBD;
	public static long tempoPausaThread = 5000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status);
		
		this.txtCondicao = (TextView) findViewById(R.id.status_txtCondicao);
		this.txtVelocidade = (TextView) findViewById(R.id.status_txtVelocidade);
		this.txtTemperatura = (TextView) findViewById(R.id.status_txtTemperatura);
		this.pgbAgua = (ProgressBar) findViewById(R.id.status_pgbAgua);
		this.pgbCombustivel = (ProgressBar) findViewById(R.id.status_pgbCombustivel);
		
		//this.info = new Informacao();
		//this.thrOBD = new RequisicaoThread(tempoPausaThread, this);
		
		//this.thrOBD.start();
	
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		//this.thrOBD.parar();
		super.onDestroy();
	}

}
