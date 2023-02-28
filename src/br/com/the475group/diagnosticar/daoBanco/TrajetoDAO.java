/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.the475group.diagnosticar.daoBanco;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.com.the475group.diagnosticar.utilitarias.Trajeto;

/**
 * 
 * @author Yves
 */
public class TrajetoDAO {

	private SQLiteDatabase db;
	private Cursor cursor;
	private Context context;

	public TrajetoDAO(Context context) {
		this.context = context;
		this.createTable();
	}

	private void abreBanco() {
		this.db = context.openOrCreateDatabase("DiagnostiCar",
				SQLiteDatabase.CREATE_IF_NECESSARY, null);
	}

	public void fecharBanco() {
		if (this.db.isOpen()) {
			this.db.close();
		}
	}

	private void createTable() {
		this.abreBanco();
		this.db.beginTransaction();
		try {
			String tableSql = "CREATE TABLE IF NOT EXISTS trajeto (tjt_id PRIMARY KEY, tjt_nome TEXT, tjt_origem TEXT, tjt_destino TEXT)";
			this.db.execSQL(tableSql);
			this.db.setTransactionSuccessful();
		} catch (SQLException ex) {
		} finally {
			this.db.endTransaction();
			this.fecharBanco();
		}
	}

	private boolean verificaRegistro() {
		String comandoSQL = "SELECT * FROM trajeto ORDER BY tjt_nome";
		this.abreBanco();
		this.db.beginTransaction();
		try {
			this.cursor = this.db.rawQuery(comandoSQL, null);

			if (this.cursor.getCount() != 0) {
				this.cursor.moveToFirst();
				return true;
			} else {
				return false;
			}

		} catch (Exception erro) {
			return false;
		} finally {
			this.db.setTransactionSuccessful();
			this.db.endTransaction();
			this.fecharBanco();
		}
	}

	public void insert(Trajeto tjt) {
		this.abreBanco();
		this.db.beginTransaction();
		try {
			String comandoSQL = "INSERT INTO trajeto (tjt_nome, tjt_origem, tjt_destino) VALUES(?,?,?)";
			this.db.execSQL(comandoSQL,	new Object[] { tjt.getNome(), tjt.getOrigem(), tjt.getDestino() });
			this.db.setTransactionSuccessful();
		} finally {
			this.db.endTransaction();
			this.fecharBanco();
		}
	}

	public boolean delete(long id) {
		this.abreBanco();
		this.db.beginTransaction();
		try {
			String comandoSQL = "DELETE FROM trajeto WHERE tjt_id = VALUES(?)";
			this.db.execSQL(comandoSQL, new Object[] { id });
			this.db.setTransactionSuccessful();
			return true;
		} catch (Exception erro) {
			return false;
		} finally {
			this.db.endTransaction();
			this.fecharBanco();
		}
	}
	
	public boolean clear() {
		this.abreBanco();
		this.db.beginTransaction();
		try {
			String comandoSQL = "DELETE FROM trajeto WHERE 1 = 1";
			this.db.execSQL(comandoSQL);
			this.db.setTransactionSuccessful();
			return true;
		} catch (Exception erro) {
			return false;
		} finally {
			this.db.endTransaction();
			this.fecharBanco();
		}
	}

	public Trajeto selectByName(String nome) {
		this.abreBanco();
		this.db.beginTransaction();
		Trajeto tjt = new Trajeto();

		try {
			String comandoSQL = "SELECT * FROM trajeto where tjt_nome=?";
			this.cursor = this.db.rawQuery(comandoSQL, new String[] { nome });

			if (this.cursor.getCount() != 0) {
				this.cursor.moveToFirst();
				tjt.setId(this.cursor.getLong(this.cursor
						.getColumnIndex("tjt_id")));
				tjt.setNome(this.cursor.getString(this.cursor
						.getColumnIndex("tjt_nome")));
				tjt.setOrigem(this.cursor.getString(this.cursor
						.getColumnIndex("tjt_origem")));
				tjt.setDestino(this.cursor.getString(this.cursor
						.getColumnIndex("tjt_destino")));
			} else {
				tjt = null;
			}

			this.cursor.close();
		} catch (Exception erro) {
			tjt = null;
		} finally {
			this.db.setTransactionSuccessful();
			this.db.endTransaction();
			fecharBanco();
		}
		return tjt;
	}

	public ArrayList<Trajeto> selectAll() {
		ArrayList<Trajeto> tjtLista = new ArrayList<Trajeto>();
		try {
			if (this.verificaRegistro()) {
				for (int i = 1; i <= this.cursor.getCount(); i++) {
					Trajeto tjt = new Trajeto();

					tjt.setId(this.cursor.getLong(this.cursor
							.getColumnIndex("tjt_id")));
					tjt.setNome(this.cursor.getString(this.cursor
							.getColumnIndex("tjt_nome")));
					tjt.setOrigem(this.cursor.getString(this.cursor
							.getColumnIndex("tjt_origem")));
					tjt.setDestino(this.cursor.getString(this.cursor
							.getColumnIndex("tjt_destino")));

					tjtLista.add(tjt);
					this.cursor.moveToNext();
				}
			}
		} catch (Exception erro) {
			tjtLista = null;
		} finally {
			this.cursor.close();
		}
		return tjtLista;
	}
}