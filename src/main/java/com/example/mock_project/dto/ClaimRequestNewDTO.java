package com.example.mock_project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ClaimRequestNewDTO {

    @NotNull(message = "Must have url of reciept")
    @NotEmpty(message = "Must have url of reciept")
    private List<String> listUrlImage;

    @NotNull(message = "Must have name")
    @NotBlank(message = "Must have name")
    private String name;

    @NotNull(message = "Must have card id")
    @NotBlank(message = "Must have card id")
    @Pattern(regexp = "\\d+", message = "CardId must be a number")
    private String cardId;
}
