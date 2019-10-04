package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;



public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {
        hode = hale = null;
        antall = 0;
    }

    public DobbeltLenketListe(T[] a) {
        Objects.requireNonNull(a, "Ikke tillatt med tom tabell!");
        for (T t : a) {
            if (t != null) {
                if(antall == 0){
                    hode = new Node<T>(t, null, hale);
                    hale = hode;
                    antall++;
                }else{
                    hale = hale.neste = new Node<T>(t, hale, null);
                    antall++;
                }
            }
        }
    }

    public Liste<T> subliste(int fra, int til){
        fratilKontroll(antall, fra, til);
        DobbeltLenketListe<T> dll = new DobbeltLenketListe<>();
        for (int i = fra; i < til ; i++) {
            dll.leggInn(finnNode(i).verdi);
        }
        return dll;
    }

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        return antall == 0;
    }

    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");
        if (antall == 0)  hode = hale = new Node<T>(verdi, null, hale);
        else hale = hale.neste = new Node<T>(verdi, hale, null);
        antall++;
        endringer++;
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");
        indeksKontroll(indeks, true);
        if(antall == 0){
            hode = hale = new Node<T>(verdi, hode, hale);
        }else if (indeks == 0){
            hode = new Node<T>(verdi, null, hode);
            hode.neste.forrige = hode;
        }else if(indeks == antall){
            hale = hale.neste = new Node<T>(verdi, hale, null);
        }else{
            Node<T> p = hode;
            for (int i = 1; i < indeks; i++) {
                p = p.neste;
            }
            p.neste = new Node<T>(verdi, p, p.neste);
            p.neste.neste.forrige = p.neste;
        }
        antall++;
        endringer++;
    }

    @Override
    public boolean inneholder(T verdi) {
        return indeksTil(verdi) >= 0;
    }

    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi) {
        if (verdi == null){
            return -1;
        }
        for (int i = 0; i < antall ; i++) {
            if (verdi.equals((finnNode(i).verdi))){
                return i;
            }
        }
        return -1;
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        Objects.requireNonNull(nyverdi, "Ikke tillatt med nullverdier!");
        indeksKontroll(indeks, false);
        Node<T> p = finnNode(indeks);
        T gammelVerdi = p.verdi;
        p.verdi = nyverdi;
        endringer++;
        return gammelVerdi;
    }

    @Override
    public boolean fjern(T verdi) {
        if (verdi == null){
            return false;
        }
        Node<T> temp = hode;

        if (temp != null && temp.verdi.equals(verdi)){
            hode = hode.neste;
            if (hode != null) hode.forrige = null;
            if (antall == 1) hale = null;
            antall--;
            endringer++;
            return true;
        }
        while (temp != null && !temp.verdi.equals(verdi)) {
            temp = temp.neste;
        }
        if (temp == null) return false;
        temp.forrige.neste = temp.neste;

        if (temp.neste != null){
            temp.neste.forrige = temp.forrige;
        }else {
            hale = temp.forrige;
        }
        antall--;
        endringer++;
        return true;
    }

    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks, false);
        T temp;
        if (indeks == 0){
            temp = hode.verdi;
            hode = hode.neste;
            if (hode != null) hode.forrige = null;
            if (antall == 1) hale = null;
        }else if (indeks == antall - 1){
            temp = hale.verdi;
            hale = hale.forrige;
            hale.neste = null;
        }else {
            Node<T> p = finnNode(indeks - 1);
            Node<T> q = p.neste;
            temp = q.verdi;

            p.neste = q.neste;
            if(q.neste != null) {
                q.neste.forrige = q.forrige;
            }
        }
        antall--;
        endringer++;
        return temp;
    }

    @Override
    public void nullstill() {
        int n = antall;
        for (int i = 0; i < n ; i++) {
            fjern(0);
        }
    }

    @Override
    public String toString() {
       Node current = hode;
       StringBuilder sb = new StringBuilder();
        sb.append("[");
       if(current != null) {
           sb.append(current.verdi);
           current = current.neste;
           while (current != null) {
               sb.append(", ").append(current.verdi);
               current = current.neste;
           }
       }
       sb.append("]");
       return sb.toString();
    }


    public String omvendtString() {
        Node current = hale;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if(current != null) {
            sb.append(current.verdi);
            current = current.forrige;
            while (current != null) {
                sb.append(", ").append(current.verdi);
                current = current.forrige;
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks) {
        indeksKontroll(indeks, false);
        return new DobbeltLenketListeIterator(indeks);
    }

    private Node<T> finnNode(int indeks){
        if(indeks < antall/2){
            Node<T> p = hode;
            for (int i = 0; i < indeks; i++) p = p.neste;
            return p;
        }else {
            Node<T> p = hale;
            for (int i = 0; i < (antall-indeks-1); i++) p = p.forrige;
            return p;
        }
    }

    public static void fratilKontroll(int antall, int fra, int til)
    {
        if (fra < 0)                                  // fra er negativ
            throw new IndexOutOfBoundsException
                    ("fra(" + fra + ") er negativ!");

        if (til > antall)                          // til er utenfor tabellen
            throw new IndexOutOfBoundsException
                    ("til(" + til + ") > antall(" + antall + ")");

        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator(){
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks){
            denne = finnNode(indeks);
            fjernOK = false;
            iteratorendringer = endringer;
        }

        @Override
        public boolean hasNext(){
            return denne != null;
        }

        @Override
        public T next(){
            if (endringer != iteratorendringer){
                throw new ConcurrentModificationException();
            }
            if (!hasNext()){
                throw new NoSuchElementException();
            }
            fjernOK = true;
            T temp = denne.verdi;
            denne = denne.neste;
            return temp;
        }

        @Override
        public void remove(){
            if (!fjernOK){
                throw new IllegalStateException();
            }
            if (iteratorendringer != endringer){
                throw new ConcurrentModificationException();
            }
            fjernOK = false;
            if (antall ==1){
                hale = hode = null;
            }else if (denne == null){
                hale = hale.forrige;
            }else if (denne.forrige == hode){
                hode = denne;
                denne.forrige = null;
            }else{

            }
            antall--;
            endringer++;
            iteratorendringer++;
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new NotImplementedException();
    }

} // class DobbeltLenketListe


