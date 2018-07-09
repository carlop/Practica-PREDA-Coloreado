/**
 * @author Francisco Carlos López Porcel
 * @version 0.1
 */
public class ColoreadoGrafos {

    // Colores elementales para utilizar en el coloreado
    private String[] colores = {
            "Amarillo",
            "Verde",
            "Rojo",
            "Azul",
    };

    // Traza
    private Boolean traza;
    // Contenido de la traza
    private String contenidoTraza;

    /**
     * Constructor
     */
    public ColoreadoGrafos(Boolean traza){
        this.traza = traza;
        this.contenidoTraza = "";
    }

    /**
     * Realiza el coloreado del grafo
     * @param grafo Matriz de adyacencia que representa al grafo
     * @param m     Número máximo de colores a utilizar
     * @param k     Nivel del grafo a explorar
     * @param v     Vector con las soluciones al problema
     * @param exito Establece si ha habido exito en la exploración anterior
     */
    public Boolean coloreaGrafo(int[][] grafo, int m, int k, int[] v, Boolean exito) {
        generaTraza("\nExplorando nivel " + k);
        v[k] = 0;
        exito = false;
        while (v[k] < m && !exito) {
            v[k] = v[k] + 1;
            generaTraza(imprimeVector(v));
            if (completable(grafo, v, k)){
                if (k == v.length-1) {
                    exito = true;
                    generaTraza(" => Solución encontrada");
                } else {
                    generaTraza(" => Solución completable: se explora en el siguiente nivel");
                    exito = coloreaGrafo(grafo, m, k + 1, v, exito);
                }
            } else {
                generaTraza(" => Solución NO completable");
            }
        }

        return exito;
    }



    /**
     *  Comprueba si un color es válido
     *  @param grafo Matriz de adyacencia que representa al grafo
     *  @param v     Vector con la solución al problema
     *  @param k     Nivel
     */
    private boolean completable(int[][] grafo, int[] v, int k) {
        boolean completable = true;
        for (int i = 0; i <= k-1; i++) {
            if (grafo[k][i] == 1 && v[k] == v[i]) {
                completable = false;
            }
        }
        return completable;
    }

    /**
     * Imprime la solución al problema
     * @param v Vector con la solución al problema
     * @return Imprime la solución al problema
     */
    public String imprimeSolucion(int[] v) {
        String solucion = "";
        if (traza) solucion = contenidoTraza;
        solucion += "\nLa solución al problema es:";
        for (int i = 0; i < v.length; i++) {
            int j = i+1;
            solucion += "\n" + j + " " + colores[v[i]-1];
        }
        return solucion;
    }

    /**
     * Imprime que no hay solución al problema
     * @return Imprime que no hay solución al problema
     */
    public String imprimeNoSolucion() {
        String solucion = "";
        if (traza) solucion = contenidoTraza;
        solucion += "\nNo existe solución con los datos introducidos.\n";
        return solucion;
    }

    /**
     * Imprime la cabecera de la traza
     * @param m Número de colores a utilizar en el problema
     * @param n Número de nodos del grafo del problema
     * @return Cabecera de la traza
     */
    public String imprimeCabeceraTraza(int m, int n) {
        String solucion = "";
        int t = n - 1;
        if (traza) {
            solucion += "Datos del problema:";
            solucion += "\n  - Número de colores máximos a utilizar: " + m;
            solucion += "\n  - Número de nodos del grafo: " + n + "[0.." + t + "]";
        }
        return solucion;
    }
    /**
     * Imprime el contenido de la traza
     * @param texto Texto a imprimir con la traza
     */
    private void generaTraza(String texto) {
        if (traza) {
            this.contenidoTraza += texto;
        }
    }

    /**
     * Imprime el vector solución
     * @param v vector con las soluciones parciales al problema
     * @return vector con las soluciones parciales en un string para imprimir
     */
    private String imprimeVector(int[] v) {
        String solucion = "\n  - v = { ";
        for (int i : v) {
            solucion += i + " ";
        }
        solucion += "}";
        return solucion;
    }
}
