package com.mycompany.myapp.domain;


import javax.persistence.*;
import java.io.Serializable;

/**
 * A Personne.
 */
@Entity
@Table(name = "personne")
public class Personne implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nbrtest")
    private String nbrtest;

    @Column(name = "positif")
    private String positif;

    @Column(name = "cascommunautaire")
    private String cascommunautaire;

    @Column(name = "deces")
    private String deces;

    @Column(name = "suivis")
    private String suivis;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNbrtest() {
        return nbrtest;
    }

    public Personne nbrtest(String nbrtest) {
        this.nbrtest = nbrtest;
        return this;
    }

    public void setNbrtest(String nbrtest) {
        this.nbrtest = nbrtest;
    }

    public String getPositif() {
        return positif;
    }

    public Personne positif(String positif) {
        this.positif = positif;
        return this;
    }

    public void setPositif(String positif) {
        this.positif = positif;
    }

    public String getCascommunautaire() {
        return cascommunautaire;
    }

    public Personne cascommunautaire(String cascommunautaire) {
        this.cascommunautaire = cascommunautaire;
        return this;
    }

    public void setCascommunautaire(String cascommunautaire) {
        this.cascommunautaire = cascommunautaire;
    }

    public String getDeces() {
        return deces;
    }

    public Personne deces(String deces) {
        this.deces = deces;
        return this;
    }

    public void setDeces(String deces) {
        this.deces = deces;
    }

    public String getSuivis() {
        return suivis;
    }

    public Personne suivis(String suivis) {
        this.suivis = suivis;
        return this;
    }

    public void setSuivis(String suivis) {
        this.suivis = suivis;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Personne)) {
            return false;
        }
        return id != null && id.equals(((Personne) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Personne{" +
            "id=" + getId() +
            ", nbrtest='" + getNbrtest() + "'" +
            ", positif='" + getPositif() + "'" +
            ", cascommunautaire='" + getCascommunautaire() + "'" +
            ", deces='" + getDeces() + "'" +
            ", suivis='" + getSuivis() + "'" +
            "}";
    }
}
