package pl.sda.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import pl.sda.model.User;
import pl.sda.repository.ProductRepository;
import pl.sda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Qualifier("productRepository")
    @Autowired
    private ProductRepository productRepository;

    public LoginController(@Qualifier("productRepository") ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = {"/access-denied"}, method = RequestMethod.GET)
    public ModelAndView access() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.setViewName("access-denied");
        modelAndView.addObject("userNameAccessDenied", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @RequestMapping(value = "/admin/home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.addObject("adminName", "Welcome " + user.getName());

        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

    @RequestMapping(value = "/user/user-home", method = RequestMethod.GET)
    public ModelAndView homeUser() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("userMessage", "Content Available Only for Users with User Role");
        modelAndView.setViewName("user/user-home");
        return modelAndView;
    }

    @RequestMapping(value = "/default", method = RequestMethod.GET)
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/admin/home";
        }
        if (request.isUserInRole("ROLE_USER")) {
            return "redirect:/user/user-home";
        }
        return "redirect:/login";
    }
}
