package com.shop.onlineshop.controller;

import com.shop.onlineshop.dto.WebUser;
import com.shop.onlineshop.entity.User;
import com.shop.onlineshop.entity.VerificationToken;
import com.shop.onlineshop.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;

@Controller
@RequestMapping("/register")
public class RegisterController {


    Logger logger = LoggerFactory.getLogger(RegisterController.class);
    UserService userService;

    ApplicationEventPublisher applicationEventPublisher;
    public RegisterController(UserService userService, ApplicationEventPublisher applicationEventPublisher){
        this.userService = userService;
        this.applicationEventPublisher = applicationEventPublisher;
    }
    @GetMapping("/registrationForm")
    public String showRegisterForm(Model userModel){

        WebUser webUser = new WebUser();

        userModel.addAttribute("webUser", webUser);
        return "/register/registerUser-form";
    }
//    @PostMapping("/registerUser")
//    public String showUserForm(@ModelAttribute("webUser") WebUser webUser, HttpServletRequest request, Model userModel){
//
//        try {
//            User user = userService.registerUser(webUser);
//            //publish the event for sending activation emails
//            String appUrl = request.getContextPath();
//            applicationEventPublisher.publishEvent(
//                    new OnRegistrationCompleteEvent(user,request.getLocale(),appUrl));
//        }catch (UserAlreadyExistsException e){
//            logger.warn("User name already exists: " + webUser.getUserName());
//            userModel.addAttribute("webUser",new WebUser());
//            userModel.addAttribute("registrationError", "An account for that username/email already exists.");
//            return "/register/registrationForm";
//        }
//        return "/register/registration-confirmation";
//    }
    @GetMapping("/registrationConfirmation")
    public String showRegisterForm(@RequestParam(name="token") String token, Model model){

        VerificationToken verificationToken = userService.findByToken(token);
        if(verificationToken == null){
            model.addAttribute("message", "Bad Token.");
            return "/confirmation-response";
        }
        Calendar cal = Calendar.getInstance();
        if(verificationToken.getExpiryDate().getTime() - cal.getTime().getTime() <= 0){
            model.addAttribute("message", "Token expired.");
            return "/register/confirmation-response";
        }
        User user = verificationToken.getUser();
        user.setEnabled((short)1);
        userService.save(user);
       // model.addAttribute("webUser", UserToWebUserMapper.mapUserToWebUser(user));
        model.addAttribute("message", "User " + "Registration confirmation successfully for user: "+ user.getUsername());
        return "/register/confirmation-response";
    }
}
