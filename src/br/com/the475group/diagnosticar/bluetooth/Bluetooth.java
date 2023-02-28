package br.com.the475group.diagnosticar.bluetooth;

import java.util.ArrayList;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import br.com.the475group.diagnosticar.daoBanco.CarroDao;
import br.com.the475group.diagnosticar.modelo.Carro;

public class Bluetooth {

	private static final BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
	public static final boolean temBluetooth = !(adapter == null);
	public static final boolean estaLigado = (temBluetooth) ? adapter.isEnabled() : false;
	public static final Set<BluetoothDevice> dispPareados = (temBluetooth) ? adapter.getBondedDevices() : null;
	public static final ArrayList<BluetoothDevice> disponiveis = (temBluetooth) ? new ArrayList<BluetoothDevice>(dispPareados) : null;
	
	public static void ligarBluetooth(Activity act) {
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			act.startActivityForResult(enableBtIntent, 1);
	}
		
	public static BluetoothDevice getDevice(String address){
		for(BluetoothDevice device : dispPareados){
			if(device.getAddress().equals(address)){
				return device;
			}
		}
		return null;
	}
	
	public static void prepararDisponiveis(CarroDao dao){
		for(BluetoothDevice dev : Bluetooth.dispPareados){
			Carro carro = dao.selectById(dev.getAddress());
			if (carro != null){
				Bluetooth.disponiveis.remove(dev);
			}
		}
	}
}
