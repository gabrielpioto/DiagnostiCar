 package br.com.the475group.diagnosticar.gerenciadorDeCarro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import br.com.the475group.diagnosticar.R;
import br.com.the475group.diagnosticar.daoBanco.CarroDao;
import br.com.the475group.diagnosticar.modelo.Carro;

public class GerenciaCarros extends Activity {

	private ListView lstCarros;
	private ImageButton btnAdicionar;
	private ArrayList<Carro> listaCarros;// Armazena os carros já cadastrados
	private CarroDao daoCarro;

	// constantes para o método putExtra que passa o adress e o nome do
	// Dispositivo
	public static final String extraKeyDispositivo = "Dispositivo";
	public static final String extraKeyAdress = "Adress";
	public static final String extraKeyActivity = "Activity";
	public static final String extraKeyNomeCarro = "NomeCarro";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gerencia_carros);

		this.lstCarros = (ListView) findViewById(R.id.gerencia_lstCarros);
		this.btnAdicionar = (ImageButton) findViewById(R.id.gerencia_imgBtnAdiciona);

		this.daoCarro = new CarroDao(this);
		this.lstCarros.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent it = new Intent(getApplicationContext(), RegistraCarro.class);
				it.putExtra(extraKeyDispositivo, listaCarros.get(arg2).getDispositivo());
				it.putExtra(extraKeyNomeCarro, listaCarros.get(arg2).getNome());
				it.putExtra(extraKeyAdress, listaCarros.get(arg2).getAddress());
				it.putExtra(extraKeyActivity, true);
				startActivity(it);
			}
		});

		this.lstCarros.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, final int arg2, long arg3) {
						// TODO Auto-generated method stub
						AlertDialog.Builder builder = new AlertDialog.Builder(
								GerenciaCarros.this);
						builder.setMessage(R.string.activity_gerencia_carros_dialog_msg_deletar);
						builder.setPositiveButton(R.string.activity_gerencia_carros_dialog_btnSim, new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										daoCarro.delete(listaCarros.get(arg2));
										listaCarros = (ArrayList<Carro>)daoCarro.selectAll();
										insereListView();
										Toast.makeText(
												getApplicationContext(),
												R.string.activity_gerencia_carros_toast_deletado,
												Toast.LENGTH_LONG).show();
									}
								});
						builder.setNegativeButton(
								R.string.activity_gerencia_carros_dialog_btnNao,
								null);
						builder.show();
						return false;
					}
				});
		
		this.btnAdicionar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent it = new Intent(getApplicationContext(), RegistraCarro.class);
				it.putExtra(extraKeyActivity, false);
				startActivity(it);
			}

		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		this.listaCarros = (ArrayList<Carro>)daoCarro.selectAll();
		this.insereListView();
		super.onResume();
	}

	// Método pega as informações do ArrayList e mostra na ListView
	private void insereListView() {
		List<Map<String, String>> nomesCarros = new ArrayList<Map<String, String>>();
		nomesCarros = geraLista();
		SimpleAdapter adapter = new SimpleAdapter(this, nomesCarros,
				android.R.layout.simple_list_item_2, new String[] { "campo1",
						"campo2" }, new int[] { android.R.id.text1,
						android.R.id.text2 });
		lstCarros.setAdapter(adapter);
	}

	// Gera um List<Map<String, String>> para ser usado no método
	// insereListView, pegando as informações
	// do arryList da classe, o listaCarros, e organiza para mostrar o nome como
	// Item e o adress como subItem
	private List<Map<String, String>> geraLista() {

		List<Map<String, String>> nomesCarros = new ArrayList<Map<String, String>>();

		for (Carro carro : this.listaCarros) {
			Map<String, String> datum = new HashMap<String, String>(2);

			datum.put("campo1", carro.getNome());
			datum.put("campo2", carro.getAddress());
			nomesCarros.add(datum);
		}
		return nomesCarros;
	}
}
