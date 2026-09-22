package com.rohit.inventory.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet; import java.util.Set;
@Entity @Table(name = "products")
public class Product extends BaseEntity {
    @Column(nullable = false, unique = true, length = 80) private String sku;
    @Column(nullable = false, length = 180) private String name;
    private String description;
    @Column(unique = true, length = 120) private String barcode;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "category_id", nullable = false) private Category category;
    @Column(name = "unit_of_measure", nullable = false) private String unitOfMeasure;
    @Column(name = "cost_price", nullable = false) private BigDecimal costPrice;
    @Column(name = "selling_price", nullable = false) private BigDecimal sellingPrice;
    @Column(name = "minimum_stock", nullable = false) private BigDecimal minimumStock;
    @Column(name = "reorder_level", nullable = false) private BigDecimal reorderLevel;
    @Column(name = "maximum_stock", nullable = false) private BigDecimal maximumStock;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private ProductStatus status = ProductStatus.ACTIVE;
    @ManyToMany(fetch = FetchType.LAZY) @JoinTable(name="product_suppliers", joinColumns=@JoinColumn(name="product_id"), inverseJoinColumns=@JoinColumn(name="supplier_id")) private Set<Supplier> suppliers = new HashSet<>();
    public String getSku() { return sku; } public String getName() { return name; }
    public String getBarcode() { return barcode; } public Category getCategory() { return category; }
    public ProductStatus getStatus() { return status; }
    public String getDescription(){return description;} public String getUnitOfMeasure(){return unitOfMeasure;} public BigDecimal getCostPrice(){return costPrice;} public BigDecimal getSellingPrice(){return sellingPrice;} public BigDecimal getMinimumStock(){return minimumStock;} public BigDecimal getReorderLevel(){return reorderLevel;} public BigDecimal getMaximumStock(){return maximumStock;}
    public Set<Supplier> getSuppliers(){return suppliers;}
    public void setSku(String v){sku=v;} public void setName(String v){name=v;} public void setDescription(String v){description=v;} public void setBarcode(String v){barcode=v;} public void setCategory(Category v){category=v;} public void setUnitOfMeasure(String v){unitOfMeasure=v;} public void setCostPrice(BigDecimal v){costPrice=v;} public void setSellingPrice(BigDecimal v){sellingPrice=v;} public void setMinimumStock(BigDecimal v){minimumStock=v;} public void setReorderLevel(BigDecimal v){reorderLevel=v;} public void setMaximumStock(BigDecimal v){maximumStock=v;} public void setStatus(ProductStatus v){status=v;}
    public void setSuppliers(Set<Supplier> v){suppliers=v;}
}
