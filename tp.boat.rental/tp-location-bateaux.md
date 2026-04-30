# TP — Plateforme de location de bateaux

## Contexte

Une petite société de location de bateaux installée sur la côte gère actuellement son activité sur papier et avec un tableur. Le gérant veut pouvoir suivre sa flotte, ses clients et ses réservations depuis un seul outil, et arrêter de découvrir le matin que deux clients ont réservé le même bateau le même jour.

L'objectif de cette V1 est de fournir un outil interne, utilisé par les agents de location en agence. Le client final n'a pas accès à l'outil : c'est l'agent qui saisit tout pour lui.

## Acteurs

- **L'agent de location** : seul utilisateur de l'outil. Il gère la flotte, enregistre les clients qui se présentent, et crée les réservations.
- **Le client** : ne se connecte pas, mais ses informations sont enregistrées dans l'outil.

## Périmètre fonctionnel

### Gestion de la flotte

L'agent doit pouvoir ajouter un nouveau bateau dans le catalogue, consulter la liste des bateaux disponibles, modifier les informations d'un bateau et retirer un bateau du catalogue (par exemple en cas de revente).

Un bateau est décrit par : un nom, un type (voilier, bateau à moteur, semi-rigide), une capacité maximale de personnes, une longueur en mètres, un tarif journalier, un montant de caution, et une indication précisant si un permis bateau est nécessaire pour le piloter.

### Gestion des clients

L'agent enregistre les clients qui se présentent : nom, prénom, email, téléphone, et indication s'ils possèdent un permis bateau. Il peut consulter la fiche d'un client et la mettre à jour.

### Réservations

L'agent crée une réservation en choisissant un client, un bateau, une date de début et une date de fin. Il peut consulter l'ensemble des réservations, filtrer celles à venir, voir l'historique d'un client ou les réservations d'un bateau donné. Il peut également annuler une réservation.

## Règles de gestion

**RG1 — Pas de double réservation.** Un bateau ne peut pas être réservé sur deux périodes qui se chevauchent. Si une réservation existe déjà du 10 au 15 juillet, on ne peut pas en créer une autre du 12 au 18 juillet sur le même bateau.

**RG2 — Cohérence des dates.** La date de fin doit être strictement postérieure à la date de début.

-- CHECK
**RG3 — Pas de réservation dans le passé.** La date de début doit être supérieure ou égale à aujourd'hui.

-- CHECK
**RG4 — Permis obligatoire.** Si le bateau choisi nécessite un permis, le client doit en posséder un. Sinon la réservation est refusée.

-- CHECK
**RG5 — Capacité respectée.** Au moment de créer la réservation, l'agent saisit le nombre de personnes prévues à bord. Ce nombre ne doit pas dépasser la capacité maximale du bateau.

-- CHECK
**RG6 — Calcul du prix.** Le prix total est égal au tarif journalier du bateau multiplié par le nombre de jours de location. Le jour de départ et le jour de retour sont tous les deux comptés (une location du 10 au 12 juillet = 3 jours).

-- CHECK
**RG7 — Caution.** Le montant de la caution est repris depuis la fiche du bateau au moment de la création de la réservation, et reste figé sur cette réservation même si le tarif du bateau change ensuite.

**RG8 — Politique d'annulation.**
- Plus de 7 jours avant le départ : remboursement intégral.
- Entre 7 jours et 2 jours avant : 50 % du prix retenu.
- Moins de 2 jours, ou réservation déjà commencée : aucun remboursement.

**RG9 — Libération du créneau.** Une réservation annulée libère immédiatement le bateau pour la période concernée. Un autre client peut donc réserver dessus.

**RG10 — Suppression d'un bateau.** Un bateau qui a au moins une réservation à venir ou en cours ne peut pas être supprimé du catalogue. L'agent doit d'abord traiter ces réservations.

## Statuts d'une réservation

Une réservation passe automatiquement par les statuts suivants : **À venir** (date de début dans le futur), **En cours** (on est dans la période), **Terminée** (date de fin passée), **Annulée** (annulée par l'agent).

## Hors périmètre pour cette V1

Pour cadrer le sujet, les éléments suivants sont explicitement exclus : paiement en ligne, espace client en self-service, gestion multi-agences ou multi-utilisateurs, envoi d'emails ou de notifications, gestion des photos de bateaux, avis et notation, gestion d'une assurance, météo et conditions de navigation.
