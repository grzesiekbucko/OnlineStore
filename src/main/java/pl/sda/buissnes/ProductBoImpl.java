package pl.sda.buissnes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.dto.ProductDto;
import pl.sda.model.Category;
import pl.sda.model.Product;
import pl.sda.repository.CategoryRepository;
import pl.sda.repository.ProductRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class ProductBoImpl {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryBoImpl categoryBo;

    public void saveProduct(ProductDto dto) throws IOException {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setModel(dto.getModel());
        product.setBrand(dto.getBrand());
        product.setOption(dto.getOption());
        product.setSize(dto.getSize());
        product.setCategory(categoryRepository.getOne(dto.getCategoryId()));
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setDescription(dto.getDescription());
        product.setPicture(dto.getPicture().getBytes());
        product.setCreationDate(new Date());
        productRepository.save(product);
    }

    public ProductDto getProduct(Long id) throws IOException {
        Product product = productRepository.findById(Math.toIntExact(id)).get();
        Category category = product.getCategory();
        Long categoryId = categoryBo.findCategoryId(category);

        byte[] bytes = product.getPicture();


        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setModel(product.getModel());
        productDto.setBrand(product.getBrand());
        productDto.setOption(product.getOption());
        productDto.setSize(product.getSize());
        productDto.setCategoryId(categoryId);
        productDto.setPrice(product.getPrice());
        productDto.setStock(product.getStock());
        productDto.setDescription(product.getDescription());


        return productDto;
    }

    public void delete(Long id) {
        productRepository.deleteById(Math.toIntExact(id));
    }

    public Product get(Long id) {

        return productRepository.findById(Math.toIntExact(id)).get();
    }

    public List<Product> showProduct() {
        return productRepository.findAll();
    }

    public List<Product> findProductsByCategory(Category category) {
        return productRepository.findProductsByCategory(category);
    }


    @Transactional
    public List<String> categoryProdImg(Category category) {
        List<Product> products = productRepository.findProductsByCategory(category);
        List<String> img = new ArrayList<>();
        for (Product product : products) {
            img.add(Base64.getEncoder().encodeToString(product.getPicture()));
        }
        return img;
    }
}
