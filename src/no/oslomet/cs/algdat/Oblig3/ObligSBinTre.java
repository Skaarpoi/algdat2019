//Sebastian Skaar Simenstad s331355

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
                if (b != null){
                    rot = b;
                    rot.forelder = null;
                }else{
                    rot = null;
                }
            }
            else if (p == q.venstre) {
                if(b != null){
                    q.venstre = b;
                    b.forelder = q;
                }else {
                    q.venstre = null;
                }
            }
            else {
                if(b != null){
                    q.høyre = b;
                    b.forelder = q;
                }else {
                    q.høyre = null;
                }
            }
            p = null;
        } else {
            Node<T> s = p, r = p.høyre;
            while (r.venstre != null)
            {
                s = r;
                r = r.venstre;
            }

            p.verdi = r.verdi;

            if (s != p) s.venstre = r.høyre;
            else s.høyre = r.høyre;

            r = null;
        }

        antall--;
        endringer++;
        return true;
    }

    public int fjernAlle(T verdi)
    {
        if (verdi == null || antall == 0) return 0;
        int fjernet = 0;
        boolean fjernOK = true;

        while (fjernOK){
            fjernOK = fjern(verdi);

            if(fjernOK){
                fjernet++;
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
        Node<T> p = rot;
        Node<T> q = null;

        while (antall > 0){

            while (p.venstre != null|| p.høyre != null ){
                if(p.venstre != null){
                    p = p.venstre;
                }else{
                    p = p.høyre;
                }
            }

            q = p;
            p = p.forelder;
            q = null;
            endringer++;
            antall--;
        }
        rot = null;
    }

    private static <T> Node<T> førsteInorden(Node<T> p)
    {
        while (p.venstre != null) p = p.venstre;
        return p;
    }

    private static <T> Node<T> nesteInorden(Node<T> p)
    {
        if (p.høyre != null) {
            return førsteInorden(p.høyre);
        }
        else {
            while (p.forelder != null && p.forelder.høyre == p) {
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
        if (rot == null || tom()) return "[]";
        Node<T> p = null;
        ArrayDeque<Node<T>> queue = new ArrayDeque<>();
        queue.add(rot);

        while(!queue.isEmpty()){
            p = queue.removeFirst();
            if(p.høyre != null) queue.addLast(p.høyre);
            if(p.venstre != null) queue.addLast(p.venstre);
        }

        ArrayDeque<T> stakk = new ArrayDeque<>();
        while (p != null){
            stakk.addFirst(p.verdi);
            p = p.forelder;
        }

        return stakk.toString();
    }

    public String[] grener()
    {
        if (antall == 0 || rot == null) return new String[] {"[]"};
        if (antall() == 1) return new String[] {"[" + rot.verdi + "]"};

        Node<T> p = førsteInorden(rot);
        Deque<Node> grenDekk = new ArrayDeque<>();

        while (p != null){
            p = nesteInorden(p);
            if (p == null) break;
            if(p.venstre == null && p.høyre == null) grenDekk.push(p);
        }

        String[] s = new String[grenDekk.size()];
        Node q = null;

        int teller = 0;

        while (!grenDekk.isEmpty()){
            Deque<Node> omvendtQ = new ArrayDeque<>();
            q = grenDekk.removeLast();
            s[teller] = "[";

            while (q != null){
                omvendtQ.push(q);
                q = q.forelder;
            }

            while (!omvendtQ.isEmpty()){
                Node tmp = omvendtQ.pop();
                if (omvendtQ.size() == 0) s[teller] += tmp.verdi + "]";
                else s[teller] += tmp.verdi + ", ";
            }
            teller++;
        }
        return s;
    }

    public String bladnodeverdier()
    {
        if (antall == 0 || rot == null){
            return "[]";
        }

        Node<T> p = rot;
        StringBuilder str = new StringBuilder();
        str.append("[]");
        rekursivInorden(p, str);
        str.replace(str.length() - 2, str.length(), "]");
        return str.toString();
    }

    private static <T> void rekursivInorden(Node p, StringBuilder tekst) {
        if (p.venstre != null) rekursivInorden(p.venstre,tekst);
        if (p.venstre == null && p.høyre == null) tekst.append(p.verdi).append(", ");
        if (p.høyre != null) rekursivInorden(p.høyre,tekst);
    }

    public String postString()
    {
        if (antall == 0) return "[]";
        if (antall == 1) return "[" + rot.verdi + "]";

        StringBuilder str = new StringBuilder();

        Deque<Node> stack1 = new ArrayDeque<>();
        Deque<Node> stack2 = new ArrayDeque<>();

        Node p;
        stack1.push(rot);

        while (!stack1.isEmpty()){
            p = stack1.pop();
            stack2.push(p);

            if(p.venstre != null) stack1.push(p.venstre);
            if(p.høyre != null) stack1.push(p.høyre);
        }

        str.append("[");
        while (!stack2.isEmpty()){
            if(stack2.size() == 1) str.append(stack2.pop().verdi).append("]");
            else str.append(stack2.pop().verdi).append("]");
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

}
