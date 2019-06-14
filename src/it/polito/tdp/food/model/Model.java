package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.Condiment;
import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private FoodDao dao;
	private Graph<Condiment, DefaultWeightedEdge> grafo;
	private List<Condiment> ingredienti;
	private List<Condiment> soluzioneRicorsiva;
	private Double calorieMax = 0.0;
	
	public Model() {
		this.dao = new FoodDao();
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.ingredienti = new ArrayList<>();
	}
	
	public void creaGrafo(Double calorie) {
		ingredienti = dao.listIngredienti(calorie);
		Graphs.addAllVertices(this.grafo, ingredienti);
		
		
		for(Condiment c1: this.grafo.vertexSet()) {
			for(Condiment c2: this.grafo.vertexSet()) {
				if(!c1.equals(c2) && this.grafo.getEdge(c1, c2)==null) {
					Double peso = this.dao.listAdiacenze(c1.getFood_code(),c2.getFood_code());
					if(peso>0) {
						Graphs.addEdgeWithVertices(this.grafo, c1, c2, peso);
					}
				}
			}
		}
		
		System.out.println("Grafo creato");
		System.out.println("vertici:  "+this.grafo.vertexSet().size()+ " archi: "+this.grafo.edgeSet().size());
		
		
	}
	
	public Integer getVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public Integer getArchi() {
		return this.grafo.edgeSet().size();
	}
	
	
	public List<Ingrediente> getIngredienti(){
		List<Ingrediente> result = new ArrayList<>();
		
		for(Condiment c: this.grafo.vertexSet()) {
			Integer nCibo= 0;
			List<Condiment> vicini = Graphs.neighborListOf(this.grafo, c);
			for(Condiment tmp: vicini) {
			nCibo= nCibo + (int)this.grafo.getEdgeWeight(this.grafo.getEdge(c, tmp));
			}
			Ingrediente i = new Ingrediente(c.getCondiment_id(),c.getCondiment_calories(),nCibo);
			result.add(i);
		}
		Collections.sort(result);
		return result;
	}
	
	public List<Condiment> ingredientiSelezionabili(Double calorie){
		List<Condiment> list = new ArrayList<>();
		list = this.dao.listIngredienti(calorie);
		Collections.sort(list);
		return list;
	}
	
	/*
	 * RICORSIONE
	 * Soluzione parziale: lista di ingredienti (Condiment)
	 * Livello ricorsione: numero calorie del Condiment
	 * Casi terminali: non c'è un ingrediente con un numero massimo si calorie 
	 */
	
	public List<Condiment> creaDieta(Condiment condiment){
		List<Condiment> parziale = new ArrayList<>();
		this.soluzioneRicorsiva = new ArrayList<>();
		
		//valori iniziali di partenza (livello 0)
		calorieMax = condiment.getCondiment_calories();
		parziale.add(condiment);
		
		cerca(condiment, parziale);
		
		return soluzioneRicorsiva;
	}
	
	public void cerca(Condiment condiment, List<Condiment> parziale) {
		//algoritmo ricorsivo
		for(Condiment c: grafo.vertexSet()) {
			if(!parziale.contains(c)&& nonContieneArco(parziale, c)) {
				parziale.add(c);
				cerca(c,parziale);
				parziale.remove(c);
			}
		}
		//condizione finale
		if(calorie(parziale) > calorieMax) {
			calorieMax = calorie(parziale);
			soluzioneRicorsiva = new ArrayList<>(parziale);
		}
	}
	
	//metodo che mi calcola la somma di tutte le calorie dei vertici contenuti in parziale
	private double calorie(List<Condiment> parziale) {
		Double somma = 0.0;
		for(Condiment c : parziale) {
			somma = somma + c.getCondiment_calories();
		}
				
		return somma;
	}

	//metodo per dire se ci sono degli archi tra i vertici in parziale e i vertice che sto verificando (c)
	private boolean nonContieneArco(List<Condiment> parziale, Condiment c) {
		for(Condiment tmp : parziale) {
			if(this.grafo.containsEdge(tmp, c) || this.grafo.containsEdge(c, tmp))
				return false;
		}
		return true;
	}
}
