package com.example.demo.service;

import com.example.demo.entity.Localization;
import com.example.demo.repository.LocalizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocalizationService {
    @Autowired
    LocalizationRepository localizationRepository;
    public void saveLoacation (Localization localization){
        localizationRepository.save(localization);
    }
}
