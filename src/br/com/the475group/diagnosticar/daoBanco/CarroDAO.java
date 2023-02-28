package br.com.the475group.diagnosticar.daoBanco;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.com.the475group.diagnosticar.utilitarias.Carro;

public class CarroDAO implements DAO<Carro, String>{

	private static final long serialVersionUID = 1L;
	private SQLiteDatabase db;
	private Context context;
	private Cursor cursor;
	private ArrayList<Carro> listaCarros;
	private Carro carro;
	
	public CarroDAO(Context context) {
		this.context = context;
		this.listaCarros = new ArrayList<Carro>();
		this.carro = new Carro();
		this.createTable();
	}
	
	private void openDB() {
		this.db = context.openOrCreateDatabase("DiagnostiCar", SQLiteDatabase.CREATE_IF_NECESSARY, null);
	}
	
	private void createTable() {
		this.openDB();
		this.db.beginTransaction();
		try {
			String tableSql = "CREATE TABLE IF NOT EXISTS carro (carro_address TEXT PRIMARY KEY, carro_nome TEXT, carro_dispositivo)";

			this.db.execSQL(tableSql);
			this.db.setTransactionSuccessful();
		} catch(SQLException ex){
			
		}finally {
			this.db.endTransaction();
			this.closeDB();
		}
	}
	
	private boolean verificaRegistro() {
		this.openDB();
		this.db.beginTransaction();
		try {
			this.cursor = this.db.rawQuery("SELECT * FROM carro ORDER BY carro_nome", null);

			if (this.cursor.getCount() != 0) {
				this.cursor.moveToFirst();
				return true;
			}
			else {
				return false;
			}
		} catch (Exception erro) {
			return false;
		} finally {
			this.db.setTransactionSuccessful();
			this.db.endTransaction();
			this.closeDB();
		}
	}
	
	@Override
	public boolean insert(Carro data) {
		boolean retorno = false;
		this.openDB();
		this.db.beginTransaction();
		try {
			String comandoSQL = "INSERT INTO carro (carro_address, carro_nome, carro_dispositivo) VALUES(?,?,?)";
			this.db.execSQL(comandoSQL, new Object[] { data.getAddress(), data.getNome(), data.getDispositivo() });
			this.db.setTransactionSuccessful();
			retorno = true;
		} catch(SQLException ex){
			retorno = false;
		}finally {
			this.db.endTransaction();
			this.closeDB();
		}
		return retorno;
	}

	@Override
	public boolean update(Carro data, String older) {
		boolean retorno = false;
		this.openDB();
		this.db.beginTransaction();
		try {
			String comandoSQL = "UPDATE carro set carro_address=?, carro_nome=?, carro_dispositivo=? where carro_address=?";
			this.db.execSQL(comandoSQL, new Object[] { data.getAddress(), data.getNome(), data.getDispositivo(),  older});
			this.db.setTransactionSuccessful();
			retorno = true;
		} catch(SQLException ex){
			retorno = false;
		}finally {
			this.db.endTransaction();
			this.closeDB();
		}
		return retorno;
	}

	@Override
	public boolean delete(String id) {
		this.openDB();
		this.db.beginTransaction();
		try {
			String comandoSQL = "DELETE FROM carro WHERE carro_address =?";
			this.db.execSQL(comandoSQL, new Object[] { id });
			this.db.setTransactionSuccessful();
			return true;
		} catch (Exception erro) {
			return false;
		} finally {
			this.db.endTransaction();
			this.closeDB();
		}
	}

	@Override
	public Carro selectById(String field) {
		this.openDB();
		this.db.beginTransaction();
		
		try {
			String comandoSQL = "SELECT * FROM carro where carro_address=?";
			this.cursor = this.db.rawQuery(comandoSQL, new String[] {field});

			if (this.cursor.getCount() != 0) {
				this.cursor.moveToFirst();
				this.carro.setAddress(this.cursor.getString(this.cursor.getColumnIndex("carro_address")));
				this.carro.setNome(this.cursor.getString(this.cursor.getColumnIndex("carro_nome")));
				this.carro.setDispositivo(this.cursor.getString(this.cursor.getColumnIndex("carro_dispositivo")));
			}
			else {
				return null;
			}
			
			this.cursor.close();
		} catch (Exception erro) {
			return null;
		} finally {
			this.db.setTransactionSuccessful();
			this.db.endTransaction();
			this.closeDB();
		}
		return this.carro;
	}

	@Override
	public ArrayList<Carro> selectAll() {
		this.listaCarros.removeAll(this.listaCarros);
		try {
			if (this.verificaRegistro()) {
				for (int i = 1; i <= this.cursor.getCount(); i++) {
					Carro car = new Carro();
					
					car.setAddress(this.cursor.getString(this.cursor.getColumnIndex("carro_address")));
					car.setNome(this.cursor.getString(this.cursor.getColumnIndex("carro_nome")));
					car.setDispositivo(this.cursor.getString(this.cursor.getColumnIndex("carro_dispositivo")));
					
					this.listaCarros.add(car);
					this.cursor.moveToNext();
				}
			}
		} catch (Exception erro) {
			this.listaCarros = null;
		} finally {
			this.cursor.close();
		}
		return this.listaCarros;
	}

	@Override
	public void closeDB() {
		// TODO Auto-generated method stub
		if(this.db.isOpen())
			this.db.close();
	}
	
}
