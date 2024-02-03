package estructura;

import java.io.*;
import java.util.*;

public class ArbreBinari {
    int profunditat;
    Node<Equip> arrel;
    List<String> list;


    public ArbreBinari(String fileName, int profunditat) {
        Scanner scanner = new Scanner(System.in);
        this.profunditat = profunditat;
        while (true) {
            try {
                BufferedReader reader;
                reader = new BufferedReader(new FileReader(fileName));
                arrel = preorderLoad(reader, profunditat);
                System.out.println("Árbol binario cargado correctamente desde el archivo " + fileName);
                break;

            } catch (IOException e) {
                System.err.println("Error al cargar el árbol binario " + e.getMessage());
                System.out.println("Introdueixi a continuacio el nom del fitxer: ");
                fileName = scanner.next();
                System.out.println("Introduix la profunditat: ");
                profunditat = scanner.nextInt();
            }
        }


    }

    public ArbreBinari(String equip) {
        this(equip.split(";"));
    }

    public ArbreBinari(String[] equips) {
        list = Arrays.asList(equips);
        Collections.shuffle(list);

        this.profunditat = calculateProfunditat(equips.length);
        arrel = new Node<>();
        ArbreBinari.construirArbre(this.profunditat, arrel);
        for (String equip : list) {
            inserir(arrel, new Equip(equip), profunditat);
        }
    }

    private Node<Equip> preorderLoad(BufferedReader reader, int ronda) throws IOException {
        String line;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        if (line == null || line.isEmpty()) {
            return null;
        }
        Node<Equip> node;
        if (line.equals("null")) {
            node = new Node<>();
        } else {
            String[] aux = line.split(";");
            Equip equipo = new Equip(aux[0], Integer.parseInt(aux[1]));
            node = new Node<>(equipo);
        }


        if (ronda == 0) {
            return node;
        }

        node.esq = preorderLoad(reader, ronda - 1);
        node.dret = preorderLoad(reader, ronda - 1);

        return node;
    }

    public static void construirArbre(int profunditatActual, Node<Equip> actual) {
        if (profunditatActual == 0) {
            return;
        }
        actual.esq = new Node<>();
        actual.dret = new Node<>();

        construirArbre(profunditatActual - 1, actual.esq);
        construirArbre(profunditatActual - 1, actual.dret);
    }

    public int calculateProfunditat(int lenght) {
        return (int) (Math.log(lenght) / Math.log(2));
    }

    public boolean inserir(Node<Equip> inici, Equip equip, int profunditatActual) {

        if (profunditatActual == 0) {
            if (inici.contingut != null) {
                return false;
            }
            inici.contingut = (equip);
            return true;
        }
        if (inici.esq != null) {
            if (inserir(inici.esq, equip, profunditatActual - 1)) {
                return true;
            }
            if (inici.dret != null) {
                return inserir(inici.dret, equip, profunditatActual - 1);
            }

        }

        return false;
    }

    public void mostrar(int ronda) {
        mostrar(arrel, profunditat - ronda + 1);
        System.out.println();
    }

    private void mostrar(Node<Equip> nodoInicialMaybe, int nivelProfundidadMaybe) {
        if (nivelProfundidadMaybe == 0) {
            if (nodoInicialMaybe.contingut == null) {
                System.out.print("null");
            } else {
                System.out.print(nodoInicialMaybe.contingut);
            }

        } else {
            mostrar(nodoInicialMaybe.esq, nivelProfundidadMaybe - 1);
            mostrar(nodoInicialMaybe.dret, nivelProfundidadMaybe - 1);
        }

    }


    private void saveRecursiu(Node<Equip> actual, BufferedWriter buf) throws Exception {
        if (actual.contingut == null) {
            buf.write("null");
        } else {
            buf.write(actual.contingut.toSave());
        }

        buf.newLine();
        if (actual.esq != null) {
            saveRecursiu(actual.esq, buf);
            saveRecursiu(actual.dret, buf);
        }

    }

    public void save(String filename) throws Exception {

        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        saveRecursiu(arrel, writer);
        writer.close();
        System.out.println("Árbol binario guardado correctamente en el archivo " + filename);


    }


    public int rondaActual() {
        Node<Equip> aux = arrel;
        if (aux.contingut != null) {
            return profunditat;
        }
        for (int i = profunditat; i >= 0; i--) {
            if (aux.esq.contingut != null) {
                return i;
            }
            aux = aux.esq;
        }
        return -1;


    }

    public void demanarResultats() {
        demanarResultats(arrel, rondaActual());
    }

    private boolean demanarResultats(Node<Equip> actual, int profunditatActual) {
        Scanner scanner = new Scanner(System.in);

        if (actual.contingut != null) {
            System.out.print("Introduiu els punt del equip " + actual.contingut + ": ");
            int puntuacioEquip = scanner.nextInt();
            actual.contingut.setPuntuacio(puntuacioEquip);
            return true;
        }
        boolean boolAux = demanarResultats(actual.dret, profunditatActual - 1);
        if (!(demanarResultats(actual.esq, profunditatActual - 1) && boolAux)) {
            return false;
        }
        int comparar = actual.esq.contingut.compareTo(actual.dret.contingut);
        if (comparar < 0) {
            actual.contingut = new Equip(actual.dret.contingut.getNom());
            return false;

        } else if (comparar > 0) {
            actual.contingut = new Equip(actual.esq.contingut.getNom());
            return false;
        }
        do {
            System.out.println("Hi ha hagut un empat.");
            System.out.print("Introdueix el nou resultat del equip " + actual.esq.contingut.toString());
            actual.esq.contingut.setPuntuacio(scanner.nextInt());
            System.out.print("Introdueix el nou resultat del equip " + actual.dret.contingut.toString());
            actual.dret.contingut.setPuntuacio(scanner.nextInt());
            comparar = actual.esq.contingut.compareTo(actual.dret.contingut);


        }
        while (comparar == 0);
        if (comparar < 0) {
            actual.contingut = new Equip(actual.dret.contingut.getNom());
            return false;

        } else {
            actual.contingut = new Equip(actual.esq.contingut.getNom());
            return false;
        }

    }

}
