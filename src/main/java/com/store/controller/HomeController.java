package com.store.controller;

import java.security.Principal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import com.store.domain.CartItem;
import com.store.domain.Order;
import com.store.domain.Product;
import com.store.service.CartItemService;
import com.store.service.OrderService;
import com.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.store.domain.User;
import com.store.domain.security.PasswordResetToken;
import com.store.domain.security.Role;
import com.store.domain.security.UserRole;
import com.store.service.UserService;
import com.store.service.impl.UserSecurityService;
import com.store.utility.MailConstructor;
import com.store.utility.SecurityUtility;

@Controller
public class HomeController {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MailConstructor mailConstructor;

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserSecurityService userSecurityService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private ProductService productService;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/products")
	public String products() {
		return "products";
	}

	@RequestMapping("/dostavka-oplata")
	public String dostavka() {
		return "dostavka-oplata";
	}

	@RequestMapping("/contacts")
	public String contacts() {
		return "contacts";
	}

	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("classActiveLogin", true);
		return "myAccount";
	}

	@RequestMapping("/forgetPassword")
	public String forgetPassword(
			HttpServletRequest request,
			@ModelAttribute("email") String email,
			Model model
			) {

		model.addAttribute("classActiveForgetPassword", true);
		
		User user = userService.findByEmail(email);
		
		if (user == null) {
			model.addAttribute("emailNotExist", true);
			return "myAccount";
		}
		
		String password = SecurityUtility.randomPassword();
		
		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedPassword);
		
		userService.save(user);
		
		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(user, token);
		
		String appUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		
		SimpleMailMessage newEmail = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);
		
		mailSender.send(newEmail);
		
		model.addAttribute("forgetPasswordEmailSent", "true");
		
		
		return "myAccount";
	}

	@RequestMapping("/myProfile")
	public String myProfile(Model model, Principal principal){
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user",user);
		model.addAttribute("orderList", user.getOrderList());
		model.addAttribute("classActiveEdit", true);

		return "myProfile";

	}

	@RequestMapping(value="/newUser", method = RequestMethod.POST)
	public String newUserPost(
			HttpServletRequest request,
			@ModelAttribute("email") String userEmail,
			@ModelAttribute("username") String username,
			Model model
			) throws Exception{
		model.addAttribute("classActiveNewAccount", true);
		model.addAttribute("email", userEmail);
		model.addAttribute("username", username);
		
		if (userService.findByUsername(username) != null) {
			model.addAttribute("usernameExists", true);
			
			return "myAccount";
		}
		
		if (userService.findByEmail(userEmail) != null) {
			model.addAttribute("emailExists", true);
			
			return "myAccount";
		}
		
		User user = new User();
		user.setUsername(username);
		user.setEmail(userEmail);
		
		String password = SecurityUtility.randomPassword();
		
		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedPassword);
		
		Role role = new Role();
		role.setRoleId(1);
		role.setName("ROLE_USER");
		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(user, role));
		userService.createUser(user, userRoles);
		
		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(user, token);
		
		String appUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		
		SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);
		
		mailSender.send(email);
		
		model.addAttribute("emailSent", "true");
		model.addAttribute("orderList", user.getOrderList());

		return "myAccount";
	}
	

	@RequestMapping("/newUser")
	public String newUser(Locale locale, @RequestParam("token") String token, Model model) {
		PasswordResetToken passToken = userService.getPasswordResetToken(token);

		if (passToken == null) {
			String message = "Invalid Token.";
			model.addAttribute("message", message);
			return "redirect:/badRequest";
		}

		User user = passToken.getUser();
		String username = user.getUsername();

		UserDetails userDetails = userSecurityService.loadUserByUsername(username);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		model.addAttribute("user", user);

		model.addAttribute("classActiveEdit", true);
		return "myProfile";
	}

	@RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
	public String updateUserInfo(@ModelAttribute("user") User user, @ModelAttribute("newPassword") String newPassword, Model model) throws Exception{
		User currentUser = userService.findById(user.getId());
		if (currentUser == null){
			throw new Exception("User not found");
		}
		/*check email already exists*/
		if (userService.findByEmail(user.getEmail())!=null){
			if (userService.findByEmail(user.getEmail()).getId() != currentUser.getId()){
				model.addAttribute("emailExists", true);
				return "myProfile";
			}
		}
		/*check username already exists*/
		if (userService.findByUsername(user.getUsername())!=null){
			if (userService.findByUsername(user.getUsername()).getId() != currentUser.getId()){
				model.addAttribute("usernameExists", true);
				return "myProfile";
			}
		}
		/* update Password*/
		if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")){
			BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
			String dbpassword = currentUser.getPassword();
			if (passwordEncoder.matches(user.getPassword(),dbpassword)){
				currentUser.setPassword(passwordEncoder.encode(newPassword));
			} else {
				model.addAttribute("incorrectPassword",true);
				return "myProfile";
			}
		}
		currentUser.setFirstName(user.getFirstName());
		currentUser.setLastName(user.getLastName());
		currentUser.setUsername(user.getUsername());
		currentUser.setEmail(user.getEmail());

		userService.save(currentUser);
		model.addAttribute("updateSuccess", true);
		model.addAttribute("user", currentUser);
		model.addAttribute("classActiveEdit", true);

		UserDetails userDetails = userSecurityService.loadUserByUsername(currentUser.getUsername());

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);

		model.addAttribute("orderList", user.getOrderList());

		return "myProfile";
	}

	@RequestMapping("/orderDetail")
	public String orderDetail(@RequestParam("id") Long orderId, Principal principal, Model model){
		User user = userService.findByUsername(principal.getName());
		Order order = orderService.findOne(orderId);

		if(order.getUser().getId()!=user.getId()){
			return "badRequestPage";
		} else {
			List<CartItem> cartItemList = cartItemService.findByOrder(order);
			model.addAttribute("cartItemList", cartItemList);
			model.addAttribute("user", user);
			model.addAttribute("order", order);
			model.addAttribute("orderList", user.getOrderList());

			model.addAttribute("classActiveOrders", true);
			model.addAttribute("displayOrderDetail", true);
		}
		return "myProfile";
	}

	@RequestMapping("/productDetail")
	public String prodDetail(@PathParam("id") Long id, Model model, Principal principal){
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
