package br.com.the475group.diagnosticar;

import android.os.Bundle;
import android.util.Log;

public class GerenciaColetaBDD extends BaseActivity {
	private long tmpEspera, tmpEsperaObd;
	private boolean isColeta, isObd;
	

	public void startColeta(long tmpespera) {
		this.tmpEspera = tmpespera;
		while (isColeta) {
			try {
				Thread.sleep(tmpEspera);
				// Todo code aplication here(zueira é pra fazer a coleta do
				// banco da
				//skynet aki)
			} catch (InterruptedException ie) {
				Log.d("gerencia activity", ie.toString());
			}
		}
	}

	public void startObd(long tmpesperaObd) {
		this.tmpEspera = tmpesperaObd;
		while (isObd) {
			try {
				Thread.sleep(tmpEspera);
				// Todo code aplication here(zueira é pra iniciar a classe de
				// coleta do obd da skynet aqui
			} catch (InterruptedException ie) {
				Log.d("Gerencia Coleta Activity", ie.toString());
			}
		}
	}
	
	public void stopColeta(){
		this.isColeta = false;
	}

	public void stopObd(){
		this.isObd = false;
	}
}
