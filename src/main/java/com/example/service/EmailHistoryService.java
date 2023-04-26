package com.example.service;

import com.example.dto.category.CategoryDto;
import com.example.dto.emailHistory.EmailHistoryDto;
import com.example.entity.CategoryEntity;
import com.example.entity.EmailHistoryEntity;
import com.example.exps.AppBadRequestException;
import com.example.exps.ItemNotFoundException;
import com.example.repository.EmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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

    public Page<EmailHistoryDto> getAll(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable paging = PageRequest.of(page - 1, size, sort);
        Page<EmailHistoryEntity> pageObj = emailHistoryRepository.findAll(paging);

        Long totalCount = pageObj.getTotalElements();

        List<EmailHistoryEntity> entityList = pageObj.getContent();
        List<EmailHistoryDto> dtoList = new LinkedList<>();

        if (!pageObj.equals(null)) {
            for (EmailHistoryEntity entity : entityList) {
                EmailHistoryDto dto = new EmailHistoryDto();
                dto.setId(entity.getId());
                dto.setEmail(entity.getEmail());
                dto.setMessage(entity.getMessage());
                dto.setCreatedDate(entity.getCreatedDate());
                dtoList.add(dto);
            }
            Page<EmailHistoryDto> response = new PageImpl<EmailHistoryDto>(dtoList, paging, totalCount);
            return response;
        }
        throw new ItemNotFoundException("ArticleType is empty");
    }
}
