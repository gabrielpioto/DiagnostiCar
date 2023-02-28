package br.com.the475group.diagnosticar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import br.com.the475group.diagnosticar.daoBanco.DAO;

public class GerenciaActivity<T,ID> extends BaseActivity implements OnClickListener{
	
	private BaseActivity proximaActivity;
	private DAO<T,ID> dao;
	private String title;
	private ArrayList<T> itensCadastrados;
	
	//TODO: criar adapter e interagir com DAO
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//TODO: definir strings padronizadas de troca de objeto
		proximaActivity = (BaseActivity) getIntent().getSerializableExtra("act");
		dao = (DAO<T, ID>) getIntent().getSerializableExtra("dao");
		title = getIntent().getAction();
		
		ListView listView = (ListView)findViewById(R.id.gerencia_listView);
		listView.setAdapter(new MyAdapter(itensCadastrados));
		
		
//		SimpleAdapter adapter = new SimpleAdapter(this, nomesCarros ,
//				android.R.layout.simple_list_item_2, new String[] { "campo1",
//		"campo2" }, new int[] { android.R.id.text1,
//		android.R.id.text2 });
		
		setBtnCabecImage(android.R.drawable.ic_menu_add);
		setBtnCabecClickListener(this);
		setTxtCabecText(title);
		
		super.onCreate(savedInstanceState);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO: programar o que voltar da act de cadastro (só visualizo ou update)
		super.onActivityResult(requestCode, resultCode, data);
	}


	@Override
	public void onClick(View v) {
		Intent it = new Intent(this, proximaActivity.getClass());
		startActivityForResult(it, 0);
		//TODO: definir requests codes
		
	}
	
	private class MyAdapter extends SimpleAdapter {

		public MyAdapter(ArrayList array) {
			this(null,null,0,null,null);
			//TODO: programar tratamento de array
		}
		
		public MyAdapter(Context context, List<? extends Map<String, ?>> data,
				int resource, String[] from, int[] to) {
			super(context, data, resource, from, to);
			// TODO Auto-generated constructor stub
		}
		
		
		
	}
	


	
}
