package excecucio;

import estructura.ArbreBinari;


import java.lang.Exception;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IllegalArgumentException {
        Scanner scanner = new Scanner(System.in);

        ArbreBinari arbre = getNomsEquips();
        while (true) {
            System.out.println("Opcions:");
            System.out.println("1- Mostra una ronda");
            System.out.println("2- Introduir resultats a la ronda actual");
            System.out.println("3- Guardar l'abre i sortir");
            try {
                switch (scanner.nextInt()) {
                    case 1:
                        System.out.print("Quina ronda vols mostrar?: ");
                        if (arbre != null) {
                            arbre.mostrar(scanner.nextInt());
                        }
                        break;
                    case 2:
                        doResultats(arbre);
                        break;
                    case 3:
                        doSave(arbre);
                        System.exit(0);

                        break;
                    default:
                        throw new IllegalArgumentException("Numero incorrecte");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage() + " Intenta una altre cop");
            }


        }
    }


    private static ArbreBinari getNomsEquips() {
        Scanner scanner = new Scanner(System.in);


        System.out.println("Escull una de les seguents opcions:\n   1- Crear un nou torneix\n   2- Carregar un torneig");
        switch (scanner.nextInt()) {
            case 1:
                System.out.println("Introdueixi a continuacio els noms dels equips seguint el seguent format sense espais: NomEquip");
                return new ArbreBinari(scanner.next());
            case 2:
                return getNomFitxer();
        }
        return null;
    }

    private static ArbreBinari getNomFitxer() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introdueixi a continuacio el nom del fitxer: ");
        String nom = scanner.next();
        System.out.println("Introduix la profunditat: ");
        int profunditat = scanner.nextInt();

        return new ArbreBinari(nom, profunditat);

    }

    private static void doMostrarRonda(ArbreBinari arbre) {
        System.out.println("Ronda: " + arbre.rondaActual());
        arbre.mostrar(arbre.rondaActual());

    }

    private static void doResultats(ArbreBinari arbre) {
        doMostrarRonda(arbre);
        arbre.demanarResultats();

    }

    private static void doSave(ArbreBinari arbre) {
        Scanner scanner = new Scanner(System.in);
        boolean correcte = true;
        do {
            System.out.print("Introdueixi el nom de l'arxiu: ");
            try {
                arbre.save(scanner.next());
                correcte = false;
            } catch (Exception e) {
                System.out.println("Error al guardar el árbol binario: " + e.getMessage());
            }
        } while (correcte);


    }


}


