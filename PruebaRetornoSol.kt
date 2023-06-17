// Librerías

import kotlin.math.roundToInt
import java.io.File
import java.io.Reader
import java.io.InputStream
import java.io.BufferedReader

fun main(args: Array<String>) {
    println("La instancia a resolver es:" +args[0])
	var P = obtenerDatosTSP(args[0])
    //var tourSolucion = divideAndConquerAndLocalSearchTSP(P)
    var tourAleatorio = obtenerTourAleatorio(P) // ESTO SE BORRA DESPUÉS, ES PARA PROBAR EL PROGRAMA
    generarArchivoSolucionTSPLIB(args[1], tourAleatorio) // AQUÍ LUEGO SE DEBE PONER COMO PARÁMETRO EL TOUR SOLUCIÓN
}

// Procedimiento para generar el archivo de salida con la solución del problema TSP, en formato TSPLIB

/* Función: generarArchivoSolucionTSPLIB
 * Descripción: Recibe el nombre del archivo de salida y el tour solución, el cual viene
 * dado por el algoritmo divideAndConquerAndLocalSearchTSP. Este procedimiento genera el archivo de salida con
 * la solución del problema TSP, en formato TSPLIB
 * Precondición: nombreSalida != null && tourSolucion.size >= 2
 * Postondición: true
 */ 
fun generarArchivoSolucionTSPLIB(nombreSalida: String, tourSolucion: Array<Triple<Double,Double,Int>>) {
    var distancia = obtenerDistanciaTour(tourSolucion)
    var tourTSPLIB = obtenerTourTSPLIB(tourSolucion)
    var dimensionTSP = tourTSPLIB.size
    var archivoSalida = File(args[1])
    var textoSalida = "NAME: "+args[1]
    archivoSalida.writeText(textoSalida)
    archivoSalida.appendText("\nCOMMENT: Length "+distancia.toString())
    archivoSalida.appendText("\nTYPE: TOUR")
    archivoSalida.appendText("\nDIMENSION: ${dimensionTSP}")
    archivoSalida.appendText("\nTOUR_SECTION")
    for (i in 0 until tourTSPLIB.size) { 
        archivoSalida.appendText("\n"+tourTSPLIB[i])
    }
    archivoSalida.appendText("\n-1")
    archivoSalida.appendText("\nEOF")
}

// Función para convertir el tour solución al formato TSPLIB

/* Función: obtenerTourTSPLIB
 * Descripción: Recibe un tour: Array<Triple<Double,Double,Int>> y retorna un tour de strings que contiene 
 * sólo los números de las ciudades, es decir, está en formato TSPLIB
 * Precondición: tour.size >= 2
 * Postondición: \result.size >= 1
 */ 
fun obtenerTourTSPLIB(tour: Array<Triple<Double,Double,Int>>): Array<String> {
	var tourTSPLIB = Array(tour.size-1, {""})
	for(i in 0 until tour.size-1) {
		tourTSPLIB[i] = (tour[i].third).toString()
	}
	return tourTSPLIB
}

// Las siguientes funciones ya están en el DCLSTSP.kt de Main

/* Función: obtenerDatosTSP
 * Descripción: Esta función recibe y procesa los datos del archivo de entrada en formato TSPLIB y retorna un arreglo 
 * de coordenadas Array<Triple<Double,Double,Int>> que contiene en \result[i] los datos de la ciudad número i en el siguiente
 * orden: coordenada x, coordenada y, número de la ciudad
 * Precondición: A: != null
 * Postondición: \result.size >= 1
 */ 
fun obtenerDatosTSP(A: String): Array<Triple<Double,Double,Int>> {
    var i = 0
    var j = 0
    var m: Int
    var centinela1: Int
    var centinela2: Int
    // Se cuenta la cantidad de líneas
    File(A).forEachLine {i++}
    var numeroDeLineas = i
    // Se crea arreglo con tamaño igual al número de líneas
    var B = Array(numeroDeLineas){""}
    // Rellenamos cada elemento de B con las líneas del texto
    i = 0
    File(A).forEachLine {line -> B[i++] = line}
    // Se cuenta la cantidad de líneas que contienen coordenadas
    var lineasDeCoordeadas = numeroDeLineas - 7
    var finalB = numeroDeLineas - 1
    if (B[numeroDeLineas-1] != "EOF") {
      lineasDeCoordeadas = lineasDeCoordeadas - 1
      finalB = finalB - 1
    }
    // Se crea un arreglo con tamaño igual a la cantidad de líneas que contienen coordenadas 
    var C = Array(lineasDeCoordeadas){Triple(0.0, 0.0, 0)}
    // Revisamos cada elemento de B
    for (k in 6 until finalB) {
        centinela1 = 0
        centinela2 = 0
        m = 0
        // Verificamos cada caracter del string que se encuentra dentro del arreglo B
        for (p in 1 until B[k].length) {
            // Caso en donde haya espacios al inicio
            if (B[k][0] == ' ') {
                if (B[k][p] != ' ' && centinela1 == 0 && B[k][p-1] == ' ' && m != 0) {
                    centinela1 = p-1
                } else if (B[k][p] != ' ' && centinela1 != 0 && B[k][p-1] == ' ' && m != 0) {
                    centinela2 = p-1
                } else if (B[k][p] != ' ' &&  B[k][p-1] == ' ') {
                    m++
                } 
            } else {
                // Caso en donde no hay espacio al principio
                if (B[k][p] != ' ' && centinela1 == 0 && B[k][p-1] == ' ' && m == 0) {
                    centinela1 = p-1
                    m++
                } else if (B[k][p] != ' ' && centinela1 != 0 && B[k][p-1] == ' ' && m != 0) {
                    centinela2 = p-1
                } 
            }         
        }
        /* Se extrae la primera división y la segunda división del string usando substring, y se crea un Triple con esas coordenadas
         * el número de la ciudad. Esto se guarda en el arreglo C
         */
        C[j] = Triple((B[k].substring(centinela1, centinela2)).toDouble(), (B[k].substring(centinela2, B[k].length)).toDouble(), j+1)
        j++
    }
    return C
}

/* Función: distanciaDosPuntos
 * Descripción: Recibe dos coordenadas de puntos y retorna la distancia euclidiana entre dichos puntos, redondeada a un entero
 * Las coordenadas x,y de los puntos corresponden a las dos primeras coordendas de un triple
 * Precondición: true
 * Postondición: \result = (sqrt((Punto2.first - Punto1.first)*(Punto2.first - Punto1.first) + (Punto2.second - Punto1.second)*(Punto2.second - Punto1.second))).roundToInt()
 */ 
fun distanciaDosPuntos(Punto1: Triple<Double,Double,Int>, Punto2: Triple<Double,Double,Int>): Int {
    var xd = Punto2.first - Punto1.first
    var yd = Punto2.second - Punto1.second
    var d = Math.sqrt(xd*xd + yd*yd)
    return d.roundToInt()
}

/* Función: obtenerDistanciaTour
 * Descripción: Recibe un tour: Array<Pair<Double,Double>>, y retorna la distancia del tour
 * Precondición: (\forall int i; 0 <= i && i < tour.size; 1 <= tour[i] && tour[i] <= P.size)
 * Postondición: \result == \sum(int i; 0 <= i < tour.size - 1; (sqrt((tour[i+1].first - tour[i].first)*(tour[i+1].first - tour[i].first) + (tour[i].second - tour[i].second)*(tour[i].second - tour[i].second))).roundToInt())
 */ 
fun obtenerDistanciaTour(tour: Array<Triple<Double,Double,Int>>): Int {
    var n = tour.size
    var distancia = 0
    for (i in 0 until n-1) {
        distancia = distancia + distanciaDosPuntos(tour[i], tour[i+1])
    }
    return distancia
}

// EXTRA PARA PROBAR LA ESCRITURA

// Para generar tours aleatorios
fun obtenerTourAleatorio(A: Array<Triple<Double, Double,Int>>): Array<Triple<Double, Double,Int>> {
    var B = Array(A.size){i -> i}
    var C: Array<Triple<Double, Double,Int>> = Array(A.size+1){Triple(0.0, 0.0, 0)}
    for (i in 0 until A.size) {
        var x = (0 until A.size).random()
        while(B[x] == -1) {
            x = (0 until A.size).random()
        }
        B[x] = -1
        C[i] = A[x]
    }
    C[A.size] = C[0]
    return C
}