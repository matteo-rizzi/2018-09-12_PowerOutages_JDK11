package it.polito.tdp.poweroutages.model;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Model m = new Model();
		
		m.creaGrafo();
		
		m.simula(200);
		System.out.println(m.getCatastrofi());
		System.out.println(m.getBonus());
	}

}
