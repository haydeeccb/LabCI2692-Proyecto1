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
fun divideAndConquerTSP(A: Array<Triple<Double, Double, Int>>):  Array<Triple<Double, Double, Int>> {
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
    
}

/* Función: cicloUnaCiudad
 * Descripción: De entrada recibe un arreglo de coordenadas de tamaño uno y retorna un arreglo de coordenadas de tamaño dos.
 * Este arreglo representa un tour, una solución válida del TSP.
 * Precondición: A.size == 1  
 * Postcondición: (\result.size == 2) && (\forall int i; 0 <= i < \result.size; \result[i] == A[0]) 
*/
fun cicloUnaCiudad(A: Array<Triple<Double, Double, Int>>):  Array<Triple<Double, Double, Int>> {
    var B = arrayOf(A[0], A[0])
    return B
}

/* Función: cicloDosCiudades
 * Descripción: De entrada recibe un arreglo de coordenadas de tamaño dos y retorna un arreglo de coordenadas de tamaño tres.
 * Arreglo que representa un tour, una solución válida del TSP.
 * Precondición: A.size == 2 
 * Postcondición: (\result.size == 3) && (\forall int i; 0 <= i < \result.size; (\exists int j: 0 <= j < A.size; \result[i] == A[j] )) 
*/
fun cicloDosCiudades(A: Array<Triple<Double, Double, Int>>):  Array<Triple<Double, Double, Int>> {
    return permutaciones(A)
}

/* Función: cicloTresCiudades
 * Descripción: De entrada recibe un arreglo de coordenadas de tamaño tres y retorna un arreglo de coordenadas de tamaño cuatro.
 * Arreglo que representa un tour, una solución válida del TSP.
 * Precondición: A.size == 3  
 * Postcondición: (\result.size == 4) && (\forall int i; 0 <= i < \result.size; (\exists int j: 0 <= j < A.size; \result[i] == A[j] )) 
*/
fun cicloTresCiudades(A:  Array<Triple<Double, Double, Int>>): Array<Triple<Double, Double, Int>> {
    return permutaciones(A)
}

/* Función: permutaciones
 * Descripción: Como entrada recibe un arreglo de coordenadas de un tamaño mayor que 3 y
 * retorna un arreglo de coordenadas que es una permutación del arreglo de entrada.
 * Además se debe cumplir ques su ultimo elemento sea igual al primer elemento(\result[0] == \result[A.size]).
 * Precondición: A.size > 3  
 * Postcondición: (\result.size == A.size + 1) && (\forall int i; 0 <= i < \result.size; (\exists int j: 0 <= j < A.size; \result[i] == A[j] )) 
*/
fun permutaciones(A: Array<Triple<Double, Double, Int>>): Array<Triple<Double, Double, Int>> {
    var B = Array(A.size){i -> i}
    var C: Array<Triple<Double, Double, Int>> = Array(A.size+1){Triple(0.0,0.0, 0)}
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
 * Descripción: Recibe como entreda dos arreglos que contienen las coordenadas de las ciudades(Array<Pair<Double,Double>>). 
 * La funcion busca unir ambos arreglos(cada arreglo es una solucion, es decir, un tour) para contruir uno nuevo,
 * este elemento de salida debe cumple dos requisitos, tener la distancia mas corta a recorrer todas las ciudades y cumplir las especificaciones de un tour. 
 * Precondición: A.size >= 0  && B.size >= 0  
 * Postcondición: (\result.size == (A.size + B.size -1 )) && 
 * (\forall int i; 0 <= i < \result.size; (\exists int j: 0 <= j < A.size; \result[i] == A[j] ) || (\exists int j: 0 <= j < B.size; \result[i] == B[j] )) 
*/
fun combinarCiclos(A: Array<Triple<Double, Double, Int>>, B: Array<Triple<Double, Double, Int>>): Array<Triple<Double, Double, Int>> {
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
        var a = A[i]
        var b: Triple<Double, Double, Int>
        if (i == A.size-1) {
            b = A[0]
        } else {
            b = A[i+1]
        }
        var dOLD1 = distancia(a, b)
        for (j in 0 until B.size-1) {
            var c = B[j]
            var d: Triple<Double, Double, Int>
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
fun remover(A: Array<Triple<Double, Double, Int>>, x: Pair<Triple<Double, Double, Int>, Triple<Double, Double, Int>>): Array<Triple<Double, Double, Int>> {
    if (A.size == 3) {
        var B = Array(0){Triple(0.0,0.0, 0)}
        return B
    } else {
        var B = Array(A.size-3){Triple(0.0,0.0, 0)}
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
}

/* Función: nuevoTour
 * Descripción: Recibe como entrada un arreglo de pares(Array<Pair<Double, Double>>) que contiene las ciudades, este arreglo es una de las particiones del algoritmo combinarCiclos,
 * ademas se ingresa un valor que sera el rango a estudiar(IntRange).
 * La funcion crea un nuevo arreglo de pares, que contiene las cooredenadas de las ciudades. Para crear este arreglo, se eligen
 * los elementos de A que se encuentran en el rango indicado.
 * Precondición: A.size > 0 && rango > 0
 * Postcondición: \result.size == (A.size-3) && (\forall int i: 0 <= i < \result.size; (\exists int j: 0 <= j < A.size; \result[i] == A[j] )) 
 * 
*/
fun nuevoTour(A: Array<Triple<Double, Double, Int>>, rango: IntRange): Array<Triple<Double, Double, Int>> {
    var i = 0
    var B = Array(A.size-3){Triple(0.0,0.0,0)}
    for (par in rango) {
        B[i] = A[par]
        i++
    }
    return B
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
fun tour(A: Array<Triple<Double, Double, Int>>, B: Array<Triple<Double, Double, Int>>, x: Pair<Triple<Double, Double, Int>, Triple<Double, Double, Int>>, y: Pair<Triple<Double, Double, Int>, Triple<Double, Double, Int>>): Array<Triple<Double, Double, Int>> {
    var Ciclo3 = Array(A.size+B.size+4+1){Triple(0.0,0.0, 0)}
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

/* Función: union
 * Descripción: Recibe como entrada un arreglo de pares(Array<Pair<Double, Double>>) que contiene las pares con las coordenadas de las ciudades.
 * Recibe otro arreglo (Array<Pair<Double, Double>>) que guardara los elementos de A que se deben unir, ademas tambien ingresa un valor i (Int) que indica
 * desde que posicion se debe empezar a guardar los elementos en el Ciclo.
 * Precondición: A.size > 0 && i >= 0 && Ciclo.size > 0
 * Postcondición: true 
 * 
*/
fun union(A: Array<Triple<Double, Double, Int>>, Ciclo: Array<Triple<Double, Double, Int>>, i: Int ) {
    var j = i
    for (par in A) {
        Ciclo[j] = par
        j++
    }
}

/* Función: intecambio
 * Descripción: Recibe como entrada un arreglo de pares(Array<Pair<Double, Double>>) que contiene los elementos del tour a construir,
 * ademas entra un par que contiene dos ciudades(Pair<Pair<Double, Double>, Pair<Double, Double>>) y un numero i que indica
 * la posicion de la ultima ciudad que ha sido agregada
 * La funcion tomara la coordenada de la ultima ciudad agregada a Ciclo3, sacara las distancia entre esa ciudad y las ciudades que hay dentro del par x.
 * Una vez que haya obtenido esas distancias, las compara y determina cual es mayor. 
 * Luego se procede en incluir en el arreglo la ciudad que tenga menor distancia.
 * Precondición: Ciclo3.size > 0 && i >= 0 
 * Postcondición: true
 * 
*/
fun intercambio(Ciclo3: Array<Triple<Double, Double, Int>>, x: Pair<Triple<Double, Double, Int>, Triple<Double, Double, Int>>, i: Int) {
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