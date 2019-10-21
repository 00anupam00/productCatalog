package com.adcash.productcatalog.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(name = "customer")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer implements Serializable {
    private static final long serialId= 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    private int customerId;

    @Column(length = 50)
    @NotNull
    private String name;

    @Column(length = 100, unique = true)
    @NotNull
    private String email;

    @Column(length = 100)
    @NotNull
    private String password;

    @Column(name = "credit_card", columnDefinition = "LONGTEXT")
    private String creditCard;

    @Column(name = "address_1", length = 100)
    private String address1;

    @Column(name = "address_2", length = 100)
    private String address2;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String region;

    @Column(name = "postal_code", length = 100)
    private String postalCode;

    @Column(name = "country", length = 100)
    private String country;

/*    @Column(name = "shipping_region_id")
    @NotNull
    private int shippingRegionId= 1; //defaults to 1*/

    @ManyToOne(targetEntity = ShippingRegion.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "shipping_region_id", referencedColumnName = "shipping_region_id")
    private ShippingRegion shippingRegion;

    @Column(name = "day_phone", length = 100)
    private String dayPhone;

    @Column(name = "eve_phone", length = 100)
    private String evePhone;

    @Column(name = "mob_phone", length = 100)
    private String mobPhone;

}
