# Naive_and_Karatsuba

The Multiply.java file has used two approaches, the naive approach and the Karatsuba algorithm, for multiplying two input numbers.
Afterwards, it stores the result and number of operations in an array for each sample inputs of different size.

Please refer to the karatsuba.xls file for sample outputs. 

Finally, it comes to a conclusion that the number of operations the naive method takes is significantly larger than the number of operations requires for the karatsuba algorithm, 
as the inputs' size increases.
This makes sense since the running time for the naive method is O(n^2), whereas the running time for the karatsuba algorithm is O(n^(log_2 (3)) = O(n^1.585). 
