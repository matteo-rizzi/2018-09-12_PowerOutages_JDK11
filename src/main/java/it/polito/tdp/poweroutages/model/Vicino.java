package it.polito.tdp.poweroutages.model;

public class Vicino implements Comparable<Vicino>{

	private Nerc vicino;
	private Integer peso;

	public Vicino(Nerc vicino, Integer peso){
		super();
		this.vicino = vicino;
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
