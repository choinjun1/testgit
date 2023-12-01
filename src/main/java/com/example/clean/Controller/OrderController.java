package com.example.clean.Controller;

import com.example.clean.Config.oauth.CustomLoginSuccessHandler;
import com.example.clean.DTO.CartDetailDTO;
import com.example.clean.DTO.CartItemDTO;
import com.example.clean.Service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

  @GetMapping("/order")
  public String orderForm(Model model) throws Exception {
    return "cartorder/order";
  }
}