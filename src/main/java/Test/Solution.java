package Test;

import java.util.LinkedHashMap;

/*
ReentrantReadWriteLock
*/

public class Solution{

    public static void main(String [] args){
        Solution solution = new Solution();
        int[] mas = {13, 3, 8, 1, 6, 10, 3, 7, 4};
        int [] a = solution.Sort(mas);
        for (int i = 0; i < a.length; i++)
            System.out.print(a[i] + " ");
    }


    public int [] Sort(int [] mas){

        int [] left = new int[Math.round(mas.length / 2)];
        int [] right = new int[mas.length - Math.round(mas.length / 2)];
        int [] result = new int[left.length + right.length];

        if ( mas.length <= 1 ) return mas;

        if (mas.length > 1) {
            int [] halfleft = new int[Math.round(mas.length / 2)];
            int [] halfright  = new int[mas.length - Math.round(mas.length / 2)];

            for (int i = 0; i < halfleft.length;  i ++){
                halfleft [i] = mas [i];
            }

            for (int i = 0; i < halfright.length; i++ ){
                halfright [i] = mas [i + left.length];
            }
            left = Sort(halfleft);
            right = Sort(halfright);
            result = summa(left, right);
        }
        return result;
    }

    public int [] summa (int [] src1, int [] src2){
        int a = 0;
        int b = 0;
        int [] result = new int[src1.length + src2.length];

        for (int i = 0; i < src1.length + src2.length; i++){
            if (a == src1.length){
                result [i] = src2 [b];
                b++;
            } else if (b == src2.length){
                result [i] = src1 [a];
                a++;
            } else if (src1[a] > src2 [b]){
                result [i] = src2 [b];
                b++;
            } else {
                result [i] = src1 [a];
                a++;
            }
        }
        return result;
    }
}



