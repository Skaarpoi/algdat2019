package no.oslomet.cs.algdat.Oblig2;

public class Main {
    public static void main(String[] args) {
        String[] s1 = {}, s2 = {"A"}, s3 = {null, "A", null, "B", null}, s4 = {null, "I", null, "S", null, "A", null, "K", null};
        DobbeltLenketListe<String> l1 = new DobbeltLenketListe<>(s1);
        DobbeltLenketListe<String> l2 = new DobbeltLenketListe<>(s2);
        DobbeltLenketListe<String> l3 = new DobbeltLenketListe<>(s3);
        DobbeltLenketListe<String> l4 = new DobbeltLenketListe<>(s4);

        System.out.println(l1.toString() + "\n" + l2.toString() + "\n" + l3.toString() + "\n" + l4.toString() + "\n" + l1.omvendtString() + "\n" + l2.omvendtString() + "\n" + l3.omvendtString()+ "\n" + l4.omvendtString());
    }
}
