package pl.sda.model;

import javax.persistence.*;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name ="model")
    private String model;

    @Column(name = "brand")
    private String brand;

    @Column(name = "option")
    private String option;

    @Column(name = "size")
    private String size;

    @ManyToOne
    private Category category;

    @Column
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @Column(name = "price")
    private double price;

    @Column(name = "stock")
    private int stock;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Lob
    @Column(name="picture")
    private byte[] picture;

    public Product() {
    }

    public Product(String name, String model, String brand, String option, String size, Category category, Date creationDate, double price, int stock, String description, byte[] picture) {
        this.name = name;
        this.model = model;
        this.brand = brand;
        this.option = option;
        this.size = size;
        this.category = category;
        this.creationDate = creationDate;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.picture = picture;
    }

    public Product(List<Product> showProduct) {

    }

    public String getImageString(){
        String imageString = "data:image/png;base64," + Base64.getEncoder().encodeToString(picture);
        return imageString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
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

