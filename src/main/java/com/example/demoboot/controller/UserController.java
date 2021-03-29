package com.example.demoboot.controller;

import com.example.demoboot.entitiy.Role;
import com.example.demoboot.entitiy.User;
import com.example.demoboot.service.UserDetailsServiceImp;
import com.example.demoboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {
    public UserController() {
    }

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @GetMapping(value = "/{id}")
    public String showUser(@PathVariable("id") int id, ModelMap model)  {
        model.addAttribute("messages", userService.getUserById(id));
        return "users";
    }

    @GetMapping(value = "/add")
    public String addUser(ModelMap model) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("message", user);
        return "add";
    }
  /*  public ModelAndView addPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("add");
        return modelAndView;
    }*/

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public ModelAndView deleteUser(@ModelAttribute("user")User user)  {
        ModelAndView modelAndView = new ModelAndView();
        userService.removeUser(user);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deletePage(@PathVariable("id") int id,
                                 @ModelAttribute("messages") String messages) throws Exception {
        User user = userService.getUserById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("messages", user);
        modelAndView.setViewName("delete");
        return modelAndView;
    }




    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView adduser(@ModelAttribute("user") User user,@RequestParam("Role") String[] role )throws Exception{
        ModelAndView modelAndView = new ModelAndView();
        if (role[0].equals("0")) {
            System.out.println(role[0]);
            user.setRoles(Collections.singleton(new Role(1L, "ROLE_ADMIN")));
        }
        if (role[0].equals("1")) {
            System.out.println(role[0]);
            user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
        }
        if (role[0].equals("2")) {
            Set<Role> roles = new HashSet<>();
            roles.add(new Role(1L, "ROLE_ADMIN"));
            roles.add(new Role(2L, "ROLE_USER"));
            user.setRoles(roles);
        }

        if (userService.checkEmail(user.getEmail())) {
            modelAndView.setViewName("redirect:/");
            userService.addUser(user);
        } else {
            modelAndView.addObject("messages", "part with email \"" + user.getEmail() + "\" already exists");
            modelAndView.setViewName("redirect:/");
        }

        return modelAndView;

    }


    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editPage(@PathVariable("id") int id,
                                 @ModelAttribute("messages") String messages) throws Exception {
        User user = userService.getUserById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("messages", user);
        modelAndView.setViewName("edit");
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView editUser(@ModelAttribute("user")User user,@RequestParam("Role") String[]role) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        if((role.length ==0)&&(userService.checkEmail(user.getEmail()) || userService.getUserById(user.getId()).getEmail().equals(user.getEmail()))){
            userService.updateUser(user);
            modelAndView.setViewName("redirect:/");
        }
        if ((role.length!=0)&& (userService.checkEmail(user.getEmail()) || userService.getUserById(user.getId()).getEmail().equals(user.getEmail()))) {
           if (role[0].equals("0")) {
           user.setRoles(Collections.singleton(new Role(1L, "ROLE_ADMIN")));
            }
            if (role[0].equals("1")) {
                user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
            }
            if (role[0].equals("2")) {
                Set<Role> roles = new HashSet<>();
                roles.add(new Role(1L, "ROLE_ADMIN"));
                roles.add(new Role(2L, "ROLE_USER"));
                user.setRoles(roles);
            }
            userService.updateUser(user);
            modelAndView.setViewName("redirect:/");
        }
       else {
            modelAndView.setViewName("redirect:/edit" + +user.getId());
        }
        return  modelAndView;
    }

    @GetMapping(value = "/admin")
    public String showAllUser(ModelMap model) throws Exception {
        model.addAttribute("messages", userService.getAllUsers());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("message", user);
        return "users";
    }
    @GetMapping(value = "/user")
    public String seeUser(ModelMap model)  {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("messages", user);
        return "user";
    }
    @GetMapping(value = "/")
    public String showAllUsers(ModelMap model) throws Exception {
        model.addAttribute("messages", userService.getAllUsers());
        return "users";
    }

}
