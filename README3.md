## Représentation des Solutions et Fonction Objectif pour le Sudoku
# Représentation des Solutions
Soit S une grille de sudoku de taille 9x9, où chaque élément s_{i,j} ∈ S représente le chiffre dans la cellule située à la i-ème ligne et j-ème colonne, avec i, j ∈ {1, 2, ..., 9} et s_{i,j} ∈ {0, 1, 2, ..., 9}. Le chiffre 0 représente une cellule vide qui doit être remplie. Une solution complète du sudoku est une grille S où aucun s_{i,j} n'est égal à 0.
# Fonction Objectif
La fonction objectif f(S) mesure la qualité d'une solution S, c'est-à-dire à quel point la grille de sudoku respecte les règles du jeu. Les règles du sudoku exigent qu'aucun chiffre ne se répète dans une même ligne, une même colonne ou dans l'une des neuf sous-grilles 3x3. La fonction objectif peut alors être définie comme le total des violations de ces règles :
f(S) = f_ligne(S) + f_colonne(S) + f_sous-grille(S)
où :
- f_ligne(S) compte le nombre de répétitions de chiffres dans chaque ligne,
- f_colonne(S) compte le nombre de répétitions de chiffres dans chaque colonne,
- f_sous-grille(S) compte le nombre de répétitions de chiffres dans chaque sous-grille 3x3.
Chacune de ces fonctions partielles peut être formalisée comme suit :
f_ligne(S) = ∑ de i=1 à 9 ∑ de n=1 à 9 max(0, occurrences de n dans la ligne i - 1)
f_colonne(S) = ∑ de j=1 à 9 ∑ de n=1 à 9 max(0, occurrences de n dans la colonne j - 1)
f_sous-grille(S) = ∑ de k=1 à 9 ∑ de n=1 à 9 max(0, occurrences de n dans la sous-grille k - 1)
Ici, occurrences de n représente le nombre de fois qu'un chiffre n apparaît dans une ligne, une colonne ou une sous-grille, et max(0, x) assure que la fonction compte seulement les répétitions, ignorants les cas où il n'y a pas de répétition (x ≤ 1).
# Objectif
L'objectif est de minimiser f(S), avec f(S) = 0 indiquant une solution parfaite où toutes les règles du sudoku sont respectées sans aucune répétition.
## Algorithme sudoku gloutone
Entrée: grille de sudoku 9x9, avec des cellules vides représentées par des zéros
Sortie: grille de sudoku remplie ou message d'échec

Début
    Pour chaque cellule vide dans la grille, faire:
        ListeChiffresValides = TrouverChiffresValides(cellule)

        Si ListeChiffresValides est vide, alors
            Retourner "Échec: Aucun chiffre valide pour la cellule [ligne, colonne]"
        FinSi

        // Choix glouton: sélectionner le premier chiffre valide
        ChiffreChoisi = ListeChiffresValides[0]

        Affecter ChiffreChoisi à la cellule courante

        // Optionnel: appliquer une heuristique pour choisir le meilleur chiffre
        // par exemple, celui qui minimise le nombre de contraintes pour les cellules futures

    FinPour

    Si la grille est complètement remplie et valide, alors
        Retourner grille
    Sinon
        Retourner "Échec: La grille ne peut pas être complétée"
FinSi
Fin
## Recherche locale pour sudoku avec voisinage
# Définition du Type de Voisinage
Le type de voisinage choisi pour cette recherche locale est basé sur l'inversion des valeurs dans une ligne, une colonne, ou une région (sous-grille) 3x3. Cette opération consiste à choisir deux cellules dans une même ligne, colonne ou région, et à inverser leurs valeurs. Cela permet de générer des configurations voisines de la grille actuelle qui peuvent être explorées à la recherche d'une solution améliorée.
# Algorithme de Recherche Locale
Algorithme RechercheLocaleSudoku
Entrée: Grille de sudoku initiale (peut être partiellement remplie)
Sortie: Grille de sudoku améliorée ou message d'échec

Début
    SolutionActuelle = Grille initiale
    Tant que des améliorations sont possibles
        Voisins = GénérerVoisinageParInversion(SolutionActuelle)
        MeilleurVoisin = TrouverMeilleurVoisin(Voisins)

        Si MeilleurVoisin est mieux que SolutionActuelle
            SolutionActuelle = MeilleurVoisin
        Sinon
            // Aucune amélioration n'est possible avec le voisinage actuel
            Retourner SolutionActuelle
    FinTantQue

    Si SolutionActuelle est une solution complète et valide
        Retourner SolutionActuelle
    Sinon
        Retourner "Échec: Impossible de trouver une solution valide"
Fin
Dans cet algorithme, `GénérerVoisinageParInversion` crée un ensemble de grilles voisines en inversant les valeurs dans les lignes, colonnes, ou régions 3x3 de la grille actuelle. `TrouverMeilleurVoisin` évalue ces grilles pour trouver celle qui améliore le plus la solution actuelle, en fonction d'une fonction objectif définie (par exemple, le nombre de conflits dans la grille).



