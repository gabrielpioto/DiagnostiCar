package br.com.the475group.diagnosticar.gerenciadorDeCarro;

import java.util.List;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import br.com.the475group.diagnosticar.R;
import br.com.the475group.diagnosticar.RegistraActivity;
import br.com.the475group.diagnosticar.bluetooth.Bluetooth;
import br.com.the475group.diagnosticar.modelo.Carro;

public class RegistraCarroActivity extends RegistraActivity<Carro> implements OnItemSelectedListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EditText edtNome;
	private Spinner spnrDispositivos;
	private TextView txtAddress, txtDispositivo;
	
	private MySpinnerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registra_carro);
		
		this.edtNome = (EditText) findViewById(R.id.registra_carro_edtNome);
		this.txtAddress = (TextView) findViewById(R.id.registra_carro_txtAddress);
		this.txtDispositivo = (TextView) findViewById(R.id.registra_carro_txtDispositivo);
		this.spnrDispositivos = (Spinner) findViewById(R.id.registra_carro_spnrDispositivos);
		
//		ArrayAdapter<BluetoothDevice> adapter = new ArrayAdapter<BluetoothDevice>(
//				this, android.R.layout.simple_spinner_item,
//				Bluetooth.disponiveis);
		 
		adapter = new MySpinnerAdapter(this, Bluetooth.disponiveis);
		spnrDispositivos.setAdapter(adapter);
		setTitle(R.string.registra_carro_titulo);
		spnrDispositivos.setOnItemSelectedListener(this);
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
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		this.txtDispositivo.setText(adapter.getItem(arg2).getName());
		this.txtAddress.setText(adapter.getItem(arg2).getAddress());
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		//preencherCampos(objeto);
	}	
		
	@Override
	protected void travarCampos() {
		this.edtNome.setEnabled(false);
		this.spnrDispositivos.setVisibility(Spinner.INVISIBLE);
		this.txtDispositivo.setVisibility(TextView.VISIBLE);
	}

	@Override
	protected void destravarCampos() {
		this.edtNome.setEnabled(true);
		this.spnrDispositivos.setVisibility(Spinner.VISIBLE);
		this.txtDispositivo.setVisibility(TextView.INVISIBLE);
	}

	@Override
	protected boolean validarCampos() {
		// TODO Programar isso
		return true;
	}

	@Override
	protected void limparCampos() {
		this.edtNome.setText("");
	}

	@Override
	protected void preencherCampos(Carro objeto) {
		this.edtNome.setText(objeto.getNome());
		BluetoothDevice item = Bluetooth.getDevice(objeto.getAddress()); 
		this.spnrDispositivos.setSelection(adapter.getPosition(item));
	}

	@Override
	protected Carro obterObjeto() {
		return new Carro(txtAddress.getText().toString(), edtNome.getText()
				.toString(), txtDispositivo.getText().toString());
	}
	
	private class MySpinnerAdapter extends ArrayAdapter<BluetoothDevice> {
		List<BluetoothDevice> lista;

		public MySpinnerAdapter(Context context, List<BluetoothDevice> lista) {
			super(context, android.R.layout.simple_spinner_item, lista);
			this.lista = lista;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getMyView(position, convertView, parent,
					android.R.layout.simple_spinner_item);
		}

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			return getMyView(position, convertView, parent,
					android.R.layout.simple_spinner_dropdown_item);
		}

		private View getMyView(int position, View convertView,
				ViewGroup parent, int resource) {
			TextView tv;

			if (convertView == null) {
				tv = (TextView) getLayoutInflater().inflate(resource, parent,
						false);
			} else {
				tv = (TextView) convertView;
			}
			tv.setText(lista.get(position).getName());
			return tv;
		}

	}
}
