package com.unibague.magno.domain.model.integra;

/**
 * Domain model representing a dependency record from the Integra system.
 */
public class IntegraDependency {

    private String depCode;
    private String depName;
    private String depFather;
    private String depNomFather;

    public IntegraDependency(String depCode, String depName, String depFather, String depNomFather) {
        this.depCode = depCode;
        this.depName = depName;
        this.depFather = depFather;
        this.depNomFather = depNomFather;
    }

    public IntegraDependency() {
    }

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getDepFather() {
        return depFather;
    }

    public void setDepFather(String depFather) {
        this.depFather = depFather;
    }

    public String getDepNomFather() {
        return depNomFather;
    }

    public void setDepNomFather(String depNomFather) {
        this.depNomFather = depNomFather;
    }
}
