package no.oslomet.cs.algdat;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;


public class Oblig1 {
    private Oblig1() {
    }

    ///// Oppgave 1 //////////////////////////////////////
    public static int maks(int[] a) {
        int temp = 0;
        if (a.length == 0) {
            throw new NoSuchElementException("Arrayet er tomt");
        }
        for (int i = 0; i < a.length-1; i++) {
            if (a[i] > a[i+1]){
                bytt(a, i, i+1);
            }
        }
        return a[a.length-1];
    }


    /*
    * 1: det blir flest ombyttinger om arrayet er sortert i synkende rekkefølge (største tallet ligger først)
    * 2: det blir færrest om den er sortert i stigende rekkefølge(største tallet ligger sist)
    * 3: Det blir i gjennomsnitt*/


    public static int ombyttinger(int[] a) {
        int temp = 0;
        int ombyttinger = 0;
        if (a.length == 0) {
            throw new NoSuchElementException("Arrayet er tomt");
        }
        for (int i = 0; i < a.length-1; i++) {
            if (a[i] > a[i+1]){
                bytt(a, i, i+1);
                ombyttinger++;
            }
        }
        return ombyttinger;
    }

    public static void bytt(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    ///// Oppgave 2 //////////////////////////////////////
    public static int antallUlikeSortert(int[] a) {
        int antall = 0;
        if (!sortCheck(a)){
            throw new IllegalStateException("Array er ikke sortert");
        }
        if (a.length == 0){
            return antall;
        }else {
            antall = 1;
        }
        for (int i = 0; i < a.length-1 ; i++) {
            if (a[i] != a[i+1]){
                antall++;
            }
        }

        return antall;
    }

    private static Boolean sortCheck(int[] a) {
        for (int i = 0; i < a.length-1 ; i++) {
            if( a[i] > a[i+1]){
                return false;
            }
        }
        return true;
    }


    ///// Oppgave 3 //////////////////////////////////////
    public static int antallUlikeUsortert(int[] a) {
        throw new NotImplementedException();
    }

    ///// Oppgave 4 //////////////////////////////////////
    public static void delsortering(int[] a) {
        int v = 0;
        int h = a.length-1;
        int m = a.length/2;
        while (true){
            if(a[m] % 2 == 0){
                for (int i = m; i < a.length-1 ; i++) {

                }
            }
        }
    }

    ///// Oppgave 5 //////////////////////////////////////
    public static void rotasjon(char[] a) {
        int d = 1;
        int n = a.length;
        if (n < 2) return;

        char[] b = Arrays.copyOfRange(a, n - d, n);
        for (int i = n - 1; i >= d; i--) {
            a[i] = a[i - d];
        }
        System.arraycopy(b, 0, a, 0, d);
    }

    ///// Oppgave 6 //////////////////////////////////////
    public static void rotasjon(char[] a, int k) {
        int n = a.length;
        if (n < 2) return;
        if ((k %= n) < 0) k += n;

        char[] b = Arrays.copyOfRange(a, n - k, n);
        for (int i = n - 1; i >= k; i--) {
            a[i] = a[i - k];
        }
        System.arraycopy(b, 0, a, 0, k);
    }

    ///// Oppgave 7 //////////////////////////////////////
    /// 7a)
    public static String flett(String s, String t) {
        if (s.isEmpty() && t.isEmpty()) {
            return "";
        }
        int i = 0, j = 0, k = 0;

        char[] c = new char[s.length() + t.length()];

        while (i < s.length() && j < t.length()) {
            c[k++] = s.charAt(i++);
            c[k++] = t.charAt(j++);
        }
        while (i < s.length()) c[k++] = s.charAt(i++);
        while (j < t.length()) c[k++] = t.charAt(j++);

        return new String(c);
    }

    /// 7b)
    public static String flett(String... s) {
        if (s == null){
            return "";
        }
        char[] c = new char[length(s)];
        int i = 0, k = 0;
        while (k < c.length) {
            for (int j = 0; j < 5 ; j++) {
                if (i < s[j].length()){
                    c[k++] = s[j].charAt(i);
                }
            }
            i++;
        }
        return new String(c);
    }

    private static int length(String... s){
        int length = 0;
        for (String value : s) {
            length = length + value.length();
        }
        return length;
    }

    ///// Oppgave 8 //////////////////////////////////////
    public static int[] indekssortering(int[] a) {
        throw new NotImplementedException();
    }


    ///// Oppgave 9 //////////////////////////////////////
    public static int[] tredjeMin(int[] a) {
        throw new NotImplementedException();
    }

    ///// Oppgave 10 //////////////////////////////////////
    public static int bokstavNr(char bokstav) {
        throw new NotImplementedException();
    }

    public static boolean inneholdt(String a, String b) {
        throw new NotImplementedException();
    }

}  // Oblig1

