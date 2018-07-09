import java.io.IOException;
import java.util.List;

/**
 * @author Francisco Carlos López Porcel
 * @version 0.1
 */
public class Coloreado {

    /**
     * Método main
     * @throws java.io.IOException
     */
    public static void main (String[] args) throws IOException {

        // Lee los argumentos de entrada
        ProcesadorArgumentos argumentos = new ProcesadorArgumentos(args);
        // Procesa los argumentos
        argumentos.procesarArgumentos();
        // Archivo de entrada
        String archivoEntrada = argumentos.getArchivoEntrada();
        // Archivo de salida
        String archivoSalida = argumentos.getArchivoSalida();

        // Número de colores a utilizar en el problema
        int numeroColores;
        // Número de nodos del grafo a colorear
        int nodosGrafo;
        // Matriz de adyacencia que representa el grafo a colorear
        int[][] grafo;
        // Vector con la solución al problema
        int[] vectorSolucion;

        // Muestra la ayuda
        if (argumentos.getAyuda()) {
            argumentos.mostrarAyuda();
        }

        // Si hay archivos de entrada se opera
        if (archivoEntrada != null) {
            // Lee el archivo de entrada
            List<String> contenidoArchivo = ManejadorArchivos.leerArchivo(archivoEntrada);

            // Leemos los colores a utilizar
            numeroColores = Integer.parseInt(contenidoArchivo.get(0));
            // Leemos el número de nodos del grafo
            nodosGrafo = Integer.parseInt(contenidoArchivo.get(1));
            // Iniciamos el grafo
            grafo = new int[nodosGrafo][nodosGrafo];
            // Iniciamos el vector solución
            vectorSolucion = new int[nodosGrafo];

            // Lee el contenido del archivo de entrada
            int i = 2;
            while (i < contenidoArchivo.size()) {
                String data = contenidoArchivo.get(i);
                String datos[] = data.split("\\s");
                for (int k = 0; k < datos.length; k++) {
                    grafo[i-2][k] = Integer.parseInt(datos[k]);
                }
                i++;
            }

            // Contenido de la solución
            String contenidoSolucion = "";

            // Inicia el algoritmo
            ColoreadoGrafos coloreadoGrafos = new ColoreadoGrafos(argumentos.getTraza());

            // Si el número de colores es mayor de 4 no es problema de coloreado de mapas
            if (numeroColores <= 4 && numeroColores > 1) {
                for (int j = 2; j <= numeroColores; j++) {
                    // Añade la cabecera de la traza
                    if (argumentos.getTraza()) {
                        contenidoSolucion += coloreadoGrafos.imprimeCabeceraTraza(j, nodosGrafo);
                    }
                    if (coloreadoGrafos.coloreaGrafo(grafo,j,0,vectorSolucion,false)) {
                        if (argumentos.getTraza()) {
                            contenidoSolucion += coloreadoGrafos.imprimeSolucion(vectorSolucion);
                        } else {
                            contenidoSolucion = coloreadoGrafos.imprimeSolucion(vectorSolucion);
                        }

                        break;
                    } else {
                        if (argumentos.getTraza()) {
                            contenidoSolucion += coloreadoGrafos.imprimeNoSolucion();
                        } else {
                            contenidoSolucion = coloreadoGrafos.imprimeNoSolucion();
                        }
                        // Reseteamos el vectorSolucion para evitar que coja las pruebas anterioress
                        vectorSolucion = new int[nodosGrafo];
                    }
                }
            } else if (numeroColores <= 1) {
                // El número de colores no puede ser menor de 2 si no no se puede colorear
                contenidoSolucion += "El número máximo de colores no puede ser menor de 2";
            }
            else {
                // El número máximo no puede ser mayor de 4
                contenidoSolucion += "El número máximo de colores no puede ser mayor de 4";
            }

            // Si hay hay archivo de salida guarda del resultado en él si no lo muestra en pantalla
            if (archivoSalida != null) {
                ManejadorArchivos.guardarArchivo(archivoSalida, contenidoSolucion);
            } else {
                System.out.print(contenidoSolucion);
            }
        }
    }

}