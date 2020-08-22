package minsa.formulario.DataSet;

import java.io.Serializable;

public class LaboratoryDataDataSet implements Serializable {

    private String date;
    private String hour;
    private int id_type_sample;
    private String type_sample;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public int getId_type_sample() {
        return id_type_sample;
    }

    public void setId_type_sample(int id_type_sample) {
        this.id_type_sample = id_type_sample;
    }

    public String getType_sample() {
        return type_sample;
    }

    public void setType_sample(String type_sample) {
        this.type_sample = type_sample;
    }

}