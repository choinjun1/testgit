package com.example.solace.Controller;

import com.example.solace.Constant.CategoryTypeRole;
import com.example.solace.Constant.SellStateRole;
import com.example.solace.DTO.ProductDTO;
import com.example.solace.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/product/insert")
    public String insert(Model model) throws Exception {
        model.addAttribute("categorys", CategoryTypeRole.values());
        model.addAttribute("sells", SellStateRole.values());

        return "product/insert";
    }
    @PostMapping("/product/insert")
    //public String insertProc(ProductImageDTO productImageDTO, List<MultipartFile> images, Model model) throws Exception {
    public String insertProc(ProductDTO productDTO, Model model) throws Exception {
        //System.out.println(productImageDTO.getImageDTOs().toString());

        productService.save(productDTO);
        return "redirect:/";
    }
    @GetMapping("/product/list")
    public String list(Model model) throws Exception {
        List<ProductDTO> dtos = productService.findAll();

        model.addAttribute("dtos", dtos);
        return "product/list";
    }

    @GetMapping("/product/detail")
    public String detail(Integer id, Model model) throws Exception {
        ProductDTO dtos = productService.findOne(id);

        model.addAttribute("dtos", dtos);
        model.addAttribute("categorys", CategoryTypeRole.values());
        model.addAttribute("sells", SellStateRole.values());
        return "product/detail";
    }

    @GetMapping("/product/update")
    public String update(Integer id, Model model) throws Exception {
        ProductDTO dtos = productService.findOne(id);

        model.addAttribute("dtos", dtos);
        model.addAttribute("categorys", CategoryTypeRole.values());
        model.addAttribute("sells", SellStateRole.values());
        return "product/update";
    }

    @PostMapping("/product/update")
    public String updateProc(ProductDTO productDTO, Model model) throws Exception {

        productService.update(productDTO);

        return "redirect:/";
    }

    @GetMapping("/product/remove")
    public String removeProc(Integer id, Model model) throws Exception {
        productService.remove(id);

        return "redirect:/";
    }
}
