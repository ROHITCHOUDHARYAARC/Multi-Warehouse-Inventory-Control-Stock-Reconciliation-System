package com.rohit.inventory.entity;

import jakarta.persistence.*;
@Entity @Table(name = "categories")
public class Category extends BaseEntity {
    @Column(nullable = false, length = 120) private String name;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "parent_category_id") private Category parentCategory;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private ProductStatus status = ProductStatus.ACTIVE;
    public String getName() { return name; } public ProductStatus getStatus() { return status; }
    public String getDescription(){return description;} public Category getParentCategory(){return parentCategory;} public void setName(String v){name=v;} public void setDescription(String v){description=v;} public void setParentCategory(Category v){parentCategory=v;} public void setStatus(ProductStatus v){status=v;}
}
