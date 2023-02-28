package br.com.the475group.diagnosticar.daoBanco;

import java.util.ArrayList;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.com.the475group.simulador.Informacao;

public class InformacaoDAO {
	private SQLiteDatabase db;
	private Cursor cursor;
	private Context context;

	public InformacaoDAO(Context context) {
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
			String tableSql = "CREATE TABLE IF NOT EXISTS informacao (inf_id PRIMARY KEY, inf_codigo INT, inf_data DATE, inf_valor NUM, inf_unidade VARCHAR)";
			this.db.execSQL(tableSql);
			this.db.setTransactionSuccessful();
		} catch (SQLException ex) {
		} finally {
			this.db.endTransaction();
			this.fecharBanco();
		}
	}

	private boolean verificaRegistro() {
		String comandoSQL = "SELECT * FROM informacao ORDER BY inf_id";
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

	public void insert(Informacao inf) {
		this.abreBanco();
		this.db.beginTransaction();
		try {
			String comandoSQL = "INSERT INTO informacao (inf_codigo INT, inf_data DATE, inf_valor NUM, inf_unidade VARCHAR) VALUES(?,?,?,?)";
			this.db.execSQL(
					comandoSQL,
					new Object[] { inf.getCodigo(), inf.getData(),
							inf.getValor(), inf.getUnidade() });
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
			String comandoSQL = "DELETE FROM informacao WHERE inf_id = VALUES(?)";
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
			String comandoSQL = "DELETE FROM informacao WHERE 1 = 1";
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

	public Informacao selectByCode(int codigo) {
		this.abreBanco();
		this.db.beginTransaction();
		Informacao inf = new Informacao();

		try {
			String comandoSQL = "SELECT * FROM informcao where inf_codigo=?";
			this.cursor = this.db.rawQuery(comandoSQL,
					new String[] { String.valueOf(codigo) });

			if (this.cursor.getCount() != 0) {
				this.cursor.moveToFirst();

				inf.setCodigo(this.cursor.getInt(this.cursor
						.getColumnIndex("inf_id")));
				inf.setValor(this.cursor.getDouble(this.cursor
						.getColumnIndex("inf_valor")));
				inf.setUnidade(this.cursor.getString(this.cursor
						.getColumnIndex("inf_unidade")));
				inf.setData(this.cursor.getLong(this.cursor
						.getColumnIndex("inf_data")));
			} else {
				inf = null;
			}

			this.cursor.close();
		} catch (Exception erro) {
			inf = null;
		} finally {
			this.db.setTransactionSuccessful();
			this.db.endTransaction();
			fecharBanco();
		}
		return inf;
	}

	public ArrayList<Informacao> selectAll() {
		ArrayList<Informacao> infLista = new ArrayList<Informacao>();
		try {
			if (this.verificaRegistro()) {
				for (int i = 1; i <= this.cursor.getCount(); i++) {
					Informacao inf = new Informacao();

					inf.setCodigo(this.cursor.getInt(this.cursor
							.getColumnIndex("inf_id")));
					inf.setValor(this.cursor.getDouble(this.cursor
							.getColumnIndex("inf_valor")));
					inf.setUnidade(this.cursor.getString(this.cursor
							.getColumnIndex("inf_unidade")));
					inf.setData(this.cursor.getLong(this.cursor
							.getColumnIndex("inf_data")));

					infLista.add(inf);
					this.cursor.moveToNext();
				}
			}
		} catch (Exception erro) {
			infLista = null;
		} finally {
			this.cursor.close();
		}
		return infLista;
	}
}