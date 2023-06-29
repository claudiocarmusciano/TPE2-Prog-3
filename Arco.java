import java.util.Comparator;

/*
 * La clase Arco representa un Tramo del grafo. Contiene un vertice origen, un vertice destino y una distancia.
 * Nota: Para poder exponer los Tramos fuera del grafo y que nadie los modifique se hizo esta clase inmutable
 * (Inmutable: una vez creado el Tramo no es posible cambiarle los valores).
 */

public class Arco<T> {

    private int verticeOrigen;
    private int verticeDestino;
    private T etiqueta;

    public Arco(int verticeOrigen, int verticeDestino, T etiqueta) {
        this.verticeOrigen = verticeOrigen;
        this.verticeDestino = verticeDestino;
        this.etiqueta = etiqueta;
    }

    public int getVerticeOrigen() {
        return verticeOrigen;
    }

    public int getVerticeDestino() {
        return verticeDestino;
    }

    public T getEtiqueta() {
        return etiqueta;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + verticeOrigen;
        result = prime * result + verticeDestino;
        result = prime * result + ((etiqueta == null) ? 0 : etiqueta.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Arco<?> other = (Arco<?>) obj;
        if (verticeOrigen != other.verticeOrigen)
            return false;
        if (verticeDestino != other.verticeDestino)
            return false;
        if (etiqueta == null) {
            if (other.etiqueta != null)
                return false;
        } else if (!etiqueta.equals(other.etiqueta))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return ("Vertice origen: " + this.verticeOrigen + ",Vertice destino: " + this.verticeDestino + ", distancia: "
                + this.etiqueta);
    }

    public class TramoComparator implements Comparator<Arco<Integer>> {
        @Override
        public int compare(Arco<Integer> tramo1, Arco<Integer> tramo2) {
            // Comparar por la distancia del tramo
            return tramo1.etiqueta.compareTo(tramo2.etiqueta);
        }
    }
}
