package no.oslomet.cs.algdat.Oblig2;

public class Main {
    public static void main(String[] args) {
        DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>();
        liste.leggInn(0, 4);  // ny verdi i tom liste
        liste.leggInn(0, 2);  // ny verdi legges forrest
        liste.leggInn(2, 6);  // ny verdi legges bakers
        liste.leggInn(1, 3);  // ny verdi nest forrest
        liste.leggInn(3, 5);  // ny verdi nest bakerst
        liste.leggInn(0, 1);  // ny verdi forrest
        liste.leggInn(6, 7);  // ny verdi legges bakerst
        System.out.println(liste.toString());
        System.out.println(liste.omvendtString());

        DobbeltLenketListe<String> liste2 = new DobbeltLenketListe<>();
        liste2 = new DobbeltLenketListe<>(new String[]{"A", "B", "C", "D", "E", "F", "G"});
        liste2.fjern(0);  // fjerner A
        liste2.fjern(5);  // fjerner G

        System.out.println(liste2.toString());
        System.out.println(liste2.omvendtString());

    }
}
