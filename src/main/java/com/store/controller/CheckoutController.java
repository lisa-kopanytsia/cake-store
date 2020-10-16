package com.store.controller;

import com.store.domain.CartItem;
import com.store.domain.Order;
import com.store.domain.ShoppingCart;
import com.store.domain.User;
import com.store.service.CartItemService;
import com.store.service.OrderService;
import com.store.service.ShoppingCartService;
import com.store.service.UserService;
import com.store.utility.MailConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Controller
public class CheckoutController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MailConstructor mailConstructor;

    @RequestMapping("/checkout")
    public String checkout(@RequestParam("id") Long cartId, @RequestParam(value = "missingRequiredField", required = false) boolean missingRequiredField, Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());

        if(cartId != user.getShoppingCart().getId()){
            return "badRequestPage";
        }
        List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());
        if(cartItemList.size() == 0){
            model.addAttribute("emptyCart", true);
            return "forward:/shoppingCart/cart";
        }
        model.addAttribute("cartItemList", cartItemList);

        ShoppingCart shoppingCart = user.getShoppingCart();
        model.addAttribute("shoppingCart", user.getShoppingCart());

        if (missingRequiredField){
            model.addAttribute("missingRequiredField", true);
        }
        return "checkout";
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public String checkoutPost(@ModelAttribute("shippingAddress") String shippingAddress,
                               @ModelAttribute("firstNameRecipient") String firstNameRecipient,
                               @ModelAttribute("lastNameRecipient") String lastNameRecipient,
                               @ModelAttribute("fatherNameRecipient") String fatherNameRecipient,
                               @ModelAttribute("phoneRecipient") String phoneRecipient,
                               Principal principal, Model model){
        ShoppingCart shoppingCart = userService.findByUsername(principal.getName()).getShoppingCart();
        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
        model.addAttribute("cartItemList", cartItemList);
        if(shippingAddress.isEmpty()){
            return "redirect:/checkout?id="+shoppingCart.getId()+"&missingRequiredField=true";
        }

        User user = userService.findByUsername(principal.getName());
        Order order = orderService.createOrder(shoppingCart, shippingAddress, user,firstNameRecipient,lastNameRecipient,fatherNameRecipient, phoneRecipient);

        mailSender.send(mailConstructor.constructOrderConfirmationEmail(user, order));

        shoppingCartService.clearShoppingCart(shoppingCart);
        LocalDate today = LocalDate.now();
        LocalDate estimatedDeliveryDate = today.plusDays(1);

        model.addAttribute("estimatedDeliveryDate", estimatedDeliveryDate);

        return "orderSubmittedPage";
    }
}
