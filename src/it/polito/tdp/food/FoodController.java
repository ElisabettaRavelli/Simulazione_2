/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.db.Condiment;
import it.polito.tdp.food.model.Ingrediente;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="boxIngrediente"
    private ComboBox<Condiment> boxIngrediente; // Value injected by FXMLLoader

    @FXML // fx:id="btnDietaEquilibrata"
    private Button btnDietaEquilibrata; // Value injected by FXMLLoader
    
    @FXML
    private TextArea txtResult;

    @FXML
    void doCalcolaDieta(ActionEvent event) {
    	txtResult.clear();
    	Condiment condiment = boxIngrediente.getValue();
    	List<Condiment> dieta = new ArrayList<>();
    	dieta = model.creaDieta(condiment);
    	txtResult.appendText("Insieme di ingredienti per una dieta equilibrata\n");
    	for(Condiment c: dieta) {
    		txtResult.appendText(c.toString()+ "\n");
    	}

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	try {
    		Double calorie = Double.parseDouble(txtCalorie.getText()); 
        	model.creaGrafo(calorie);
    		txtResult.appendText("Grafo creato!\n");
    		txtResult.appendText("Vertici: " + model.getVertici()+ " Archi: "+ model.getArchi()+ "\n");
    		
    		List<Ingrediente> ingredienti = new ArrayList<>();
    		ingredienti = model.getIngredienti();
    		for(Ingrediente tmp: ingredienti) {
    			txtResult.appendText("Nome: "+ tmp.getId()+" Calorie: "+tmp.getCalorie()+ " NumCibi: "+ tmp.getCibi()+ "\n");
    		}
    		List<Condiment> ingredientiSelezionabili = new ArrayList<>();
    		ingredientiSelezionabili = this.model.ingredientiSelezionabili(calorie);
    		boxIngrediente.getItems().clear();
    		boxIngrediente.getItems().addAll(ingredientiSelezionabili);
    		
    	} catch(NumberFormatException e) {
    		txtResult.appendText("Inserire un numero reale\n");
    		return;
    	}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxIngrediente != null : "fx:id=\"boxIngrediente\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnDietaEquilibrata != null : "fx:id=\"btnDietaEquilibrata\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
