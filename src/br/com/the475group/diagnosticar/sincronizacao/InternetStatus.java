package br.com.the475group.diagnosticar.sincronizacao;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

public class InternetStatus {
	
	public static boolean isWifi(Context ctx) {
		ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		State wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		
		if (wifi == NetworkInfo.State.CONNECTED)
		    return true;
		return false;
	}
	
	public static boolean conectado(Context ctx) {
	    NetworkInfo info = (NetworkInfo) ((ConnectivityManager) ctx
	            .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

	    if (info == null || !info.isConnected() || info.isRoaming())
	        return false;
	    return true;
	}
	
}
