import kotlin.math.roundToInt
import java.io.File
import java.io.Reader
import java.io.InputStream
import java.io.BufferedReader
fun main(A: Array<String>){
    var P = obtenerDatosTSP(A[0])
    var i = 0
    File(A[1]).forEachLine {line -> i++}
    var B = Array(i){""}
    i = 0
    File(A[1]).forEachLine {line -> B[i++] = line}
    datos(B)
}
fun datos(A: Array<String>) {
    if (A[0].substring(0,5) == "NAME "){
        println("EO")
    }
}
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

/* Función: repeticion
 * Descripción: Recibe de entrada un arreglo de enteros(solución del ciclo) y un número que indicara cuantas ciudades hay en el ciclo.
 * En caso que se consiga un elemento repetido este debera indicar que ciudad se repite y el programa terminara.
 * En caso contrario el programa seguira sin ningun inconveniente.
 * Precondición: A.size > 0 && indicador > 0
 * Postcondición: true 
*/
fun repeticion(A: Array<Int>, indicador: Int) {
    var C = Array(indicador+1){0}
    for (i in 0 until indicador) {
        C[A[i]] = C[A[i]] + 1
    }
    
    for (i in 0 until C.size) {
        if (C[i] > 1) {
            println("Se esta repitiendo la ciudad ${i}")
            return
        }
    }
    println("No hay repeticiones")
}

/* Función: incompleto
 * Descripción: Recibe de entrada un arreglo de enteros(solución del TSP) y un número que indicara cuantas ciudades hay en el ciclo.
 * En caso que se consiga un elemento faltante este debera indicar que ciudad falta y el programa terminara.
 * En caso contrario el programa seguira sin ningun inconveniente.
 * Precondición: A.size > 0 && indicador > 0
 * Postcondición: \result.size == A.size + 1 && (\forall int i; 0 <= i < \result.size; (\exists int j: 0 <= j < A.size; \result[i] == A[j] )) 
*/
fun incompleto(A: Array<Int>, indicador: Int){
    var C = Array(indicador+1){0}
    for (i in 0 until indicador) {
        C[A[i]] = C[A[i]] + 1
    }
    
    for (i in 1 until C.size) {
        if (C[i] == 0) {
            println("Falta la ciudad ${i}")
            return
        }
    }
    println("No esta incompleto")
}

/* Función: ultimoElemento
 * Descripción: Recibe de entrada un arreglo de enteros(solución del TSP) y verifica si el ultimo elemento del arreglo sea -1.
 * Precondición: A.size > 0 
 * Postcondición: true 
*/
fun ultimoElemento(A: Array<Int>) {
    if (A[A.size-1] != -1) {
        println("Falto indicar si termino el tour")
        return 
    }
    println("El ultimo elemento es -1")
}

/* Función: primerElemento
 * Descripción: Recibe de entrada un arreglo de enteros(solución del TSP) y verifica su primer elemento empiece en 1.
 * Precondición: A.size > 0
 * Postcondición: true
*/
fun primerElemento(A: Array<Int>) {
    if (A[0] != 1) {
        println("La solución no inicia en la primera ciudad")
        return
    }
    println("Se cumple el primer elemento")
}

/* Función: distancia
 * Descripción: Recibe dos arreglos con datos tipos Double, el primer arreglo son las coordenadas X de las ciudades
 * y el segundo arreglo son las coordenadas Y.El tercer arreglo que recibe es la solución del tour.
 * Precondición: hilera1.size > 0 && hilera2.size > 0 && solucion.size > 0 && hilera1.size == hilera2.size 
 * Postcondición: \result == (\sum int i; 0 <= i < (solucion.size-1); Math.sqrt((hilera1[i] - hilera1[i+1])*(hilera1[i] - hilera1[i+1])+(hilera2[i] - hilera2[i+1])*(hilera2[i] - hilera2[i+1])).roundToInt()) 
*/
fun distancia(hilera1: Array<Double>, hilera2: Array<Double>, solucion: Array<Int>): Int {
    var distancia = 0
    for (i in 0 until solucion.size-1) {
        var p = solucion[i]
        var q = solucion[i+1]
        var xd: Double = hilera1[p] - hilera1[q]
        var yd: Double = hilera2[p] - hilera2[q] 
        var dxy = Math.sqrt(xd*xd+yd*yd).roundToInt()
        distancia = distancia + dxy
    }
    return distancia
}