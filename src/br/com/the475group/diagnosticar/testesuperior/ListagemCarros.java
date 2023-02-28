package br.com.the475group.diagnosticar.testesuperior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import br.com.the475group.diagnosticar.R;
import br.com.the475group.diagnosticar.gerenciadorDeCarro.RegistraCarro;
import br.com.the475group.diagnosticar.utilitarias.Carro;

public class ListagemCarros extends Activity{
	
	private ListView lstCarros;
	public ArrayList<Carro> listaCarros;
	public SimpleAdapter adapter; //Usado para colocar informa��es na ListView

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gerencia_carros);
		
		this.lstCarros = (ListView) findViewById(R.id.gerencia_lstCarros);
		this.listaCarros = new ArrayList<Carro>();
		
		this.lstCarros.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				listaCarros.get(position).getAddress();
				Intent it = new Intent(ListagemCarros.this, RegistraCarro.class);
				startActivity(it);
			}
		});
				
	}
	
	//M�todo pega as informa��es do ArrayList e mostra na ListView
	public void insereListView() {
		List<Map<String, String>> nomesCarros = new ArrayList<Map<String, String>>(); 
		nomesCarros = geraLista();
		adapter = new SimpleAdapter(this, nomesCarros, android.R.layout.simple_list_item_2, new String[] { "campo1",
						"campo2" }, new int[] { android.R.id.text1, android.R.id.text2 });
		lstCarros.setAdapter(adapter);
	}
	
	//Gera um List<Map<String, String>> para ser usado no m�todo insereListView, pegando as informa��es
	//do arryList da classe, o listaCarros, e organiza para mostrar o nome como Item e o adress como subItem
	private List<Map<String, String>> geraLista() {

		List<Map<String, String>> nomesCarros = new ArrayList<Map<String, String>>();

		for (int i = 0; i < listaCarros.size(); i++) {
			Map<String, String> datum = new HashMap<String, String>(2);

			datum.put("campo1", listaCarros.get(i).getNome());
			datum.put("campo2", "" + listaCarros.get(i).getAddress());
			nomesCarros.add(datum);
		}
		return nomesCarros;
	}
	
	
	
}
