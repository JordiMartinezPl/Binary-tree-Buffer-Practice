package estructura;

public class Equip {
    private final String nom;
    private int puntuacio = -1;

    Equip(String equip) {
        nom = equip;

    }

    Equip(String nom, int puntuacio) {
        this.nom = nom;
        this.puntuacio = puntuacio;
    }

    public boolean haJugat() {
        return puntuacio != -1;

    }

    public void setPuntuacio(int punts) {
        this.puntuacio = punts;
    }


    public String getNom() {
        return nom;
    }

    public String toSave() { // possible implementacio
        return (nom + ";" + puntuacio);

    }

    public String toString() { // posible
        return (nom + ": " + puntuacio + ";");
    }

    public int compareTo(Equip equip1) {
        return Integer.compare(puntuacio, equip1.puntuacio);
    }
}
