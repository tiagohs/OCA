package br.com.oca.model;

import java.io.Serializable;

import br.com.oca.model.enums.Certificacao;
import br.com.oca.model.enums.TipoTeste;

public class Tentativa implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String filename = "tentativas.bin";
	
	private Certificacao exame;
	private TipoTeste tipoTeste;
	private String testeEscolhido;
	private Double nota;
	private Double numeroAcertos;
	private String tempoRegistrado;
	private String dataRegistrada;
	private String listaRespostas;
	
	public Tentativa(Certificacao _exame, TipoTeste _testeEscolhido, Double _nota, Double _numeroAcertos, String _tempoRegistrado, String _listaRespostas, String _dataRegistrada) {
		exame = _exame;
		tipoTeste = _testeEscolhido;
		testeEscolhido = exame.getNomeExtenso() + " - " + tipoTeste .getNome();
		nota = _nota;
		numeroAcertos = _numeroAcertos;
		tempoRegistrado = _tempoRegistrado;
		listaRespostas = _listaRespostas;
		dataRegistrada = _dataRegistrada;
	}
	
	public Certificacao getExame() {
		return exame;
	}
	
	public void setExame(Certificacao exame) {
		this.exame = exame;
	}

	public Double getNota() {
		return nota;
	}
	
	public void setNota(Double nota) {
		this.nota = nota;
	}
	
	public Double getNumeroAcertos() {
		return numeroAcertos;
	}
	
	public void setNumeroAcertos(Double numeroAcertos) {
		this.numeroAcertos = numeroAcertos;
	}

	public TipoTeste getTipoTeste() {
		return tipoTeste;
	}

	public void setTipoTeste(TipoTeste tipoTeste) {
		this.tipoTeste = tipoTeste;
	}

	public String getTesteEscolhido() {
		return testeEscolhido;
	}

	public void setTesteEscolhido(String testeEscolhido) {
		this.testeEscolhido = testeEscolhido;
	}
	
	public String getTempoRegistrado() {
		return tempoRegistrado;
	}
	
	public void setTempoRegistrado(String tempoRegistrado) {
		this.tempoRegistrado = tempoRegistrado;
	}
	
	public String getListaRespostas() {
		return listaRespostas;
	}
	
	public void setListaRespostas(String listaRespostas) {
		this.listaRespostas = listaRespostas;
	}
	
	public String getDataRegistrada() {
		return dataRegistrada;
	}
	
	public void setDataRegistrada(String dataRegistrada) {
		this.dataRegistrada = dataRegistrada;
	}

}