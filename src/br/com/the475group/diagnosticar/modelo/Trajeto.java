package br.com.the475group.diagnosticar.modelo;

/**
 *
 * @author Yves
 */
public class Trajeto {
    
    private long id;
    private String nome;
    private String origem;
    private String destino;
    
    public Trajeto() {
	}
    
    public Trajeto(String nome, String origem, String destino) {
    	this.nome = nome;
    	this.origem = origem;
    	this.destino = destino;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
    
}
