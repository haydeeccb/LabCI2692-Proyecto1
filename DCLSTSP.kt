// Librerías

import kotlin.math.roundToInt
import kotlin.math.min
import java.io.File
import java.io.Reader
import java.io.InputStream
import java.io.BufferedReader

fun main(args: Array<String>) {
    println("La instancia a resolver es: " +args[0])
    var P = obtenerDatosTSP(args[0])
    println("El arreglo de ciudades es:")
    println(P.contentToString())
    println("")
    var tourSolucion = divideAndConquerAndLocalSearchTSP(P)
    println("El tour solución es:")
    println(tourSolucion.contentToString())
    generarArchivoSolucionTSPLIB(args[1], tourSolucion)
}

/* ALGORITMOS 1 y 3 DEL PROBLEMA DEL TSP
 * Con sus respectivas funciones auxiliares
*/

/* Función: divideAndConquerTSP
 * Descripción: Recibe de entrada un arreglo de pares(Array<Pair(Double, Double)>) y retorna un arreglo de pares con una solucion válida del TSP
 * Precondición: A.size > 0 && 
 * Postcondición: \result.size == A.size + 1 && (\forall int i; 0 <= i < \result.size; (\exists int j: 0 <= j < A.size; \result[i] == A[j] )) 
*/
fun divideAndConquerTSP(A: Array<Triple<Double, Double, Int>>):  Array<Pair<Triple<Double, Double, Int>, Triple<Double, Double, Int>>> {
    var n = A.size
    if (n == 1) {
        return cicloUnaCiudad(A)
    } else if (n ==2) {
        return cicloDosCiudades(A)
    } else if (n == 3) {
        return cicloTresCiudades(A)
    } else {
        var (pIzquierda,pDerecha) = obtenerParticiones(A)
        println("Particion izquierda")
        println(pIzquierda.contentToString())
        println("Particion derecha")
        println(pDerecha.contentToString())
        println("Espacio antes de la primera llamada recursiva")
        println(" ")
        var C1 = divideAndConquerTSP(pIzquierda)
        println("Ciclo 1")
        println(C1.contentToString())
        println("Espacio antes de la segunda llamada recursiva")
        println(" ")
        var C2 = divideAndConquerTSP(pDerecha)
        println("Ciclo 2")
        println(C2.contentToString())
        println("")
        return combinarCiclos(C1, C2)
    }   
}

/* Función: cicloUnaCiudad
 * Descripción: De entrada recibe un arreglo de coordenadas de tamaño uno y retorna un arreglo de coordenadas de tamaño dos.
 * Este arreglo representa un tour, una solución válida del TSP.
 * Precondición: A.size == 1  
 * Postcondición: (\result.size == 2) && (\forall int i; 0 <= i < \result.size; \result[i] == A[0]) 
*/
fun cicloUnaCiudad(A: Array<Triple<Double, Double, Int>>):  Array<Pair<Triple<Double, Double, Int>, Triple<Double, Double, Int>>> {
    var B = arrayOf(Pair(A[0], A[0]))
    return B
}

/* Función: cicloDosCiudades
 * Descripción: De entrada recibe un arreglo de coordenadas de tamaño dos y retorna un arreglo de coordenadas de tamaño tres.
 * Arreglo que representa un tour, una solución válida del TSP.
 * Precondición: A.size == 2 
 * Postcondición: (\result.size == 3) && (\forall int i; 0 <= i < \result.size; (\exists int j: 0 <= j < A.size; \result[i] == A[j] )) 
*/
fun cicloDosCiudades(A: Array<Triple<Double, Double, Int>>):  Array<Pair<Triple<Double, Double, Int>, Triple<Double, Double, Int>>> {
    var B = arrayOf(Pair(A[0], A[1]), Pair(A[1], A[0]))
    return B
}

/* Función: cicloTresCiudades
 * Descripción: De entrada recibe un arreglo de coordenadas de tamaño tres y retorna un arreglo de coordenadas de tamaño cuatro.
 * Arreglo que representa un tour, una solución válida del TSP.
 * Precondición: A.size == 3  
 * Postcondición: (\result.size == 4) && (\forall int i; 0 <= i < \result.size; (\exists int j: 0 <= j < A.size; \result[i] == A[j] )) 
*/
fun cicloTresCiudades(A:  Array<Triple<Double, Double, Int>>): Array<Pair<Triple<Double, Double, Int>, Triple<Double, Double, Int>>> {
    var B = arrayOf(Pair(A[0], A[1]), Pair(A[1], A[2]), Pair(A[2], A[0]))
    return B
}

/* Función: combinarCiclos
 * Descripción: Recibe como entreda dos arreglos que contienen las coordenadas de las ciudades(Array<Pair<Double,Double>>). 
 * La funcion busca unir ambos arreglos(cada arreglo es una solucion, es decir, un tour) para contruir uno nuevo,
 * este elemento de salida debe cumple dos requisitos, tener la distancia mas corta a recorrer todas las ciudades y cumplir las especificaciones de un tour. 
 * Precondición: A.size >= 0  && B.size >= 0  
 * Postcondición: (\result.size == (A.size + B.size -1 )) && 
 * (\forall int i; 0 <= i < \result.size; (\exists int j: 0 <= j < A.size; \result[i] == A[j] ) || (\exists int j: 0 <= j < B.size; \result[i] == B[j] )) 
*/
fun combinarCiclos(A: Array<Pair<Triple<Double, Double, Int>, Triple<Double, Double, Int>>>, B: Array<Pair<Triple<Double, Double, Int>, Triple<Double, Double, Int>>>): 
    Array<Pair<Triple<Double, Double, Int>, Triple<Double, Double, Int>>> {
    when (A.size) {
        0 -> return B
    }
    when (B.size) {
        0 -> return A
    }
    var minG = Int.MAX_VALUE;
    var newC1: Pair<Triple<Double, Double, Int>, Triple<Double, Double, Int>> = Pair(Triple(0.0, 0.0, 0), Triple(0.0, 0.0, 0))
    var newC2: Pair<Triple<Double, Double, Int>, Triple<Double, Double, Int>> = Pair(Triple(0.0, 0.0, 0), Triple(0.0, 0.0, 0))
    var dC1: Pair<Triple<Double, Double, Int>, Triple<Double, Double, Int>> = Pair(Triple(0.0, 0.0, 0), Triple(0.0, 0.0, 0))
    var dC2: Pair<Triple<Double, Double, Int>, Triple<Double, Double, Int>> = Pair(Triple(0.0, 0.0, 0), Triple(0.0, 0.0, 0))
    for (i in 0 until A.size-1) {
        var lado1 = A[i]
        var a = lado1.first
        var b = lado1.second
        var dOLD1 = distancia(a,b)
        for (j in 0 until B.size-1) {
            var lado2 = B[j]
            var c = lado2.first
            var d = lado2.second
            var dOLD2 = distancia(c,d)
            var dNEW1 = distancia(a,c)
            var dNEW2 = distancia(b,d)
            var dNEW3 = distancia(a,d)
            var dNEW4 = distancia(b,c)

            var g1 = distanciaGanada(dOLD1, dOLD2, dNEW1, dNEW2)
            var g2 = distanciaGanada(dOLD1, dOLD2, dNEW3, dNEW4)

            var ganancia = min(g1, g2)
            if (ganancia < minG) {
                minG = ganancia
                if (g1 < g2) {
                    //agregar lados
                    newC1 = Pair(a, c)
                    // agregar lados
                    newC2 = Pair(b, d)
                } else {
                    //agregar lados
                    newC1 = Pair(a, d)
                    // agregar lados
                    newC2 = Pair(b, c)
                }
                //elimnar lados
                dC1 = Pair (a, b)
                //elimanr lados
                dC2 = Pair(c, d)
            }
        }
    }
    /*println(".....Agregar....")
    println(newC1)
    println(newC2)
    println(".......")
    println(".....Eliminar....")
    println(dC1)
    println(dC2)
    println(".......")*/

    //eliminar
    var particion1 = remover(A, dC1)
    var particion2 = remover(B, dC2)
    /*println("..Particiones")
    println(particion1.contentToString())
    println(".......")
    println(particion2.contentToString())
    println("TOUR")*/
    //agregar
    var Ciclo3 = tour(particion1, particion2, newC1, newC2)
    return Ciclo3
}

/* Función: distancia
 * Descripción: Recibe como entrada dos Pares que contienen las coordenadas de X y Y de las ciudades(Pair<Double, Double>).
 * La funcion se encarga de sacar las distancia que hay entre una ciudad y otra. Cada ciudad es un par, en donde el primer elemento del par 
 * represneta la coordenada X y el segundo elemento su coordenada en Y.
 * Precondición: true  
 * Postcondición: \result == Math.sqrt((b.first - a.first)*(b.first - a.first) + (b.second - a.second)*(b.second - a.second))
 * 
*/
fun distancia(a: Triple<Double, Double, Int> , b: Triple<Double, Double, Int>): Int {
    var xd: Double = b.first - a.first
    var yd: Double = b.second - a.second 
    var dxy = Math.sqrt(xd*xd+yd*yd).roundToInt()
    return dxy
}

/* Función: distanciaGanada
 * Descripción: Recibe como entreda 4 enteros(Int), cada uno de ellos es una distancia de entre dos ciudades.
 * El primer entero(dOLD1) es la distancia entre dos ciudades que se encuentran en la particion por la izquierda.
 * El segundo entero(dOLD2) es la distancia entre dos ciudades que se encuentran en la particion por la derecha.
 * El tercer y cuarto(dNEW1 y dNEW2) entero son las nuevas distancias entre una ciudad de la particion izquierda y una de la derecha.
 * La funcion devulve la distancia ganada entre (dOLD1, dOLD2) y (dNEW1, dNEW2).
 * La funcion busca unir ambos arreglos(cada arreglo es una solucion, es decir, un tour) para contruir uno nuevo,
 * este elemento de salida debe cumple dos requisitos, tener la distancia mas corta a recorrer todas las ciudades y cumplir las especificaciones de un tour. 
 * Precondición: dOLD1 > 0 && dOLD2 > 0  && dNEW1 > 0 && dNEW2 > 0
 * Postcondición: \result == (dNEW1 + dNEW2) - (dOLD1 + dOLD2)
*/
fun distanciaGanada(dOLD1: Int, dOLD2: Int, dNEW1: Int, dNEW2: Int): Int {
    return (dNEW1 + dNEW2) - (dOLD1 + dOLD2)
}

/* Función: remover
 * Descripción: Recibe como entrada un arreglo de pares(Array<Pair<Double, Double>>) que es una de las particiones del algoritmo combinarCiclos,
 * tambien ingresa un par que contiene dos ciudades(Pair<Pair<Double, Double>, Pair<Double, Double>>).
 * La funcion revisara cada elemento del arreglo A, si algun elemento coincide con un alguna de las ciudades del par X
 * este procede a eliminar ese par y continua con la revision.
 * Al final nos retornara un arreglo que posee todos los elementos de A exceptuando las dos ciudades del par X.
 * Precondición: A.size >= 3 && (\exists int i: 0 <= i < A.size; x.first == A[i] ||  x.second == A[i]) 
 * Postcondición: (\forall int i: 0 <= i < \result.size; x.first != A[i] ||  x.second != A[i]) 
 * 
*/
fun remover(A: Array<Pair<Triple<Double, Double, Int>, Triple<Double, Double, Int>>>, x: Pair<Triple<Double, Double, Int>, Triple<Double, Double, Int>>): Array<Pair<Triple<Double, Double, Int>, Triple<Double, Double, Int>>> {
    if (A.size == 3) {
        var B = Array(0){Pair(Triple(0.0,0.0, 0), Triple(0.0,0.0, 0))}
        return B
    } else {
        var B = Array((A.size-1)){Pair(Triple(0.0,0.0, 0), Triple(0.0,0.0, 0))}
        var j = 0
        for(i in 0 until A.size) {
            var lado = A[i]
            var ciudad1 = lado.first
            var ciudad2 = lado.second
            if ((ciudad1 == x.first || ciudad1 == x.second) && (ciudad2 == x.first || ciudad2 == x.second)) {
                continue
            } else {
                B[j] = A[i]
                j++ 
            }
        }
        //print(B.contentToString())
        return B
    }  
}

/* Función: tour
 * Descripción: Recibe dos arreglos (Array<Pair<Double, Double>>) A y B que ya pasaron por la funcion remover.
 * Estos arreglos contienen todas las ciudades exceptuando las ciudades que se debian remover para colocar los nuevos pares o conexiones. 
 * Dos pares que se reciben como entrada y cada uno contine dos ciudades, que trazan el nuevo camino que debe tomar la persona para formar un tour con A y B.
 * La funcion busca crear un nuevo arreglo, que contiene los elementos de A y B, ademas que intregra en ese arreglo las ciudades que estan dentro de los pares. 
 * Precondición: A.size > 0 && B.size > 0 && \result.size == (A.size + B.size + 5)
 * && (\forall int i: 0 <= i < A.size; x.first != A[i] &&  x.second != A[i]) && (\forall int j: 0 <= j < B.size; y.first != A[i] &&  y.second != A[i]) 
 * Postcondición: (\forall int i: 0 <= i < \result.size; x.first != A[i] ||  x.second != A[i]) 
 * 
*/
fun tour(A: Array<Pair<Triple<Double, Double, Int>, Triple<Double, Double, Int>>>, B: Array<Pair<Triple<Double, Double, Int>, Triple<Double, Double, Int>>>, 
x: Pair<Triple<Double, Double, Int>, Triple<Double, Double, Int>>, y: Pair<Triple<Double, Double, Int>, Triple<Double, Double, Int>>): Array<Pair<Triple<Double, Double, Int>, Triple<Double, Double, Int>>> {
    var Ciclo3 = Array(A.size+B.size+2){Pair(Triple(0.0,0.0, 0), Triple(0.0,0.0, 0))}
    var contador = 0
    var corte = 0
    for (i in 0 until A.size) {
        if ((A[i].first == x.first || A[i].first == x.second )||(A[i].second == x.first || A[i].second == x.second )) {
            Ciclo3[contador] = A[i]
            contador++
            break
        } else {
            Ciclo3[contador] = A[i]
            contador++
            corte++
        }
    }
    Ciclo3[contador] = x
    contador ++
    for (i in 0 until B.size) {
        Ciclo3[contador] = B[i]
        contador++
    }
    Ciclo3[contador] = y
    contador ++
    for (i  in (corte+1) until A.size) {
        Ciclo3[contador] = A[i]
        contador++
    }
    return Ciclo3
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
    return C
}

// Función para convertir el tour solución al formato TSPLIB

/* Función: obtenerTourTSPLIB
 * Descripción: Recibe un tour: tour: Array<Pair<Triple<Double,Double,Int>, Triple<Double,Double,Int>>>
 * y retorna un tour de strings que contiene sólo los números de las ciudades del tour original y en orden, 
 * es decir, está en formato TSPLIB
 * Precondición: tour.size >= 1
 * Postondición: \result.size >= 1
 */ 
fun obtenerTourTSPLIB(tour: Array<Pair<Triple<Double,Double,Int>, Triple<Double,Double,Int>>>): Array<String> {
	var tourTSPLIB = Array(tour.size, {""})
	for(i in 0 until tour.size) {
		tourTSPLIB[i] = ((tour[i].first).third).toString()
	}
	return tourTSPLIB
}

// Procedimiento para generar el archivo de salida con la solución del problema TSP, en formato TSPLIB

/* Función: generarArchivoSolucionTSPLIB
 * Descripción: Recibe el nombre del archivo de salida y el tour solución, el cual viene
 * dado por el algoritmo divideAndConquerAndLocalSearchTSP. Este procedimiento genera el archivo de salida con
 * la solución del problema TSP, en formato TSPLIB
 * Precondición: nombreSalida != null && tourSolucion.size >= 2
 * Postondición: true
 */ 
fun generarArchivoSolucionTSPLIB(nombreSalida: String, tourSolucion: Array<Pair<Triple<Double,Double,Int>, Triple<Double,Double,Int>>>) {
    var distancia = obtenerDistanciaTour(tourSolucion)
    var tourTSPLIB = obtenerTourTSPLIB(tourSolucion)
    var dimensionTSP = tourTSPLIB.size
    var archivoSalida = File(nombreSalida)
    var textoSalida = "NAME: "+nombreSalida
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

// Funciones necesarias para la implementación del algoritmo 2

/* Función: obtenerMaximoCoordenadasX
 * Descripción: Recibe un P: Array<Triple<Double,Double, Int>> de coordenadas y retorna un Double que corresponde al elemento 
 * máximo de las coordenadas X de dicho arreglo.
 * Precondición: P.size >= 1
 * Postondición: (\forall int i: 0 <= i < P.size, \result >= P[i].first) && (\exists int i; 0 <= i && i < P.size; \result == P[i].first)
 */ 
fun obtenerMaximoCoordenadasX(P: Array<Triple<Double,Double, Int>>): Double {
	var n = P.size
	var maximoX = P[0].first
	for (i in 1 until n) {
		if (P[i].first > maximoX) {
			maximoX = P[i].first
		}
	}
	return maximoX
}

/* Función: obtenerMinimoCoordenadasX
 * Descripción: Recibe un P: Array<Triple<Double,Double,Int>> de coordenadas y retorna un Double que corresponde al elemento 
 * mínimo de las coordenadas X de dicho arreglo.
 * Precondición: P.size >= 1
 * Postondición: (\forall int i: 0 <= i < P.size, \result <= P[i].first) && (\exists int i; 0 <= i && i < P.size; \result == P[i].first)
 */ 
fun obtenerMinimoCoordenadasX(P: Array<Triple<Double,Double,Int>>): Double {
	var n = P.size
	var minimoX = P[0].first
	for (i in 1 until n) {
		if (P[i].first < minimoX) {
			minimoX = P[i].first
		}
	}
	return minimoX
}

/* Función: obtenerMaximoCoordenadasY
 * Descripción: Recibe un P: Array<Triple<Double,Double>> de coordenadas y retorna un Double que corresponde al elemento 
 * máximo de las coordenadas Y de dicho arreglo.
 * Precondición: P.size >= 1
 * Postondición: (\forall int i: 0 <= i < P.size, \result >= P[i].second) && (\exists int i; 0 <= i && i < P.size; \result == P[i].second)
 */ 
fun obtenerMaximoCoordenadasY(P: Array<Triple<Double,Double,Int>>): Double {
	var n = P.size
	var maximoY = P[0].second
	for (i in 1 until n) {
		if (P[i].second > maximoY) {
			maximoY = P[i].second
		}
	}
	return maximoY
}

/* Función: obtenerMinimoCoordenadasY
 * Descripción: Recibe un P: Array<Triple<Double,Double,Int>> de coordenadas y retorna un Double que corresponde al elemento 
 * mínimo de las coordenadas Y de dicho arreglo.
 * Precondición: P.size >= 1
 * Postondición: (\forall int i: 0 <= i < P.size, \result <= P[i].second) && (\exists int i; 0 <= i && i < P.size; \result == P[i].second)
 */ 
fun obtenerMinimoCoordenadasY(P: Array<Triple<Double,Double,Int>>): Double {
	var n = P.size
	var minimoY = P[0].second
	for (i in 1 until n) {
		if (P[i].second < minimoY) {
			minimoY = P[i].second
		}
	}
	return minimoY
}

/* Función: obtenerRectangulo
 * Descripción: Recibe un P: Array<Triple<Double,Double,Int>> de coordenadas y retorna un arreglo Array<Pair<Double,Double>> 
 * que muestra las coordenadas de un rectángulo que contiene todos los puntos del arreglo P. 
 * Dichas coordenadas se encuentran en el siguiente orden:
 * esquina inferior izquierda, esquina inferior derecha, esquina superior derecha, esquina superior izquierda
 * Precondición: P.size >= 1
 * Postondición: (\forall int i: 0 <= i < P.size, \result[0].first <= P[i].first <= \result[2].first && \result[0].second <= P[i].second <= \result[2].second) 
 */ 
fun obtenerRectangulo(P: Array<Triple<Double,Double,Int>>): Array<Pair<Double,Double>> {
	var minimoX = obtenerMinimoCoordenadasX(P)
	var maximoX = obtenerMaximoCoordenadasX(P)
	var minimoY = obtenerMinimoCoordenadasY(P)
	var maximoY = obtenerMaximoCoordenadasY(P)
	var coordenadas00 = Pair(minimoX,minimoY)
	var coordenadas10 = Pair(maximoX, minimoY)
	var coordenadas01 = Pair(minimoX, maximoY)
	var coordenadas11 = Pair(maximoX, maximoY)
	var coordenadasRectangulo = arrayOf(coordenadas00, coordenadas10, coordenadas11, coordenadas01)
	return coordenadasRectangulo
}

/* Función: obtenerDimensionesRectangulo
 * Descripción: Recibe un R: Array<Pair<Double,Double>> con las coordenadas cartesianas de un rectángulo y 
 * retorna un Pair<Double,Double> que contiene las dimensiones de sus lados paralelos al eje X y al eje Y, en ese orden. 
 * Las coordendas del rectángulo que se recibe deben estar en el siguiente orden:
 * esquina inferior izquierda, esquina inferior derecha, esquina superior derecha, esquina superior izquierda
 * Precondición: R.size >= 0 && R[0].first <= R[2].first && R[0].second <= R[2].second
 * Postondición: \result.first >= 0 && \result.second >= 0
 */ 
fun obtenerDimensionesRectangulo(R: Array<Pair<Double,Double>>) : Pair<Double,Double> {
	var dimensionesX = R[2].first - R[0].first
	var dimensionesY = R[2].second - R[0].second
	return Pair(dimensionesX, dimensionesY)
}

/* Función: swap
 * Descripción: Recibe un P: Array<Triple<Double,Double,Int>> y dos i,j: Int. Esta función intercambia las posiciones P[i] y P[j]
 * Precondición: 0 <= i,j < P.size
 * Postondición: P[i] == \old(P[j]) && P[j] == \old(P[i])
 */ 
fun swap (P: Array<Triple<Double,Double,Int>>, i: Int, j: Int) {
    var temp = P[i]
    P[i] = P[j]
    P[j] = temp
}

/* Función: quicksortTSP
 * Descripción: Recibe un P: Array<Triple<Double,Double,Int>> de coordenadas, dos p,r: Int que identifican el subarreglo
 * P[p...r] que se desea ordenar, y un coordenada: String que identifica respecto a qué coordenada se debe ordenar el arreglo. 
 * Este procedimiento ordena los elementos de P de acuerdo a las coordenadas especificadas por el String coordenada, 
 * utilizando el algoritmo quicksort clásico
 * Precondición: true
 * Postondición: (\forall int i; 0 <= i && i < P.size-1; P[i].first <= P[i+1].first) || (\forall int i; 0 <= i && i < P.size-1; P[i].second <= P[i+1].second)
 */ 
fun quicksortTSP(P: Array<Triple<Double,Double,Int>>, p: Int, r: Int, coordenada: String) {
	if (p < r) {
		if (coordenada == "X" || coordenada == "x") {
			var q = particionCoordenadaX(P,p,r)
			quicksortTSP(P, p, q-1, coordenada)
			quicksortTSP(P, q+1, r, coordenada)
		} else {
			var q = particionCoordenadaY(P,p,r)
			quicksortTSP(P, p, q-1, coordenada)
			quicksortTSP(P, q+1, r, coordenada)
		}
	}
}

/* Función: particionCoordenadaX
 * Descripción: Recibe un arreglo P: Array<Triple<Double,Double,Int>> de coordenadas y dos p,r: Int que identifican el subarreglo
 * P[p...r] sobre el cual se producirá una partición. Esta función genera una partición del subarreglo P[p...r] 
 * tal que las coordenadas X de los elementos del subarreglo P[p...q-1] sean menores que P[q].first y las coordenadas X 
 * de los elementos del subarreglo P[q+1...r] sean mayores que P[q].first. La función retorna q: Int.
 * Precondición: 0 <= p < r < P.size
 * Postondición: p <= q <= r && (\forall int i; p <= i && i < q; P[i].first <= P[q].first) && (\forall int i; q+1 <= i && i <= r; P[i].first => P[q].first)
 */  
fun particionCoordenadaX(P: Array<Triple<Double,Double,Int>>, p: Int, r: Int): Int {
	var x = P[r].first
	var i = p - 1
	for (j in p until r) {
		if (P[j].first <= x) {
			i = i + 1
			swap(P, i, j)
		}
	}
	swap(P, i+1, r)
	return i+1
}

/* Función: particionCoordenadaY
 * Descripción: Recibe un P: Array<Triple<Double,Double,Int>> de coordenadas, y dos p,r: Int que identifican el subarreglo
 * P[p...r] sobre el cual se producirá una partición. Esta función genera una partición del subarreglo P[p...r] 
 * tal que las coordenadas Y de los elementos del subarreglo P[p...q-1] sean menores que P[q].second y las coordenadas Y
 * de los elementos del subarreglo P[q+1...r] sean mayores que P[q].second. La función retorna q: Int.
 * Precondición: 0 <= p < r < P.size
 * Postondición: p <= q <= r && (\forall int i; p <= i && i < q; P[i].second <= P[q].second) && (\forall int i; q+1 <= i && i <= r; P[i].second => P[q].second)
 */  
fun particionCoordenadaY(P: Array<Triple<Double,Double,Int>>, p: Int, r: Int): Int {
	var x = P[r].second
	var i = p - 1
	for (j in p until r) {
		if (P[j].second <= x) {
			i = i + 1
			swap(P, i, j)
		}
	}
	swap(P, i+1, r)
	return i+1
}

/* Función: coindicenDosCoordenadasX
 * Descripción: Recibe un P: Array<Triple<Double,Double,Int>> de coordenadas y retorna true si dos coordenadas X coindicen, 
 * y false en caso contrario
 * Precondición: P.size >= 1
 * Postondición: true || false
 */ 
fun coincidenDosCoordenadasX(P: Array<Triple<Double,Double,Int>>): Boolean {
	var n = P.size
	if(n == 1) {
		return false
	}
	for (i in 0 until n-1) {
		for (j in i+1 until n) {
			if (P[i].first == P[j].first) {
				return true
			}
		}
	}
	return false 
}

/* Función: coindicenDosCoordenadasY
 * Descripción: Recibe un P: Array<Triple<Double,Double,Int>> de coordenadas y retorna true si dos coordenadas Y coindicen, 
 * y false en caso contrario
 * Precondición: P.size >= 1
 * Postondición: true || false
 */ 
fun coincidenDosCoordenadasY(P: Array<Triple<Double,Double,Int>>): Boolean {
	var n = P.size
	if(n == 1) {
		return false
	}
	for (i in 0 until n-1) {
		for (j in i+1 until n) {
			if (P[i].second == P[j].second) {
				return true
			}
		}
	}
	return false 
}

/* Función: obtenerPuntoDeCorte
 * Descripción: Recibe un P: Array<Triple<Double,Double,Int>> de coordenadas y un ejeDeCorte: String que identifica un eje de corte.
 * La función retorna un Pair<Double,Double> que corresponde al punto de corte para el arreglo P, de acuerdo al eje especificado
 * Precondición: P.size >= 1
 * Postondición: (\exists int i; 0 <= i && i < P.size; P[i] == \result)
 */ 
fun obtenerPuntoDeCorte(P: Array<Triple<Double,Double,Int>>, ejeDeCorte: String): Pair<Double,Double> {
	var n = P.size
	var pos = n/2
	if(n%2 == 0) {
		pos = pos - 1
	}
	if (ejeDeCorte == "X" || ejeDeCorte == "x") {
		if (coincidenDosCoordenadasX(P) == true) {
			quicksortTSP(P,0,n-1,"Y")
		}
		else {
			quicksortTSP(P,0,n-1,"X")
		}
	} else {
		if (coincidenDosCoordenadasY(P) == true) {
			quicksortTSP(P,0,n-1,"X")
		}
		else {
			quicksortTSP(P,0,n-1,"Y")
		}
	}
	return Pair(P[pos].first, P[pos].second)
}

/* Función: aplicarCorte
 * Descripción: Recibe un ejeDeCorte: String que especifica el eje de corte, un puntoDeCorte: pair que especifica el punto de corte 
 * y un rectangulo: Array<Pair<Double,Double>> que especifica las coordenadas de un rectángulo, en el siguiente orden:
 * esquina inferior izquierda, esquina inferior derecha, esquina superior derecha, esquina superior izquierda.
 * La función retorna un Pair<Array<Pair<Double,Double>>, Array<Pair<Double,Double>>> que contiene las coordenadas de dos rectángulos,
 * los cuales se obtienen a partir del rectángulo original y trazando una recta
 * perpendicular al eje de corte por el punto de corte. Dichas coordenadas se encuentran en el siguiente orden:
 * esquina inferior izquierda, esquina inferior derecha, esquina superior derecha, esquina superior izquierda
 * Precondición: rectangulo.size >= 1
 * Precondición: rectangulo[0].first <= puntoDeCorte.first <= rectangulo[2].first && rectangulo[0].second <= puntoDeCorte.second <= rectangulo[2].second)
 * Postondición: (\result.first).size == 4 && (\result.second).size == 4
 */ 
fun aplicarCorte(ejeDeCorte: String, puntoDeCorte: Pair<Double,Double>, rectangulo: Array<Pair<Double,Double>>): Pair<Array<Pair<Double,Double>>, Array<Pair<Double,Double>>> {
	if (ejeDeCorte == "X" || ejeDeCorte == "x") {
		var coordenadasIzq00 = rectangulo[0]
		var coordenadasIzq10 = Pair(puntoDeCorte.first,rectangulo[0].second)
		var coordenadasIzq11 = Pair(puntoDeCorte.first, rectangulo[2].second)
		var coordenadasIzq01 = rectangulo[3]
		var coordenadasDer00 = Pair(puntoDeCorte.first,rectangulo[0].second)
		var coordenadasDer10 = rectangulo[1]
		var coordenadasDer11 = rectangulo[2]
		var coordenadasDer01 = Pair(puntoDeCorte.first, rectangulo[2].second)
		var rectanguloIzq = arrayOf(coordenadasIzq00, coordenadasIzq10, coordenadasIzq11, coordenadasIzq01)
		var rectanguloDer = arrayOf(coordenadasDer00, coordenadasDer10, coordenadasDer11, coordenadasDer01)
		return Pair(rectanguloIzq, rectanguloDer)
	} else {
		var coordenadasIzq00 = rectangulo[0]
		var coordenadasIzq10 = rectangulo[1]
		var coordenadasIzq11 = Pair(rectangulo[1].first, puntoDeCorte.second)
		var coordenadasIzq01 = Pair(rectangulo[3].first, puntoDeCorte.second)
		var coordenadasDer00 = Pair(rectangulo[3].first, puntoDeCorte.second)
		var coordenadasDer10 = Pair(rectangulo[1].first, puntoDeCorte.second)
		var coordenadasDer11 = rectangulo[2]
		var coordenadasDer01 = rectangulo[3]
		var rectanguloIzq = arrayOf(coordenadasIzq00, coordenadasIzq10, coordenadasIzq11, coordenadasIzq01)
		var rectanguloDer = arrayOf(coordenadasDer00, coordenadasDer10, coordenadasDer11, coordenadasDer01)
		return Pair(rectanguloIzq, rectanguloDer)	
	}
}

/* Función: esElPrimerRectangulo
 * Descripción: Recibe un P: Array<Triple<Double,Double,Int>> de coordenadas y un rectangulo: Array<Pair<Double,Double>> 
 * con las coordenadas de un rectángulo, las cuales deben estar en el siguiente orden:
 * esquina inferior izquierda, esquina inferior derecha, esquina superior derecha, esquina superior izquierda.
 * Esta función verifica si el rectángulo dado es el primer rectángulo que se obtiene luego de aplicar el corte al rectángulo
 * que contiene a P (el primer rectángulo corresponde al izquierdo si el corte es vertical y al inferior si es horizontal)
 * Precondición: P.size >= 1
 * Postondición: true || false
 */ 
fun esElPrimerRectangulo(P: Array<Triple<Double,Double,Int>>, rectangulo: Array<Pair<Double,Double>>): Boolean {
	var minimoX = obtenerMinimoCoordenadasX(P)
	var minimoY = obtenerMinimoCoordenadasY(P)
	var rec00 = Pair(minimoX, minimoY)
	if (rec00 == rectangulo[0]) {
		return true
	} else {
		return false
	}
}

/* Función: obtenerPuntosPrimerRectangulo
 * Descripción: Recibe un P: Array<Triple<Double,Double,Int>> de coordenadas y un rectangulo: Array<Pair<Double,Double>> 
 * con las coordenadas de un rectángulo, las cuales deben estar en el siguiente orden:
 * esquina inferior izquierda, esquina inferior derecha, esquina superior derecha, esquina superior izquierda.
 * Esta función retorna los valores de P que están contenidos en el rectángulo dado, en un Array<Pair<Double,Double>>. 
 * Se incluyen aquellas ciudades de P que están sobre el lado correspondiente al corte 
 * (dichas ciudades se asignan a la partición del primer rectángulo por diseño)
 * Precondición: P.size >= 1
 * Postondición: \result.size >= 0
 */ 
fun obtenerPuntosPrimerRectangulo(P: Array<Triple<Double,Double,Int>>, rectangulo: Array<Pair<Double,Double>>): Array<Triple<Double,Double,Int>> {
	var n = P.size
	var k = 0
	var tmp = Array(n, {0})
	for (i in 0 until n) {
		if (rectangulo[0].first <= P[i].first && P[i].first <= rectangulo[2].first) {
			if (rectangulo[0].second <= P[i].second && P[i].second <= rectangulo[2].second){
				tmp[i] = 1
				k = k + 1 
			}	
		}
	}
	var particion = Array(k, {Triple(0.0, 0.0, 0)})
	var j = 0
	for (i in 0 until n) {
		if (tmp[i] == 1) {
			particion[j] = P[i]
			j = j + 1
		} 
	}
	return particion
} 

/* Función: obtenerPuntosSegundoRectangulo
 * Descripción: Recibe un P: Array<Triple<Double,Double,Int>> de coordenadas y un rectangulo: Array<Pair<Double,Double>> 
 * con las coordenadas de un rectángulo, las cuales deben estar en el siguiente orden:
 * esquina inferior izquierda, esquina inferior derecha, esquina superior derecha, esquina superior izquierda.
 * Esta función retorna los valores de P que están contenidos en el rectángulo dado, en un Array<Triple<Double,Double,Int>>. 
 * No se incluyen aquellas ciudades de P que están sobre el lado correspondiente al corte 
 * (dichas ciudades se asignan a la partición del primer rectángulo por diseño)
 * Precondición: P.size >= 1
 * Postondición: \result.size >= 0
 */ 
fun obtenerPuntosSegundoRectangulo(P: Array<Triple<Double,Double,Int>>, rectangulo: Array<Pair<Double,Double>>) : Array<Triple<Double,Double,Int>> {
	var n = P.size
	var k = 0
	var tmp = Array(n, {0})
	var minimoX = obtenerMinimoCoordenadasX(P)
	var maximoY = obtenerMaximoCoordenadasY(P)
	if (rectangulo[3] == Pair(minimoX, maximoY)) {
		for (i in 0 until n) {
			if (rectangulo[0].first <= P[i].first && P[i].first <= rectangulo[2].first) {
				if (rectangulo[0].second < P[i].second && P[i].second <= rectangulo[2].second){
					tmp[i] = 1
					k = k + 1 
				}
			}
		}
	} else {
		for (i in 0 until n) {
			if (rectangulo[0].first < P[i].first && P[i].first <= rectangulo[2].first) {
				if (rectangulo[0].second <= P[i].second && P[i].second <= rectangulo[2].second){
					tmp[i] = 1
					k = k + 1
				} 
			}
		}
	}
	var particion = Array(k, {Triple(0.0, 0.0, 0)})
	var j = 0
	for (i in 0 until n) {
		if (tmp[i] == 1) {
			particion[j] = P[i]
			j = j + 1
		} 
	}
	return particion
}

/* Función: obtenerPuntosRectangulo
 * Descripción: Recibe un P: Array<Triple<Double,Double,Int>> de coordenadas y un rectangulo: Array<Pair<Double,Double>> 
 * con las coordenadas de un rectángulo, las cuales deben estar en el siguiente orden:
 * esquina inferior izquierda, esquina inferior derecha, esquina superior derecha, esquina superior izquierda.
 * La función retorna un arreglo Array<Triple<Double,Double,Int>> que contiene aquellos puntos de P que están contenidos en el 
 * rectángulo dado. Se incluyen los lados del corte en caso de que el rectángulo dado sea el primero y no se incluye en caso
 * contrario (por diseño)
 * Precondición: P.size >= 1
 * Postondición: 0 <= \result.size <= P.size
 */ 
fun obtenerPuntosRectangulo(P: Array<Triple<Double,Double,Int>>, rectangulo: Array<Pair<Double,Double>>): Array<Triple<Double,Double,Int>>{
	if (esElPrimerRectangulo(P, rectangulo) == true) {
		return obtenerPuntosPrimerRectangulo(P,rectangulo)
	} else {
		return obtenerPuntosSegundoRectangulo(P,rectangulo)
	}
}

/* Función: obtenerPuntoDeCorteMitad
 * Descripción: Recibe un rectangulo: Array<Pair<Double,Double>> con las coordenadas de un rectángulo y un ejeDeCorte: String,
 * el cual especifica respecto a cuál eje se debe obtener el punto de corte. Las coordenadas del rectángulo deben estar en el 
 * siguiente orden: esquina inferior izquierda, esquina inferior derecha, esquina superior derecha, esquina superior izquierda.
 * La función retorna un Pair<Double,Double>, que corresponde al punto medio del lado del rectángulo que es paralelo al 
 * eje de corte y se encuentra más cercano a dicho eje.
 * Precondición: rectangulo.size == 4
 * Postondición: rectangulo[0].first <= \result.first <= rectangulo[1].first && rectangulo[0].second <= \result.second <= rectangulo[3].second
 */ 
fun obtenerPuntoDeCorteMitad(rectangulo: Array<Pair<Double,Double>>, eje: String): Pair<Double,Double> {
	var xmin = rectangulo[0].first
	var ymin = rectangulo[0].second
	if (eje == "X" || eje == "x") {
		var xdim = rectangulo[1].first - rectangulo[0].first
		return Pair(xmin + xdim/2, ymin)
	} else {
		var ydim = rectangulo[3].second - rectangulo[0].second
		return Pair(xmin, ymin + ydim/2)
	}
}

// Algoritmo 2

/* Función: obtenerParticiones
 * Descripción: Recibe un P: Array<Triple<Double,Double,Int>> de coordenadas y retorna un 
 * Pair< Array<Triple<Double,Double,Int>>, Array<Triple<Double,Double,Int>>> que contiene dos particiones entre las cuales se reparten
 * las ciudades dadas en P. Este algoritmo cumple el papel de "Divide" en el esquema "Divide-and-Conquer" empleado para 
 * resolver el TSP
 * Precondición: P.size >= 1
 * Postondición: (\result.first).size >= 1 && (\result.second).size >= 1
 */ 
fun obtenerParticiones(P: Array<Triple<Double,Double,Int>>): Pair< Array<Triple<Double,Double,Int>>, Array<Triple<Double,Double,Int>>> {
	var rectangulo = obtenerRectangulo(P)
	var (Xdim, Ydim) = obtenerDimensionesRectangulo(rectangulo)
	var ejeDeCorte: String
	var eje: String
	if(Xdim > Ydim) {
		ejeDeCorte = "X"
		eje = "X"
	} else {
		ejeDeCorte = "Y"
		eje = "Y"
	}
	var (Xc, Yc) = obtenerPuntoDeCorte(P, ejeDeCorte)
	var (rectanguloIzq, rectanguloDer) = aplicarCorte(ejeDeCorte, Pair(Xc, Yc), rectangulo)
	var particionIzq = obtenerPuntosRectangulo(P, rectanguloIzq)
	var particionDer = obtenerPuntosRectangulo(P, rectanguloDer)
	if ((particionIzq.size == 0 && particionDer.size > 3) || (particionDer.size == 0 && particionIzq.size > 3)) {
		if (ejeDeCorte == "X") {
			ejeDeCorte = "Y"
		} else {
			ejeDeCorte = "X"
		}
		Xc = obtenerPuntoDeCorte(P, ejeDeCorte).first
		Yc = obtenerPuntoDeCorte(P, ejeDeCorte).second
		rectanguloIzq = aplicarCorte(ejeDeCorte, Pair(Xc, Yc), rectangulo).first
		rectanguloDer = aplicarCorte(ejeDeCorte, Pair(Xc, Yc), rectangulo).second
		particionIzq = obtenerPuntosRectangulo(P, rectanguloIzq)
		particionDer = obtenerPuntosRectangulo(P, rectanguloDer)
		if ((particionIzq.size == 0 && particionDer.size > 3) || (particionDer.size == 0 && particionIzq.size > 3)) {
			Xc = obtenerPuntoDeCorteMitad(rectangulo, eje).first
			Yc = obtenerPuntoDeCorteMitad(rectangulo, eje).second
			rectanguloIzq = aplicarCorte(ejeDeCorte, Pair(Xc, Yc), rectangulo).first
			rectanguloDer = aplicarCorte(ejeDeCorte, Pair(Xc, Yc), rectangulo).second
			particionIzq = obtenerPuntosRectangulo(P, rectanguloIzq)
			particionDer = obtenerPuntosRectangulo(P, rectanguloDer)
		}
	}
	return Pair(particionIzq, particionDer)
}


// Funciones necesarias para la implementación del algoritmo 4

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

/* Función: invertirTour
 * Descripción: Recibe un tour: Array<Pair<Triple<Double,Double,Int>, Triple<Double,Double,Int>>> 
 * y dos enteros p,q que indican dos lados en el tour. Esta función invierte el sub-recorrido touŕ[p..q]
 * Precondición: 0 <= p <= q < tour.size
 * Postondición: (\forall int i; p <= i && i <= q: tour[i] == \old(tour[q+p-i]))
 */ 
fun invertirTour(tour: Array<Pair<Triple<Double,Double,Int>, Triple<Double,Double,Int>>>, p: Int, q: Int) {
	var n = q-p+1
	var tmp = Array(n, {Pair(Triple(0.0, 0.0, 0), Triple(0.0, 0.0, 0))})
	var k = q
	for (i in 0 until n) {
		tmp[i] = Pair(tour[k].second, tour[k].first)
		k = k-1
	}
	k = 0
	for (i in p until q+1) {
		tour[i] = tmp[k]
		k = k+1
	}
}

/* Función: swap2OPT
 * Descripción: Recibe un tour: Array<Pair<Triple<Double,Double,Int>, Triple<Double,Double,Int>>> 
 * y dos enteros p,q que indican dos lados en el tour
 * Esta función intercambia los lados tour[p] y tour[q]
 * Precondición: 0 <= p <= q < tour.size
 * Postondición: tour[p].second == \old(tour[q].first) && tour[q].first == \old(tour[p].second) 
 */ 
fun swap2OPT(tour: Array<Pair<Triple<Double,Double,Int>, Triple<Double,Double,Int>>>, p: Int, q: Int): Array<Pair<Triple<Double,Double,Int>, Triple<Double,Double,Int>>> {
	var tmp1 = Pair(tour[p].first, tour[q].first)
	var tmp2 = Pair(tour[p].second, tour[q].second)
	tour[p] = tmp1
	tour[q] = tmp2
	invertirTour(tour, p+1, q-1)
	return tour
}

/* Función: cambioDistanciaTour
 * Descripción: Recibe un tour: Array<Triple<Double,Double,Int>> y dos enteros i,j. Esta función calcula el cambio en la distancia 
 * luego de retirar los lados (tour[i-1],tour[i]) y (tour[j],tour[j+1]) reemplazándolos con los lados (tour[i-1],tour[j])
 * y (tour[i],tour[j+1]). Dicho intercambio es el resultado de invertir el subarreglo tour[i..j]
 * Precondición: 0 < i <= j < tour.size-1
 * Postondición: true
 */ 
fun cambioDistanciaTour(tour: Array<Pair<Triple<Double,Double,Int>, Triple<Double,Double,Int>>>, p: Int, q: Int): Int {
	var dNew1 = distanciaDosPuntos(tour[p].first, tour[q].first)
	var dNew2 = distanciaDosPuntos(tour[p].second, tour[q].second)
	var dOld1 = distanciaDosPuntos(tour[p].first, tour[p].second)
	var dOld2 = distanciaDosPuntos(tour[q].first, tour[q].second)
	var cambio = dNew1 + dNew2 - dOld1 - dOld2
	return cambio
}

/* Función: busquedaLocalCon2OPT
 * Descripción: Recibe un tour: Array<Triple<Double,Double,Int>> y retorna un tour: Array<Triple<Double,Double,Int>>. 
 * Esta función emplea el operador 2opt para mejorar una solución del TSP 
 * Precondición: tour.size >= 1
 * Postondición: \result.size >= 1
 */ 
fun busquedaLocalCon2OPT(tour: Array<Pair<Triple<Double,Double,Int>, Triple<Double,Double,Int>>>) : Array<Pair<Triple<Double,Double,Int>, Triple<Double,Double,Int>>> {
	var tourActual = tour
	var n = tourActual.size
	for (i in 1 until n-1) {
		for (j in i+1 until n) {
			var cambioDistancia = cambioDistanciaTour(tourActual, i, j)
			if(cambioDistancia < 0) {
				tourActual = swap2OPT(tourActual, i, j)
			}
		}
	}
	return tourActual
}

// Algoritmo 4

/* Función: divideAndConquerAndLocalSearchTSP
 * Descripción: Recibe un arreglo P: Array<Triple<Double,Double,Int>> con coordenadas de ciudades y retorna un 
 * tour: Array<Triple<Double,Double,Int>> que resuelve el problema del TSP para dicho arreglo. 
 * La función divideAndConquerAndLocalSearchTSP resuelve el problema por medio de la técnica de Divide-And-Conquer 
 * y luego lo mejora empleando el algoritmo 2-opt
 * Precondición: P.size >= 1
 * Postondición: \result.size >= 2 && \result[0] == \result[\result.size-1]
 */ 
fun divideAndConquerAndLocalSearchTSP(P: Array<Triple<Double,Double,Int>>) : Array<Pair<Triple<Double,Double,Int>, Triple<Double,Double,Int>>> {
	var c1 = divideAndConquerTSP(P)
	var distancia1 = obtenerDistanciaTour(c1)
	println("La distancia del tour obtenido por el algoritmo de Divide-And-Conquer es ${distancia1}")
	var c2 = busquedaLocalCon2OPT(c1)
	var distancia2OPT = obtenerDistanciaTour(c2)
	println("La distancia del tour obtenido como solución final es ${distancia2OPT}")
	return c2
}


// PARA PROBAR LOS ALGORITMOS

/* Función:
 * Descripción: 
 * Precondición:
 * Postondición:
 */ 

// Para generar un tour ordenado
fun obtenerTourOrdenado(A: Array<Triple<Double, Double,Int>>): Array<Pair<Triple<Double,Double,Int>, Triple<Double,Double,Int>>> {
	var C = Array(A.size, {Pair(Triple(0.0, 0.0, 0), Triple(0.0, 0.0, 0))})
	for (i in 0 until A.size-1) {
		C[i] = Pair(A[i], A[i+1])
	}
	C[A.size-1] = Pair(A[A.size-1], A[0])
	return C
}


