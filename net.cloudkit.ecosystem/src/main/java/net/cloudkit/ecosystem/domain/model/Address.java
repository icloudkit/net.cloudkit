package net.cloudkit.ecosystem.domain.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 地址
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2016/3/17 13:51
 */
@Embeddable
public class Address implements ValueObject<Address> {

    private static final long serialVersionUID = 1L;

    @Basic
    @Column(name = "STREET", nullable = false)
    private String street;

    @Basic
    @Column(name = "ZIP", nullable = false)
    private String zip;

    @Basic
    @Column(name = "PROVINCE")
    private String province;

    @Column(name = "CITY", nullable = false)
    private String city;

    // no overriding here
    /** Country */
    @Basic
    @Column(name = "NATIONALITY")
    private String nationality;

    /**
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @param street the street to set
     */
    public Address setStreet(String street) {
        this.street = street;
        return this;
    }

    /**
     * @return the zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * @param zip the zip to set
     */
    public Address setZip(String zip) {
        this.zip = zip;
        return this;
    }

    /**
     * @return the province
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param province the province to set
     */
    public Address setProvince(String province) {
        this.province = province;
        return this;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public Address setCity(String city) {
        this.city = city;
        return this;
    }

    /**
     * @return the nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * @param nationality the nationality to set
     */
    public Address setNationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    @Override
    public boolean sameValueAs(Address other) {
        return false;
    }
}
