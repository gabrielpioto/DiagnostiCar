package br.com.the475group.diagnosticar.daoBanco;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.the475group.diagnosticar.bluetooth.Bluetooth;
import br.com.the475group.diagnosticar.modelo.Carro;

public class CarroDao extends DAO<Carro, String> {
	 
   	private static final String TAG = "CarroDao";
	
	private static final String TABLE_CARRO = "carro";
 
    public static final String COL_ID = "_id";
    public static final String COL_NOME = "name";
    public static final String COL_DISPOSITIVO = "dispositivo";
	
    
    public CarroDao(Context context) {
		super(context);
	}
    
    @Override
	public void onCreate(SQLiteDatabase db) {
    	String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CARRO + "("
                + COL_ID + " TEXT PRIMARY KEY," + COL_NOME + " TEXT,"
                + COL_DISPOSITIVO + " TEXT" + ")";
    	db.execSQL(CREATE_CONTACTS_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARRO);
        onCreate(db);		
	}

	@Override
	public void insert(Carro dado) {
		SQLiteDatabase db = this.getWritableDatabase();
		Log.d(TAG,"inserindo carro...");
				 
        ContentValues values = new ContentValues();
        values.put(COL_ID, dado.getAddress());
        values.put(COL_NOME, dado.getNome());
        values.put(COL_DISPOSITIVO, dado.getDispositivo());
        
        long n = db.insert(TABLE_CARRO, null, values);
        Log.d(TAG,"carro inserido com sucesso. Retorno: "+n);
        db.close();
	}

//	@Override
//	public void update(Carro dado) {
//		SQLiteDatabase db = this.getWritableDatabase();
//		Log.d(TAG,"atualizando carro...");
//				 
//        ContentValues values = new ContentValues();
//        values.put(COL_NOME, dado.getNome());
//        values.put(COL_DISPOSITIVO, dado.getDispositivo());
// 
//        int n = db.update(TABLE_CARRO, values, COL_ID + " = ?",
//                new String[] { String.valueOf(dado.getAddress()) });
//        Log.d(TAG,"carro atualizado com sucesso. Retorno: "+n);
//	}

	@Override
	public void delete(Carro dado) {
		SQLiteDatabase db = this.getWritableDatabase();
		Log.d(TAG,"deletando carro...");
		
        int n = db.delete(TABLE_CARRO, COL_ID + " = ?",
                new String[] { dado.getAddress() });
        
        Log.d(TAG,"carro deletado com sucesso. Retorno: "+n);
        db.close();
	}

	@Override
	public Carro selectById(String id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Log.d(TAG,"lendo carro...");
		Carro carro = null;
        Cursor cursor = db.query(TABLE_CARRO, new String[] { COL_ID,
                COL_NOME, COL_DISPOSITIVO }, COL_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        
        if(cursor.moveToFirst()){
        	carro = getByCursor(cursor);	        		
	        Log.d(TAG,"carro lido com sucesso. Retorno: "+carro.toString());
        }
        cursor.close();
        return carro;
	}

	@Override
	public Cursor getCursorWithAll() {
		SQLiteDatabase db = this.getReadableDatabase();
		Log.d(TAG,"obtendo cursor com todos os carros...");
		String selectQuery = "SELECT  * FROM " + TABLE_CARRO;
        Cursor c = db.rawQuery(selectQuery, null);
        Log.d(TAG,"cursor obtido com sucesso...");
        return c;        
	}


	@Override
	public Carro getByCursor(Cursor cursor) {
		Carro carro = new Carro();
        carro.setAddress(cursor.getString(cursor.getColumnIndex(COL_ID)));
        carro.setNome(cursor.getString(cursor.getColumnIndex(COL_NOME)));
        carro.setDispositivo(cursor.getString(cursor.getColumnIndex(COL_DISPOSITIVO)));
        return carro;
	}
}
