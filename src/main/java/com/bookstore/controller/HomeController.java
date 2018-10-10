package com.bookstore.controller;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.bookstore.domain.Book;
import com.bookstore.domain.User;
import com.bookstore.domain.UserBilling;
import com.bookstore.domain.UserPayment;
import com.bookstore.domain.UserShipping;
import com.bookstore.domain.security.PasswordResetToken;
import com.bookstore.domain.security.Role;
import com.bookstore.domain.security.UserRole;
import com.bookstore.service.BookService;
import com.bookstore.service.UserSecurityService;
import com.bookstore.service.UserService;
import com.bookstore.utility.MailConstructor;
import com.bookstore.utility.SecurityUtility;
import com.bookstore.utility.USConstants;

@Controller
public class HomeController {

	@Autowired
	private UserService userService;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MailConstructor mailConstructor;

	@Autowired
	private UserSecurityService userSecurityService;
	
	@Autowired
	private BookService bookService;
	
	
	@RequestMapping("/")
	public String homePage() {

		return "index";
	}

	@RequestMapping("/myAccount")
	public String myAccount() {

		return "myAccount";
	}

	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("classActiveLogin", true);
		return "myAccount";
	}

	/*
	 *  new User 
	 */
	@RequestMapping(value = "/newUser", method = RequestMethod.POST)
	public String newUserPost(HttpServletRequest request,
			@ModelAttribute("username") String username,
			@ModelAttribute("email") String email, Model model)throws Exception {

		model.addAttribute("classActiveNewAccount", true);
		model.addAttribute("email", email);
		model.addAttribute("username", username);

		if (userService.findByUsername(username) != null) {
			model.addAttribute("userExist", true);
			return "myAccount";
		}

		if (userService.findByEmail(email) != null) {
			model.addAttribute("emailExist", true);

			return "myAccount";
		}

		User user = new User();
		user.setUsername(username);
		user.setEmail(email);

		String password = SecurityUtility.randomPassword();
		String encryptedPasword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedPasword);
		Role role = new Role();
		role.setRoleId(1);
		role.setName("ROLE_USER");

		Set<UserRole> userRoles = new HashSet<UserRole>();
		userRoles.add(new UserRole(user, role));
		userService.createUser(user, userRoles);

		String token = UUID.randomUUID().toString();
		
		userService.createPasswordResetTokenForUser(user, token);
		
		String appUrl = "http://" + request.getServerName() + ":"+ request.getServerPort() + request.getContextPath();
		
		SimpleMailMessage myemail = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);
		
		mailSender.send(myemail);
		model.addAttribute("emailSent",true);

		return "myAccount";

	}
	
	/*
	 * Getting the connection 
	 */

	@RequestMapping("/newUser")
	public String newUser(Model model, Locale locale,@RequestParam("token") String token) {
		PasswordResetToken passtoken = userService.getPasswordResetToken(token);
		if (passtoken == null) {
			String message = "Invalid Token !";
			model.addAttribute("message", message);
			return "redirect:/badRequest";
		}
		User user = passtoken.getUser();
		String username = user.getUsername();
		UserDetails userDetails = userSecurityService.loadUserByUsername(username);

		Authentication authentication = new UsernamePasswordAuthenticationToken(
				userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		model.addAttribute("classActiveEdit", true);
		model.addAttribute("user",user);
		return "myProfile";
	}

	@RequestMapping("/forgetPassword")
	public String forgetPassword(HttpServletRequest request, @ModelAttribute("email") String email, Model model)throws Exception{

		User user=userService.findByEmail(email);
		if(user==null){
			model.addAttribute("emailNotExist", true);
			return "myAccount";

		}
		
		String password = SecurityUtility.randomPassword();
		String encryptedPasword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedPasword);
		
		userService.save(user);

		String token = UUID.randomUUID().toString();
		
		userService.createPasswordResetTokenForUser(user, token);
		
		String appUrl = "http://" + request.getServerName() + ":"+ request.getServerPort() + request.getContextPath();
		
		SimpleMailMessage myemail = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);
		
		mailSender.send(myemail);
		model.addAttribute("emailSent",true);

	
		model.addAttribute("classActiveReset", true);
		return "myAccount";
	}
	
	
	@RequestMapping("/bookshelf")
	public String bookshelf( Model model){
	  List<Book> bookList=bookService.findAllBooks();
	  model.addAttribute("bookList",bookList);
		
		return "bookshelf";
		
	}
	
	@RequestMapping("/bookDetails")
	public String bookDetails(@PathParam("id") Long id,Principal principal,Model model){
		
		if(principal!=null){
			String username=principal.getName();
			User user=userService.findByUsername(username);
			model.addAttribute("user",user);
		}
    Book book=bookService.findOne(id);
    model.addAttribute("book",book);
    List<Integer> qtyList=Arrays.asList(1,2,3,4,5,6,7,8,9,10);
    model.addAttribute("qtyList",qtyList);
    model.addAttribute("qty",1);

	
		return "bookDetails";

}
	
	
	@RequestMapping("/myProfile")
	public String myProfile(Model model, Principal principal){

		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		//model.addAttribute("userPaymentList", user.getUserPaymentList());
		//model.addAttribute("userShippingList", user.getUserShippingList());

		UserShipping userShipping = new UserShipping();
		model.addAttribute("userShipping", userShipping);
		model.addAttribute("listOfCreditCard", true);
		model.addAttribute("listOfShippingAdresses", true);

		List<String> stateList = USConstants.listOfStatesCode;
		Collections.sort(stateList);

		model.addAttribute("stateList", stateList);
		model.addAttribute("classActiveEdit", true);

		return "myProfile";

	}
	
	
	/*
	 * to print the credit card
	 */
	
	@RequestMapping("/listOfCreditCard")
	public String listOfcreditCards(Model model,Principal princial,HttpServletRequest request){
		User user=userService.findByUsername(princial.getName());
		model.addAttribute("user",user);
		//model.addAttribute("userPaymentList",user.getUserPaymentList());
		//model.addAttribute("userShipping",user.getUserShippingList());
		//model.addAttribute("listOfCreditCards",true);
		model.addAttribute("listOfShippingAdresses",true);
		model.addAttribute("classActiveBilling",true);

		
		
		return "myProfile";
	}
	
	 /*
	  * to add a new credit card
	  */
	
	@RequestMapping("/addNewCreditCard")
	public String addNewCreditCard(Model model, Principal princial){
	    User user=userService.findByUsername(princial.getName());
	    model.addAttribute("user",user);
	    model.addAttribute("addNewcreditCard",true);
	    model.addAttribute("classActiveBilling",true);
	    UserBilling userBilling=new UserBilling();
	    UserPayment  userPayment=new UserPayment();
	    
	    model.addAttribute("userBilling",userBilling);
	    model.addAttribute("userPayment",userPayment);
	    List<String> stateList=USConstants.listOfStatesCode;
	    Collections.sort(stateList);
	    model.addAttribute("stateList",stateList);
	    
	  //  model.addAttribute("userPaymentList", user.getUserPaymentList());
		//model.addAttribute("userShippingList", user.getUserShippingList());

		 return "myProfile";
	}
	
	@RequestMapping("/addNewShippingAdress")
	public String addNewShiipingAdress(Model model, Principal princial){
	    User user=userService.findByUsername(princial.getName());
	    model.addAttribute("user",user);
	    model.addAttribute("addNewShippingAdress",true);
	    model.addAttribute("classActiveShipping",true);
	    UserBilling userBilling=new UserBilling();
	    UserShipping  userShipping=new UserShipping();
	    
	    model.addAttribute("userBilling",userBilling);
	    model.addAttribute("userShipping",userShipping);
	    List<String> stateList=USConstants.listOfStatesCode;
	    Collections.sort(stateList);
	    model.addAttribute("stateList",stateList);
	    
	    //model.addAttribute("userPaymentList", user.getUserPaymentList());
		//model.addAttribute("userShippingList", user.getUserShippingList());

		 return "myProfile";
	}
	
	
	
	@RequestMapping("/listOfShippingAdresses")
	public String listOfShippingAdresses(Model model,Principal princial,HttpServletRequest request){
		User user=userService.findByUsername(princial.getName());
		model.addAttribute("user",user);
		//model.addAttribute("userPaymentList",user.getUserPaymentList());
		//model.addAttribute("userShipping",user.getUserShippingList());
		model.addAttribute("listOfCreditCards",true);
		model.addAttribute("listOfShippingAdresses",true);
		model.addAttribute("classActiveBilling",true);
				
		return "myProfile";
	
}
	
}

