package it.polito.tdp.genes.model;

public class Interactions {
	
	private int chromosome1;
	private int chromosome2;
	private Double expressionCorr ;
	
	
	public Interactions(int chromosome1, int chromosome2, Double expressionCorr) {
		super();
		this.chromosome1 = chromosome1;
		this.chromosome2 = chromosome2;
		this.expressionCorr = expressionCorr;
	}
	
	
	public int getChromosome1() {
		return chromosome1;
	}


	public int getChromosome2() {
		return chromosome2;
	}


	public Double getExpressionCorr() {
		return expressionCorr;
	}
	public void setExpressionCorr(Double expressionCorr) {
		this.expressionCorr = expressionCorr;
	}
	
	

}
