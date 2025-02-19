package com.unibague.magno.domain.model.integra;

public class IntegraDependency {

    private String dep_code;
    private String dep_name;
    private String dep_father;
    private String dep_nom_father;

    public IntegraDependency(String dep_code, String dep_name, String dep_father, String dep_nom_father) {
        this.dep_code = dep_code;
        this.dep_name = dep_name;
        this.dep_father = dep_father;
        this.dep_nom_father = dep_nom_father;
    }

    public IntegraDependency() {
    }

    public String getDep_code() {
        return dep_code;
    }

    public void setDep_code(String dep_code) {
        this.dep_code = dep_code;
    }

    public String getDep_name() {
        return dep_name;
    }

    public void setDep_name(String dep_name) {
        this.dep_name = dep_name;
    }

    public String getDep_father() {
        return dep_father;
    }

    public void setDep_father(String dep_father) {
        this.dep_father = dep_father;
    }

    public String getDep_nom_father() {
        return dep_nom_father;
    }

    public void setDep_nom_father(String dep_nom_father) {
        this.dep_nom_father = dep_nom_father;
    }
}
