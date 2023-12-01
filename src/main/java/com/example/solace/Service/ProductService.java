package com.example.solace.Service;

import com.example.solace.DTO.ImageDTO;
import com.example.solace.DTO.ProductDTO;
import com.example.solace.Entity.ImageEntity;
import com.example.solace.Entity.ProductEntity;
import com.example.solace.Repository.ImageRepository;
import com.example.solace.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    //파일업로드 클래스
    private final FileService fileService;
    private final ImageService imageService;
    private final ModelMapper modelMapper = new ModelMapper();

    //작업에 사용할 레포시토리
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    //상품 및 이미지 저장
    //public void save(ProductImageDTO productImageDTO, List<MultipartFile> images) throws Exception {
    public void save(ProductDTO productDTO) throws Exception {
        List<ImageDTO> dataDTO = productDTO.getImageDTOs();  //이미지테이블정보 분리
        List<MultipartFile> images = productDTO.getImages(); //이미지파일 분리

        //맵핑전 불필요한 필드 제외
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.typeMap(ProductDTO.class, ProductEntity.class)
                .addMappings(mapper -> mapper.skip(ProductEntity::setProductImages));

        //신규 상품 등록
        ProductEntity data = modelMapper.map(productDTO, ProductEntity.class);
        //Integer id = productRepository.save(data).getProductId();
        ProductEntity dataEntity = productRepository.save(data);

        int index=0;
        for(MultipartFile file:images) {
            ImageDTO jobDTO = dataDTO.get(index);

            try {
               //jobDTO.setProductId(id); //이미지테이블에 상품번호 등록

                imageService.save(jobDTO, dataEntity, file); //이미지 등록 및 이미지테이블에 정보 등록
            } catch(IOException e) {
                //
            }
            index++;
        }
    }

    //수정
    public void update(ProductDTO productDTO) throws Exception {
        List<ImageDTO> dataDTO = productDTO.getImageDTOs();  //이미지테이블정보 분리
        List<MultipartFile> images = productDTO.getImages(); //이미지파일 분리

        //맵핑전 불필요한 필드 제외
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.typeMap(ProductDTO.class, ProductEntity.class)
                .addMappings(mapper -> mapper.skip(ProductEntity::setProductImages));



        //상품 수정 등록
        ProductEntity data = modelMapper.map(productDTO, ProductEntity.class);
        ProductEntity dataEntity = productRepository.save(data);
        //Integer id = productRepository.save(data).getProductId();

        int index=0;
        for(MultipartFile file:images) {
            ImageDTO jobDTO = dataDTO.get(index);

            try {
                //jobDTO.setProductId(id); //이미지테이블에 상품번호 등록

                imageService.update(jobDTO, dataEntity, file); //이미지 등록 및 이미지테이블에 정보 등록

            } catch(IOException e) {
                //
            }
            index++;
        }
    }

    public void remove(Integer id) throws Exception {
        ProductEntity entity = productRepository.findByProductId(id); //상품조회

        if(entity == null) {
            return;
        }

        List<ImageEntity> imageEntitys = entity.getProductImages(); //imageRepository.findAllByProductId(id);  //imageService.detail(id);

        for(ImageEntity data:imageEntitys) {
            fileService.deleteFile(data.getImageFile());
        }

        productRepository.deleteById(id);
    }

    //상품개별조회
    //public ProdcutImageDTO findOne(int id) throws Exception { //개별 조회(상세)
    public ProductDTO findOne(int id) throws Exception { //개별 조회(상세)
        //modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.typeMap(ProductEntity.class, ProductDTO.class)
                .addMappings(mapper -> mapper.skip(ProductDTO::setImages));

        Optional<ProductEntity> data = productRepository.findById(id); //in(int)->out(Optional<entity>)
        ProductEntity entity = data.get();

        //변환
        ProductDTO result = data.map(mapper->modelMapper.map(mapper, ProductDTO.class)).orElse(null);
        List<ImageDTO> imageDTOS = entity.getProductImages().stream()
                .map(imageEntity -> modelMapper.map(imageEntity, ImageDTO.class))
                        .collect(Collectors.toList());
        result.setImageDTOs(imageDTOS);
        //System.out.println("변환 후");
        //System.out.println(result.getImageDTOs().size());
       // System.out.println(result.toString());
        return result;
    }

    //상품전체조회
    public List<ProductDTO> findAll() throws Exception { //모두 조회
        //전체 조회
        List<ProductEntity> entities =productRepository.findAll();

        //상품정보 및 이미지들을 Entity에서 DTO로 복수전달
        List<ProductDTO> dtos = new ArrayList<>();

        for(ProductEntity entity:entities) {
            ProductDTO dto = ProductDTO.builder()
                    .productId(entity.getProductId())
                    .productName(entity.getProductName())
                    .productContent(entity.getProductContent())
                    .productDetail(entity.getProductDetail())
                    .productCost(entity.getProductCost())
                    .productPrice(entity.getProductPrice())
                    .productDis(entity.getProductDis())
                    .productOpt(entity.getProductOpt())
                    .productCnt(entity.getProductCnt())
                    .productLike(entity.getProductLike())
                    .productViewcnt(entity.getProductViewcnt())
                    .categoryTypeRole(entity.getCategoryTypeRole())
                    .sellStateRole(entity.getSellStateRole())
                    .imageDTOs(mapImagesToDTOs(entity.getProductImages()))
                    .build();

            dtos.add(dto);
        }
        return dtos;
    }

    //각 상품에 이미지테이블 전달
    private List<ImageDTO> mapImagesToDTOs(List<ImageEntity> imagesEntities) {
        List<ImageDTO> imageDTOs = new ArrayList<>();

        for (ImageEntity imageEntity : imagesEntities) {
            ImageDTO imageDTO =  modelMapper.map(imageEntity, ImageDTO.class); //  new ImageDTO(/* Populate with necessary fields */);
            imageDTOs.add(imageDTO);
        }
        return imageDTOs;
    }
}

