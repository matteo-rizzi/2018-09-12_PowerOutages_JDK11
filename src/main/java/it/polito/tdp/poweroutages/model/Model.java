package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.poweroutages.db.PowerOutagesDAO;

public class Model {
	
	private PowerOutagesDAO dao;
	private Graph<Nerc, DefaultWeightedEdge> grafo;
	private Map<Integer, Nerc> idMap;
	
	public Model() {
		this.dao = new PowerOutagesDAO();
		this.idMap = new HashMap<>();
		this.dao.loadAllNercs(this.idMap);
	}

	public List<Nerc> loadAllNercs() {
		List<Nerc> nercs = new ArrayList<>(this.idMap.values());
		return nercs;
	}
	
	public void creaGrafo() {
		this.grafo = new SimpleWeightedGraph<Nerc, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		// aggiungo i vertici
		Graphs.addAllVertices(this.grafo, this.loadAllNercs());
		
		// aggiungo gli archi
		for(Adiacenza a : this.dao.getAdiacenze(idMap)) {
			Graphs.addEdge(this.grafo, a.getPrimo(), a.getSecondo(), a.getPeso());
		}
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Vicino> getVicini(Nerc selezionato) {
		List<Vicino> vicini = new ArrayList<Vicino>();
		for(Nerc vicino : Graphs.neighborListOf(this.grafo, selezionato)) {
			Vicino prossimo = new Vicino(vicino, (int) this.grafo.getEdgeWeight(this.grafo.getEdge(selezionato, vicino)));
			vicini.add(prossimo);
		}
		Collections.sort(vicini);
		return vicini;
	}
}
