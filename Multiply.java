import java.util.*;
import java.io.*;

public class Multiply{

    private static int randomInt(int size) {
        Random rand = new Random();
        int maxval = (1 << size) - 1;
        return rand.nextInt(maxval + 1);
    }
    
    public static int[] naive(int size, int x, int y) {
        
        int naiveResult[] = new int[2];                  // Array to return.
        
        if (size == 1)                                   // Base case
        {
            naiveResult[0] = x * y;
            naiveResult[1] = 1;
        }

        else                                            // Inductive case
        {
            int m = (size + 1) >> 1;                    // Taking the upper bound of size (n) divided by 2.

            int a = x >> m;                             // Extract the upper bits of the input x 
            int b = x & ((1 << m) - 1);                 // Getting the lower bits of the input x
            
            int c = y >> m;
            int d = y & ((1 << m) - 1);                 // We do the same thing for input y as we did for input x.
            
            int e = naive(m, a, c)[0];                  // upper bits multiplication
            int f = naive(m, b, d)[0];                  // lower bits multiplication 
            int g = naive(m, b, c)[0];
            int h = naive(m, a, d)[0];
            
            naiveResult[0] = ((e << (2 * m)) + ((g + h) << m) + f);
            naiveResult[1] = ((3 * m) + (naive(m, a, c)[1] + naive(m, b, d)[1] + naive(m, b, c)[1] + naive(m, a, d)[1]));
        }
        return naiveResult;        
    }

    public static int[] karatsuba(int size, int x, int y) {
        
        int kresult[] = new int[2];                     // Our output array

        if (size == 1)                                  // Base case
        {
            kresult[0] = x * y;
            kresult[1] = 1;
        }

        else                                            // Inductive case: referring from the pseudo codes in slides
        {
            int m = (size + 1) >> 1;               
            
            int a = x >> m;
            int b = x & ((1 << m) - 1); 
            
            int c = y >> m;
            int d = y & ((1 << m) - 1); 
            
            int e = karatsuba(m, a, c)[0];
            int f = karatsuba(m, b, d)[0];
            int g = karatsuba(m, a - b, c - d)[0];

            kresult[0] = ((e << (2 * m)) + ((e + f - g) << m) + f);
            kresult[1] = ((6 * m) + (karatsuba(m, a, c)[1]) + (karatsuba(m, b, d)[1]) + (karatsuba(m, a - b, c - d)[1]));
        }
        return kresult;    
    }
    
    public static void main(String[] args){

        try{
            int maxRound = 20;
            int maxIntBitSize = 16;
            for (int size=1; size<=maxIntBitSize; size++) {
                int sumOpNaive = 0;
                int sumOpKaratsuba = 0;
                for (int round=0; round<maxRound; round++) {
                    int x = randomInt(size);
                    int y = randomInt(size);
                    int[] resNaive = naive(size,x,y);
                    int[] resKaratsuba = karatsuba(size,x,y);
            
                    if (resNaive[0] != resKaratsuba[0]) {
                        throw new Exception("Return values do not match! (x=" + x + "; y=" + y + "; Naive=" + resNaive[0] + "; Karatsuba=" + resKaratsuba[0] + ")");
                    }
                    
                    if (resNaive[0] != (x*y)) {
                        int myproduct = x*y;
                        throw new Exception("Evaluation is wrong! (x=" + x + "; y=" + y + "; Your result=" + resNaive[0] + "; True value=" + myproduct + ")");
                    }
                    
                    sumOpNaive += resNaive[1];
                    sumOpKaratsuba += resKaratsuba[1];
                }
                int avgOpNaive = sumOpNaive / maxRound;
                int avgOpKaratsuba = sumOpKaratsuba / maxRound;
                System.out.println(size + "," + avgOpNaive + "," + avgOpKaratsuba);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

   } 
}
