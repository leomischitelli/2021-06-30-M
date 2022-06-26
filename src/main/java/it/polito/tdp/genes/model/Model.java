package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	
	private Graph<Integer, DefaultWeightedEdge> grafo;
	private GenesDao dao;
	private List<Interactions> interazioni;
	private double min;
	private double max;
	private int maggiori;
	private int minori;
	private List<Integer> camminoMigliore;
	private double pesoMax;
	
	public Model() {
		this.dao = new GenesDao();
	}
	
	public void creaGrafo() {
		
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		if(this.interazioni == null)
			this.interazioni = new ArrayList<>(this.dao.getInteractionsChromosome());
		for(Interactions i : this.interazioni) {
			Graphs.addEdgeWithVertices(this.grafo, i.getChromosome1(), i.getChromosome2(), i.getExpressionCorr());
		}
		
		calcolaMinMax();
		
	}
	
	private void calcolaMinMax() {
		this.min = 0.0; //conosco il dataset, so che min < 0
		this.max = 1.0;
		
		for(DefaultWeightedEdge e : this.grafo.edgeSet()) {
			double peso = this.grafo.getEdgeWeight(e);
			if(peso < this.min) {
				min = peso;
			}
			else if(peso > this.max) {
				max = peso;
			}
		}
		
	}
	
	public void contaArchi(double soglia) {
		this.maggiori = 0;
		this.minori = 0;
		
		for(DefaultWeightedEdge e : this.grafo.edgeSet()) {
			double peso = this.grafo.getEdgeWeight(e);
			if(peso > soglia) {
				this.maggiori++;
			} else if(peso < soglia) {
				this.minori++;
			}
		}
	}
	
	public List<Integer> ricercaCammino(double soglia){
		
		this.camminoMigliore = new ArrayList<>();
		this.pesoMax = 0.0;
		
		for(Integer c : this.grafo.vertexSet()) {
			List<Integer> camminoAttuale = new ArrayList<>();
			camminoAttuale.add(c);
			cerca(camminoAttuale, soglia, 0.0, c);
		}
		
		return this.camminoMigliore;
		
	}

	private void cerca(List<Integer> camminoAttuale, double soglia, double somma, Integer ultimoChromo) {
		
		//ricerco negli outgoingedges
		Set<DefaultWeightedEdge> setUltimo = new HashSet<>(this.grafo.outgoingEdgesOf(ultimoChromo));
		
		for(DefaultWeightedEdge e : setUltimo) {
			Integer chromo = this.grafo.getEdgeTarget(e);
			if(this.grafo.getEdgeWeight(e) > soglia && !camminoAttuale.contains(chromo)) {
				camminoAttuale.add(chromo);
				cerca(camminoAttuale, soglia, somma + this.grafo.getEdgeWeight(e), chromo);
				camminoAttuale.remove(camminoAttuale.size() - 1);
			}
		}
		
		//fuori dal for si e' conclusa la ricerca, controllo
		
		if(somma > this.pesoMax) {
			this.pesoMax = somma;
			this.camminoMigliore = new ArrayList<>(camminoAttuale);
		}
		
		
	}
	
	

	public double getPesoMax() {
		return pesoMax;
	}

	public int getNumeroVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNumeroArchi() {
		return this.grafo.edgeSet().size();
	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}

	public int getMaggiori() {
		return maggiori;
	}

	public int getMinori() {
		return minori;
	}
	
	
	
	

}