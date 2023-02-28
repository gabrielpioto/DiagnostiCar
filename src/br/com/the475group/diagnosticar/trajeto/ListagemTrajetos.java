/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.the475group.diagnosticar.trajeto;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.the475group.diagnosticar.R;
import br.com.the475group.diagnosticar.daoBanco.TrajetoDAO;
import br.com.the475group.diagnosticar.utilitarias.Trajeto;

/**
 * 
 * @author Yves
 */
public class ListagemTrajetos extends Activity {
	private ListView lstTrajetos;
	private TrajetoDAO tjtDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gerencia_carros);

		tjtDao = new TrajetoDAO(this);
		final ArrayList<Trajeto> tjtArrayFromBdd = tjtDao.selectAll();
		ArrayAdapter<Trajeto> adapter = new ArrayAdapter<Trajeto>(this,
				android.R.layout.simple_list_item_1, tjtArrayFromBdd);

		this.lstTrajetos = (ListView) findViewById(R.id.gerencia_lstCarros);
		this.lstTrajetos.setAdapter(adapter);
		this.lstTrajetos
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
						System.out.println(tjtArrayFromBdd.get(position).getId());
						tjtDao.delete(tjtArrayFromBdd.get(position).getId());
						startActivity(new Intent(ListagemTrajetos.this, ListagemTrajetos.class ));

					}
				});
	}
}