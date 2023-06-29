import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class BusquedaSolucion {
    private int metrica;
    private int mejorKm;
    private int metricaG;
    private int kmG;
    private GrafoNoDirigido<Integer> grafoEstaciones;
    private LinkedList<Arco<Integer>> solucionB;
    private HashMap<Arco<Integer>, Boolean> arcosVisitados; 
     

    public BusquedaSolucion(GrafoNoDirigido<Integer> grafoEstaciones) {
        this.grafoEstaciones = grafoEstaciones;
        this.solucionB = new LinkedList<>();
        this.arcosVisitados = new HashMap<>();
    }

    public void back(String metodo) {
        mejorKm = Integer.MAX_VALUE;
        metrica = 0;
        Iterator<Arco<Integer>> arcos = grafoEstaciones.obtenerArcos();
        while (arcos.hasNext()) {
            Arco<Integer> arco = arcos.next();
            arcosVisitados.put(arco, false);
        }

        LinkedList<Arco<Integer>> lista = new LinkedList<>();
        backTracking(null, lista, 0, 1);
        
        imprimirResultado(metodo, mejorKm, metrica, solucionB);

    }

    private void backTracking(Arco<Integer> tramoActual, LinkedList<Arco<Integer>> listaSolucion, int km,int visitados) {
        metrica++;
        arcosVisitados.put(tramoActual, true);
        if (isFullyConnected(listaSolucion)) {
                solucionB = new LinkedList<>(listaSolucion);
                mejorKm = km;
        } else {
            Iterator<Arco<Integer>> arcos = grafoEstaciones.obtenerArcos();
            while (arcos.hasNext()) {
                Arco<Integer> arcoActual = arcos.next();
                Integer distanciaTramoActual = arcoActual.getEtiqueta();
               
                if (!arcosVisitados.get(arcoActual) && (km + distanciaTramoActual)<mejorKm) { 
                    listaSolucion.add(arcoActual);
                    backTracking(arcoActual, listaSolucion, km + distanciaTramoActual, visitados + 1);
                    listaSolucion.remove(arcoActual);
                }
            }
        }
        arcosVisitados.put(tramoActual, false);
    }

    public boolean isFullyConnected(LinkedList<Arco<Integer>> l) {
        UnionFind uf = new UnionFind(grafoEstaciones.cantidadVertices());

        for (Arco<Integer> arco:l) {
            int u = arco.getVerticeOrigen()-1;
            int v = arco.getVerticeDestino()-1;
            uf.unir(u, v);
        }

        int representative = uf.encontrar(0);
        for (int i = 1; i < grafoEstaciones.cantidadVertices(); i++) {
            if (uf.encontrar(i) != representative) {
                return false;
            }
        }
        return true;
    }

    public void gridi(String metodo) {
        metricaG = 0;
        kmG = 0;
        Iterator<Arco<Integer>> arcos = grafoEstaciones.obtenerArcos();
        LinkedList<Arco<Integer>> listaArcos = new LinkedList<>();
        while (arcos.hasNext()) {
            Arco<Integer> arco = arcos.next();
            listaArcos.add(arco);
        }
        
        LinkedList<Arco<Integer>> solucionG = new LinkedList<>();
        greedy(listaArcos, solucionG);
        
        imprimirResultado(metodo, kmG, metricaG, solucionG);

    }

    private void greedy(LinkedList<Arco<Integer>> arcos, LinkedList<Arco<Integer>> listaSolucion) {
        // Instanciamos la variable ufg (UnionFindGreedy) con la cantidad de arcos del grafo:
        UnionFind ufg = new UnionFind(arcos.size());
        // Ordenamos los arcos de menor a mayor kms 
        Collections.sort(arcos, new TramoComparator());
        
        // Ejecutamos un while mientras la lista de arcos tenga elementos y el 'numberOfSets' del 'ufg' != 1, 
        // es decir, mientras haya arcos por conectar:
        while (!arcos.isEmpty() && (ufg.numberOfSets != 1)) {
            // Guardamos el primer arco de la lista 'arcos' en la variable 'arcoActual', sumamos a la metrica y
            // quitamos el arcoActual de la lista 'arcos':
            Arco<Integer> arcoActual = arcos.get(0);
            metricaG++;
            arcos.remove(arcoActual);
            // Obtenemos los vertices origen y destino del arcoActual...
            Integer origen = arcoActual.getVerticeOrigen();
            Integer destino = arcoActual.getVerticeDestino();
            // ... y verificamos si esos vertices no pertencen al mismo conjunto, para agregarlos a la solucion y unirlos:
            if (ufg.encontrar(origen) != ufg.encontrar(destino)) {
                listaSolucion.add(arcoActual);
                kmG += arcoActual.getEtiqueta();
                ufg.unir(origen,destino);
            }

        }
    }
        

    static class TramoComparator implements Comparator<Arco<Integer>> {
        public int compare(Arco<Integer> tramo1, Arco<Integer> tramo2) {
            // Comparar por la distancia del tramo
            return (tramo1.getEtiqueta()).compareTo(tramo2.getEtiqueta());
        }
    }

    public class UnionFind {
        int[] parent;
        int[] rank;
        int numberOfSets;
    
        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            numberOfSets = n;
    
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }
    
        int encontrar(int x) {
            if (parent[x] != x) {
                parent[x] = encontrar(parent[x]);
            }
            return parent[x];
        }
    
        void unir(int x, int y) {
            int rootX = encontrar(x);
            int rootY = encontrar(y);
    
            if (rootX != rootY) {
                if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
    
                numberOfSets--; // Reducir el número de conjuntos
            }
        }
    }


    private void imprimirResultado(String metodo, int mejorKm, int metrica, LinkedList<Arco<Integer>> solucion) {
        System.out.println("Solucion por medio de: " + metodo);
        for (int i = 0; i < solucion.size(); i++) {
            if (i != 0) {
                System.out.print(", ");
            }
            System.out.print(
                    "E" + solucion.get(i).getVerticeOrigen() + "-E" + solucion.get(i).getVerticeDestino());
        }
        System.out.println();
        System.out.println(mejorKm + " Kms");
        System.out.println(metrica + " de Metrica");
    }
}
