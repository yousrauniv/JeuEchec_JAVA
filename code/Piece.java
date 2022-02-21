import java.io.Serializable;

public abstract class Piece implements Serializable {

  protected String couleur; // Protected -> Pions puisse avoir accès aux attributs de Piece
  protected int ligne;
  protected int colonne;
  protected Echiquier echiquier;
  protected Piece[] plateau;
  private static final long serialVersionUID = 4654564643165484L; // La valeur du serialVersionUID de cet exemple a été
                                                                  // générée avec Eclipse

  public Piece(final String couleur, final int ligne, final int colonne, final Echiquier echiquier) {
    this.couleur = couleur; // Différencier couleur attribut/argument
    this.ligne = ligne;
    this.colonne = colonne;
    this.echiquier = echiquier;
    this.plateau = this.echiquier.getEchiquier();
  }

  public Piece[] getPlateau() { // Accès
    return this.plateau;
  }

  public String getCouleur() {
    return this.couleur;
  }

  public int getLigne() {
    return this.ligne;
  }

  public int getColonne() {
    return this.colonne;
  }

  public void setCouleur(final String couleur) { // Modifier
    this.couleur = couleur;
  }

  public void setLigne(final int ligne) {
    this.ligne = ligne;
  }

  public void setColonne(final int colonne) {
    this.colonne = colonne;
  }

  public boolean verifCouleurPiece(final int ligneArriver, final int colonneArriver) {
    final Piece p = this.plateau[8 * ligneArriver + colonneArriver]; // on met notre piece qui est sur la case
    if (p != null && this.couleur.equals(p.getCouleur())) { // si la case n'est pas vide et que la couleur de l'objet
                                                            // courant est egale a la couleur de la piece
      return false; // si c'est egale alors return false car une piece noir ne peut pas manger une
                    // piece noir
    }
    return true;
  }

  public abstract boolean verifMouvement(int ligneArriver, int colonneArriver); // Pas de code (classe fille)

  public boolean mouvement(final int ligneArriver, final int colonneArriver) {
    if (this.verifMouvement(ligneArriver, colonneArriver)) {
      final Piece caseArriver = this.plateau[8 * ligneArriver + colonneArriver];
      final int x = this.ligne;
      final int y = this.colonne;
      final Piece caseDepart = this.plateau[8 * x + y];

      this.plateau[8 * ligneArriver + colonneArriver] = caseDepart;
      this.plateau[8 * x + y] = null;
      this.ligne = ligneArriver;
      this.colonne = colonneArriver;

      if (this.echiquier.echec(couleur)) {
        this.plateau[8 * ligneArriver + colonneArriver] = caseArriver;
        this.plateau[8 * x + y] = caseDepart;
        this.ligne = x;
        this.colonne = y;
        return false;
      }
      return true;
    } else {
      System.out.println("Mouvement impossible!");
    }
    return false;
  }

  public String direction(final int ligneArriver, final int colonneArriver) {
    final int[] tab = new int[2];

    if (ligneArriver < this.ligne)
      tab[0] = -1; // vers le haut
    else if (ligneArriver > this.ligne)
      tab[0] = 1; // vers le bas
    else
      tab[0] = 0; // reste sur la meme ligne

    if (colonneArriver < this.colonne)
      tab[1] = -1; // vers la gauche
    else if (colonneArriver > this.colonne)
      tab[1] = 1; // vers la droite
    else
      tab[1] = 0; // reste sur la meme colonne

    if (tab[0] == -1 && tab[1] == -1)
      return "haut gauche";
    else if (tab[0] == -1 && tab[1] == 1)
      return "haut droite";
    else if (tab[0] == 1 && tab[1] == -1)
      return "bas gauche";
    else if (tab[0] == 1 && tab[1] == 1)
      return "bas droite";
    else if (tab[0] == 0 && tab[1] == -1)
      return "gauche";
    else if (tab[0] == 0 && tab[1] == 1)
      return "droite";
    else if (tab[0] == 1 && tab[1] == 0)
      return "bas";
    else if (tab[0] == -1 && tab[1] == 0)
      return "haut";
    else
      return "aucun changement";
  }

  public boolean testAutoEchec(final int ligneArriver, final int colonneArriver) {
    if (this.verifMouvement(ligneArriver, colonneArriver)) {

      final Piece caseArriver = this.plateau[8 * ligneArriver + colonneArriver];
      final int x = this.ligne;
      final int y = this.colonne;
      final Piece caseDepart = this.plateau[8 * x + y];

      this.plateau[8 * ligneArriver + colonneArriver] = caseDepart;
      this.plateau[8 * x + y] = null;
      this.ligne = ligneArriver;
      this.colonne = colonneArriver;

      if (this.echiquier.echec(couleur)) {
        this.plateau[8 * ligneArriver + colonneArriver] = caseArriver;
        this.plateau[8 * x + y] = caseDepart;
        this.ligne = x;
        this.colonne = y;
        return false;
      }

      this.plateau[8 * ligneArriver + colonneArriver] = caseArriver;
      this.plateau[8 * x + y] = caseDepart;
      this.ligne = x;
      this.colonne = y;
      return true;
    }
    return false;
  }
}
