package pl.sda.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {

    @Autowired
    private final ProductService productService;

    @Autowired
    private final UserService userService;

    @Qualifier("productRepository")
    @Autowired
    private final ProductRepository productRepository;

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
        modelAndView.addObject("userNameProd", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
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
        modelAndView.addObject("userNameProd", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("product", product);
        modelAndView.setViewName("/admin/new");
        return modelAndView;
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView editProductView(@PathVariable(name = "id") Long id){
        ModelAndView modelAndView = new ModelAndView("/admin/edit");
        Product product = productService.get(id);
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @RequestMapping("/preview/{id}")
    public ModelAndView previewProduct(@PathVariable(name = "id") Long id){
        ModelAndView modelAndView = new ModelAndView("/admin/card");
        Product product = productService.get(id);
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("product") Product product) {
        productService.save(product);
        return "redirect:/admin/products";
    }

    @RequestMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id) {
        productService.delete(id);
        return "redirect:/admin/products";
    }

}
