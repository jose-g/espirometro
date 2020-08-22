package minsa.formulario.DataSet;

public class TipDocDataSet {

    private String TipDocIdenti;
    private String TipDocDescri;

    public TipDocDataSet() {
    }

    public TipDocDataSet(String tipDocIdenti, String tipDocDescri) {
        TipDocIdenti = tipDocIdenti;
        TipDocDescri = tipDocDescri;
    }

    public String getTipDocIdenti() {
        return TipDocIdenti;
    }
    public void setTipDocIdenti(String TipDocIdenti) {
        this.TipDocIdenti = TipDocIdenti;
    }

    public String getTipDocDescri() {
        return TipDocDescri;
    }
    public void setTipDocDescri(String TipDocDescri) {
        this.TipDocDescri = TipDocDescri;
    }

}
