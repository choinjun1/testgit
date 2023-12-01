package com.example.solace.Service;

import com.example.solace.DTO.ImageDTO;
import com.example.solace.Entity.ImageEntity;
import com.example.solace.Entity.ProductEntity;
import com.example.solace.Repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {
    //파일업로드 클래스
    private final FileService fileService;
    private final ModelMapper modelMapper = new ModelMapper();

    //작업에 사용할 레포시토리
    private final ImageRepository imageRepository;

    //이미지 저장
    public void save(ImageDTO imageDTO, ProductEntity entity, MultipartFile imageFile) throws Exception {
        String originalFileName = imageFile.getOriginalFilename(); //기존파일명
        String newFileName=""; //UUID로 생성된 새로운 파일명

        if(originalFileName != null && originalFileName.length() >0) { //파일존재시 업로드 진행
            newFileName = fileService.uploadFile(
                    originalFileName, imageFile.getBytes()
            );
        }

        imageDTO.setImageFile(newFileName); //새로운 이름으로 변경
        ImageEntity imageEntity = modelMapper.map(imageDTO, ImageEntity.class); //데이터 변환
        imageEntity.setProductEntity(entity);
        try {
            imageRepository.save(imageEntity);
        } catch(Exception e) {

        }

    }

    //수정
    public void update(ImageDTO imageDTO, ProductEntity entity, MultipartFile imageFile) throws Exception {
        String originalFileName = imageFile.getOriginalFilename(); //기존파일명
        String newFileName=""; //UUID로 생성된 새로운 파일명

        //해당 이미지 존재 여부 확인
        ImageEntity imageEntity = imageRepository
                .findById(imageDTO.getImageId())
                .orElseThrow();
        String oldFilename = imageEntity.getImageFile(); //이전 파일명

        if(originalFileName != null && originalFileName.length()>0) { //파일존재시 업로드 진행
           if(oldFilename.length()!=0) { //이전파일 존재시 삭제
               fileService.deleteFile(oldFilename);
           }
           newFileName = fileService.uploadFile(
                   originalFileName, imageFile.getBytes()
           );

           //imageDTO.setImageFile(newFileName); //새로운 이름으로 변경
           //imageDTO.setImageId(imageEntity.getImageId());


           ImageEntity update = modelMapper.map(imageDTO, ImageEntity.class); //데이터 변환

            update.setProductEntity(entity);
           update.setImageFile(newFileName);
           update.setImageId(imageEntity.getImageId());

           imageRepository.save(update);
        }

    }
    //삭제
    public void remove() throws Exception {

    }
}
