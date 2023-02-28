package br.com.the475group.diagnosticar.sincronizacao;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import br.com.the475group.diagnosticar.R;

public class PreferenciasActivity extends PreferenceActivity {

	private static final long serialVersionUID = 1L;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.preferences);
		
	}

}
