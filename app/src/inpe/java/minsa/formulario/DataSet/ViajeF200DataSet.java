package minsa.formulario.DataSet;

public class ViajeF200DataSet {

    private int id_pais;
    private String pais;
    private String ciudad;

    public ViajeF200DataSet(int id_pais, String pais, String ciudad) {
        this.id_pais = id_pais;
        this.pais = pais;
        this.ciudad = ciudad;
    }

    public int getId_pais() {
        return id_pais;
    }

    public void setId_pais(int id_pais) {
        this.id_pais = id_pais;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

}
