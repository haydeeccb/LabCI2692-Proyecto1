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
/*fun divideAndConquerTSP(A: Array<Pair<Double, Double>>):  Array<Pair<Double, Double>> {
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
}*/

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
    var minG = Int.MAX_VALUE;
    var newC1: Pair<Pair<Double, Double>, Pair<Double, Double>> = Pair(Pair(0.0, 0.0), Pair(0.0, 0.0))
    var newC2: Pair<Pair<Double, Double>, Pair<Double, Double>> = Pair(Pair(0.0, 0.0), Pair(0.0, 0.0))
    var dC1: Pair<Pair<Double, Double>, Pair<Double, Double>> = Pair(Pair(0.0, 0.0), Pair(0.0, 0.0))
    var dC2: Pair<Pair<Double, Double>, Pair<Double, Double>> = Pair(Pair(0.0, 0.0), Pair(0.0, 0.0))
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
    println(particion2.contentToString())
    println("TOUR")
    //agregar
    var Ciclo3 = tour(particion1, particion2, newC1, newC2)
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

fun remover(A: Array<Pair<Double, Double>>, x: Pair<Pair<Double, Double>, Pair<Double, Double>>): Array<Pair<Double, Double>> {
    if (A.size == 3) {
        var B = Array(0){Pair(0.0,0.0)}
        return B
    } else {
        var B = Array(A.size-3){Pair(0.0,0.0)}
        var rango: IntRange
        if (A[1] == x.first) {
            rango = 3 until A.size
            var T = nuevoTour(A, rango)
            println("removedor1")
            println(T.contentToString())
            return T
        } else if (A[1] == x.second) {
            rango = 2 until A.size-1
            var T = nuevoTour(A, rango)
            println("removedor2")
            println(T.contentToString())
            return T
        } else if (A[A.size-2] == x.first) {
            rango = 1 until A.size-2
            var T = nuevoTour(A, rango)
            println("removedor3")
            println(T.contentToString())
            return T
        } else if (A[A.size-2] == x.second) {
            rango = 0 until A.size-3
            var T = nuevoTour(A, rango)
            println("removedor4")
            println(T.contentToString())
            return T
        } else {
            //var tamaño1 = (A.size-1 - A.indexOf(x.second))
            //var tamaño2 = (A.indexOf(x.first) - 1)
            //var particion1 = Array(tamaño1) {Pair(0.0, 0.0)}
            //var particion2 = Array(tamaño2) {Pair(0.0, 0.0)}
            var indicador = 0
            for (elementos in A.indexOf(x.second) until A.size ) {
                B[indicador] = A[elementos]
                indicador++
            }
            for (elementos in 1 until A.indexOf(x.first)) {
                B[indicador] = A[elementos]
                indicador++
            }
            print("removedor5")
            print(B.contentToString())
            return B
        }
    }
    /*if (A.size-3 == 0) {
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
    return newCicle*/
}

fun nuevoTour(A: Array<Pair<Double, Double>>, rango: IntRange): Array<Pair<Double, Double>> {
    var i = 0
    var B = Array(A.size-3){Pair(0.0,0.0)}
    for (par in rango) {
        B[i] = A[par]
        i++
    }
    return B
}


fun tour(A: Array<Pair<Double, Double>>, B: Array<Pair<Double, Double>>, x: Pair<Pair<Double, Double>, Pair<Double, Double>>, y: Pair<Pair<Double, Double>, Pair<Double, Double>>): Array<Pair<Double, Double>> {
    var Ciclo3 = Array(A.size+B.size+4+1){Pair(0.0,0.0)}
    union(A, Ciclo3, 0)
    println("Union 1")
    print(Ciclo3.contentToString())
    println(".....")
    var i = A.size-1
    intercambio(Ciclo3, x, i)
    println("Intercambio 1")
    print(Ciclo3.contentToString())
    println(".....")
    i = i+3
    union(B, Ciclo3, i)
    println("Union 2")
    print(Ciclo3.contentToString())
    println(".....")
    i = B.size-1+i
    intercambio(Ciclo3, y, i)
    println("Intercambio 2")
    print(Ciclo3.contentToString())
    println(".....")
    Ciclo3[Ciclo3.size-1] = Ciclo3[0]
    return Ciclo3
}

fun union(A: Array<Pair<Double, Double>>, Ciclo: Array<Pair<Double, Double>>, i: Int ) {
    var j = i
    for (par in A) {
        Ciclo[j] = par
        j++
    }
}

fun intercambio(Ciclo3: Array<Pair<Double, Double>>, x: Pair<Pair<Double, Double>, Pair<Double, Double>>, i: Int) {
    var j = i
    var g1 = distancia(Ciclo3[i], x.first)
    var g2 = distancia(Ciclo3[i], x.second)
    if (g1 < g2) {
        j++
        Ciclo3[j] = x.first 
        j++
        Ciclo3[j] = x.second
    } else {
        j++
        Ciclo3[j] = x.second 
        j++
        Ciclo3[j] = x.first
    }
}

fun main() {
    var C1 = arrayOf(Pair(1.0,1.0), Pair(0.0,9.0), Pair(3.0,8.0), Pair(4.0,0.0), Pair(1.0,1.0))
    var C2 = arrayOf(Pair(6.0,6.0), Pair(10.0,7.0), Pair(7.0,3.0),Pair(5.0,1.0), Pair(6.0,6.0))
    var C3 = combinarCiclos(C1, C2)
    println(C3.contentToString())
}