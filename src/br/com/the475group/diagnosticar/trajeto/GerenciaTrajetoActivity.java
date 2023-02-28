package br.com.the475group.diagnosticar.trajeto;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;
import br.com.the475group.diagnosticar.GerenciaActivity;
import br.com.the475group.diagnosticar.R;
import br.com.the475group.diagnosticar.daoBanco.TrajetoDao;
import br.com.the475group.diagnosticar.modelo.Trajeto;

public class GerenciaTrajetoActivity extends GerenciaActivity<Trajeto, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setDao(TrajetoDao.getInstance(this));
		
		ArrayAdapter<Trajeto> adapter = new MyArrayAdapter(this,getDao().getAll());
		
		setArrayAdapter(adapter);
		
		setRegistraActivityClass(RegistraTrajetoActivity.class);
		setTitle(R.string.gerencia_trajeto_titulo);
	}
	
	private class MyArrayAdapter extends ArrayAdapter<Trajeto>{
		private List<Trajeto> trajetos;
		
		public MyArrayAdapter(Context ctx, List<Trajeto> carros) {
			super(ctx,android.R.layout.simple_list_item_2,carros);
			this.trajetos = carros;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			 TwoLineListItem twoLineListItem;

		        if (convertView == null) {
		            twoLineListItem = (TwoLineListItem) getLayoutInflater().inflate(
		                    android.R.layout.simple_list_item_2, null);
		        } else {
		            twoLineListItem = (TwoLineListItem) convertView;
		        }

		        TextView text1 = twoLineListItem.getText1();
		        TextView text2 = twoLineListItem.getText2();

		        Trajeto tjt = trajetos.get(position);
		        
		        text1.setText(tjt.getNome());
		        text2.setText(tjt.getOrigem()+" --> "+tjt.getDestino());

		        return twoLineListItem;
		}
	}

}
