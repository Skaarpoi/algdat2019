package no.oslomet.cs.algdat.Oblig1;

import no.oslomet.cs.algdat.Oblig1.Oblig1;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class Oblig1UnitTest {
    @org.junit.jupiter.api.Test
    void maks() {
        int[] a = new int[]{10, 4, 16, 13, 12};
         assertEquals(16, Oblig1.maks(a), "Implementer maks og denne testen");
    }

    @org.junit.jupiter.api.Test
    void ombyttinger() {
        int[] a = new int[]{10, 4, 16, 13, 12};
        assertEquals(3, Oblig1.ombyttinger(a), "Implementer ombyttinger og denne testen");
    }

    @org.junit.jupiter.api.Test
    void antallUlikeSortert() {
        int[] a = new int[]{3, 3, 4, 5, 5, 6, 7, 7, 7,8};
        int[] b = new int[]{3, 3, 4, 5, 5, 6, 7, 7, 7, 8, 8, 9, 10, 10, 11, 11};
        assertEquals(6, Oblig1.antallUlikeSortert(a), "Implementer antallUlikeSortert og denne testen");
        assertEquals(9, Oblig1.antallUlikeSortert(b), "Implementer antallUlikeSortert og denne testen");
    }

    @org.junit.jupiter.api.Test
    void antallUlikeUsortert() {
        assertEquals(true, false, "Implementer antallUlikeUsortert og denne testen");
    }

    @org.junit.jupiter.api.Test
    void delsortering() {
        int[] a = {9, 5, 3, 1, 7};
        int[] b = {1, 3, 5, 2, 4, 6};
        int[] c = a.clone();
        Oblig1.delsortering(c);
        assertEquals(Arrays.toString(b), Arrays.toString(c), "Implementer delsortering og denne testen");
    }

    @org.junit.jupiter.api.Test
    void rotasjon() {
        char[] a = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        Oblig1.rotasjon(a);
        System.out.println(Arrays.toString(a));
        Oblig1.rotasjon(a, 3);
        System.out.println(Arrays.toString(a));
        Oblig1.rotasjon(a, -3);
        System.out.println(Arrays.toString(a));
        assertEquals(true, true, "Implementer rotasjon og denne testen");
    }

    @org.junit.jupiter.api.Test
    void flett() {
        String a = Oblig1.flett("ABC","DEFGH");
        String b = Oblig1.flett("IJKLMN","OPQ");
        String c = Oblig1.flett("","AB");
        System.out.println(a + " " + b + " " + c);
        assertEquals("ADBECFGH IOJPKQLMN AB", a + " " + b + " " + c, "Implementer flett og denne testen");
    }
    @org.junit.jupiter.api.Test
    void flett2() {
        String a = Oblig1.flett("AM ","L","GEDS","ORATKRR","","R TRTE","IO","TGAUU");
        System.out.println(a);
        assertEquals("ALGORITMER OG DATASTRUKTURER", a, "Implementer flett og denne testen");
    }

    @org.junit.jupiter.api.Test
    void indekssortering() {
        int[] a = {1, 3, 6, 4, 5, 2};
        System.out.println(Arrays.toString(Oblig1.indekssortering(a)));
        assertEquals(true, true, "Implementer indekssortering og denne testen");
    }

    @org.junit.jupiter.api.Test
    void tredjeMin()
    {
        int[] a = {1, 3, 6, 4, 5, 2};
        System.out.println(Arrays.toString(Oblig1.indekssortering(a)));
        assertEquals(true, true, "Implementer tredjeMin og denne testen");
    }

    @org.junit.jupiter.api.Test
    void bokstavNr() {
        assertEquals(true, false, "Implementer bokstavNr og denne testen");
    }

    @org.junit.jupiter.api.Test
    void inneholdt()
    {
        boolean b = false;
        b = Oblig1.inneholdt("ØÅÅØ", "ÅØØÅØ");
        System.out.println(b);
        assertEquals(true, true, "Implementer inneholdt og denne testen");
    }
}
