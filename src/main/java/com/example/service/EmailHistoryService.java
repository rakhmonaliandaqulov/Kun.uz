package com.example.service;

import com.example.dto.emailHistory.EmailHistoryDto;
import com.example.entity.EmailHistoryEntity;
import com.example.exps.AppBadRequestException;
import com.example.repository.EmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
public class EmailHistoryService {
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;
    public EmailHistoryDto getByEmail(String email) {
        EmailHistoryEntity emailHistoryEntity = emailHistoryRepository.findByEmail(email);
        if (emailHistoryEntity == null){
            throw new AppBadRequestException("Email does not exists mazgi.");
        }
        EmailHistoryDto emailHistoryDto = new EmailHistoryDto();
        emailHistoryDto.setId(emailHistoryEntity.getId());
        emailHistoryDto.setEmail(emailHistoryEntity.getEmail());
        emailHistoryDto.setMessage(emailHistoryEntity.getMessage());
        emailHistoryDto.setCreatedDate(emailHistoryEntity.getCreatedDate());
        return emailHistoryDto;
    }

    public List<EmailHistoryDto> getByDate(LocalDate date) {
        List<EmailHistoryEntity> emailHistoryEntityList = emailHistoryRepository.findByCreatedDate(date);
        List<EmailHistoryDto> emailHistoryDtoList = new LinkedList<>();
        if (emailHistoryEntityList == null){
            return null;
        }
        for (EmailHistoryEntity emailHistoryEntity : emailHistoryEntityList){
            EmailHistoryDto emailHistoryDto = new EmailHistoryDto();
            emailHistoryDto.setId(emailHistoryEntity.getId());
            emailHistoryDto.setMessage(emailHistoryEntity.getMessage());
            emailHistoryDto.setEmail(emailHistoryEntity.getEmail());
            emailHistoryDto.setCreatedDate(emailHistoryEntity.getCreatedDate());
            emailHistoryDtoList.add(emailHistoryDto);
        }

        return emailHistoryDtoList;
    }
}
