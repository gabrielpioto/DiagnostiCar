/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.the475group.diagnosticar.daoBanco;

import org.droidpersistence.dao.TableDefinition;

import android.content.Context;
import br.com.the475group.diagnosticar.modelo.Trajeto;

/**
 * 
 * @author Yves
 */
public class TrajetoDao extends DAO<Trajeto,Integer>{
	
	private static TrajetoDao instance = null;
	
	private TrajetoDao(Context ctx) {
		super(Trajeto.class, new TrajetoTableDefinition(),ctx);
	}
	
	public static class TrajetoTableDefinition extends TableDefinition<Trajeto>{
		public TrajetoTableDefinition() {
			super(Trajeto.class);
		}
	}

	@Override
	public boolean delete(Trajeto dado) {
		return super.delete(dado.getId());
	}
	
	public static TrajetoDao getInstance(Context ctx){
		if(instance == null){
			instance = new TrajetoDao(ctx);
		}
		return instance;
	}
}
