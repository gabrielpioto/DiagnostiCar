package br.com.the475group.diagnosticar.gerenciadorDeCarro;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;
import br.com.the475group.diagnosticar.GerenciaActivity;
import br.com.the475group.diagnosticar.R;
import br.com.the475group.diagnosticar.daoBanco.CarroDao;
import br.com.the475group.diagnosticar.modelo.Carro;

public class GerenciaCarrosActivity extends GerenciaActivity<Carro, String> implements OnClickListener, OnItemClickListener{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setDao(CarroDao.getInstance(this));
		
		setArrayAdapter(new MyArrayAdapter(this, getDao().getAll()));
		
		setRegistraActivityClass(RegistraCarroActivity.class);
		
		setTitle(R.string.gerencia_carros_titulo);
	}
	
	private class MyArrayAdapter extends ArrayAdapter<Carro>{
		private List<Carro> carros;
		
		public MyArrayAdapter(Context ctx, List<Carro> carros) {
			super(ctx,android.R.layout.simple_list_item_2,carros);
			this.carros = carros;
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

		        text1.setText(carros.get(position).getNome());
		        text2.setText(carros.get(position).getAddress());

		        return twoLineListItem;
		}
	}	
}
