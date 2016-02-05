package br.com.oca.controller;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import br.com.oca.model.Calculos;
import br.com.oca.model.Resposta;
import br.com.oca.model.Tentativas;
import br.com.oca.model.conteudo.Conteudo;
import br.com.oca.model.enums.Certificacao;
import br.com.oca.model.enums.Idioma;
import br.com.oca.model.enums.TipoTeste;
import br.com.oca.model.i18n.janelas.JanelasSource;
import br.com.oca.view.HomeController;
import br.com.oca.view.NovoTesteController;
import br.com.oca.view.PropriedadesController;
import br.com.oca.view.QuizController;
import br.com.oca.view.ResultadoController;
import br.com.oca.view.RootLayoutController;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

public class MainApp extends Application {
	private ObservableList<Tentativas> listaTentativas;
	private Stage homeStage;
	private BorderPane rootLayout;
	private JanelasSource label;
	private Idioma idioma;

	public MainApp() {
	}

	@Override
	public void start(Stage _homeStage) {

		homeStage = _homeStage;
		homeStage.setTitle("CertficTests");
		idioma = Idioma.Portugues;
		label = JanelasSource.getInstance(idioma);
		
		atualizaTabelaTentativas();
		initRootLayout();

		showHome();
	}
	
	private FXMLLoader getNovoLoader() {

		FXMLLoader loader = new FXMLLoader();
		loader.setResources(label.getBundle());

		return loader;
	}
	
	private void abrirNovaJanela() {
		
		Window window = new Stage();
		PauseTransition pause = new PauseTransition(Duration.seconds(30));
		pause.setOnFinished(e -> window.hide());
		pause.play();
	}
	
	private Stage getNovoStage(FXMLLoader loader, AnchorPane page, String tituloJanela) {

		Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setResizable(false);
		stage.setTitle(tituloJanela);
		stage.setScene(new Scene(page));
		stage.show();

		return stage;
	}

	private AnchorPane getNovoAnchorPane(FXMLLoader loader, String caminhoFXML) {
		try {
			return (AnchorPane) loader.load(this.getClass().getResource(caminhoFXML).openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void initRootLayout() {
		try {
			FXMLLoader loader = getNovoLoader();
			rootLayout = (BorderPane) loader.load(this.getClass().getResource("../view/RootLayout.fxml").openStream());

			Scene scene = new Scene(rootLayout);
			homeStage.setScene(scene);
			homeStage.setResizable(false);
			
			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);
			controller.setDialogHome(homeStage);

			homeStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showPropriedades() {
		
		abrirNovaJanela();
		FXMLLoader loader = getNovoLoader();
		AnchorPane page = getNovoAnchorPane(loader, "../view/Propriedades.fxml");

		Stage propriedadesStage = getNovoStage(loader, page, label.getString("propriedadesTitulo"));

		PropriedadesController propriedadesController = loader.getController();
		propriedadesController.setDialogStage(propriedadesStage);

	}

	public void showHome() {
		
		FXMLLoader loader = getNovoLoader();
		AnchorPane personOverview = getNovoAnchorPane(loader, "../view/Home.fxml");

		rootLayout.setCenter(personOverview);

		HomeController homeController = loader.getController();
		homeController.setHomeStage(homeStage);
		homeController.setIdioma(idioma);
		homeController.setMainApp(this);
		homeController.setListaTentativas(listaTentativas);
		homeController.setLoader(loader);
	}

	public void showNovoTesteDialog() {
		
		abrirNovaJanela();
		FXMLLoader loader = getNovoLoader();
		AnchorPane page = getNovoAnchorPane(loader, "../view/NovoTeste.fxml");

		Stage novoTesteStage = getNovoStage(loader, page, "Novo Teste");

		NovoTesteController novoTesteController = loader.getController();
		novoTesteController.setIdioma(idioma);
		novoTesteController.setDialogStage(novoTesteStage);
		novoTesteController.setDialogHome(homeStage);
		novoTesteController.setMainApp(this);
	}

	public void showQuiz(Conteudo conteudo) {

		abrirNovaJanela();
		FXMLLoader loader = getNovoLoader();
		AnchorPane page = getNovoAnchorPane(loader, "../view/Quiz.fxml");
		Stage quizStage = getNovoStage(loader, page, "Quiz - CertificTests");

		QuizController quizController = loader.getController();
		quizController.setDialogStage(quizStage);
		quizController.setDialogHome(homeStage);
		quizController.setIdioma(idioma);
		quizController.setConteudo(conteudo);
		quizController.setMainApp(this);
		quizController.iniciarQuiz();
	}

	public void showResultadoController(ArrayList<Resposta> listaRespostas, Conteudo conteudo) {

		abrirNovaJanela();
		FXMLLoader loader = getNovoLoader();
		AnchorPane page = getNovoAnchorPane(loader, "../view/Resultado.fxml");
		Stage resultadoStage = getNovoStage(loader, page, "Resultados - CertificTests");
		
		Calculos calculos = new Calculos(conteudo, listaRespostas);
		ResultadoController resultadoController = loader.getController();
		resultadoController.setDialogStage(resultadoStage);
		resultadoController.setListaRespostas(listaRespostas);
		resultadoController.setConteudo(conteudo);
		resultadoController.setCalculos(calculos);
		resultadoController.setDialogHome(homeStage);
		resultadoController.setIdioma(idioma);
		resultadoController.setMainApp(this);
		resultadoController.calcularResultados();

	}
	
	public void atualizaTabelaTentativas() {
		listaTentativas = listaTentativas = FXCollections.observableArrayList();
		
		try {
			FileInputStream fileInputStream = new FileInputStream(Tentativas.filename);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			while (true) {
				try {
					Tentativas tentativas = (Tentativas) objectInputStream.readObject();
					listaTentativas.add(tentativas);
				} catch (EOFException e) {
					break;
				} catch (FileNotFoundException e) {
					break;
				}
			}
			objectInputStream.close();
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo ainda n�o Criado.");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Stage getPrimaryStage() {
		return homeStage;
	}

	public ObservableList<Tentativas> getListaTentativas() {
		return listaTentativas;
	}

	public static void main(String[] args) {
		launch(args);
	}

	public Idioma getIdioma() {
		return idioma;
	}

	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
	}

}
