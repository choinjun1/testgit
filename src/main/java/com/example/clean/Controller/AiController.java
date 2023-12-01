package com.example.clean.Controller;

import com.example.clean.Constant.CategoryTypeRole;
import com.example.clean.Constant.SellStateRole;
import com.example.clean.DTO.ProductDTO;
import com.example.clean.Repository.ProductRepository;
import com.example.clean.Service.ProductService;
import com.example.clean.Util.Flask;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class AiController {

  @Autowired
  private Flask flask;

  @Value("${uploadPath}")
  private String uploadPath;

  @Value("${imgLocation}")
  private String imgLocation;

  private final ProductRepository productRepository;
  private final ProductService productService;


  //이미지 인식페이지
  @GetMapping("/ai_img_search")
  public String imgsearchForm(Model model) throws Exception {
    return "/ai/img_search";
  }


  //이미지 인식페이지 - 다시하기
  @PostMapping("/ai_img_search")
  public String imgsearchProc() throws Exception {
    return "redirect:/ai_img_search";
  }

  // 이미지 결과 페이지에 대한 POST 처리
  @PostMapping("/ai_img_result")
  public String imgResultProc(@RequestParam("file") MultipartFile file,
                              @RequestParam(value = "type", defaultValue = "") String type,
                              @RequestParam(value = "keyword", defaultValue = "") String keyword,
                              @RequestParam(value = "productName", required = false) String productName,
                              @RequestParam(value = "productContent", required = false) String productContent,
                              @RequestParam(value = "sellsState", defaultValue = "") String sellState,
                              @RequestParam(value = "categoryType", defaultValue = "MEMBERSALE") String categoryType,
                              @PageableDefault(page = 1) Pageable pageable,
                              Model model) {

    try {
      System.out.println("Received file: " + file.getOriginalFilename());
      // 플라스크 서버에 분석할 이미지를 전달하여 처리
      flask.requestToFlask(file);

      List<String> sellStateOptions = Arrays.stream(SellStateRole.values())
              .map(SellStateRole::getDescription)
              .collect(Collectors.toList());
      model.addAttribute("sellStateOptions", sellStateOptions); // 판매상태 옵션을 전달

      // CategoryTypeRole 열거형의 한글 설명을 리스트로 가져오기
      List<String> categoryOptions = Arrays.stream(CategoryTypeRole.values())
              .map(CategoryTypeRole::getDescription)
              .collect(Collectors.toList());
      model.addAttribute("categoryOptions", categoryOptions);

      Page<ProductDTO> productDTOS = productService.findALl(type, keyword, sellState, categoryType, pageable);
      // Page<ProductDTO> productDTOS = productService.findALl(type, keyword, productName, productContent, sellState, categoryType, pageable);

      productDTOS.getTotalElements();   // 전체 게시글 조회

      int blockLimit = 5;
      int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
      int endPage = Math.min(startPage + blockLimit - 1, productDTOS.getTotalPages());

      int prevPage = productDTOS.getNumber();
      int currentPage = productDTOS.getNumber() + 1;
      int nextPage = productDTOS.getNumber() + 2;
      int lastPage = productDTOS.getTotalPages();

      model.addAttribute("productDTOS", productDTOS);

      model.addAttribute("startPage", startPage);
      model.addAttribute("endPage", endPage);
      model.addAttribute("prevPage", prevPage);
      model.addAttribute("currentPage", currentPage);
      model.addAttribute("nextPage", nextPage);
      model.addAttribute("lastPage", lastPage);

      model.addAttribute("type", type);
      model.addAttribute("keyword", keyword);

      model.addAttribute("productName", productName);
      model.addAttribute("productContent", productContent);

      return "/ai/img_result";
    } catch (Exception e) {
      // "/ai_img_search"로 리다이렉트
      return "redirect:/ai_img_search";
    }
  }

}