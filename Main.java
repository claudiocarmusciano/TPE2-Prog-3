//package ProgramacionIII.tpe;
// -*- coding: utf-8 -*-

public class Main {

	public static void main(String[] args) {
		String path = "./DataSets/dataset2.txt";
		CSVReader reader = new CSVReader(path);
		reader.read();

		// Instanciamos un nuevo grafo usando el método que está en el CSVReader.java:
		GrafoNoDirigido<Integer> grafoEstaciones = reader.instanciarGrafoEstaciones();
		// Imprimimos la cantidad de vértices (Estaciones) del grafo:
		System.out.println();
		System.out.println("Cantidad de estaciones del grafo:" +  grafoEstaciones.cantidadVertices());
		System.out.println();
		// Llamamos la función y método de Bactracking para buscar una solución:
		BusquedaSolucion busqueda = new BusquedaSolucion(grafoEstaciones);
		
		busqueda.back("Backtracking");
		busqueda.gridi("Greedy");

	}

}
