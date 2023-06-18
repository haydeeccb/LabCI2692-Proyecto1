// Librerias
import kotlin.math.roundToInt
import kotlin.math.min

/* ALGORITMOS 1 y 3 DEL PROBLEMA DEL TSP
 * Con sus respectivas funciones auxiliares
*/

/* Función: divideAndConquerTSP
 * Descripción: Recibe de entrada un arreglo de pares(Array<Pair(Double, Double)>) y retorna un arreglo de pares con una solucion válida del TSP
 * Precondición: A.size > 0 && 
 * Postcondición: \result.size == A.size + 1 && (\forall int i; 0 <= i < \result.size; (\exists int j: 0 <= j < A.size; \result[i] == A[j] )) 
*/
/*fun divideAndConquerTSP(A: Array<Triple<Double, Double, Int>>):  Array<Pair<Triple<Double, Double, Int>, Triple<Double, Double, Int>>> {
    var n = A.size
    if (n == 1) {
        return cicloUnaCiudad(A)
    } else if (n ==2) {
        return cicloDosCiudades(A)
    } else if (n == 3) {
        return cicloTresCiudades(A)
    } else {
        var (P,Q) = obtenerParticiones(A)
        var C1 = divideAndConquerTSP(P)
        var C2 = divideAndConquerTSP(Q)
        return combinarCiclos(C1, C2)
    }
    
}*/

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
    println(".....Agregar....")
    println(newC1)
    println(newC2)
    println(".......")
    println(".....Eliminar....")
    println(dC1)
    println(dC2)
    println(".......")

    //eliminar
    var particion1 = remover(A, dC1)
    var particion2 = remover(B, dC2)
    println("..Particiones")
    println(particion1.contentToString())
    println(".......")
    println(particion2.contentToString())
    println("TOUR")
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
        print(B.contentToString())
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


fun main() {
    var C1 = arrayOf(Pair(Triple(1.0,1.0,1), Triple(0.0,9.0,2)),Pair(Triple(0.0,9.0,2),Triple(3.0,8.0,3)), Pair(Triple(3.0,8.0,3), Triple(4.0,0.0,4)),
    Pair(Triple(4.0,0.0,4), Triple(1.0,1.0,1)))
    var C2 = arrayOf(Pair(Triple(5.0,1.0,9), Triple(6.0,6.0,6)), Pair(Triple(6.0,6.0,6), Triple(10.0,7.0,7)), Pair(Triple(10.0,7.0,7), Triple(7.0,3.0,8)), 
    Pair(Triple(7.0,3.0,8), Triple(5.0,1.0,9)))
    var C3 = combinarCiclos(C1, C2)
    println(C1.size)
    println(C2.size)
    for (i in 0 until C3.size) {
        println(C3[i])
    }   
}