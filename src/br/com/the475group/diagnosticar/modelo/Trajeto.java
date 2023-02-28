package br.com.the475group.diagnosticar.modelo;

import java.io.Serializable;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Table;

/**
 *
 * @author Yves
 */
@Table(name = "TRAJETO")
public class Trajeto implements Serializable{
	
	@PrimaryKey(autoIncrement = true)
	@Column(name = "_id")
	private Integer id;
	
	@Column(name = "NOME")
    private String nome;
	
	@Column(name = "ORIGEM")
    private String origem;
    
	@Column(name = "DESTINO")
    private String destino;
    
    public Trajeto() {
		id = null;
		nome = "";
		origem = "";
		destino = "";
	}	

    public Trajeto(Integer id, String nome, String origem, String destino) {
		super();
		this.id = id;
		this.nome = nome;
		this.origem = origem;
		this.destino = destino;
	}

	public int getId() {
    	return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
    
    @Override
    public String toString() {
    	return "Nome: "+nome+"; "+
    			"Origem: "+origem+"; "+
    			"Destino: "+destino+"; ";
    }
    
}
