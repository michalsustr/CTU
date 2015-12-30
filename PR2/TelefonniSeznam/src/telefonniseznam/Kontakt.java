package telefonniseznam;

/**
 *
 * @author balikm1
 */
public class Kontakt {

    public static final int pocet = 3;
    private String jmeno;
    private String prijmeni;
    private String telefonniCislo;

    public Kontakt(String jmeno, String prijmeni, String telefonniCislo) {
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.telefonniCislo = telefonniCislo;
    }

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public void setPrijmeni(String prijmeni) {
        this.prijmeni = prijmeni;
    }

    public String getTelefonniCislo() {
        return telefonniCislo;
    }

    public void setTelefonniCislo(String telefonniCislo) {
        this.telefonniCislo = telefonniCislo;
    }

    @Override
    public String toString() {
        return prijmeni + " " + jmeno + " \t" + telefonniCislo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof Kontakt) {
            Kontakt k = (Kontakt) o;
            if (this.jmeno.equals(k.jmeno) && this.prijmeni.equals(k.prijmeni) && this.telefonniCislo.equals(k.telefonniCislo)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.jmeno != null ? this.jmeno.hashCode() : 0);
        hash = 59 * hash + (this.prijmeni != null ? this.prijmeni.hashCode() : 0);
        hash = 59 * hash + (this.telefonniCislo != null ? this.telefonniCislo.hashCode() : 0);
        return hash;
    }
}
