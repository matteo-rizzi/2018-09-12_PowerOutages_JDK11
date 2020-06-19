package it.polito.tdp.poweroutages.model;

public class Vicino implements Comparable<Vicino>{

	private Nerc vicino;
	private Integer peso;

	public Vicino(Nerc vicino, Integer peso){
		super();
		this.vicino = vicino;
		this.peso = peso;
	}
	
	

	public Nerc getVicino() {
		return vicino;
	}



	public void setVicino(Nerc vicino) {
		this.vicino = vicino;
	}



	public Integer getPeso() {
		return peso;
	}



	public void setPeso(Integer peso) {
		this.peso = peso;
	}



	@Override
	public String toString() {
		return vicino + " (" + peso + ")";
	}

	@Override
	public int compareTo(Vicino other) {
		return other.peso.compareTo(this.peso);
	}

}
