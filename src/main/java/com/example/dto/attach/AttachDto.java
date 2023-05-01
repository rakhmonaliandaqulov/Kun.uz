package com.example.dto.attach;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachDto {
    private String id;
    @NotNull(message = "originalName required")
    private String originalName;
    @NotNull(message = "path required")
    private String path;
    @NotNull(message = "size required")
    private Long size;
    @NotNull(message = "extension required")
    private String extension;
    private LocalDateTime createdData;
    private String url;
}
