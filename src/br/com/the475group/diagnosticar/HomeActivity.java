package br.com.the475group.diagnosticar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;
import br.com.the475group.diagnosticar.bluetooth.Bluetooth;
import br.com.the475group.diagnosticar.bluetooth.ConectaCarro;
import br.com.the475group.diagnosticar.estatistica.Estatistica;
import br.com.the475group.diagnosticar.gerenciadorDeCarro.GerenciaCarrosActivity;
import br.com.the475group.diagnosticar.status.StatusActivity;
import br.com.the475group.diagnosticar.trajeto.GerenciaTrajetoActivity;

public class HomeActivity extends BaseActivity implements OnClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageButton btnTrajeto, btnEstatistica, btnStatus, btnWeb;
	private ConectaCarro cntCarro;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		// Instancia e associa os botões da Activity Home com os botões do XML
		// Home
		
		this.btnTrajeto = (ImageButton) findViewById(R.id.home_imgBtnTrajetos);
		this.btnEstatistica = (ImageButton) findViewById(R.id.home_imgBtnEstatistica);
		this.btnStatus = (ImageButton) findViewById(R.id.home_imgBtnStatus);
		this.btnWeb = (ImageButton) findViewById(R.id.home_imgBtnWEB);
		
		this.btnTrajeto.setOnClickListener(this);
		this.btnStatus.setOnClickListener(this);
		this.btnWeb.setOnClickListener(this);
		this.btnEstatistica.setOnClickListener(this);
		
		
		if(Bluetooth.temBluetooth){
			this.cntCarro = new ConectaCarro(Bluetooth.dispPareados, this);
			if(Bluetooth.estaLigado){
				this.cntCarro.execute();
			}else{
				Bluetooth.ligarBluetooth(this);
			}
		}else{
			Toast.makeText(getApplicationContext(), R.string.activity_home_toast_blueNaoSuportado, Toast.LENGTH_LONG).show();
		}
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.activity_home, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.gerenciadorDeCarro:
			Intent it = new Intent(getApplicationContext(), GerenciaCarrosActivity.class);
			startActivity(it);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(resultCode == RESULT_OK){
			Toast.makeText(getApplicationContext(), R.string.activity_home_toast_blueAtivado, Toast.LENGTH_LONG).show();
			this.cntCarro.execute();
		}else{
			Toast.makeText(getApplicationContext(), R.string.activity_home_toast_blueNaoAtivado, Toast.LENGTH_LONG).show();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View v) {
		Intent it = null;
		switch (v.getId()) {
		case R.id.home_imgBtnEstatistica:
			it = new Intent(HomeActivity.this, Estatistica.class);
			break;
		case R.id.home_imgBtnTrajetos:
			it = new Intent(HomeActivity.this, GerenciaTrajetoActivity.class);
			break;
		case R.id.home_imgBtnStatus:
			it = new Intent(HomeActivity.this, StatusActivity.class);
		//TODO: continuar cases
		}
		
		if(it!=null){
			startActivity(it);
		}
	}
}
