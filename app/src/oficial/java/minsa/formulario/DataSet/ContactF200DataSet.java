package minsa.formulario.DataSet;

import java.util.ArrayList;

public class ContactF200DataSet {

    private String id_document_type;
    private String document_type;
    private String number_document;
    private String date_birth;
    private String name;
    private String paternal;
    private String maternal;
    private int id_relationship;
    private String relationship;
    private int edad;
    private String id_sex;
    private String sex;
    private String mobile;
    private String address;
    private int f100;
    private ArrayList<RiskFactors> riskFactors;

    public String getId_document_type() {
        return id_document_type;
    }

    public void setId_document_type(String id_document_type) {
        this.id_document_type = id_document_type;
    }

    public String getDocument_type() {
        return document_type;
    }

    public void setDocument_type(String document_type) {
        this.document_type = document_type;
    }

    public String getNumber_document() {
        return number_document;
    }

    public void setNumber_document(String number_document) {
        this.number_document = number_document;
    }

    public String getDate_birth() {
        return date_birth;
    }

    public void setDate_birth(String date_birth) {
        this.date_birth = date_birth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPaternal() {
        return paternal;
    }

    public void setPaternal(String paternal) {
        this.paternal = paternal;
    }

    public String getMaternal() {
        return maternal;
    }

    public void setMaternal(String maternal) {
        this.maternal = maternal;
    }

    public int getId_relationship() {
        return id_relationship;
    }

    public void setId_relationship(int id_relationship) {
        this.id_relationship = id_relationship;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getId_sex() {
        return id_sex;
    }

    public void setId_sex(String id_sex) {
        this.id_sex = id_sex;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getF100() {
        return f100;
    }

    public void setF100(int f100) {
        this.f100 = f100;
    }

    public ArrayList<RiskFactors> getRiskFactors() {
        return riskFactors;
    }

    public void setRiskFactors(ArrayList<RiskFactors> riskFactors) {
        this.riskFactors = riskFactors;
    }

    public static class RiskFactors {

        private int id_risk_factor;
        private String risk_factor;

        public int getId_risk_factor() {
            return id_risk_factor;
        }

        public void setId_risk_factor(int id_risk_factor) {
            this.id_risk_factor = id_risk_factor;
        }

        public String getRisk_factor() {
            return risk_factor;
        }

        public void setRisk_factor(String risk_factor) {
            this.risk_factor = risk_factor;
        }

    }

}
