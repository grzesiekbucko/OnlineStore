package pl.sda.service;

import pl.sda.model.Product;
import pl.sda.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> showProduct() {
        return productRepository.findAll();
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public Product get(Long id){
        return productRepository.findById(Math.toIntExact(id)).get();

    }

    public void delete(Long id) {
        productRepository.deleteById(Math.toIntExact(id));
    }
}
