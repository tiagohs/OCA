package br.com.oca.controllers;

import java.io.IOException;

import br.com.oca.model.Tentativa;
import br.com.oca.model.enums.Certificacao;
import br.com.oca.model.enums.TipoTeste;
import br.com.oca.model.i18n.janelas.JanelasSource;
import br.com.oca.view.HomeController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {
	private ObservableList<Tentativa> listaTentativas = listaTentativas = FXCollections.observableArrayList();
	private Stage stagePrimario;
    private BorderPane rootLayout;
    private JanelasSource label;
    
    public MainApp() {
    	listaTentativas.add(new Tentativa(Certificacao.OCA, TipoTeste.TESTE_1, 90.0, 2.0));
    	listaTentativas.add(new Tentativa(Certificacao.OCP, TipoTeste.TESTE_2, 80.0, 1.0));
    	listaTentativas.add(new Tentativa(Certificacao.OCA, TipoTeste.TESTE_1, 90.0, 2.0));
    	listaTentativas.add(new Tentativa(Certificacao.OCP, TipoTeste.TESTE_2, 80.0, 1.0));
    	listaTentativas.add(new Tentativa(Certificacao.OCA, TipoTeste.TESTE_1, 90.0, 2.0));
    	listaTentativas.add(new Tentativa(Certificacao.OCP, TipoTeste.TESTE_2, 80.0, 1.0));
    }
    
    @Override
    public void start(Stage _stagePrimario) {
    	
    	this.stagePrimario = _stagePrimario;
        this.stagePrimario.setTitle("CertficTests");

        initRootLayout();

        showHome();
    }

    /**
     * Inicializa o root layout (layout base).
     */
    public void initRootLayout() {
        try {
            // Carrega o root layout do arquivo fxml.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Mostra a scene (cena) contendo o root layout.
            Scene scene = new Scene(rootLayout);
            stagePrimario.setScene(scene);
            stagePrimario.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mostra o person overview dentro do root layout.
     */
    public void showHome() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/Home.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            rootLayout.setCenter(personOverview);
            
            HomeController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retorna o palco principal.
     * @return
     */
    public Stage getPrimaryStage() {
        return stagePrimario;
    }
    
    public ObservableList<Tentativa> getListaTentativas() {
		return listaTentativas;
	}
    
    public static void main(String[] args) {
        launch(args);
    }

}