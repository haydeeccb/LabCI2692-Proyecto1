
/* Función: ladosAPuntos
 * Descripción: Esta función recibe un tourLados: Array<Pair<Triple<Double,Double,Int>, Triple<Double,Double,Int>>>
 * que corresponde a un ciclo que contiene los lados del tour de las ciudades. Dichos lados vienen dados por un pair
 * que contiene primero las coordenadas de la ciudad de partida y luego las coordenadas de la ciudad de llegada.
 * La función retorna un tour que contiene las ciudades a visitar en orden. Dicho resultado contiene las coordenadas de 
 * las ciudades de partida (en el mismo orden del tour de lados). 
 * Solo se toman las ciudades de partida pues al retornar las ciudades en orden se tiene que 
 * la ciudad número i+1 es la ciudad de llegada para la ciudad de partida número i, con 0 <= i < numCiudades.
 * Además, se toma la ciudad de inicio del tourLados como la última ciudad del tour de puntos (termina donde comienza)
 * Precondición: tourLados.size >= 1
 * Postondición: \result.size >= 1
 */ 
fun ladosAPuntos(tourLados: Array<Pair<Triple<Double,Double,Int>, Triple<Double,Double,Int>>>): Array<Triple<Double,Double,Int>> {
	var numCiudades = tourLados.size
	var tourPuntos = Array(numCiudades+1, {Triple(0.0, 0.0, 0)})
	for (i in 0 until numCiudades) {
		tourPuntos[i] = tourLados[i].first 
	}
	tourPuntos[numCiudades] = tourLados[0].first
	return tourPuntos
}

/* Función: puntosALados
 * Descripción: Esta función recibe un tourPuntos: Array<Triple<Double,Double,Int>> que corresponde a un ciclo que contiene
 * las coordenadas de las ciudades del tour. Dichas ciudades deben estar en el orden en que se visitan 
 * y cada triple tiene como elementos: coordenada x, coordenada y, número de la ciudad.
 * La función retorna un tour Array<Pair<Triple<Double,Double,Int>, Triple<Double,Double,Int>>> que contiene 
 * los lados del tour. Dichos lados vienen dados por un pair que contiene primero las coordenadas de la ciudad 
 * de partida y luego las coordenadas de la ciudad de llegada.
 * Precondición: tourPuntos.size >= 2
 * Postondición: tourLados.size >= 1
 */ 
fun puntosALados(tourPuntos: Array<Triple<Double,Double,Int>>): Array<Pair<Triple<Double,Double,Int>, Triple<Double,Double,Int>>> {
	var numCiudades = tourPuntos.size - 1
	var tourLados = Array(numCiudades, {Pair(Triple(0.0, 0.0, 0), Triple(0.0, 0.0, 0))})
	for (i in 0 until numCiudades) {
		tourLados[i] = Pair(tourPuntos[i], tourPuntos[i+1])
	}
	return tourLados
}