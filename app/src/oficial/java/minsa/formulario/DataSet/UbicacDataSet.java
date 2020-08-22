package minsa.formulario.DataSet;

public class UbicacDataSet {

    private static UbicacDataSet instance;

    private String UbicacLatitu;
    private String UbicacLongit;

    private UbicacDataSet(){}

    public String getUbicacLatitu() {
        return UbicacLatitu;
    }
    public void setUbicacLatitu(String UbicacLatitu) {
        this.UbicacLatitu = UbicacLatitu;
    }

    public String getUbicacLongit() {
        return UbicacLongit;
    }
    public void setUbicacLongit(String UbicacLongit) {
        this.UbicacLongit = UbicacLongit;
    }

    public static synchronized UbicacDataSet getInstance(){
        if(instance==null){
            instance=new UbicacDataSet();
        }
        return instance;
    }

}
