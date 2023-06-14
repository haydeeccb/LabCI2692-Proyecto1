#!/usr/bin/bash
A=()
i=0
p=0

while IFS= read -r line
do  
    if [ $i -gt 5 ]; then
        A+=($line)
        p=$(($p+1))
    else 
        i=$(($i+1))
    fi
done < $1*

B=()
j=0
q=0
while IFS= read -r line
do  
    if [ $j -gt 3 ]; then
        B+=($line)
        echo $line
        q=$(($q+1))
    else 
        j=$(($j+1))
    fi
done < $2*

i=${#A[@]}
echo ${#A[@]}
echo ${#A[0]}
echo ${#A[1]}
echo ${#A[2]}
echo ${A[0]}
echo ${A[1]}
echo ${A[2]}
echo $i
echo $p
echo "...."
echo ${#B[@]}
echo ${#B[0]}
echo ${#B[1]}
echo ${#B[2]}
echo ${B[0]}
echo ${B[1]}
echo ${B[2]}
echo $j
echo $q
exit 0
