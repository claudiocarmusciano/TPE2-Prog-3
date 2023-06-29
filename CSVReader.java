// package ProgramacionIII.tpe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader {

	private String path;
	// private int cantTramos;
	public ArrayList<String[]> lines;
	private GrafoNoDirigido<Integer> grafoEstaciones;

	public CSVReader(String path) {
		this.path = path;
		// this.cantTramos = 0;
		this.lines = new ArrayList<>();
		this.grafoEstaciones = new GrafoNoDirigido<>();

	}

	public void read() {

		// Obtengo una lista con las lineas del archivo
		// lines.get(0) tiene la primer linea del archivo
		// lines.get(1) tiene la segunda linea del archivo... y así
		this.lines = this.readContent();
		// HashMap<Integer, Boolean> verticesVisitados;

		// Grafo<Integer> grafo = new GrafoNoDirigido<>();

		for (String[] line : this.lines) {
			// Cada linea es un arreglo de Strings, donde cada posicion guarda un elemento
			Integer origen = Integer.parseInt(line[0].trim().substring(1));
			Integer destino = Integer.parseInt(line[1].trim().substring(1));
			Integer distancia = Integer.parseInt(line[2].trim());

			grafoEstaciones.agregarVertice(origen);
			grafoEstaciones.agregarVertice(destino);
			grafoEstaciones.agregarArco(origen, destino, distancia);
			// this.cantTramos++;

			// System.out.println("origen: " + origen + ", destino: " + destino + ",
			// distancia: " + distancia);
		}

		// ((GrafoDirigido<Integer>) grafo).imprimirGrafo();
		// ((GrafoNoDirigido<Integer>) grafo).imprimirGrafoNoDirigido();

	}

	private ArrayList<String[]> readContent() {
		ArrayList<String[]> lines = new ArrayList<String[]>();

		File file = new File(this.path);
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				line = line.trim();
				lines.add(line.split(";"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (bufferedReader != null)
				try {
					bufferedReader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		}

		return lines;
	}

	public ArrayList<String[]> getLines() {
		return lines;
	}

	// método para instanciar grafo de Estaciones desde el Main.java
	public GrafoNoDirigido<Integer> instanciarGrafoEstaciones() {
		return grafoEstaciones;
	}

}
