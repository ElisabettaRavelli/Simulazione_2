package it.polito.tdp.food.model;

public class Ingrediente implements Comparable<Ingrediente>{
	
	private Integer id;
	private Double calorie;
	private Integer cibi;
	public Ingrediente(Integer id, Double calorie, Integer cibi) {
		super();
		this.id = id;
		this.calorie = calorie;
		this.cibi = cibi;
	}
	public Integer getId() {
		return id;
	}
	public Double getCalorie() {
		return calorie;
	}
	public Integer getCibi() {
		return cibi;
	}
	@Override
	public int compareTo(Ingrediente o) {
		return - calorie.compareTo(o.calorie);
	}
	
	
	

}
