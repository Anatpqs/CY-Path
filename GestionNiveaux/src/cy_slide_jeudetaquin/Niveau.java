package cy_slide_jeudetaquin;

public class Niveau {
    private String nom;
    private int[][] grille;
    private int lignes;
    private int colonnes;
    private int score;

    public Niveau(String nom, int lignes, int colonnes) {
        this.nom = nom;
        this.lignes = lignes;
        this.colonnes = colonnes;
        this.grille = new int[lignes][colonnes];
        this.score = 0;
    }

    public void setTuile(int ligne, int colonne, int tuile) {
        grille[ligne][colonne] = tuile;
    }
    public int getTuile(int i, int j) {
        if (i >= 0 && i < lignes && j >= 0 && j < colonnes) {
            return grille[i][j];
        } else {
            throw new IndexOutOfBoundsException("Coordonnées de tuile invalides.");
        }
    }
   
    public int[][] getGrille() {
        return grille;
    }
    
    public String getNom() {
        return nom;
    }
    
    public int getLignes() {
        return lignes;
    }
    
    public int getColonnes() {
        return colonnes;
    }
    
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    public void afficherNiveau() {
        System.out.println("Nom du niveau : " + nom);
        System.out.println("Taille : " + lignes + "x" + colonnes);
        System.out.println("Score : " + score);
        for (int[] ligne : grille) {
            for (int tuile : ligne) {
                System.out.print(tuile + " ");
            }
            System.out.println();
        }
    }
}
