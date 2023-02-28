package br.com.the475group.diagnosticar.bluetooth;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;

public class Bluetooth {

	private static final BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
	public static final boolean temBluetooth = !(adapter == null);
	public static final boolean estaLigado = (temBluetooth) ? adapter.isEnabled() : false;
	public static final Set<BluetoothDevice> dispPareados = (temBluetooth) ? adapter.getBondedDevices() : null;

	public static void ligaBluetooth(Activity act) {
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			act.startActivityForResult(enableBtIntent, 1);
	}
}
