/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.genes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.genes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model ;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnContaArchi"
    private Button btnContaArchi; // Value injected by FXMLLoader

    @FXML // fx:id="btnRicerca"
    private Button btnRicerca; // Value injected by FXMLLoader

    @FXML // fx:id="txtSoglia"
    private TextField txtSoglia; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doContaArchi(ActionEvent event) {
    	
    	try {
    		
    		Double soglia = Double.parseDouble(txtSoglia.getText());
    		if(soglia < this.model.getMin() || soglia > this.model.getMax()) {
    			txtResult.appendText("Inserire un numero compreso fra " + this.model.getMin() + " e " + this.model.getMax() + "\n");
    			return;
    		}
    		this.model.contaArchi(soglia);
    		txtResult.appendText("\nSoglia " + soglia + " --> Maggiori " + this.model.getMaggiori() + ", minori " + this.model.getMinori());
    		
    	}catch (NumberFormatException e) {
    		txtResult.appendText("\nInserire un numero valido prima di procedere\n");
    	}

    }

    @FXML
    void doRicerca(ActionEvent event) {
    	
    	try {
    		
    		Double soglia = Double.parseDouble(txtSoglia.getText());
    		
    		if(soglia < this.model.getMin() || soglia > this.model.getMax()) {
    			txtResult.appendText("Inserire un numero compreso fra " + this.model.getMin() + " e " + this.model.getMax() + "\n");
    			return;
    			}
    		
    		List<Integer> cammino = this.model.ricercaCammino(soglia);
    		txtResult.appendText("\nTrovato cammino con " + cammino.size() + " cromosomi di lunghezza " + this.model.getPesoMax() + "\n");
    		for(Integer i : cammino) {
    			txtResult.appendText(i + " ");
    		}
    		
    		txtSoglia.clear();
    		
    	}catch (NumberFormatException e) {
    		txtResult.appendText("\nInserire un numero valido prima di procedere\n");
    	}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnContaArchi != null : "fx:id=\"btnContaArchi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnRicerca != null : "fx:id=\"btnRicerca\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtSoglia != null : "fx:id=\"txtSoglia\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model ;
		this.model.creaGrafo();
		txtResult.setText("Grafo creato!\nNumero vertici: " + model.getNumeroVertici());
		txtResult.appendText("\nNumero archi: " + model.getNumeroArchi());
		txtResult.appendText("\nPeso minimo: " + model.getMin() + "\nPeso massimo: " + model.getMax());
		
	}
}
