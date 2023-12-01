package com.example.solace.Controller;

import com.example.solace.DTO.CartDetailDTO;
import com.example.solace.DTO.CartItemDTO;
import com.example.solace.Service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    //장바구니에 해당상품 등록
    @PostMapping(value="cart/add")
    public String addCart(CartItemDTO cartItemDTO, Model model) {
        String email ="sample@naver.com"; //회원이메일
        cartService.addCart(cartItemDTO, email);

        return "redirect:/product/list";
    }

    //장바구니에서 해당상품 삭제
    @GetMapping(value="/cart/remove")
    public String deleteCartItem(Integer cartId, Model model) throws Exception {
        cartService.deleteCartItem(cartId);;
        return "redirect:/cart/list";
    }

    //해당회원의 이메일로 장바구니 목록 조회
    @GetMapping(value="/cart/list")
    public String CartItem(Model model) {
        String email = "sample@naver.com"; //섹션이나 로그인 정보를 이용해서 사용

        List<CartDetailDTO> cartDetailDTOList = cartService.getCartList(email);
        model.addAttribute("lists", cartDetailDTOList);
        return "cart/list";
    }

    //해당상품의 수량 수정
    @GetMapping(value="cart/update")
    public String updateCartItem(Integer cartId, int count, Model model) throws Exception{
        cartService.updateCartCount(cartId, count);
        return "redirect:/cart/list";
    }
}
