package com.rohit.inventory.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity @Table(name = "warehouses")
public class Warehouse extends BaseEntity {
    @Column(nullable = false, unique = true, length = 40) private String code;
    @Column(nullable = false, length = 150) private String name;
    @Column(name = "address_line1") private String addressLine1;
    private String city; private String state;
    @Column(nullable = false) private String country = "India";
    @Column(name = "capacity_quantity", nullable = false) private BigDecimal capacityQuantity;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private WarehouseStatus status = WarehouseStatus.ACTIVE;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "manager_user_id") private User manager;
    public String getCode() { return code; } public String getName() { return name; } public String getAddressLine1(){return addressLine1;} public String getState(){return state;} public String getCountry(){return country;} public User getManager(){return manager;}
    public String getCity() { return city; } public WarehouseStatus getStatus() { return status; }
    public BigDecimal getCapacityQuantity() { return capacityQuantity; }
    public void setCode(String v){code=v;} public void setName(String v){name=v;} public void setAddressLine1(String v){addressLine1=v;} public void setCity(String v){city=v;} public void setState(String v){state=v;} public void setCountry(String v){country=v;} public void setCapacityQuantity(BigDecimal v){capacityQuantity=v;} public void setStatus(WarehouseStatus v){status=v;} public void setManager(User v){manager=v;}
}
