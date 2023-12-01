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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;

  //장바구니에 상품등록
  @PostMapping("/cart/add")
  public String addToCart(Principal principal, CartItemDTO cartItemDTO, HttpServletRequest request, Model model) throws Exception {

    // 사용자가 인증되지 않은 경우 로그인 페이지로 리다이렉트
    if (principal == null) {
      return "redirect:/login";
    }
    // 이전 페이지의 정보 가져오기
    //HttpSession session = request.getSession();
    //String prevPage = (String) session.getAttribute("prevPage");

    // 사용자가 인증된 경우, 장바구니에 상품 추가
    String email = principal.getName();
    cartService.addCart(cartItemDTO, email);

    // 장바구니 페이지로 이동
    return "redirect:/cart";
  }


  //회원 이메일로 장바구니 목록 조회
  @GetMapping("/cart")
  public String CartList(Principal principal, Model model) throws Exception {

    String email = principal.getName();

    List<CartDetailDTO> cartDetailDTOList = cartService.getCartList(email);
    model.addAttribute("lists", cartDetailDTOList);

    return "cartorder/cart";

  }


  //장바구니 상품 수량 변경
  @GetMapping( "/cart/update")
  public String updateCartItem(Integer cartId, Integer count, Model model) throws Exception {
    cartService.updateCartCount(cartId, count);

    return "redirect:/cart";
  }


  //장바구니에서 해당상품 삭제
  @DeleteMapping(value="/cart/remove")
  public String deleteCartItem(Integer cartId, Model model) throws Exception {
    cartService.deleteCartItem(cartId);

    return "redirect:/cart";
  }

}






/*
//장바구니에 상품등록
  @PostMapping("/cart/add")
  public String addCart(Principal principal, CartItemDTO cartItemDTO, HttpServletRequest request, Model model) throws Exception {

    // 사용자가 인증되지 않은 경우 로그인페이지로
    if (principal == null) {
      return "redirect:/login";
    }
    // 이전 페이지의 정보를 가져오기
    HttpSession session = request.getSession();
    String prevPage = (String) session.getAttribute("prevPage");

    //사용자가 인증된 경우, 장바구니를 가져오기 (세션 또는 인증 정보를 통해 사용자 이메일을 가져옴)
    String email = principal.getName();
    cartService.addCart(cartItemDTO, email);

    return "redirect:/cart";
  }


  //회원 이메일로 장바구니 목록 조회
  @GetMapping("/cart")
  public String CartList(Principal principal, CartItemDTO cartItemDTO, HttpServletRequest request, Model model) throws Exception {

    // 사용자가 인증되지 않은 경우 로그인페이지로
    if (principal == null) {
      return "redirect:/login";
    }
    // 이전 페이지의 정보를 가져오기
    HttpSession session = request.getSession();
    String prevPage = (String) session.getAttribute("prevPage");

    //사용자가 인증된 경우, 장바구니를 가져오기 (세션 또는 인증 정보를 통해 사용자 이메일을 가져옴)
    String email = principal.getName();
    cartService.addCart(cartItemDTO, email);

    List<CartDetailDTO> cartDetailDTOS = cartService.getCartList(email);
    model.addAttribute("cartDetailDTOS", cartDetailDTOS);

    return "/cartorder/cart";

  }

 */