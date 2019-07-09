package pl.sda.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.sda.buissnes.ProductBoImpl;
import pl.sda.dto.ProductDto;
import pl.sda.model.Product;
import pl.sda.model.User;
import pl.sda.repository.ProductRepository;
import pl.sda.service.ProductService;
import pl.sda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Base64;

@Controller
public class ProductController {

    @Autowired
    private final ProductService productService;

    @Autowired
    private final UserService userService;

    @Qualifier("productRepository")
    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private ProductBoImpl productBo;

    public ProductController(ProductService productService, UserService userService, @Qualifier("productRepository") ProductRepository productRepository) {
        this.productService = productService;
        this.userService = userService;
        this.productRepository = productRepository;
    }

    @RequestMapping(value = "/admin/products", method = RequestMethod.GET)
    public ModelAndView productViewAll() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userNameProd", user.getName() + " " + user.getLastName());
        modelAndView.addObject("products", productRepository.findAll());
        modelAndView.setViewName("admin/products");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/new", method = RequestMethod.GET)
    public ModelAndView addProductView() {
        ModelAndView modelAndView = new ModelAndView();
        Product product = new Product();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userNameProd",user.getName() + " " + user.getLastName());
        modelAndView.addObject("product", product);
        modelAndView.setViewName("/admin/new");
        return modelAndView;
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView editProductView(@PathVariable(name = "id") Long id){
        ModelAndView modelAndView = new ModelAndView("/admin/editor");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Product product = productService.get(id);
        modelAndView.addObject("userNameEdit", user.getName() + " " + user.getLastName());
        modelAndView.addObject("userEmail", user.getEmail());
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @RequestMapping("/preview/{id}")
    public ModelAndView previewProduct(@PathVariable(name = "id") Long id){
        ModelAndView modelAndView = new ModelAndView("/admin/card");
        Product product = productService.get(id);
        byte[] prodPic = product.getPicture();
        modelAndView.addObject("product", product);
        modelAndView.addObject("picture", Base64.getEncoder().encodeToString(prodPic));
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("product") ProductDto product) throws IOException {
        productBo.saveProduct(product);
        return "redirect:/admin/products";
    }


    @RequestMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id) {
        productService.delete(id);
        return "redirect:/admin/products";
    }

}
