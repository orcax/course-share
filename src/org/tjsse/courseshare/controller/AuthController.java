package org.tjsse.courseshare.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/auth")
public class AuthController {

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @ResponseBody
  public String login(HttpServletRequest req) {
    String pwd = req.getParameter("pwd");
    if ("666888".equals(pwd)) {
      req.getSession().setAttribute("login", true);
      System.out.println("login");
      return "success";
    }
    return "failed";
  }

  @RequestMapping(value = "/logout", method = RequestMethod.POST)
  @ResponseBody
  public String logout(HttpSession session) {
    session.removeAttribute("login");
    System.out.println("logout");
    System.out.println(session.getAttribute("login"));
    return "success";
  }

}
