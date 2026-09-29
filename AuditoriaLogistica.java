public class AuditoriaLogistica {
    static final int X = 2;

    public static void main(String[] args) {
        fase1();
        fase2();
        fase3();
    }

    static void fase1() {
        System.out.println("===== FASE 1: UNIDIMENSIONAL =====");
        int[] temperaturas = {12, -3, 4, 8, -1, X, 15, 2};

        
        double promedio = promedioPositivas(temperaturas);
        System.out.printf("Promedio de temperaturas positivas: %.2f%n", promedio);

        // Tarea 1.2
        /*
         * RESPUESTA 1.2:
         * ¿Por qué un arreglo unidimensional no puede cambiar de tamaño?
         * Cuando se crea con "new", la JVM reserva en el heap un bloque de
         * memoria contiguo cuyo tamaño queda fijado (el atributo length es
         * final). La memoria contigua a ese bloque puede estar ocupada por
         * otros objetos, por lo que no se puede "estirar". Para "agrandarlo"
         * hay que crear un arreglo nuevo más grande y copiar los elementos
         * (por ejemplo con Arrays.copyOf) o usar una estructura dinámica como
         * ArrayList, que hace justamente eso internamente.
         *
         * ¿Qué ocurre al acceder a temperaturas[8]?
         * El arreglo tiene 8 posiciones (índices 0 a 7). En cada acceso la JVM
         * verifica que el índice esté dentro de [0, length-1]. Como 8 está
         * fuera de rango, NO lee memoria ajena (a diferencia de C/C++) y lanza
         * una ArrayIndexOutOfBoundsException ("Index 8 out of bounds for
         * length 8") en tiempo de ejecución, lo que protege la integridad de
         * la memoria.
         */
        System.out.println();
    }

    static double promedioPositivas(int[] temps) {
        int suma = 0;
        int contador = 0;

        System.out.print("Índices con temperatura bajo cero: ");
        boolean hayNegativas = false;
        for (int i = 0; i < temps.length; i++) {
            if (temps[i] > 0) {
                suma += temps[i];
                contador++;
            } else if (temps[i] < 0) {
                System.out.print(i + " ");
                hayNegativas = true;
            }
        }
        if (!hayNegativas) {
            System.out.print("(ninguno)");
        }
        System.out.println();

        
        return contador == 0 ? 0 : (double) suma / contador;
    }

    static void fase2() {
        System.out.println("===== FASE 2: BIDIMENSIONAL =====");
        int[][] inventario = {
            {10, 20,     15,  5},
            { 8, X + 5,  12, 30},
            {25, 14,      0, 18},
            { 2,  9,     11, 40}
        };


        System.out.println("Matriz de inventario:");
        for (int i = 0; i < inventario.length; i++) {
            for (int j = 0; j < inventario[i].length; j++) {
                System.out.printf("%4d", inventario[i][j]);
            }
            System.out.println();
        }


        System.out.println("\nTotal de stock por sucursal:");
        for (int i = 0; i < inventario.length; i++) {
            int totalFila = 0;
            for (int j = 0; j < inventario[i].length; j++) {
                totalFila += inventario[i][j];
            }
            System.out.println("Sucursal " + (i + 1) + ": " + totalFila);
        }

        // Tarea 2.1 (2): diagonal principal (i == j)
        System.out.print("\nDiagonal principal: ");
        for (int i = 0; i < inventario.length; i++) {
            System.out.print(inventario[i][i] + " ");
        }
        System.out.println("\n");

        // Tarea 2.2 (Pregunta conceptual)
        /*
         * RESPUESTA 2.2:
         * En Java no existen "matrices reales": un arreglo bidimensional es un
         * arreglo de referencias a otros arreglos unidimensionales. El arreglo
         * externo guarda referencias y cada fila es un objeto independiente en
         * el heap.
         *
         * - Arreglo bidimensional regular (rectangular): todas las filas
         *   tienen la misma longitud, p. ej. new int[4][4]. Se declara con
         *   ambas dimensiones y la JVM crea las 4 filas de 4 elementos.
         *
         * - Arreglo dentado (jagged array): las filas pueden tener longitudes
         *   distintas. Se crea indicando solo la primera dimensión,
         *   new int[4][], y luego se asigna a cada fila su propio tamaño:
         *   fila[0] = new int[2]; fila[1] = new int[5]; etc. Ahorra memoria
         *   cuando los datos no son uniformes (no se reservan celdas vacías),
         *   pero hay que tener cuidado de no asumir que todas las filas miden
         *   lo mismo (usar fila.length en cada recorrido).
         *
         * Ninguno de los dos garantiza que las filas estén contiguas en
         * memoria entre sí; solo cada fila individual es contigua.
         */
    }

    // =====================================================================
    // FASE 3: TRIDIMENSIONAL
    // =====================================================================
    static void fase3() {
        System.out.println("===== FASE 3: TRIDIMENSIONAL =====");
        int[][][] naves = new int[2][3][3]; 

        for (int e = 0; e < naves.length; e++) {
            for (int p = 0; p < naves[e].length; p++) {
                for (int a = 0; a < naves[e][p].length; a++) {
                    naves[e][p][a] = e + p + a + X;
                }
            }
        }
        System.out.println("Coordenadas con valor par:");
        for (int e = 0; e < naves.length; e++) {
            for (int p = 0; p < naves[e].length; p++) {
                for (int a = 0; a < naves[e][p].length; a++) {
                    if (naves[e][p][a] % 2 == 0) {
                        System.out.println("[" + e + "][" + p + "][" + a + "] = "
                                + naves[e][p][a]);
                    }
                }
            }
        }

        // Tarea 3.3 (Análisis crítico)
        /*
         * RESPUESTA 3.3:
         * Con 100 edificios, 50 pisos y 50 pasillos habría 100*50*50 =
         * 250,000 celdas. Problemas:
         *
         * Rendimiento / memoria:
         * - Se crean 1 + 100 + 5,000 objetos arreglo (un arreglo de
         *   referencias por nivel), cada uno con su cabecera, lo que añade
         *   sobrecarga.
         * - El tamaño es fijo: si un edificio necesita más pisos o pasillos
         *   hay que crear un arreglo nuevo y copiar todo. Además todos los
         *   edificios tienen las mismas dimensiones aunque no las necesiten,
         *   así que se desperdicia memoria.
         * - Recorrerlo requiere tres bucles anidados (O(n^3) sobre las
         *   dimensiones) y el acceso a las filas no es del todo contiguo, lo
         *   que perjudica la localidad de caché.
         *
         * Legibilidad / mantenimiento:
         * - naves[e][p][a] no dice qué representa cada índice ni el dato
         *   almacenado; solo son números. Es fácil confundir el orden de los
         *   índices y cometer errores.
         * - No se puede agregar información adicional (nombre, capacidad,
         *   tipo de contenedor, responsable) sin más arreglos paralelos.
         * - No hay encapsulación ni métodos asociados a los datos.
         *
         * Con POO se modelaría con clases Edificio, Piso y Pasillo (o
         * Contenedor), y listas como List<Edificio> / ArrayList. Esto permite
         * tamaños dinámicos, cada objeto con sus atributos y métodos
         * (por ejemplo edificio.getOcupacionTotal()), código autodocumentado,
         * más fácil de extender, probar y mantener.
         */
    }
}
