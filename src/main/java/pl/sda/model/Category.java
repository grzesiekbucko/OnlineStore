package pl.sda.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "CATEGORY_ID")
    private Long id;

    @OneToMany(mappedBy="category")
    private Set<Product> products = new HashSet<>();

    @Column(length = 100)
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Lob
    @Column(name="picture")
    private byte[] picture;


    public Category(Set<Product> products, String name, String description, byte[] picture) {
        this.products = products;
        this.name = name;
        this.description = description;
        this.picture = picture;
    }

    public Category() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}
