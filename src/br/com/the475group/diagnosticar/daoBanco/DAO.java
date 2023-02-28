package br.com.the475group.diagnosticar.daoBanco;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.com.the475group.diagnosticar.modelo.Carro;

public abstract class DAO<T, ID> extends SQLiteOpenHelper implements Serializable{
	
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_NAME = "DiagnostiCar";
	
	public DAO(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public abstract void insert(T dado);
	
	public abstract void delete(T dado);
	
	public abstract T selectById(ID id);
	
	public abstract Cursor getCursorWithAll();
	
	public abstract T getByCursor(Cursor c);
	
	public void update (T novo, T antigo){
		delete(antigo);
		insert(novo);
	}
	
	public List<T> selectAll(){
		List<T> lista = new ArrayList<T>(); 
		Cursor cursor = getCursorWithAll();
		if (cursor.moveToFirst()) {
            do {               
                lista.add(getByCursor(cursor));
            } while (cursor.moveToNext());
        }
		cursor.close();
		return lista;
	}
	
	public void insertAll(List<T> dados){
		for(T dado : dados) insert(dado);
	}	
	
	public void deleteAll(List<T> dados){
		for(T dado : dados) delete(dado);
	}
	
}
