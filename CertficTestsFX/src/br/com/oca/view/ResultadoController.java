package br.com.oca.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import br.com.oca.model.Calculos;
import br.com.oca.model.Questao;
import br.com.oca.model.Resposta;
import br.com.oca.model.Tentativas;
import br.com.oca.model.conteudo.Conteudo;
import br.com.oca.model.enums.Idioma;
import br.com.oca.util.AppendingObjectOutputStream;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class ResultadoController {
	@FXML
	private Label suaNota;
	@FXML
	private Label questoesCorretas;
	@FXML
	private ScrollPane scrollEnunciado;
	@FXML
	private Label resultados;
	
	private Stage dialogHome;
	private Stage dialogStage;
	
	private ArrayList<Resposta> listaRespostas;
	private Idioma idioma;
	private Conteudo conteudo;
	private String stringResultados;
	private Integer numeroQuestao;
	private Calculos calculos;
	private Tentativas tentativa;
	
	public ResultadoController() {
		stringResultados = "";
		numeroQuestao = 1;
	}

	@FXML
	private void initialize() {
		
	}
	
	public void calcularResultados() {
		showResultados();
		registrarTentativa();
	}
	
	private void showResultados() {
		suaNota.setText(calculos.getNota() + " de 100.00");
		questoesCorretas.setText(calculos.getNumeroQuestoesCorretas() + " de " + conteudo.getTotalQuestoes());
		
		for (int cont = 0; cont < conteudo.getTotalQuestoes(); cont++) 
			setTextoRespostas(cont);
		
		resultados.setWrapText(true);
		resultados.setText(stringResultados);
	}
	
	private void setTextoRespostas(int count) {
		
		stringResultados += numeroQuestao + " - " + conteudo.getQuestao(count).getEnunciado() + "\n\n";
		
		switch (listaRespostas.get(count).getTipoQuestao()) {
			case UNICA:
				stringResultados += "Resposta Correta: " + conteudo.getQuestao(count).getEnunciadoAlternativaCorreta() + "\n";
				if (calculos.isRespostaCorreta(count)) 
					stringResultados += "Sua Resposta: " + listaRespostas.get(count).getResposta() + "\n\n";
				else 
					stringResultados += "Sua Resposta: " + listaRespostas.get(count).getResposta() + "\n\n";
				break;
			case MULTIPLA:
				exibirMultiplasRespostas(conteudo.getQuestao(count), listaRespostas.get(count));
		}
		
		numeroQuestao++;
	}
	
	private void exibirMultiplasRespostas(Questao questao, Resposta resposta) {
		
		stringResultados += "Respostas Corretas: \n\n";
		for (int cont = 0; cont < questao.getAlternativasCorretas().size(); cont++) {
			stringResultados += questao.getAlternativasCorretas().get(cont) + "\n";
		}
		
		stringResultados += "\nSuas Respostas: \n\n";
		for (int cont = 0; cont < resposta.getRespostas().size(); cont++) {
			if (questao.getAlternativasCorretas().contains(resposta.getRespostas().get(cont))) {
				stringResultados += resposta.getRespostas().get(cont) + "\n";
			} else {
				stringResultados += resposta.getRespostas().get(cont) + "\n";
			}
		}
		
		stringResultados += "\n";
	}
	
	private void registrarTentativa() {
		
		File file = new File (Tentativas.filename);
        ObjectOutputStream out = null;

        try {
            if (!file.exists ()) 
            	out = new ObjectOutputStream (new FileOutputStream(Tentativas.filename));
            else 
            	out = new AppendingObjectOutputStream (new FileOutputStream(Tentativas.filename, true));
            out.writeObject(new Tentativas(conteudo.getNomeTeste(), conteudo.getTipoTeste(), calculos.getNota(), calculos.getNumeroQuestoesCorretas()));
            out.flush();
            out.close();
        } catch (Exception e){
            e.printStackTrace ();
        } 
	}
	
	@FXML
	private void handleOk() {
		dialogHome.show();
		dialogStage.close();
	}
	
	public void setListaRespostas(ArrayList<Resposta> listaRespostas) {
		this.listaRespostas = listaRespostas;
	}
	
	public ArrayList<Resposta> getListaRespostas() {
		return listaRespostas;
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setDialogHome(Stage dialogHome) {
		this.dialogHome = dialogHome;
	}
	
	public void setConteudo(Conteudo conteudo) {
		this.conteudo = conteudo;
	}
	
	public void setCalculos(Calculos calculos) {
		this.calculos = calculos;
	}
	
	public void setTentativa(Tentativas tentativa) {
		this.tentativa = tentativa;
	}
	
	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
	}
}