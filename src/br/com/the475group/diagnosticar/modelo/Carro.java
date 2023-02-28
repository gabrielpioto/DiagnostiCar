package br.com.the475group.diagnosticar.modelo;

import java.io.Serializable;

public class Carro implements Serializable{
	
	private String address;
	private String nome;
	private String dispositivo;
	
	public Carro() {
		this.address = "";
		this.nome = "";
		this.dispositivo = "";
	}

	public Carro(String adress, String nome, String dispositivo) {
		this.address = adress;
		this.nome = nome;
		this.dispositivo = dispositivo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDispositivo() {
		return dispositivo;
	}

	public void setDispositivo(String dispositivo) {
		this.dispositivo = dispositivo;
	}
}
