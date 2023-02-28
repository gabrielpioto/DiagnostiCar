package br.com.the475group.diagnosticar.modelo;

import java.io.Serializable;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Table;

@Table(name = "CARRO")
public class Carro implements Serializable{

	@PrimaryKey
	@Column(name = "_id")
	private String address;
	
	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "DISPOSITIVO")
	private String dispositivo;
	
	public Carro() {
		nome = "";
		address = "";
		dispositivo = "";
	}	

	public Carro(String address, String nome, String dispositivo) {
		super();
		this.address = address;
		this.nome = nome;
		this.dispositivo = dispositivo;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDispositivo() {
		return dispositivo;
	}

	public void setDispositivo(String dispositivo) {
		this.dispositivo = dispositivo;
	}	
	
}
