package com.example.mock_project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ClaimRequestForAnalyzeDTO {
    private String id;
    private List<String> listUrlImage;
}
