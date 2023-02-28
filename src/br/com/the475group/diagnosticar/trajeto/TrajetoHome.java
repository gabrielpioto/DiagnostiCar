/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.the475group.diagnosticar.trajeto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import br.com.the475group.diagnosticar.R;
import br.com.the475group.diagnosticar.daoBanco.TrajetoDAO;
import br.com.the475group.diagnosticar.utilitarias.Trajeto;

/**
 * 
 * @author Yves
 */
public class TrajetoHome extends Activity {

	private ListView lstTrajetos;
	private TrajetoDAO tjtDao;
	private Button btnAdcionarTrajeto;
	private ArrayList<Trajeto> tjtArrayFromBdd;
	//Teste Slide
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	View.OnTouchListener gestureListener;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_trajeto_home);

		this.tjtDao = new TrajetoDAO(this);
	    
		this.lstTrajetos = (ListView) findViewById(R.id.lstView_listagem_trajetos);
		this.btnAdcionarTrajeto = (Button) findViewById(R.id.btnNovo_Trajeto);
		this.btnAdcionarTrajeto.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(TrajetoHome.this,
						CadastroTrajeto.class));
			}
		});

		
		
		this.lstTrajetos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3) {
						tjtDao.delete(tjtArrayFromBdd.get(position).getId());
						tjtDao.clear();
						startActivity(new Intent(TrajetoHome.this, TrajetoHome.class));

					}
				});

	}
	
	class MyGestureDetector extends SimpleOnGestureListener {
	    @Override
	    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
	        try {
	            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
	                return false;
	            // right to left swipe
	            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	                Toast.makeText(TrajetoHome.this, "Left Swipe", Toast.LENGTH_SHORT).show();
	            }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	                Toast.makeText(TrajetoHome.this, "Right Swipe", Toast.LENGTH_SHORT).show();
	            }
	        } catch (Exception e) {
	            // nothing
	        }
	        return false;
	    }
	}

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		tjtArrayFromBdd = tjtDao.selectAll();
		this.insereListView();
		
//		//teste
//		try{
//		 gestureDetector = new GestureDetector(new MyGestureDetector());
//		((View) lstTrajetos.getItemAtPosition(0)).setOnTouchListener((new View.OnTouchListener() {
//	        public boolean onTouch(View v, MotionEvent event) {
//	            return gestureDetector.onTouchEvent(event);
//	        }
//	    }));		
//		}catch(IndexOutOfBoundsException ex){
//			Toast.makeText(this, "deu merda:"+ex, 20000).show();
//		}
		
	}

	//Método pega as informações do ArrayList e mostra na ListView
		public void insereListView() {
			List<Map<String, String>> nomesTrajetos = new ArrayList<Map<String, String>>(); 
			nomesTrajetos = geraLista();
			SimpleAdapter adapter = new SimpleAdapter(this, nomesTrajetos, android.R.layout.simple_list_item_2, new String[] { "campo1",
							"campo2" }, new int[] { android.R.id.text1, android.R.id.text2 });
			lstTrajetos.setAdapter(adapter);
		}
		
		//Gera um List<Map<String, String>> para ser usado no método insereListView, pegando as informações
		//do arryList da classe, o listaCarros, e organiza para mostrar o nome como Item e o adress como subItem
		private List<Map<String, String>> geraLista() {

			List<Map<String, String>> nomesTrajetos = new ArrayList<Map<String, String>>();

			for (int i = 0; i < tjtArrayFromBdd.size(); i++) {
				Map<String, String> datum = new HashMap<String, String>(2);

				datum.put("campo1", tjtArrayFromBdd.get(i).getNome());
				datum.put("campo2", ""+ tjtArrayFromBdd.get(i).getOrigem()+" - " + tjtArrayFromBdd.get(i).getDestino());
				nomesTrajetos.add(datum);
			}
			return nomesTrajetos;
		}
}
