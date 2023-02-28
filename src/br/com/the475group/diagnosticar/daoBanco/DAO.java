package br.com.the475group.diagnosticar.daoBanco;

import java.io.Serializable;
import java.util.ArrayList;

public interface DAO<T, ID> extends Serializable{
	
	public boolean insert(T data);
	
	public boolean update (T data, ID older);
	
	public boolean delete(ID id);
	
	public T selectById(String field);
	
	public ArrayList<T> selectAll();
	
	public void closeDB();
	
	
}
