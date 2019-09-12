package no.oslomet.cs.algdat;

import java.util.Arrays;

public class Week3 {

    public static int findMax(int[] values, int left, int right){
        int max_value = values[left];
        int max_index = left;
        for (int i=left+1; i<=right; ++i){
            if(values[i] > max_value) {
                max_value = values[i];
                max_index = i;
            }

        }
        return max_index;

    }

    public static void selectionSort(int[] values){
        for (int i=0; i<values.length-1; ++i){
            int max_index = findMax(values, i , values.length-1);
            System.out.println(i + " - " + (values.length-1));
            System.out.println();

            //Bytt elementer
            int temp = values[max_index];
            values[max_index] = values [i];
            values[i] = temp;

            System.out.println(Arrays.toString(values));

        }
    }
    public static int binarySearch(int[] values, int target){
        int left = 0;
        int right = values.length-1;
        while(left < right){
            System.out.println("Søker i " + left + " - " + right);
            int mid = (left + right)/2;
            if(target < values[mid]){
                right = mid - 1;
            }
            else if(target > values[mid]){
                 left = mid + 1;
            }
            return mid;
        }

        return left;
    }
}

