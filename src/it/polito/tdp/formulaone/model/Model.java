package it.polito.tdp.formulaone.model;

import java.time.Year;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {
	private Graph<Race, MyEdge> grafo;
	private FormulaOneDAO dao;
	public Model() {
		this.dao = new FormulaOneDAO();
		this.grafo = new SimpleWeightedGraph<Race, MyEdge>(MyEdge.class);
	}
	
	public List<Integer> getStagioni(){
		List<Integer> years = new LinkedList<Integer>();
		for(Season s : dao.getAllSeasons()) {
			years.add(s.getYear());
		}
		return years;
	}
	
	public List<Race> getGare(int y){
		List<Race> allGare = this.dao.getGare(Year.of(y));
		
		return allGare;
	}
	
	public void creaGrafo(List<Race> allGare)
	{
		this.grafo = new SimpleWeightedGraph<Race,MyEdge>(MyEdge.class);
		
		for(Race r: allGare) {
			this.grafo.addVertex(r);
		}
		
		for(Race a: grafo.vertexSet()) {
			 for(Race s: grafo.vertexSet()) {
					if(!a.equals(s)) {
						MyEdge edge = 	grafo.addEdge(a, s );
						if(edge!=null) {
							edge.setWeight(this.getWeight(a.getRaceId(), s.getRaceId()));
						}
					}
				}
		}
		System.out.println(grafo.toString());
       
	}
	
	public int getWeight(int raceId1, int raceId2) {
		int peso = 0;
		List<Integer> listaPiloti = dao.getPilotidiquellaGara(raceId1);
		for(Integer i: listaPiloti) {
			if(this.dao.getPilotaGare(i.intValue(), raceId1, raceId2)) {
				peso = peso+1;
			}
		}
		return peso;
	}
	
	
	//cerco l'arco di peso massimo voglio stampare una stringa con i vertici e il loro peso vicino;
	
	public String arcoPesoMax() {
		String ret="";
		double maxPeso = 0.0;
		
		 for(MyEdge i: this.grafo.edgeSet()){
			 System.out.println("Archi : " + i.getWeight());
			if(i.getWeight()>maxPeso) {
				maxPeso = i.getWeight();
			}
		}
		 for(MyEdge e: this.grafo.edgeSet()) {
			 if(e.getWeight()== maxPeso) {
				 ret += e.getWeight()+ " " + e.getSource() + " " + e.getTarget() + "\n";
			 }
		 }
		
		return ret;
	}
	
	public int maxGiri(int raceId) {
		return this.dao.totGiri(raceId);
	}
	
	public List<Pilota> getPiloti (int raceId){
		return this.dao.getPilotiGara(raceId);
	}
	
	
	
}
