import kotlin.math.roundToInt
import java.io.File
import java.io.Reader
import java.io.InputStream
import java.io.BufferedReader

fun main(A: Array<String>){
    var P = obtenerDatosTSP(A[0])
}

// Función para obtener los datos a partir del archivo_entrada con formato TSPLIB

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
    println(C.contentToString()) // BORRAR ESTO DESPUÉS, ES PARA VERIFICAR QUE EL ARREGLO ESTÉ BIEN
    return C
}