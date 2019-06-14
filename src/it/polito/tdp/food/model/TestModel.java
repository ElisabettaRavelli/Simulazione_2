package it.polito.tdp.food.model;

import java.util.List;

public class TestModel {
	public static void main(String[] args) {
		 Model m = new Model();
		 m.creaGrafo(1000.00);
		 List<Ingrediente> lista = m.getIngredienti();
		 for(Ingrediente tmp: lista) {
			 System.out.println("Nome: "+ tmp.getId()+" Calorie: "+tmp.getCalorie()+ " NumCibi: "+ tmp.getCibi());
		 }

	}
}
