package pl.sda.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private int id;

    @Column(name = "barcode")
    private int barcode;

    @Column(name = "name")
    @NotEmpty(message = "*Please provide a name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "stock")
    private int stock;

    @Column(name = "description")
    private String description;

    public Product(int barcode, String name, double price, int stock, String description) {
        this.barcode = barcode;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBarcode() {
        return barcode;
    }

    public void setBarcode(int barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Product{");
        sb.append("id=").append(id);
        sb.append(", barcode=").append(barcode);
        sb.append(", name='").append(name).append('\'');
        sb.append(", price=").append(price);
        sb.append(", stock=").append(stock);
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                barcode == product.barcode &&
                Double.compare(product.price, price) == 0 &&
                stock == product.stock &&
                Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(barcode, name);
    }
}

