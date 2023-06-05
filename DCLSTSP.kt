/* Función: obtenerMaximoArreglo
 * Descripción: Recibe un arreglo A: Array<Double> y retorna el elemento máximo de dicho arreglo
 * Precondición: A.size >= 1
 * Postondición: (\forall int i: 0 <= i < A.size, \result >= A[i]) && (\exists int i; 0 <= i && i < A.size; \result == A[i])
 */ 
fun obtenerMaximoArreglo(A: Array<Double>): Double {
	var n = A.size
	var maximo = A[0]
	for (i in 1 until n) {
		if (A[i] > maximo) {
			maximo = A[i]
		}
	}
	return maximo
}

/* Función: obtenerMinimoArreglo
 * Descripción: Recibe un arreglo A: Array<Double> y retorna el elemento mínimo de dicho arreglo
 * Precondición: A.size >= 1
 * Postondición: (\forall int i: 0 <= i < A.size, \result <= A[i]) && (\exists int i; 0 <= i && i < A.size; \result == A[i])
 */ 
fun obtenerMinimoArreglo(A: Array<Double>): Double {
	var n = A.size
	var minimo = A[0]
	for (i in 1 until n) {
		if (A[i] < minimo) {
			minimo = A[i]
		}
	}
	return minimo
}

/* Función: obtenerRectangulo
 * Descripción: Recibe un arreglo de coordenadas cartesianas P: Array<Pair<Double,Double>> 
 * y retorna un arreglo Array<Pair<Double,Double>> que muestra las coordenadas de un rectángulo que contiene
 * todos los puntos del arreglo P. Dichas coordenadas se encuentran en el siguiente orden:
 * esquina inferior izquierda, esquina inferior derecha, esquina superior derecha, esquina superior izquierda
 * Precondición: P.size >= 1
 * Postondición: (\forall int i: 0 <= i < P.size, \result[0].first <= P[i].first <= \result[2].first && \result[0].second <= P[i].second <= \result[2].second) 
 */ 
fun obtenerRectangulo (P: Array<Pair<Double,Double>>): Array<Pair<Double,Double>> {
	var n = P.size
	var coordenadasX = Array(n, {0.0})
	var coordenadasY = Array(n, {0.0})
	for (i in 0 until n) {
		coordenadasX[i] = P[i].first
		coordenadasY[i] = P[i].second
	}
	var minimoX = obtenerMinimoArreglo(coordenadasX)
	var maximoX = obtenerMaximoArreglo(coordenadasX)
	var minimoY = obtenerMinimoArreglo(coordenadasY)
	var maximoY = obtenerMaximoArreglo(coordenadasY)
	var coordenadas00 = Pair(minimoX,minimoY)
	var coordenadas10 = Pair(maximoX, minimoY)
	var coordenadas01 = Pair(minimoX, maximoY)
	var coordenadas11 = Pair(maximoX, maximoY)
	var coordenadasRectangulo = arrayOf(coordenadas00, coordenadas10, coordenadas11, coordenadas01)
	return coordenadasRectangulo
}

/* Función: obtenerDimensionesRectangulo
 * Descripción: Recibe un arreglo R: Array<Pair<Double,Double>> con las coordenadas cartesianas de un rectángulo y 
 * retorna las dimensiones de sus lados paralelos al eje X y al eje Y. Dichas coordenadas deben estar en el siguiente orden:
 * esquina inferior izquierda, esquina inferior derecha, esquina superior derecha, esquina superior izquierda
 * Precondición: P.size >= 0 && P[0].first <= P[2].first && P[0].second <= P[2].second
 * Postondición: \result.first >= 0 && \result.second >= 0
 */ 
fun obtenerDimensionesRectangulo(R: Array<Pair<Double,Double>>) : Pair<Double,Double> {
	var dimensionesX = R[2].first - R[0].first
	var dimensionesY = R[2].second - R[0].second
	return Pair(dimensionesX, dimensionesY)
}

/* Función: swap
 * Descripción: Recibe un arreglo P: Array<Pair<Double,Double>> y dos enteros i,j. Esta función intercambia las posiciones A[i] y A[j]
 * Precondición: 0 <= i,j < P.size
 * Postondición: P[i] == \old(P[j]) && P[j] == \old(P[i])
 */ 
fun swap (P: Array<Pair<Double,Double>>, i: Int, j: Int) {
    var temp = P[i]
    P[i] = P[j]
    P[j] = temp
}

/* Función: quicksortTSP
 * Descripción: Recibe un arreglo P: Array<Pair<Double,Double>> de coordenadas, dos enteros p y r que identifican el subarreglo
 * P[p...r] que se desea ordenar, y un String que identifica respecto a qué coordenada se debe ordenar el arreglo. 
 * Este procedimiento ordena los elementos de P de acuerdo a las coordenadas especificadas por el String coordenada, 
 * utilizando el algoritmo quicksort clásico
 * Precondición: true
 * Postondición: (\forall int i; 0 <= i && i < P.size-1; P[i].first <= P[i+1].first) || (\forall int i; 0 <= i && i < P.size-1; P[i].second <= P[i+1].second)
 */ 
fun quicksortTSP(P: Array<Pair<Double,Double>>, p: Int, r: Int, coordenada: String) {
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
 * Descripción: Recibe un arreglo P: Array<Pair<Double,Double>> de coordenadas y dos enteros p y r que identifican el subarreglo
 * P[p...r] sobre el cual se producirá una partición. Esta función genera una partición del subarreglo P[p...r] 
 * tal que las coordenadas X de los elementos del subarreglo P[p...q-1] sean menores que P[q].first y las coordenadas X 
 * de los elementos del subarreglo P[q+1...r] sean mayores que P[q].first. La función retorna q.
 * Precondición: 0 <= p < r < P.size
 * Postondición: p <= q <= r && (\forall int i; p <= i && i < q; P[i].first <= P[q].first) && (\forall int i; q+1 <= i && i <= r; P[i].first => P[q].first)
 */  
fun particionCoordenadaX(P: Array<Pair<Double,Double>>, p: Int, r: Int): Int {
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
 * Descripción: Recibe un arreglo P: Array<Pair<Double,Double>> de coordenadas y dos enteros p y r que identifican el subarreglo
 * P[p...r] sobre el cual se producirá una partición. Esta función genera una partición del subarreglo P[p...r] 
 * tal que las coordenadas Y de los elementos del subarreglo P[p...q-1] sean menores que P[q].second y las coordenadas Y
 * de los elementos del subarreglo P[q+1...r] sean mayores que P[q].second. La función retorna q.
 * Precondición: 0 <= p < r < P.size
 * Postondición: p <= q <= r && (\forall int i; p <= i && i < q; P[i].second <= P[q].second) && (\forall int i; q+1 <= i && i <= r; P[i].second => P[q].second)
 */  
fun particionCoordenadaY(P: Array<Pair<Double,Double>>, p: Int, r: Int): Int {
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
 * Descripción: Recibe un arreglo P: Array<Pair<Double,Double>> de coordenadas y retorna true si dos coordenadas x coindicen, 
 * y false en caso contrario
 * Precondición: P.size >= 1
 * Postondición: true || false
 */ 
fun coincidenDosCoordenadasX(P: Array<Pair<Double,Double>>): Boolean {
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

/* Función: obtenerPuntoDeCorte
 * Descripción: Recibe un arreglo P: Array<Pair<Double,Double>> de coordenadas y un String que identifica un eje.
 * La función retorna un punto de corte para el arreglo P de acuerdo al eje especificado
 * Precondición: P.size >= 1
 * Postondición: (\exists int i; 0 <= i && i < P.size; P[i] == \result)
 */ 
fun obtenerPuntoDeCorte(P: Array<Pair<Double,Double>>, ejeDeCorte: String): Pair<Double,Double> {
	var n = P.size
	var pos = n/2
	if(n%2 == 0) {
		pos = pos - 1
	}
	var coindicenDosX = coincidenDosCoordenadasX(P)
	if (ejeDeCorte == "X" || ejeDeCorte == "x") {
		if (coindicenDosX == true) {
			quicksortTSP(P,0,n-1,"Y")
		}
		else {
			quicksortTSP(P,0,n-1,"X")
		}
	} else {
		if (coindicenDosX == true) {
			quicksortTSP(P,0,n-1,"X")
		}
		else {
			quicksortTSP(P,0,n-1,"Y")
		}
	}
	return P[pos]
}

// PARA PROBAR LOS ALGORITMOS

/* Función:
 * Descripción: 
 * Precondición:
 * Postondición:
 */ 

fun imprimirArregloCoordenadas(A: Array<Pair<Double,Double>>) {
	var n = A.size 
	print("[ ")
	for (i in 0 until (n-1)) {
		var xi = A[i].first
		var yi = A[i].second
		print("( ${xi} , ${yi} ) , ")
	}
	var xi = A[n-1].first
	var yi = A[n-1].second
	println("( ${xi} , ${yi} ) ] ")
}

fun main(args: Array<String>) {
	var n = args[0].toInt() // Tamaño del arreglo de coordenadas
	var P = Array(n, {Pair(0.0,0.0)}) // Se inicializa el arreglo de coordenadas
	var k = 1
	// Con el siguiente for se asignan los valores del arreglo de coordenadas dados por el usuario
	for (i in 0 until n) { 
		P[i] = Pair(args[k].toDouble(), args[k+1].toDouble())
		k = k+2
	}
	println("El arreglo de coordenadas es")
	imprimirArregloCoordenadas(P)
	println("Y el rectángulo que las contiene vienen dado por las siguientes coordenadas")
	var R = obtenerRectangulo(P)
	imprimirArregloCoordenadas(R)
	println("El punto de corte del rectángulo, para el ejeDeCorte X, es")
	var puntoDeCorte = obtenerPuntoDeCorte(P, "X")
	println("( ${puntoDeCorte.first} , ${puntoDeCorte.second} )")
	println("Con el arreglo ordenado como:")
	imprimirArregloCoordenadas(P)
	println("Y para el ejeDeCorte Y, es")
	puntoDeCorte = obtenerPuntoDeCorte(P, "Y")
	println("( ${puntoDeCorte.first} , ${puntoDeCorte.second} )")
	println("Con el arreglo ordenado como:")
	imprimirArregloCoordenadas(P)
}

// EXTRAS  

/* Función: quicksortCoordenadaYTSP
 * Descripción: Recibe un arreglo P: Array<Pair<Double,Double>> de coordenadas, y dos enteros p y r que identifican el subarreglo
 * P[p...r] que se desea ordenar. Esta función ordena los elementos de P de acuerdo a las coordenadas Y, 
 * utilizando el algoritmo quicksort clásico
 * Precondición: true
 * Postondición: (\forall int i; 0 <= i && i < P.size-1; P[i].second <= P[i+1].second)
 */ 
fun quicksortCoordenadaYTSP(P: Array<Pair<Double,Double>>, p: Int, r: Int) {
	if (p < r) {
		var q = particionCoordenadaY(P,p,r)
		quicksortCoordenadaYTSP(P, p, q-1)
		quicksortCoordenadaYTSP(P, q+1, r)
	}
} 
