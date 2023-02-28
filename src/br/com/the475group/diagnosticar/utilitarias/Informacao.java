package br.com.the475group.diagnosticar.utilitarias;

import java.sql.Date;

/**
 * @author Yves
 *
 */
public class Informacao {
	
	private long id;
	private long codigo;
	private Date data;
	private double valor;
	private String unidade;
	
	public Informacao() {
	}
	
	public Informacao(long id, long codigo, Date data, double valor, String unidade) {
		this.id = id;
		this.codigo = codigo;
		this.data = data;
		this.valor = valor;
		this.unidade = unidade;
	}
	
	//Getters & Setters
	
	public String getUnidade() {
		return unidade;
	}
	
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	
	public double getValor() {
		return valor;
	}
	
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public long getCodigo() {
		return codigo;
	}
	
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
}
