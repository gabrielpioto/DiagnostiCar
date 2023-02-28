package br.com.the475group.diagnosticar.daoBanco;

import org.droidpersistence.dao.TableDefinition;

import android.content.Context;
import br.com.the475group.diagnosticar.modelo.Carro;


public class CarroDao extends DAO<Carro,String>{
	
	private static CarroDao instance = null;
	
	private CarroDao(Context ctx) {
		super(Carro.class, new CarroTableDefinition(), ctx);
	}
	
	public static class CarroTableDefinition extends TableDefinition<Carro>{
		public CarroTableDefinition() {
			super(Carro.class);
		}
	}

	@Override
	public boolean delete(Carro dado) {
		return super.delete(dado.getAddress());
	}
	
	public static CarroDao getInstance(Context ctx){
		if(instance == null){
			instance = new CarroDao(ctx);
		}
		return instance; 		
	}
	
}

