import kotlin.math.roundToInt
fun main(A: Array<String>){
    var n = (A.size-1)/4
    var m = n*3 
    var posicion = Array(n){0} 
    var hilera1 = Array(n){0.0}
    var hilera2 = Array(n){0.0}
    var solucion = Array(n+1){0}
    var j = 0
    for (i in 0 until m step 3) {
        posicion[j] = A[i].toInt()
        j++
    }

    j = 0
    var k = 0
    for (i in 1 until m step 3) {
        hilera1[j] = A[i].toDouble()
        hilera2[k] = A[i+1].toDouble()
        j++
        k++
    }

    j = 0
    for (i in m until A.size) {
        solucion[j] = A[i].toInt()
        j++
    }
    repeticion(solucion, n)
    incompleto(solucion, n)
    ultimoElemento(solucion)
    primerElemento(solucion)
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