package br.com.the475group.diagnosticar.testesuperior;

import java.util.Random;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;
import br.com.the475group.diagnosticar.daoBanco.CarroDAO;
import br.com.the475group.diagnosticar.utilitarias.Carro;

public class EncontraCarro extends AsyncTask<Void, Carro, Boolean>{
	
	private int cond; //contador do la�o no doInBackground
	private Random aleatorio;
	private static int tamanhoMaximo = 5;//Condi��o para o la�o do doInBackground
	private String listaAdress[];
	private ListagemCarros activity;
	private CarroDAO daoCarro;
	private ProgressDialog dialog;
	
	public EncontraCarro(ListagemCarros activity) {
		this.cond = 0;
		this.activity = activity;
		this.daoCarro = new CarroDAO(this.activity);
		this.listaAdress = new String[tamanhoMaximo];
		this.aleatorio = new Random();
		this.dialog = new ProgressDialog(this.activity);
	}
	
	@Override
	protected void onProgressUpdate(Carro... values) {
		// TODO Auto-generated method stub
		this.activity.listaCarros.add(values[0]);
		//this.activity.adapter.notifyDataSetChanged();
		this.activity.insereListView();
		super.onProgressUpdate(values);
	}
	
	@Override
	protected void onPreExecute() {
		//Colocando informa��es para a ProgressDialog
		this.dialog.setMessage("Buscando dispositivos...");
		this.dialog.setCancelable(false);
        this.dialog.show();
		super.onPreExecute();
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		
		while(cond<tamanhoMaximo){
			try {
				Thread.sleep(1500);
				if(this.aleatorio.nextBoolean()){
					//Associa o adress "encontrado"
					this.listaAdress[cond] = String.valueOf(1000 + cond);
					
					//Verifica de o carro ja est� cadastrado no banco
					//Caso ja esteja, o objeto retornado vai ao progressUpdate
					//Caso n esteja, manda um novo objeto, com o nome de desconhecido
					Carro car = this.daoCarro.selectById(listaAdress[cond]);
					if(car != null){
						publishProgress(car);
					}else{
						publishProgress(new Carro(this.listaAdress[cond], "Desconhecido", "Desconhecido"));
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				return false;
			} finally{
				cond++;
			}
		}
		return true;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		//Ve se a ProgressDialog est� em execu��o, caso esteja, ela � finalizada 
		if (this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
		
		//Exibe mensagem atrav�s de um Toast, informando se tudo ocorreu correto ou n�o
		if(result){
			Toast.makeText(this.activity, "Busca de dispositivos finalizada", Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(this.activity, "Erro ao buscar", Toast.LENGTH_LONG).show();
		}
		super.onPostExecute(result);
	}
}
