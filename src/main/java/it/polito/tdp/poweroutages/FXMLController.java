package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.Vicino;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextArea txtResult;

	@FXML
	private Button btnCreaGrafo;

	@FXML
	private ComboBox<Nerc> cmbBoxNerc;

	@FXML
	private Button btnVisualizzaVicini;

	@FXML
	private TextField txtK;

	@FXML
	private Button btnSimula;

	@FXML
	void doCreaGrafo(ActionEvent event) {
		this.txtResult.clear();

		this.model.creaGrafo();
		this.txtResult.appendText("Grafo creato!\n");
		this.txtResult.appendText("# VERTICI: " + this.model.nVertici() + "\n");
		this.txtResult.appendText("# ARCHI: " + this.model.nArchi() + "\n");

	}

	@FXML
	void doSimula(ActionEvent event) {
		this.txtResult.clear();
		try {
			Integer K;
			try {
				K = Integer.parseInt(this.txtK.getText());
			} catch (NumberFormatException e) {
				this.txtResult.appendText("Errore! Devi inserire un valore numerico per K!");
				return;
			}
			
			this.model.simula(K);
			this.txtResult.appendText(this.model.getBonus() + "\n");
			this.txtResult.appendText("Numero di catastrofi: " + this.model.getCatastrofi());
		} catch (RuntimeException e) {
			this.txtResult.appendText("Errore! Per poter iniziare la simulazione devi prima creare il grafo!");
			return;
		}

	}

	@FXML
	void doVisualizzaVicini(ActionEvent event) {
		this.txtResult.clear();
		try {
			Nerc selezionato = this.cmbBoxNerc.getValue();
			if (selezionato == null) {
				this.txtResult.appendText("Errore! Devi selezionare un nerc!");
				return;
			}
			List<Vicino> vicini = this.model.getVicini(selezionato);
			if (vicini.size() == 0) {
				this.txtResult.appendText("Non sono presenti vicini del nerc " + selezionato + "!\n");
				return;
			}
			this.txtResult.appendText("Elenco vicini al nerc " + selezionato + ":\n");
			for (Vicino vicino : vicini) {
				this.txtResult.appendText(vicino + "\n");
			}
		} catch (RuntimeException e) {
			this.txtResult.appendText(
					"Errore! Per poter visualizzare i vicini del nerc selezionato devi prima creare il grafo!");
			return;
		}
	}

	@FXML
	void initialize() {
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'PowerOutages.fxml'.";
		assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'PowerOutages.fxml'.";
		assert cmbBoxNerc != null : "fx:id=\"cmbBoxNerc\" was not injected: check your FXML file 'PowerOutages.fxml'.";
		assert btnVisualizzaVicini != null : "fx:id=\"btnVisualizzaVicini\" was not injected: check your FXML file 'PowerOutages.fxml'.";
		assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'PowerOutages.fxml'.";
		assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'PowerOutages.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;
		this.cmbBoxNerc.getItems().addAll(this.model.loadAllNercs());
	}
}
