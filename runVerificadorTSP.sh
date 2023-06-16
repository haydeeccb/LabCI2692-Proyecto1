#!/usr/bin/bash

#Creación de Arreglo que contendra las ciudades
A=()
i=0
p=0

#Bucle que verificara las lineas con las coordenadas de las ciudades y la guardara en A
while IFS= read -r line
do  
    if [ $i -gt 5 ]; then
        if [[ $line == *"EOF"* ]]; then
            continue
        else 
            A+=($line)
            p=$(($p+1))
        fi
    else 
        i=$(($i+1))
    fi
done < $1*

#Creación del Arreglo con la solución
B=()
j=0
q=0

#Bucle que verificara las lineas con la solución y la guardara en B
while IFS= read -r line
do  
    if [ $j -gt 3 ]; then
        B+=($line)
        q=$(($q+1))
    else 
        j=$(($j+1))
    fi
done < $2*

#Codigo que compila el archivo
kotlinc VerificadorTSP.kt -include-runtime -d test.jar
#Codigo que corre el archivo como un arreglo, que es la unión de A y B
java -jar test.jar ${A[@]} ${B[@]}

exit 0
