package no.oslomet.cs.algdat;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class Week3Test {

    @org.junit.jupiter.api.Test
    void findMax() {
        int[] values = {1, 7, 2, 4, 6};


        int max_1 = Week3.findMax(values, 0, 4);
    }
    @Test
    void selectionSort() {
        int[]values = {1, 7, 2, 4, 6};

        Week3.selectionSort(values);
    }

    @Test
    void binarySearch() {
        int[] values = {1, 2, 3, 4, 5, 6, 7, 9, 17};

        int index_7 = Week3.binarySearch(values, 7);
        assertEquals(4, index_7);

        int index_8 = Week3.binarySearch(values, 8);
        assertEquals(4, index_8);
    }
}