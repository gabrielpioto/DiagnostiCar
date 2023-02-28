package br.com.the475group.diagnosticar.bluetooth;

import java.util.Set;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.AsyncTask;
import br.com.the475group.diagnosticar.HomeActivity;
import br.com.the475group.diagnosticar.R;
import br.com.the475group.diagnosticar.daoBanco.CarroDao;
import br.com.the475group.diagnosticar.modelo.Carro;

public class ConectaCarro extends AsyncTask<Void, BluetoothDevice, Boolean> {

	private HomeActivity act;
	private Set<BluetoothDevice> listaDispositivos;
	private Context context;
	private CarroDao daoCarro;

	public ConectaCarro(Set<BluetoothDevice> listaDispositivos, HomeActivity act) {
		this.act = act;
		this.listaDispositivos = listaDispositivos;
		this.context = act.getApplicationContext();
		this.daoCarro = CarroDao.getInstance(context);
	}

	@Override
	protected void onProgressUpdate(BluetoothDevice... values) {
		if(values!=null){
			this.act.setTitle("Conectado a " + 
				this.daoCarro.get(values[0].getAddress()).getNome());
		}else{
			this.act.setTitle(R.string.assynTask_conectaCarro_nenhumEncontrado);
		}
		super.onProgressUpdate(values);
	}

	@Override
	protected Boolean doInBackground(Void... params) {

		if (this.listaDispositivos.size() > 0) {

			this.act.setTitle(R.string.assynTask_conectaCarro_conectando);

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (BluetoothDevice dispositivo : this.listaDispositivos) {

				Carro carro = this.daoCarro
						.get(dispositivo.getAddress());
				if (carro != null) {
					if (true) { // aqui é feita comparação para descobrir se o
								// dispositivo está disponivel para se conectar
						publishProgress(dispositivo);
						return true;
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
		return false;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}
}
