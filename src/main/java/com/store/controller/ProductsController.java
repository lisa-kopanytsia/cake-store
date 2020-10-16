package com.store.controller;

import com.store.domain.Product;
import com.store.domain.User;
import com.store.service.ProductService;
import com.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @RequestMapping("/classic-cakes")
    public String classicCakes(@RequestParam("category") String category, Model model, Principal principal){
        if(principal!=null){
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        String classActiveCategory = "active"+category;
        classActiveCategory = classActiveCategory.replaceAll("\\s+","");
        classActiveCategory = classActiveCategory.replaceAll("&","");
        model.addAttribute(classActiveCategory, true);

        List<Product> productList = productService.findByCategory(category);
        if(productList.isEmpty()){
            model.addAttribute("emptyList", true);
            return "classicCakes";
        }
        model.addAttribute("productList", productList);
        return "classicCakes";
    }
    @RequestMapping("/cheesecakes")
    public String cheesecakes(@RequestParam("category") String category, Model model, Principal principal){
        if(principal!=null){
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        String classActiveCategory = "active"+category;
        classActiveCategory = classActiveCategory.replaceAll("\\s+","");
        classActiveCategory = classActiveCategory.replaceAll("&","");
        model.addAttribute(classActiveCategory, true);

        List<Product> productList = productService.findByCategory(category);
        if(productList.isEmpty()){
            model.addAttribute("emptyList", true);
            return "cheesecakes";
        }
        model.addAttribute("productList", productList);
        return "cheesecakes";
    }

    @RequestMapping("/sponge_cakes")
    public String spongeCakes(@RequestParam("category") String category, Model model, Principal principal){
        if(principal!=null){
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        String classActiveCategory = "active"+category;
        classActiveCategory = classActiveCategory.replaceAll("\\s+","");
        classActiveCategory = classActiveCategory.replaceAll("&","");
        model.addAttribute(classActiveCategory, true);

        List<Product> productList = productService.findByCategory(category);
        if(productList.isEmpty()){
            model.addAttribute("emptyList", true);
            return "spongeCakes";
        }
        model.addAttribute("productList", productList);
        return "spongeCakes";
    }

    @RequestMapping("/cookies")
    public String cookies(@RequestParam("category") String category, Model model, Principal principal){
        if(principal!=null){
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        String classActiveCategory = "active"+category;
        classActiveCategory = classActiveCategory.replaceAll("\\s+","");
        classActiveCategory = classActiveCategory.replaceAll("&","");
        model.addAttribute(classActiveCategory, true);

        List<Product> productList = productService.findByCategory(category);
        if(productList.isEmpty()){
            model.addAttribute("emptyList", true);
            return "cookies";
        }
        model.addAttribute("productList", productList);
        return "cookies";
    }

    @RequestMapping("/cakes")
    public String cakes(@RequestParam("category") String category, Model model, Principal principal){
        if(principal!=null){
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        String classActiveCategory = "active"+category;
        classActiveCategory = classActiveCategory.replaceAll("\\s+","");
        classActiveCategory = classActiveCategory.replaceAll("&","");
        model.addAttribute(classActiveCategory, true);

        List<Product> productList = productService.findByCategory(category);
        if(productList.isEmpty()){
            model.addAttribute("emptyList", true);
            return "cakes";
        }
        model.addAttribute("productList", productList);
        return "cakes";
    }

    @RequestMapping("/jams")
    public String jams(@RequestParam("category") String category, Model model, Principal principal){
        if(principal!=null){
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        String classActiveCategory = "active"+category;
        classActiveCategory = classActiveCategory.replaceAll("\\s+","");
        classActiveCategory = classActiveCategory.replaceAll("&","");
        model.addAttribute(classActiveCategory, true);

        List<Product> productList = productService.findByCategory(category);
        if(productList.isEmpty()){
            model.addAttribute("emptyList", true);
            return "jams";
        }
        model.addAttribute("productList", productList);
        return "jams";
    }

    @RequestMapping("/cupcakes")
    public String cupcakes(@RequestParam("category") String category, Model model, Principal principal){
        if(principal!=null){
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        String classActiveCategory = "active"+category;
        classActiveCategory = classActiveCategory.replaceAll("\\s+","");
        classActiveCategory = classActiveCategory.replaceAll("&","");
        model.addAttribute(classActiveCategory, true);

        List<Product> productList = productService.findByCategory(category);
        if(productList.isEmpty()){
            model.addAttribute("emptyList", true);
            return "cupcakes";
        }
        model.addAttribute("productList", productList);
        return "cupcakes";
    }

    @RequestMapping("/chocolate_and_sweets")
    public String chocolateAndSweets(@RequestParam("category") String category, Model model, Principal principal){
        if(principal!=null){
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        String classActiveCategory = "active"+category;
        classActiveCategory = classActiveCategory.replaceAll("\\s+","");
        classActiveCategory = classActiveCategory.replaceAll("&","");
        model.addAttribute(classActiveCategory, true);

        List<Product> productList = productService.findByCategory(category);
        if(productList.isEmpty()){
            model.addAttribute("emptyList", true);
            return "chocolateAndSweets";
        }
        model.addAttribute("productList", productList);
        return "chocolateAndSweets";
    }

    @RequestMapping("/mousse_cakes")
    public String mousseCakes(@RequestParam("category") String category, Model model, Principal principal){
        if(principal!=null){
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        String classActiveCategory = "active"+category;
        classActiveCategory = classActiveCategory.replaceAll("\\s+","");
        classActiveCategory = classActiveCategory.replaceAll("&","");
        model.addAttribute(classActiveCategory, true);

        List<Product> productList = productService.findByCategory(category);
        if(productList.isEmpty()){
            model.addAttribute("emptyList", true);
            return "mousseCakes";
        }
        model.addAttribute("productList", productList);
        return "mousseCakes";
    }

    @RequestMapping("**/productDetail")
    public String productDetail(@PathParam("id") Long id, Model model, Principal principal){
        if(principal != null){
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        Product product = productService.findOne(id);
        model.addAttribute("product",product);
        List<Integer> qtyList = Arrays.asList(1,2,3,4,5,6,7,8,9,10);

        model.addAttribute("qtyList", qtyList);
        model.addAttribute("qty", 1);

        return "productDetail";
    }

}
