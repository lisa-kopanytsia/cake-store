package com.store.controller;

import com.store.domain.CartItem;
import com.store.domain.Product;
import com.store.domain.ShoppingCart;
import com.store.domain.User;
import com.store.service.CartItemService;
import com.store.service.ProductService;
import com.store.service.ShoppingCartService;
import com.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ProductService productService;

    @RequestMapping("/cart")
    public String shoppingCart(Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        shoppingCartService.updateShoppingCart(shoppingCart);

        model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("shoppingCart", shoppingCart);

        return "shoppingCart";
    }

    @RequestMapping("/addItem")
    public String addItem(
            @ModelAttribute("product") Product product,
            @ModelAttribute("qty") String qty,
            Model model, Principal principal
    ) {
        User user = userService.findByUsername(principal.getName());
        product = productService.findOne(product.getId());


        CartItem cartItem = cartItemService.addProductToCartItem(product, user, Integer.parseInt(qty));
        model.addAttribute("addProductSuccess", true);

        return "forward:/shoppingCart/cart";

    }

    @RequestMapping("/updateCartItem")
    public  String updateShoppingCart(@ModelAttribute("id") Long cartItemId, @ModelAttribute("qty") int qty){
        CartItem cartItem = cartItemService.findById(cartItemId);
        cartItem.setQty(qty);
        cartItemService.updateCartItem(cartItem);
        return "forward:/shoppingCart/cart";
    }

    @RequestMapping("/removeItem")
    public String removeItem(@RequestParam("id") Long id){
        cartItemService.removeCartItem(cartItemService.findById(id));
        return "forward:/shoppingCart/cart";
    }

}
