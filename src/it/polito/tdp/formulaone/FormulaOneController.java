package it.polito.tdp.formulaone;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.formulaone.model.Model;
import it.polito.tdp.formulaone.model.Race;
import it.polito.tdp.formulaone.model.Simulatore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FormulaOneController {

	Model model = new Model();
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="btnSelezionaStagione"
    private Button btnSelezionaStagione; // Value injected by FXMLLoader

    @FXML // fx:id="boxGara"
    private ComboBox<Race> boxGara; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimulaGara"
    private Button btnSimulaGara; // Value injected by FXMLLoader

    @FXML // fx:id="textInputK"
    private TextField textInputK; // Value injected by FXMLLoader

    @FXML // fx:id="textInputK1"
    private TextField textInputK1; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doSelezionaStagione(ActionEvent event) {
    	//txtResult.setText("btn Seleziona stagione premuto");
    	int s = boxAnno.getValue();
    	model.creaGrafo(model.getGare(s));
    	ObservableList<Race> races = FXCollections.observableList(model.getGare(s));
    	boxGara.setItems(races);
    	boxGara.setValue(races.get(0));
    	txtResult.setText("l'arco di peso maggiore e' " + model.arcoPesoMax());
    }

    @FXML
    void doSimulaGara(ActionEvent event) {
    	txtResult.setText("btn simula gara premuto");
    	
    	Simulatore s = new Simulatore();
    	try {
    		s.init(Double.parseDouble(textInputK.getText()), Integer.parseInt(textInputK1.getText()), model.maxGiri(boxGara.getValue().getRaceId()), boxGara.getValue().getRaceId());
    		s.run();
    		txtResult.setText(s.getClassifica());
    	}
    	catch(NumberFormatException n) {
    		textInputK.clear();
    		textInputK1.clear();
    		txtResult.setText("Inserisci dei numeri!!!");
    	}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert btnSelezionaStagione != null : "fx:id=\"btnSelezionaStagione\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert boxGara != null : "fx:id=\"boxGara\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert btnSimulaGara != null : "fx:id=\"btnSimulaGara\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK != null : "fx:id=\"textInputK\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK1 != null : "fx:id=\"textInputK1\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'FormulaOne.fxml'.";
    }

	public void setModel(Model model) {
		this.model = model;
		ObservableList<Integer> stagione = FXCollections.observableList(model.getStagioni());
		boxAnno.setItems(stagione);
		boxAnno.setValue(stagione.get(0));
		
	}
}
