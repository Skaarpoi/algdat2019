package no.oslomet.cs.algdat.Forelesning;

public class Week1 {
    public static void main(String[] args) {

        int[] values = {9, 17, 11, 4, 8, 32};
        int max_value = findMaximum(values);

        System.out.println(values);

    }

    /** finner maksimum i et array med tall **/
    static int findMaximum(int[] values){
        int maxValue = values[0];
        for (int i=0; i<values.length; ++i) {
            int value = values[i];
            if (value > maxValue){
                maxValue = value;
            }
        }
        return maxValue;
    }
}
