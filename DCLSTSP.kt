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
fun divideAndConquerTSP(A: Array<Pair<Double, Double>>):  Array<Pair<Double, Double>> {
    var n = A.size
    when (n) {
        1 -> return cicloUnaCiudad(A)
        2 -> return cicloDosCiudades(A)
        3 -> return cicloTresCiudades(A)
    } 
    var (P,Q) = obtenerParticiones(A)
    var C1 = divideAndConquerTSP(P)
    var C2 = divideAndConquerTSP(Q)
    return combinarCiclos(C1, C2)
}

/* Función: cicloUnaCiudad
 * Descripción: De entrada recibe un arreglo de coordenadas de tamaño uno y retorna un arreglo de coordenadas de tamaño dos.
 * Este arreglo representa un tour, una solución válida del TSP.
 * Precondición: A.size == 1  
 * Postcondición: (\result.size == 2) && (\forall int i; 0 <= i < \result.size; \result[i] == A[0]) 
*/
fun cicloUnaCiudad(A: Array<Pair<Double, Double>>):  Array<Pair<Double, Double>> {
    var B = arrayOf(A[0], A[0])
    return B
}

/* Función: cicloDosCiudades
 * Descripción: De entrada recibe un arreglo de coordenadas de tamaño dos y retorna un arreglo de coordenadas de tamaño tres.
 * Arreglo que representa un tour, una solución válida del TSP.
 * Precondición: A.size == 2 
 * Postcondición: (\result.size == 3) && (\forall int i; 0 <= i < \result.size; (\exists int j: 0 <= j < A.size; \result[i] == A[j] )) 
*/
fun cicloDosCiudades(A: Array<Pair<Double, Double>>):  Array<Pair<Double, Double>> {
    return permutaciones(A)
}

/* Función: cicloTresCiudades
 * Descripción: De entrada recibe un arreglo de coordenadas de tamaño tres y retorna un arreglo de coordenadas de tamaño cuatro.
 * Arreglo que representa un tour, una solución válida del TSP.
 * Precondición: A.size == 3  
 * Postcondición: (\result.size == 4) && (\forall int i; 0 <= i < \result.size; (\exists int j: 0 <= j < A.size; \result[i] == A[j] )) 
*/
fun cicloTresCiudades(A:  Array<Pair<Double, Double>>): Array<Pair<Double, Double>> {
    return permutaciones(A)
}

/* Función: permutaciones
 * Descripción: Como entrada recibe un arreglo de coordenadas de un tamaño mayor que 3 y
 * retorna un arreglo de coordenadas que es una permutación del arreglo de entrada.
 * Además se debe cumplir ques su ultimo elemento sea igual al primer elemento(\result[0] == \result[A.size]).
 * Precondición: A.size > 3  
 * Postcondición: (\result.size == A.size + 1) && (\forall int i; 0 <= i < \result.size; (\exists int j: 0 <= j < A.size; \result[i] == A[j] )) 
*/
fun permutaciones(A: Array<Pair<Double, Double>>): Array<Pair<Double, Double>> {
    var B = Array(A.size){i -> i}
    var C: Array<Pair<Double, Double>> = Array(A.size+1){Pair(0.0,0.0)}
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

/* Función: combinarCiclos
 * Descripción: Como entrada recibe un arreglo de coordenadas de un tamaño mayor que 3 y
 * retorna un arreglo de coordenadas que es una permutación del arreglo de entrada.
 * Además se debe cumplir ques su ultimo elemento sea igual al primer elemento(\result[0] == \result[A.size]).
 * Precondición: A.size > 3  
 * Postcondición: (\result.size == A.size + 1) && (\forall int i; 0 <= i < \result.size; (\exists int j: 0 <= j < A.size; \result[i] == A[j] )) 
*/
fun combinarCiclos(A: Array<Pair<Double, Double>>, B: Array<Pair<Double, Double>>): Array<Pair<Double, Double>> {
    when (A.size) {
        0 -> return B
    }
    when (B.size) {
        0 -> return A
    }
    var Ciclo3 = Array(B.size+A.size -2){Pair(0.0,0.0)}
    var minG = Double.MAX_VALUE;
    for (i in 0 until A.size-1) {
        var a = A[i]
        var b: Pair<Double, Double>
        if (i == A.size-1) {
            b = A[0]
        } else {
            b = A[i+1]
        }
        var dOLD1 = distancia(a, b)
        for (j in 0 until B.size-1) {
            var c = B[j]
            var d: Pair<Double, Double>
            if (j == B.size-1) {
                d = B[0]
            } else {
                d = B[j+1]
            }
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
                    var newC1 = Pair(a, c)
                    // agregar lados
                    var newC2 = Pair(b, d)
                } else {
                    //agregar lados
                    var newC1 = Pair(a, d)
                    // agregar lados
                    var newC2 = Pair(b, c)
                }
                //elimnar lados
                var dC1 = Pair (a, b)
                //elimanr lados
                var dC2 = Pair(c, d)
            }
        }
    }
    //remover
    //remover
    //agregar
    return Ciclo3
}

fun distancia(a: Pair<Double, Double> , b: Pair<Double, Double>): Int {
    var xd: Double = b.first - a.first
    var yd: Double = b.second - a.second 
    var dxy = Math.sqrt(xd*xd+yd*yd).roundToInt()
    return dxy
}

fun distanciaGanada(dOLD1: Int, dOLD2: Int, dNEW1: Int, dNEW2: Int): Int {
    return (dNEW1 + dNEW2) - (dOLD1 + dOLD2)
}

fun remover(A: Array<Pair<Double, Double>>, x: Pair<Double, Double>): Array<Pair<Double, Double>> {
    if (A.size-3 == 0) {
        var B = Array(0){Pair(0,0)}
        // emptyArray<Pair<Double, Double>>()
        return B
    }
    var newCicle = Array(A.size-3){Pair(0.0,0.0)}
    var i = 0
    for (par in A) {
        if (par != x.first || par != x.second ) {
            if (i == newCicle.size-1) {
                break
            } else {
                newCicle[i] = par
                i++ 
            }
        }
    }
    return newCicle
}

fun union(A: Array<Pair<Double, Double>>, B: Array<Pair<Double, Double>>, x: Pair<Pair<Double, Double>, Pair<Double, Double>>, y: Pair<Pair<Double, Double>, Pair<Double, Double>>): Array<Pair<Double, Double>> {
    var Ciclo3 = Array(A.size+B.size+4+1){Pair(0.0,0.0)}
    var i = 0
    for (par in A) {
        Ciclo3[i] = par
        i++
    }
    var g1 = distancia(Ciclo3[i], x.first)
    var g2 = distancia(Ciclo3[i], x.second)
    if (g1 < g2) {
        i++
        Ciclo3[i] = x.first 
        i++
        Ciclo3[i] = x.second
        i++
    } else {
        i++
        Ciclo3[i] = x.second 
        i++
        Ciclo3[i] = x.first
        i++
    }
    
    for (par in B) {
        Ciclo3[i] = par
        i++
    }

    g1 = distancia(Ciclo3[i], y.first)
    g2 = distancia(Ciclo3[i], y.second)
    if (g1 < g2) {
        i++
        Ciclo3[i] =  y.first
        i++
        Ciclo3[i] =  y.second
        i++
    } else {
        i++
        Ciclo3[i] =  y.second
        i++
        Ciclo3[i] =  y.first
        i++
    }
}