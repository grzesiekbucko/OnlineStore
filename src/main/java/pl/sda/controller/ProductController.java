package pl.sda.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.buissnes.CategoryBoImpl;
import pl.sda.buissnes.ProductBoImpl;
import pl.sda.dto.ProductDto;
import pl.sda.model.Category;
import pl.sda.model.Product;
import pl.sda.model.User;
import pl.sda.repository.ProductRepository;
import pl.sda.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private final UserService userService;

    @Qualifier("productRepository")
    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private ProductBoImpl productBo;

    @Autowired
    private CategoryBoImpl categoryBo;

    public ProductController(UserService userService, @Qualifier("productRepository") ProductRepository productRepository) {
        this.userService = userService;
        this.productRepository = productRepository;
    }

    @RequestMapping(value = "/admin/products", method = RequestMethod.GET)
    public ModelAndView productViewAll() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userNameProd", user.getName() + " " + user.getLastName());
        modelAndView.addObject("products", productBo.showProduct());
        modelAndView.setViewName("admin/products");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/new", method = RequestMethod.GET)
    public ModelAndView addProductView() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userNameProd", user.getName() + " " + user.getLastName());
        modelAndView.addObject("product", new ProductDto());
        modelAndView.addObject("categories", categoryBo.findAll());
        modelAndView.setViewName("admin/new");
        return modelAndView;
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView editProductView(@PathVariable(name = "id") Long id) throws IOException {
        ModelAndView modelAndView = new ModelAndView("admin/editor");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        ProductDto product = productBo.getProduct(id);
        modelAndView.addObject("userNameEdit", user.getName() + " " + user.getLastName());
        modelAndView.addObject("userEmail", user.getEmail());
        modelAndView.addObject("product", product);
        modelAndView.addObject("categories", categoryBo.findAll());

        return modelAndView;
    }

    @RequestMapping(value = "/preview/{id}")
    public ModelAndView previewProduct(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("admin/product-card");
        Product product = productBo.get(id);
        byte[] prodPic = product.getPicture();

        Category category = product.getCategory();

        modelAndView.addObject("product", product);
        modelAndView.addObject("products", productBo.showProduct());
        modelAndView.addObject("picture", "data:image/png;base64," + Base64.getEncoder().encodeToString(prodPic));

        modelAndView.addObject("categoryProd", productBo.findProductsByCategory(category));


        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("product") ProductDto product) throws IOException {
        productBo.saveProduct(product);
        return "redirect:/admin/products";
    }

    @RequestMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id) {
        productBo.delete(id);
        return "redirect:/admin/products";
    }

}
