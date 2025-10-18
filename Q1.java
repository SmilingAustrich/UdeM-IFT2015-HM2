package ift2015_a25_tp2;
import java.util.*;

/**
 * IFT2015 - Automne 2025. TP2 - Question 1
 *
 * Ce programme simule un système de distribution de livres lors d’un évènement.
 * Chaque participant demande un seul livre (identifié par un numéro)
 * et les livres sont remis dans un ordre précis défini par le tableau `distribution`.
 *
 * À chaque fois qu’un livre est remis, il doit être donné au participant
 * le plus "ancien" (numéro le plus petit) qui attend ce livre.
 *
 * Si un participant constate qu’une personne derrière lui (numéro plus grand)
 * reçoit son livre avant lui, sa valeur de colère augmente de 1.
 *
 * L’objectif est donc de calculer :
 *  1. L’ordre dans lequel les participants reçoivent leurs livres.
 *  2. La somme totale de la "colère" de tous les participants.
 *
 * Exemple issu de l’énoncé :
 *   livres demandés = {2, 1, 3, 4, 3}
 *   distribution    = {1, 3, 3, 2, 4}
 * Résultat attendu :
 *   participant served: 2 3 5 1 4
 *   total anger: 4
 *
 * @author Tarik Hireche
 */
public class Q1 {

    /**
     * Point d’entrée du programme.
     * Ici, on initialise les données d’exemple et on appelle les routines principales.
     */
    public static void main(String[] args) {

        // Exemple fourni dans le sujet
        int[] books = {2, 1, 3, 4, 3};       // livres demandés par chaque participant
        int[] distribution = {1, 3, 3, 2, 4}; // ordre de distribution
        int n = books.length;

        // On crée une file d’attente pour chaque type de livre
        // waitlist[b] = file contenant les participants qui attendent le livre b
        List<Queue<Integer>> waitlist = new ArrayList<>();
        for (int i = 0; i <= n; i++) waitlist.add(new LinkedList<>());

        // On peuple les files avec les participants selon leur demande
        for (int i = 0; i < n; i++) {
            int livreDemande = books[i];
            int participant = i + 1; // +1 pour que les participants soient numérotés à partir de 1
            waitlist.get(livreDemande).add(participant);
        }

        // Tableaux de suivi
        boolean[] served = new boolean[n + 1]; // indique si un participant a reçu son livre
        int[] anger = new int[n + 1];          // valeur de colère accumulée
        List<Integer> servedOrder = new ArrayList<>();

           // Simulation de la distribution des livres
        // -------------------------------------------------------
        // On parcourt l’ordre dans lequel les livres arrivent.
        // À chaque itération, on prend le livre, on regarde qui l’attend,
        // et on le remet à la bonne personne (celle avec le plus petit numéro).
        // C’est un peu comme une file au comptoir du café : on sert toujours le premier qui attend ce produit-là.
        for (int book : distribution) {

            Queue<Integer> q = waitlist.get(book);

            // Juste un ptit "safe mecanism" : normalement, toutes les files ont au moins une personne
            // mais si jamais c’est vide (erreur d’entrée, par exemple), on skip pour éviter un crash.
            if (q.isEmpty()) continue;

            // Le premier dans la file reçoit son livre.
            int participant = q.poll();
            served[participant] = true;
            servedOrder.add(participant);

            // Maintenant, on gère la "colère" : tous les participants avant lui (numéros plus petits)
            // qui n’ont pas encore reçu leur livre sont entrain de bien rager.
            // En gros, ils voient quelqu’un derrière eux être servi avant eux.
            for (int i = 1; i < participant; i++) {
                if (!served[i]) anger[i]++;
            }

        }

        // Affichage des résultats
        System.out.print("participant served: ");
        for (int p : servedOrder) {
            System.out.print(p + " ");
        }
        System.out.println();

        int totalAnger = 0;
        for (int i = 1; i <= n; i++) totalAnger += anger[i];
        System.out.println("total anger: " + totalAnger);
    }
}
