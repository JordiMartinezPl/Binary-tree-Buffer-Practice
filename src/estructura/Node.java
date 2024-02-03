package estructura;

public class Node<Equip> {
    Equip contingut;
    Node<Equip> esq, dret;

    Node() {
        contingut = null;
        esq = null;
        dret = null;
    }

    Node(Equip contingut) {
        this(contingut, null, null);
    }

    Node(Equip contingut, Node<Equip> esq, Node<Equip> dret) {
        this.contingut = contingut;
        this.esq = esq;
        this.dret = dret;

    }

}
