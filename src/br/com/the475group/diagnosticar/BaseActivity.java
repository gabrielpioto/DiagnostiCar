package br.com.the475group.diagnosticar;

import java.io.Serializable;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BaseActivity extends Activity implements Serializable{
	
	private LinearLayout baseLayout;
	private TextView txtCabecalho;
	private ImageButton btnCabecalho;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.setContentView(R.layout.activity_base);
		baseLayout = (LinearLayout) findViewById(R.id.base_layout);
		txtCabecalho = (TextView) findViewById(R.id.txtCabecalho);
		btnCabecalho = (ImageButton) findViewById(R.id.btnCabecalho);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void setContentView(int layoutResID) {
		View view = View.inflate(this,layoutResID,null);
		baseLayout.addView(view,LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	}
	
	@Override
	public void setContentView(View view) {
		baseLayout.addView(view,LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	}
	
	public void setTxtCabecText(final int textId){
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				txtCabecalho.setText(textId);
			}
		});	
		
	}
	
	public void setTxtCabecText(final String texto){
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				txtCabecalho.setText(texto);
			}
		});	
	}
	
	protected void setBtnCabecClickListener(OnClickListener listener){
		btnCabecalho.setOnClickListener(listener);
	}
	
	protected void setBtnCabecImage(int resId){
		btnCabecalho.setImageResource(resId);
	}

}
