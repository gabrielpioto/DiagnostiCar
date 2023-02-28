package br.com.the475group.diagnosticar.status;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import br.com.the475group.diagnosticar.BaseActivity;
import br.com.the475group.diagnosticar.R;
import br.com.the475group.diagnosticar.modelo.RecebeInfoThread;
import br.com.the475group.simulador.Informacao;

public class StatusActivity extends BaseActivity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TextView txtCondicao, txtVelocidade, txtTemperatura, txtAgua, txtCombustivel;
	private ProgressBar pgbCombustivel, pgbAgua;
	private RecebeInfoThread recebeInfo;
	public static long tempoPausaOBD = 5000;
	public static long tempoPausaRecebe = 8000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status);
		
		this.txtCondicao = (TextView) findViewById(R.id.status_txtCondicao);
		this.txtVelocidade = (TextView) findViewById(R.id.status_txtVelocidade);
		this.txtTemperatura = (TextView) findViewById(R.id.status_txtTemperatura);
		this.txtAgua = (TextView) findViewById(R.id.status_txtAgua);
		this.txtCombustivel = (TextView) findViewById(R.id.status_txtCombustivel);
		this.pgbAgua = (ProgressBar) findViewById(R.id.status_pgbAgua);
		this.pgbCombustivel = (ProgressBar) findViewById(R.id.status_pgbCombustivel);
		
		Toast.makeText(getApplicationContext(), "sdfsdf", Toast.LENGTH_LONG).show();
		
		this.recebeInfo = new RecebeInfoThread(tempoPausaRecebe, this);
		
		this.recebeInfo.start();
	}
	
	public void mostraInfo(ArrayList<Informacao> listaInfo){
		for(Informacao info : listaInfo){
			switch(info.getCodigo()){
				case 1 : this.txtCondicao.setText(String.valueOf(info.getValor()));
						 break;
				case 2 : this.txtVelocidade.setText(String.valueOf(info.getValor())+info.getUnidade());
						 break;
				case 3 : this.txtTemperatura.setText(String.valueOf(info.getValor())+info.getUnidade());
						 break;
				case 4 : this.pgbAgua.setProgress((int) info.getValor());
						 this.txtAgua.setText(String.valueOf(info.getValor()) + " %");
						 break;
				case 5 : this.pgbCombustivel.setProgress((int) info.getValor());
						 this.txtCombustivel.setText(String.valueOf(info.getValor()) + " %");
						 break;
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		this.recebeInfo.parar();
		super.onDestroy();
	}
}
