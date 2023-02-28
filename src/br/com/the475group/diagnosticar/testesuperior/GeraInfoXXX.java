package br.com.the475group.diagnosticar.testesuperior;

import java.sql.Date;

import br.com.the475group.diagnosticar.utilitarias.Informacao;

public class GeraInfoXXX {

	private SuperiorBancoDao sbdao;

	public GeraInfoXXX() {
		sbdao = new SuperiorBancoDao(null);

		for (int i = 0; i < 10; i++)
			sbdao.insert(criarInformacao());
	}

	private Informacao criarInformacao() {
		Informacao inf = new Informacao();

		inf.setCodigo(1 + (long) (Math.random() * 10));
		inf.setData((Date) new java.util.Date());
		inf.setValor(1 + (long) (Math.random() * 100));
		inf.setUnidade("%");

		return inf;
	}

	public SuperiorBancoDao getSbdao() {
		return this.sbdao;
	}

}