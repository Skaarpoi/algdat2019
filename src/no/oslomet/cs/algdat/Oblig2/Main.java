package no.oslomet.cs.algdat.Oblig2;

public class Main {
    public static void main(String[] args) {
        String[] s = {"Ole", null, "Per", "kari", null};
        Liste<String> liste = new DobbeltLenketListe<>(s);
        System.out.println(liste.antall() + "​ "+ liste.tom());
    }
}
