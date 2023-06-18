import kotlin.math.roundToInt
import java.io.File
import java.io.Reader
import java.io.InputStream
import java.io.BufferedReader
fun main(A: Array<String>){
    // Obtencion de arreglo con las coordenadas
    var P = obtenerDatosTSP(A[0])

    // Conteo de elementos en el archivo solucion y creacion del arreglo contenedor de este
    var i = 0
    File(A[1]).forEachLine {line -> i++}
    var B = Array(i){""}
    i = 0
    File(A[1]).forEachLine {line -> B[i++] = line}
    //Creacion de arreglo que contiene el tour
    var C = datos(B, P.size)
    // Verificacion de casos

    // Al repetirse un elemento o estar incompleto el tour no es posible sacar su distancia
    if (repeticion(C.first, P.size) || incompleto(C.first, P.size) || primerElemento(C.first)) {
        println("Imposible obtener una distancia correcta para este")
        println("Verifique que todos los datos del tour estan correctos")
    } else {
        ultimoElemento(C.first) 
        var nuevoTour = convertirTour(P, C.first)
        var distanciaT = obtenerDistanciaTour(nuevoTour)
        println(distanciaT)
        if (distanciaT == C.second) {
            println("Distacia correcta")
        }else {
            println("Distacia incorrecta")
        }
    }
}
/* Función: datos
 * Descripción: Recibe de entrada un arreglo de String, este arreglo posee las lineas del archivo solucion 
 * y número que indicara el tamaño del arreglo de Int que se va crear. Este arreglo que se creara sera el tour que contiene el archivo solución.
 * El programa verificara si el formato del archivo tiene forma  de TSPLIB, indicara si falta algun dato y devolvera un par que tendra 
 * como primer elemento el tour y de segundo elemento la longitud del tour.
 * Precondición: A.size > 0 && x > 0
 * Postcondición:\result.first.size > 0 && \result.second >= 0
 */
fun datos(A: Array<String>, x: Int): Pair<Array<Int>, Int> {
    var B = Array(x+2){0} 
    var j = 0
    var longitud = 0
    var check = 0
    for (i in 0 until 5) {
        if(A[i].length == 1) {
            break
        } else if (A[i].length  < x.toString().length) {
            continue
        } else if (A[i].substring(0,5) == "NAME "){
            check++
        } else if(A[i].substring(0,8) == "COMMENT ") {
            check++
            if (A[i].length < 17) {
                longitud = A[i].substring(10,A[i].length).toInt()
            } else {
                longitud = A[i].substring(17,A[i].length).toInt()
            }
        } else if(A[i].substring(0,5) == "TYPE ") {
            check++
        } else if(A[i].substring(0,10) == "DIMENSION " ) {
            check++
        } else if(A[i].substring(0,A[i].length) == "TOUR_SECTION") {
            check++
        } else {
            continue
        }
    }
    if (check != 5) {
        println("Error en el formato de TSPLIB ")
        println("Verifique si falta algun dato de información ")
    }
    println(longitud)
    for (i in check until A.size) {
        if (A[i] == "EOF") {
            B[j] = -2
        } else {
            B[j] = A[i].toInt()
            j++
        } 
    }
    return Pair(B, longitud)
}
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
/* Función: repeticion
 * Descripción: Recibe de entrada un arreglo de enteros(solución del ciclo) y un número que indicara cuantas ciudades hay en el ciclo.
 * En caso que se consiga un elemento repetido este debera indicar que ciudad se repite, el programa terminara y debera regresar FALSE.
 * En caso contrario el programa seguira sin ningun inconveniente.
 * Precondición: A.size > 0 && indicador > 0
 * Postcondición: \result == true || \result == false
 */
fun repeticion(A: Array<Int> , indicador: Int): Boolean {
    var C = Array(indicador+1){0}
    for (i in 0 until indicador) {
        C[A[i]] = C[A[i]] + 1
    }
    
    for (i in 1 until C.size) {
        if (C[i] > 1) {
            println("Se esta repitiendo la ciudad ${i}")
            return false
        }
    }
    println("No hay repeticiones")
    return true
}
/* Función: incompleto
 * Descripción: Recibe de entrada un arreglo de enteros(solución del TSP) y un número que indicara cuantas ciudades hay en el ciclo.
 * En caso que se consiga un elemento faltante este debera indicar que ciudad falta, el programa terminara y debera regresar FALSE.
 * En caso contrario el programa seguira sin ningun inconveniente y devolvera TRUE.
 * Precondición: A.size > 0 && indicador > 0
 * Postcondición: \result == true || \result == false
 */
fun incompleto(A: Array<Int>, indicador: Int): Boolean{
    var C = Array(indicador+1){0}
    for (i in 0 until indicador) {
        C[A[i]] = C[A[i]] + 1
    }
    
    for (i in 1 until C.size) {
        if (C[i] == 0) {
            println("Falta la ciudad ${i}")
            return false
        }
    }
    println("No esta incompleto")
    return true
}
/* Función: ultimoElemento
 * Descripción: Recibe de entrada un arreglo de enteros(solución del TSP) y verifica si el ultimo elemento del arreglo sea -1.
 * Precondición: A.size > 0 
 * Postcondición: true 
 */
fun ultimoElemento(A: Array<Int>) {
    if (A[A.size-1] != -2 && A[A.size-2] != -2 ) {
        println("Falta indicar el EOF para cumplir los requisitos de un archivo en forma TSPLIB")
    }
    if (A[A.size-2] != -1) {
        println("Falta indicar como ultimo elemento el -1")
        return 
    }
    println("Cumple que termine en -1 el tour")
}
/* Función: primerElemento
 * Descripción: Recibe de entrada un arreglo de enteros(solución del TSP) y verifica su primer elemento empiece en 1.
 * Precondición: A.size > 0
 * Postcondición: true
 */
fun primerElemento(A: Array<Int>): Boolean {
    if (A[0] != 1) {
        println("La solución no inicia en la primera ciudad")
        return false
    }
    println("Se cumple el primer elemento")
    return true
}
/* Función: obtenerDistanciaTour
 * Descripción: Recibe un tour: tour: Array<Pair<Triple<Double,Double,Int>, Triple<Double,Double,Int>>> 
 * y retorna la distancia del tour
 * Precondición: tour.size >= 1
 * Postondición: \result == \sum int i; 0 <= i && i < tour.size; distanciaDosPuntos(tour[i].first, tour[i].second)
 */ 
fun obtenerDistanciaTour(tour: Array<Pair<Triple<Double,Double,Int>, Triple<Double,Double,Int>>>): Int {
	var distancia = 0
	for (i in 0 until tour.size) {
		distancia = distancia + distanciaDosPuntos(tour[i].first, tour[i].second)
	}
	return distancia
}
/* Función: distanciaDosPuntos
 * Descripción: Recibe dos coordenadas de puntos y retorna la distancia euclidiana entre dichos puntos, redondeada a un entero
 * Las coordenadas x,y de los puntos corresponden a las dos primeras coordendas de un triple
 * Precondición: true
 * Postondición: \result == (sqrt((Punto2.first - Punto1.first)*(Punto2.first - Punto1.first) + (Punto2.second - Punto1.second)*(Punto2.second - Punto1.second))).roundToInt()
 */ 
fun distanciaDosPuntos(Punto1: Triple<Double,Double,Int>, Punto2: Triple<Double,Double,Int>): Int {
	var xd = Punto2.first - Punto1.first
	var yd = Punto2.second - Punto1.second
	var d = Math.sqrt(xd*xd + yd*yd)
	return d.roundToInt()
}

/* Función: convertirTour
 * Descripción: Recibe un arreglo que contiene una tripleta con las coordenadas de una ciudad y su posicion (coordenadas)
 * y el segundo arreglo contiene las posiciones del tour que nos brinda el archivo solución (tour).
 * La función se encarga de crear un arreglo que contiene los lados del tour, para esto se crea un nuevo arreglo que almacenera esas coordenadas
 * y con las posiciones que nos brinda tour verificaremos su valor gracias al arreglo de coordenadas. De esta podremos contruir los lados/pares del tour.
 * Precondición: coordenadas.size > 0 && tour.size > 0 
 * Postcondición: (\forall int i; 0 <= i < (tour.size-2);(\exist int j; 0 <= j < coordenadas.size; \result[i].first == coordenadas[j] || \result[i].second == coordenadas[j]))
 */

fun convertirTour(coordenadas: Array<Triple<Double,Double,Int>>, tour: Array<Int>): 
    Array<Pair<Triple<Double,Double,Int>, Triple<Double,Double,Int>>>{

    var nTour = Array((tour.size-2)){Pair(Triple(0.0,0.0,0), Triple(0.0,0.0,0))}
    var j = 0
    for(i in 1 until tour.size-2) {
        var ciudad1 = Triple(0.0,0.0,0)
        var ciudad2 = Triple(0.0,0.0,0)
        for (ciudad in coordenadas) {
            if (tour[i] == ciudad.third) {
                ciudad1 = ciudad
            }
            if (tour[i-1] == ciudad.third) {
                ciudad2 = ciudad
            }
        }
        nTour[j] = Pair(ciudad2, ciudad1)
        j++
    }

    nTour[(nTour.size-1)] = Pair(nTour[(nTour.size-2)].second, nTour[0].first)
    println(nTour.contentToString())
    return nTour
}
