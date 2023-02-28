package br.com.the475group.diagnosticar.modelo;

import android.os.Handler;
import android.view.View.OnCreateContextMenuListener;
import br.com.the475group.diagnosticar.status.StatusActivity;
import br.com.the475group.simulador.InfoDAO;

public class RecebeInfoThread extends Thread {

	private boolean cond;
	private long tempoPausa;
	private InfoDAO infoDAO;
	private Handler handler;
	private Runnable runnAtualizacao;

	public RecebeInfoThread(long tempoPausa, final StatusActivity act) {
		this.cond = true;
		this.tempoPausa = tempoPausa;
		this.infoDAO = new InfoDAO(act);
		this.handler = new Handler();
		this.runnAtualizacao = new Runnable() {
			
			@Override
			public void run() {
				act.mostraInfo(infoDAO.selectAll());
				infoDAO.clear();
			}
		};
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		while (this.cond) {
			try {
				this.handler.post(this.runnAtualizacao);
				Thread.sleep(this.tempoPausa);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void parar(){
		this.cond = false;
	}
}