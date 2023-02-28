package br.com.the475group.diagnosticar.sincronizacao;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import br.com.the475group.diagnosticar.BaseActivity;
import br.com.the475group.diagnosticar.R;
import br.com.the475group.diagnosticar.modelo.Notificacao;

public class SincronizaActivity extends BaseActivity {

	private static final long serialVersionUID = 1L;
	private static final String SHARED_PREFERENCES = "sincPreferencias";
	private static final String SHARED_CONTA = "conta";
	
	private static final int DIALOG_LISTA = 0;
	private static final int DIALOG_ADD_CONTA = 1;
	private static final int DIALOG_DESCONECTADO = 2;
	
	private Button btnSincroniza;
	
	private String[] contas;
	private int idConta;
	private boolean sincronizando = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sincroniza);
		
		btnSincroniza = (Button) findViewById(R.id.sincroniza_btnSincroniza);
		if(sincronizando) {
			btnSincroniza.setText(R.string.sincroniza_btn_cancelar);
		}
		btnSincroniza.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(sincronizando) {
					Notificacao.cancelaNotificacao(getApplicationContext());
					btnSincroniza.setText(R.string.sincroniza_btn_sincroniza);
					sincronizando = false;
				} else {
					sinc();
				}
			}
		});
		setBtnCabecClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				getAccounts();
			}

		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_sincroniza, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_settings:
				Intent it = new Intent(SincronizaActivity.this, PreferenciasActivity.class);
				startActivity(it);
				break;
		}
		return false;
	}	
	
	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		switch (id) {
			case DIALOG_LISTA:
				return new AlertDialog.Builder(SincronizaActivity.this)
				.setIcon(R.drawable.ic_launcher)
                .setTitle(R.string.sincroniza_dialog_lista_titulo)
                .setSingleChoiceItems(args.getStringArray("nomes"), args.getInt("check"), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        idConta = whichButton;
                    }
                })
                .setPositiveButton(R.string.sincroniza_dialog_lista_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    	String conta = contas[idConta];
                    	salvarConta(conta);
                    }
                })
                .setNegativeButton(R.string.sincroniza_dialog_lista_cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    	
                    }
                })
               .create();
			
			case DIALOG_ADD_CONTA:
				return new AlertDialog.Builder(SincronizaActivity.this)
                .setIcon(R.drawable.ic_launcher)
                .setTitle(R.string.sincroniza_dialog_conta_titulo)
                .setMessage(R.string.sincroniza_dialog_conta_msg)
                .setPositiveButton(R.string.sincroniza_dialog_conta_criar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    	final Intent intent = new Intent(Settings.ACTION_ADD_ACCOUNT);
                    	startActivityForResult(intent, 0);
                    }
                })
                .setNegativeButton(R.string.sincroniza_dialog_conta_depois, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                })
                .create();
			
			case DIALOG_DESCONECTADO:
				return new AlertDialog.Builder(SincronizaActivity.this)
                .setIcon(R.drawable.ic_launcher)
                .setTitle(R.string.sincroniza_dialog_desconectado_titulo)
                .setMessage(R.string.sincroniza_dialog_desconectado_msg)
                .setPositiveButton(R.string.sincroniza_dialog_desconectado_criar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    	final Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            			startActivityForResult(intent, 0);
                    }
                })
                .setNegativeButton(R.string.sincroniza_dialog_conta_depois, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                })
                .create();
		}
		return null;
	}
	
	private void sinc() {
		if( InternetStatus.conectado(getApplicationContext()) ) {
			if( InternetStatus.isWifi(getApplicationContext()) ) 
				Toast.makeText(getApplicationContext(), "Conectado ao wifi", Toast.LENGTH_LONG).show();
			
			Notificacao.geraNotificacao(getApplicationContext(), this.getIntent(), 
					"Sincronizando...", "Diagnosticar", "Sincronizando dados");
			
			sincronizando = true;
			btnSincroniza.setText(R.string.sincroniza_btn_cancelar);
			
			// TODO efetivar a sincronização
			//Toast.makeText(getApplicationContext(), Sincronizar.testeJson(), Toast.LENGTH_LONG).show();
			
			// Verificar melhor método para cancelar a notificação e a sincronização 
			// finish();
		} else {
			showDialog(DIALOG_DESCONECTADO);
		}
	}
	
	private void getAccounts() {		
		Account[] accounts = AccountManager.get(this).getAccounts();
		int tam = accounts.length;
		
		if(tam > 0) { 
			Bundle bd = new Bundle();
			String[] nomes = new String[tam];
			bd.putInt("check", 0);
			
			for (int i = 0; i < tam; i++) {
				nomes[i] = accounts[i].name;
				if( recuperarConta().equals(nomes[i]) )	
					bd.putInt("check", i);
			}

			this.contas = nomes;
			bd.putStringArray("nomes", nomes);
			showDialog(DIALOG_LISTA, bd);
		} else {
			showDialog(DIALOG_ADD_CONTA);
		}
	}
	
	public void salvarConta(String conta) {
		SharedPreferences prefs = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);

		Editor ed = prefs.edit();
		ed.putString(SHARED_CONTA, conta);
		ed.commit();
	}

	public String recuperarConta() {
		SharedPreferences prefs = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
		return (String) prefs.getString(SHARED_CONTA, "Nenhuma Conta");
	}
	
}
