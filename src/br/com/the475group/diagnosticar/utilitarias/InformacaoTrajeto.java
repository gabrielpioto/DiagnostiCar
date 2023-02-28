package br.com.the475group.diagnosticar.utilitarias;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;

public class InformacaoTrajeto {
	
	private Trajeto trajeto;
	//Conjunto de informações sobre o carro durante o trajeto
	private ArrayList<Informacao> informacoes;
	
	// método construtor principal
	public InformacaoTrajeto(Trajeto trajeto) {
		this.trajeto = trajeto;
		this.informacoes = new ArrayList<Informacao>();
	}
	
	// sobre carga de método construtor
	public InformacaoTrajeto(Trajeto trajeto, ArrayList<Informacao> informacoes) {
		this.trajeto = trajeto;
		this.informacoes = informacoes;
	}

	// sobre carga de método construtor
	public InformacaoTrajeto(Trajeto trajeto, Informacao info) {
		// super(), nesse caso, instância o ArrayList
		super();  
		
		this.trajeto = trajeto;
		this.informacoes.add(info);
	}
	
	// adiciona informacao
	public void addInformacao(Informacao info){
		this.informacoes.add(info);
	}
	
	// deleta informacao
	public void delInformacao(Informacao info) {
		this.informacoes.remove(info);
	}
	
	// deleta informacao por data
	public void delData(Date data) {
		Iterator<Informacao> it = this.informacoes.iterator();
		while( it.hasNext() ) {
			Informacao info = it.next();
			if( info.getData() == data )
				this.informacoes.remove(info);
		}
	}
		
	// Getters & Setters
	
	public ArrayList<Informacao> getInformacoes() {
		return informacoes;
	}
	
	public Trajeto getTrajeto() {
		return trajeto;
	}
	
}
