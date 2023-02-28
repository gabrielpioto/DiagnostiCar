package br.com.the475group.diagnosticar.estatistica;

import java.util.HashMap;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import br.com.the475group.diagnosticar.BaseActivity;
import br.com.the475group.diagnosticar.R;

public class EstatisticaActivity extends BaseActivity implements OnItemSelectedListener{

	private Spinner spnrEixoX;
	private Spinner spnrEixoY;
	private String [] elementosX;
	private String [] elementosY;
	private Button btnTesteGrafico;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_estatistica);		
		setBtnCabecImage(android.R.drawable.ic_menu_preferences);
		
		Resources res = getResources();
		elementosX = res.getStringArray(R.array.estatistica_axisX_elements);
		elementosY = res.getStringArray(R.array.estatistica_axisY_elements);
		
		spnrEixoX = (Spinner) findViewById(R.id.spnrEixoX);
		spnrEixoX.setOnItemSelectedListener(this);
		spnrEixoX.setAdapter(criaAdapter(spnrEixoX, elementosX));
		
		spnrEixoY = (Spinner) findViewById(R.id.spnrEixoY);
		spnrEixoY.setOnItemSelectedListener(this);
		spnrEixoY.setAdapter(criaAdapter(spnrEixoY, elementosY));
		
		btnTesteGrafico = (Button) findViewById(R.id.btnTesteGrafico);
		btnTesteGrafico.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), XYChartBuilder.class));
				
			}
		});
		
		int codX = getIntent().getIntExtra("x", 0);
		int codY = getIntent().getIntExtra("y", 0);
		HashMap<String,Ponto[]> dados = (HashMap<String, Ponto[]>) getIntent().getSerializableExtra("dados");
				
		atualizaGrafico(elementosX[codX], elementosY[codY], dados);
	}

	private ArrayAdapter<String> criaAdapter(Spinner s, String[] resource) {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, resource); 
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return adapter;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_estatistica, menu);
		return true;
	}

	private void atualizaGrafico(String eixoX, String eixoY, HashMap<String,Ponto[]> dados){
			
	}
	
	public class Ponto{
		public double x;
		public double y;
		
		public Ponto() {
			 x=0;
			 y=0;
		}

		public Ponto(double x, double y) {
			super();
			this.x = x;
			this.y = y;
		}
		
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO: atualizar grafico
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO: fazer nada eu acho
		
	}
	
	
	

}
