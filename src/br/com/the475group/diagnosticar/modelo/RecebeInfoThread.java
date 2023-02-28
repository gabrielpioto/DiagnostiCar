package br.com.the475group.diagnosticar.modelo;

public class RecebeInfoThread extends Thread {

	private boolean cond;
	private long tempoPausa;

	public RecebeInfoThread(long tempoPausa) {
		this.cond = true;
		this.tempoPausa = tempoPausa;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		while (this.cond) {
			try {
				Thread.sleep(this.tempoPausa);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
