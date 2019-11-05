package no.oslomet.cs.algdat.Oblig3;

import java.util.*;

public class ObligSBinTre<T> implements Beholder<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder)
        {
            this.verdi = verdi;
            venstre = v; høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString(){ return "" + verdi;}

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public ObligSBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    @Override
    public boolean leggInn(T verdi)
    {
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> p = rot, q = null;
        int cmp = 0;

        while (p != null)
        {
            q = p;
            cmp = comp.compare(verdi,p.verdi);
            p = cmp < 0 ? p.venstre : p.høyre;
        }
        p = new Node<>(verdi, q);

        if (q == null) rot = p;
        else if (cmp < 0) q.venstre = p;
        else q.høyre = p;

        antall++;
        return true;
    }

    @Override
    public boolean inneholder(T verdi)
    {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null)
        {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    @Override
    public boolean fjern(T verdi)
    {
        if (verdi == null) return false;

        Node<T> p = rot, q = null;

        while (p != null)
        {
            int cmp = comp.compare(verdi,p.verdi);
            if (cmp < 0) { q = p; p = p.venstre; }
            else if (cmp > 0) { q = p; p = p.høyre; }
            else break;
        }
        if (p == null) return false;

        if (p.venstre == null || p.høyre == null)
        {
            Node<T> b = p.venstre != null ? p.venstre : p.høyre;
            if (p == rot){
                rot = b;
            }
            else if (p == q.venstre) {
                q.venstre = b;
            }
            else {
                q.høyre = b;

            }
        }
        else
        {
            Node<T> s = p, r = p.høyre;
            while (r.venstre != null)
            {
                s = r;
                r = r.venstre;
            }

            p.verdi = r.verdi;

            if (s != p) s.venstre = r.høyre;
            else s.høyre = r.høyre;
        }

        antall--;
        return true;
    }

    public int fjernAlle(T verdi)
    {
        int fjernet = 0;
        if (verdi == null) return 0;
        Node<T> p = inorden(), q = null;

        while (p != null){
            int cmp = comp.compare(verdi,p.verdi);
            if (cmp == 0){
                if(p.venstre == null && p.høyre == null){
                    if(p == rot){
                        rot = null;
                    }else{
                        q = p.forelder;
                        if (p == q.venstre){
                            q.venstre = null;
                        }else {
                            q.høyre = null;
                        }
                    }
                }
                else if (p.venstre != null && p.høyre == null){
                    if (p == rot) rot = p.venstre;
                    else {
                        q = p.forelder;
                        q.venstre = p.venstre;
                    }
                }
                else if (p.høyre != null && p.venstre == null){
                    if (p == rot) rot = p.høyre;
                    else {
                        q = p.forelder;
                        q.høyre = p.høyre;
                    }
                }
                p = nesteInorden(p);
                antall--;
                fjernet++;
            }else {
                p = nesteInorden(p);
            }
        }
        return fjernet;
    }

    @Override
    public int antall()
    {
        return antall;
    }

    public int antall(T verdi)
    {
        if (verdi == null) return 0;

        int count = 0;

        Node<T> p = rot;

        while (p != null)
        {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else {
                count++;
                p = p.høyre;
            }
        }
        return count;
    }

    @Override
    public boolean tom()
    {
        return antall == 0;
    }

    @Override
    public void nullstill()
    {
        Node<T> p = inorden();
        while (p != null){
            Node<T> q = p;
            p = null;
            p = nesteInorden(q);
        }
    }

    private static <T> Node<T> førsteInorden(Node<T> p)
    {
        while (p.venstre != null) p = p.venstre;
        return p;
    }

    private static <T> Node<T> nesteInorden(Node<T> p)
    {
        if (p.høyre != null)
        {
            return førsteInorden(p.høyre);
        }
        else
        {
            while (p.forelder != null && p.forelder.høyre == p)
            {
                p = p.forelder;
            }
            return p.forelder;
        }
    }

    @Override
    public String toString()
    {
        Node<T> p = inorden();
        StringBuilder str = new StringBuilder();
        str.append("[");
        while (p != null) {
            str.append(p.verdi);
            p = nesteInorden(p);
            str.append(", ");
        }
        str.delete(str.length()-2,str.length());
        str.append("]");
        return str.toString();
    }

    public String omvendtString()
    {
        if (tom()) return "[]";            // tomt tre

        Stakk<Node<T>> stakk = new TabellStakk<>();
        Node<T> p = rot;
        for ( ; p.høyre != null; p = p.høyre) stakk.leggInn(p);

        StringBuilder str = new StringBuilder();
        str.append("[");
        while (true) {
            str.append(p.verdi);
            str.append(", ");

            if (p.venstre != null)
            {
                for (p = p.venstre; p.høyre != null; p = p.høyre) {
                    stakk.leggInn(p);

                }
            } else if (!stakk.tom()) {
                p = stakk.taUt();
            } else break;
        }
        str.delete(str.length()-2,str.length());
        str.append("]");
        return str.toString();
    }

    public String høyreGren()
    {
        if (rot == null) return "[]";
        Node<T> p = rot;
        StringBuilder str = new StringBuilder();
        str.append("[");
        while (p != null){
            str.append(p.verdi);
            if (p.høyre != null){
                str.append(", ");
                p = p.høyre;
            }else if (p.venstre != null){
                str.append(", ");
                p = p.venstre;
            }
            else{
                str.append("]");
                break;
            }
        }
        return str.toString();
    }

    public String lengstGren()
    {
        if (rot == null) return "[]";
    }

    public String[] grener()
    {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public String bladnodeverdier()
    {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public String postString()
    {
        Node <T> p = førstPostorden();
        StringBuilder str = new StringBuilder();
        str.append("[");
        while (p != null){
            str.append(p.verdi);
            p = nestePostorden(p);
            if (p != null){
                str.append(", ");
            }
        }
        return str.toString();
    }

    @Override
    public Iterator<T> iterator()
    {
        return new BladnodeIterator();
    }

    private class BladnodeIterator implements Iterator<T>
    {
        private Node<T> p = rot, q = null;
        private boolean removeOK = false;
        private int iteratorendringer = endringer;

        private BladnodeIterator()  // konstruktør
        {
            throw new UnsupportedOperationException("Ikke kodet ennå!");
        }

        @Override
        public boolean hasNext()
        {
            return p != null;  // Denne skal ikke endres!
        }

        @Override
        public T next()
        {
            throw new UnsupportedOperationException("Ikke kodet ennå!");
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException("Ikke kodet ennå!");
        }

    } // BladnodeIterator

    private Node<T> inorden(){
        Node<T> p = rot;
        while(p.venstre != null){
            p = p.venstre;
        }
        return p;
    }

    public Node<T> førstPostorden()
    {
        if (tom()) throw new NoSuchElementException("Treet er tomt!");

        Node<T> p = rot;
        while (true)
        {
            if (p.venstre != null) p = p.venstre;
            else if (p.høyre != null) p = p.høyre;
            else return p;
        }
    }

    private static <T> Node<T> nestePostorden(Node<T> p)
    {
        while (p.forelder != null && p.forelder.venstre == p)
        {
            p = p.forelder;
        }
        return p.forelder;

    }

}
