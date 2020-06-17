package it.polito.tdp.poweroutages.model;

public class Adiacenza {

	private Nerc primo;
	private Nerc secondo;
	private Integer peso;

	public Adiacenza(Nerc primo, Nerc secondo, Integer peso) {
		super();
		this.primo = primo;
		this.secondo = secondo;
		this.peso = peso;
	}

	public Nerc getPrimo() {
		return primo;
	}

	public void setPrimo(Nerc primo) {
		this.primo = primo;
	}

	public Nerc getSecondo() {
		return secondo;
	}

	public void setSecondo(Nerc secondo) {
		this.secondo = secondo;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

}
