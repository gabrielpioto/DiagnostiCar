package br.com.the475group.diagnosticar.bluetooth;

import java.util.ArrayList;
import java.util.Set;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.AsyncTask;
import br.com.the475group.diagnosticar.HomeActivity;
import br.com.the475group.diagnosticar.R;
import br.com.the475group.diagnosticar.daoBanco.CarroDao;
import br.com.the475group.diagnosticar.modelo.Carro;

public class ConectaCarro extends AsyncTask<Void, Carro, Integer> {

	private HomeActivity act;
	private Set<BluetoothDevice> listaDispositivos;
	private ArrayList<Carro> listaConectaveis;
	private Context context;
	private CarroDao daoCarro;

	public ConectaCarro(Set<BluetoothDevice> listaDispositivos, HomeActivity act) {
		this.act = act;
		this.listaConectaveis = new ArrayList<Carro>();
		this.listaDispositivos = listaDispositivos;
		this.context = act.getApplicationContext();
		this.daoCarro = new CarroDao(context);
	}

	@Override
	protected void onProgressUpdate(Carro... values) {
		// TODO Auto-generated method stub
		
		if (values != null) {
			this.listaConectaveis.add(values[0]);
		} 
		super.onProgressUpdate(values);
	}

	@Override
	protected Integer doInBackground(Void... params) {

		int conectados = 0;

		if (this.listaDispositivos.size() > 0) {
			
			this.act.setTxtCabecText(R.string.assynTask_conectaCarro_conectando);
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (BluetoothDevice dispositivo : this.listaDispositivos) {
			
				Carro carro = this.daoCarro.selectById(dispositivo
						.getAddress());
				if (carro != null) {
					if (true) { //aqui é feita comparação para descobrir se o dispositivo está disponivel para se conectar
						publishProgress(carro);
						conectados++;
					}
				}
				// Add the name and address to an array adapter to show in a
				// ListView
				// mArrayAdapter.add(device.getName() + "\n" +
				// device.getAddress());
			}
		} else {
			publishProgress(null);
		}

		return conectados;
	}

	@Override
	protected void onPostExecute(Integer result) {
		if (result > 0) {
			if (result == 1) {
				//this.conectar(this.listaConectaveis.get(0).getAdress());
				this.act.setTxtCabecText("Conectado a " + this.listaConectaveis.get(0).getNome());
			} else {
				this.act.setTxtCabecText(R.string.assynTask_conectaCarro_escolhaCarro);
			}
		} else {
			this.act.setTxtCabecText(R.string.assynTask_conectaCarro_nenhumEncontrado);
		}
		super.onPostExecute(result);
	}
}
