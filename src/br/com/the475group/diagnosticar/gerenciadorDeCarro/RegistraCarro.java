package br.com.the475group.diagnosticar.gerenciadorDeCarro;

import java.util.ArrayList;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import br.com.the475group.diagnosticar.R;
import br.com.the475group.diagnosticar.bluetooth.Bluetooth;
import br.com.the475group.diagnosticar.daoBanco.CarroDao;
import br.com.the475group.diagnosticar.modelo.Carro;

public class RegistraCarro extends Activity implements OnClickListener {

	//Elementos do Layout
	private Button btnSalvar;
	private EditText edtNome;
	private boolean isAtualizar;
	private Spinner spnrDispositivos;
	private TextView txtAddress, txtDispositivo;
	
	//Auxiliares Spinner
	private ArrayList<String> elementos;
	private ArrayAdapter<String> adapter;
	
	//Utilitárias
	private Carro carro;
	private ArrayList<BluetoothDevice> listaDispositivos;
	private CarroDao carDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registra_carro);

		this.carDao = new CarroDao(this);
		this.carro = new Carro();
		this.listaDispositivos = new ArrayList<BluetoothDevice>();
		
		//this.btnSalvar = (Button) findViewById(R.id.registra_carro_btnSalvar);
		this.edtNome = (EditText) findViewById(R.id.registra_carro_edtNome);
		this.txtAddress = (TextView) findViewById(R.id.registra_carro_txtAddress);
		this.txtDispositivo = (TextView) findViewById(R.id.registra_carro_txtDispositivo);
		this.spnrDispositivos = (Spinner) findViewById(R.id.registra_carro_spnrDispositivos);
		
		this.btnSalvar.setOnClickListener(this);
		
		//Esta é a parte em que o nome e o adress são recebidos da GerenciaCarros
		try {
			this.isAtualizar = getIntent().getExtras().getBoolean(GerenciaCarros.extraKeyActivity);
			
			if(this.isAtualizar){
				//Atributo de classe carro, usado principalmente na hora de atualizar os dados
				this.carro.setNome(getIntent().getExtras().getString(GerenciaCarros.extraKeyNomeCarro));
				this.carro.setAddress(getIntent().getExtras().getString(GerenciaCarros.extraKeyAdress));
				this.carro.setDispositivo(getIntent().getExtras().getString(GerenciaCarros.extraKeyDispositivo));
				
				//Exibindo os dados aos usuários
				this.edtNome.setText(this.carro.getNome());
				this.txtAddress.setText(this.carro.getAddress());
				this.txtDispositivo.setText(this.carro.getDispositivo());
				this.edtNome.setEnabled(false);
				this.spnrDispositivos.setVisibility(Spinner.INVISIBLE);
			}else{
				this.txtDispositivo.setVisibility(TextView.INVISIBLE);
			}

			this.iniciaSpinner();
		} catch (Exception ex) {
			Log.d("RegistraCarro","Erro: "+ex);
			finish();
		}
	}
	
	private void iniciaSpinner(){
		this.elementos = this.geraListaDispositivos();
		this.adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, elementos);
		this.adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.spnrDispositivos.setAdapter(this.adapter);
		this.spnrDispositivos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int posicao, long arg3) {
				// TODO Auto-generated method stub
				txtAddress.setText(listaDispositivos.get(posicao).getAddress());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private ArrayList<String> geraListaDispositivos(){
		ArrayList<String>listaDispositivos = new ArrayList<String>();
		this.listaDispositivos.clear();
		
		for(BluetoothDevice dispositivo : Bluetooth.dispPareados){
			if(this.carDao.selectById(dispositivo.getAddress()) == null){
				listaDispositivos.add(dispositivo.getName());
				this.listaDispositivos.add(dispositivo);
			}else{
				if(dispositivo.getAddress().equals(this.carro.getAddress())){
					listaDispositivos.add(0, dispositivo.getName());
					this.listaDispositivos.add(0, dispositivo);
				}
			}
		}
		return listaDispositivos;
	}
	
	private void atualizar(){
		String addressAntigo = this.carro.getAddress();
		this.carro.setNome(this.edtNome.getText().toString());
		this.carro.setAddress(this.txtAddress.getText().toString());
		this.carro.setDispositivo(this.spnrDispositivos.getSelectedItem().toString());
		//Comparação do campo nome para que não esteja vazio
		if ("".equals(this.carro.getNome())) {
			Toast.makeText(getApplicationContext(),
					R.string.activity_cadastro_carro_aviso_nome,
					Toast.LENGTH_LONG).show();
		} else {
			//carDao.update(this.carro,addressAntigo);
			//if(this.carDao.update(this.carro, addressAntigo)){
			finish();
			
		}
	}
	
	private void cadastrar(){
		this.carro.setAddress(this.txtAddress.getText().toString());
		this.carro.setNome(this.edtNome.getText().toString());
		this.carro.setDispositivo(this.spnrDispositivos.getSelectedItem().toString());
		//Comparação do campo nome para que não esteja vazio
		if ("".equals(this.carro.getNome())) {
			Toast.makeText(getApplicationContext(),
					R.string.activity_cadastro_carro_aviso_nome,
					Toast.LENGTH_LONG).show();
		} else {
			this.carDao.insert(this.carro);
			finish();			
		}
	}
	
	private void abilitaEdicao(){
		this.btnSalvar.setVisibility(Button.VISIBLE);
		this.edtNome.setEnabled(true);
		this.spnrDispositivos.setVisibility(Spinner.VISIBLE);
		this.txtDispositivo.setVisibility(TextView.INVISIBLE);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		if(this.isAtualizar){
			getMenuInflater().inflate(R.menu.activity_visualiza_carro, menu);
			return super.onCreateOptionsMenu(menu);
		}
		return false;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.editar:
			this.abilitaEdicao();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
//		switch(v.getId()){
//			case R.id.registra_carro_btnSalvar:
//				if(this.isAtualizar)
//					this.atualizar();
//				else
//					this.cadastrar();
//				break;
//		}
	}
}
