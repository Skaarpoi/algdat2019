package no.oslomet.cs.algdat;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.IntStream;


//Sebastian Skaar Simenstad s331355
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
        int antall = 1;
        int n = a.length;
        if (n == 0) {
            antall = 0;
            return antall;
        }
        for (int i = 1; i < n ; i++) {
            int j = 0;
            for (; j < i; j++) {
                if (a[i] == a[j]){
                    break;
                }
            }
            if (i == j){
                antall++;
            }
        }
        return antall;
    }

    ///// Oppgave 4 //////////////////////////////////////
    public static void delsortering(int[] a) {
        if (a != null) //separerer oddetall og partall
        {
            int v = 0;
            int h = a.length-1;
            while (v < h){
                while (a[v] % 2 != 0 && v < h){
                    v++;
                }
                while (a[h] % 2 == 0 && v < h){
                    h--;
                }
                if (v < h){
                    bytt(a, v, h);
                    v++;
                    h--;
                }
            }
            System.out.println(Arrays.toString(a));
            int m = h+1;
            System.out.println(m);
            v = 0;
            h = m;
            while (v < h)
            {
                bytt(a, v, min(a, v, h));
                v++;
            }
            v = m;
            h = a.length;
            while (v < h)
            {
                bytt(a, v, min(a, v, h));
                v++;
            }
        }
    }

    public static int min(int[] a, int v, int h)
    {
        if (v < 0 || h > a.length || v >= h)
        {
            throw new IllegalArgumentException("Illegalt intervall!");
        }

        int m = v;
        int minverdi = a[v];

        for (int i = v + 1; i < h; i++)
        {
            if (a[i] < minverdi)
            {
                m = i;
                minverdi = a[m];
            }
        }

        return m;  // posisjonen til minste verdi i a[fra:til>
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
            for (String value : s) {
                if (!value.isEmpty()) {
                    if (i < value.length()) {
                        c[k++] = value.charAt(i);
                    }
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
        int[] temp = new int[a.length];
        int[] index = new int[a.length];
        System.arraycopy(a, 0, temp, 0 ,a.length);
        for (int i = 0; i < temp.length-1 ; i++) {
            bytt(temp, i, min(temp, i, temp.length));
        }
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < a.length ; j++) {
                if(temp[i] == a[j]){
                    index[i] = j;
                }
            }
        }
        return index;
    }


    ///// Oppgave 9 //////////////////////////////////////
    public static int[] tredjeMin(int[] a) {
        if (a.length < 3){
            throw new NoSuchElementException("Tabellen er for kort!");
        }
        int[] temp = new int[a.length];
        int[] index = new int[3];
        System.arraycopy(a, 0, temp, 0 ,a.length);
        for (int i = 0; i < 3 ; i++) {
            bytt(temp, i, min(temp, i, temp.length));
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < a.length ; j++) {
                if(temp[i] == a[j]){
                    index[i] = j;
                }
            }
        }
        return index;
    }

    ///// Oppgave 10 //////////////////////////////////////
    public static boolean inneholdt(String a, String b) {
        if (a.isEmpty()){
            return true;
        }
        Set<Character> aChar = new TreeSet<>();
        for( char c : a.toCharArray() ) {
            aChar.add(c);
        }
        Character[] aArray = aChar.toArray(new Character[0]);
        Set<Character> bChar = new TreeSet<>();
        for( char c : b.toCharArray() ) {
            bChar.add(c);
        }
        Character[] bArray = bChar.toArray(new Character[0]);
        int[] aAntall = new int[aArray.length];
        for (int i = 0; i < aAntall.length ; i++) {
            int count = 0;
            for (int j = 0; j < a.length() ; j++) {
                if(aArray[i] == a.charAt(j)){
                    count++;
                }
            }
            aAntall[i] = count;
        }
        int[] bAntall = new int[bArray.length];
        for (int i = 0; i < bAntall.length ; i++) {
            int count = 0;
            for (int j = 0; j < b.length() ; j++) {
                if(bArray[i] == b.charAt(j)){
                    count++;
                }
            }
            bAntall[i] = count;
        }
        if (aArray.length > bArray.length){
            return false;
        }
        boolean check = false;
        for (int i = 0; i < aArray.length ; i++) {
            check = false;
            for (int j = 0; j < bArray.length ; j++) {
                int first = aArray[i];
                int second = bArray[j];
                if (first == second){
                    if(aAntall[i] <= bAntall[j]){
                        check = true;
                        break;
                    }
                }
            }
            if(!check){
                return false;
            }
        }
        return check;
    }

}  // Oblig1

