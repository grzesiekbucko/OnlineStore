package pl.sda.buissnes;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.dto.ProductDto;
import pl.sda.model.Product;
import pl.sda.repository.ProductRepository;

import java.io.IOException;

@Service
public class ProductBoImpl {

    @Autowired
    private ProductRepository productRepository;

    public void saveProduct(ProductDto dto) throws IOException {
        Product product = new Product();
        product.setId(dto.getId());
        product.setModel(dto.getModel());
        product.setName(dto.getName());
        product.setBrand(dto.getBrand());
        product.setGender(dto.getGender());
        product.setBarcode(dto.getBarcode());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setDescription(dto.getDescription());
        product.setPicture(dto.getPicture().getBytes());
        productRepository.save(product);

    }

}
