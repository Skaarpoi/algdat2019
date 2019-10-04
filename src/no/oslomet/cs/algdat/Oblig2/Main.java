package no.oslomet.cs.algdat.Oblig2;

public class Main {
    public static void main(String[] args) {
        DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>();
        for (int i = 1; i <= 100_000; i++) liste.leggInn(i);
        for (int i = 40000; i <= 50000; i++) liste.fjern(new Integer(i));
    }
}
