package com.example.demo.service;

import com.example.demo.entity.Image;
import com.example.demo.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    @Autowired
    private  ImageRepository imageRepository;
    public void save(Image img) {
        imageRepository.save(img);
    }
    public void deleteImgHotel(Long id) {
        imageRepository.deleteHotelInImg(id);
    }
    public void deleteImgRoom(Long id) {
        imageRepository.deleteImgRoom(id);
    }

}
