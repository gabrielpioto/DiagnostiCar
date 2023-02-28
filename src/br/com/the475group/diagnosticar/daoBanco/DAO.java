package br.com.the475group.diagnosticar.daoBanco;

import java.io.Serializable;
import java.util.List;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class DAO<T, ID extends Serializable> extends DroidDao<T,ID>{	
	
	private static final String DATABASE_NAME = "DIAGNOSTICAR";
	
	protected DAO(Class<T> model, TableDefinition<T> tableDef, Context ctx){
		super(model, tableDef, createDatabase(ctx));
	}
	
	private static SQLiteDatabase createDatabase(Context ctx){
		SQLiteDatabase db = ctx.openOrCreateDatabase(DATABASE_NAME, SQLiteDatabase.CREATE_IF_NECESSARY, null);
		try {
			TableDefinition.onCreate(db);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return db;
	}
	
	public boolean insert(T dado){
		try {
			return (super.save(dado)!=-1);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean update (T novo, T antigo){
		return (delete(antigo) && insert(novo));
	}
	
	public abstract boolean delete(T dado);
	
	public void insertAll(List<T> dados){
		for(T dado : dados) insert(dado);
	}	
	
	public void deleteAll(List<T> dados){
		for(T dado : dados) delete(dado);
	}
	
}
