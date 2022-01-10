package com.example.mock_project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class CustomerDTO {

    @NotNull(message = "Name not null")
    private String name;
    @NotNull(message = "Gender not null")
    @Pattern(regexp = "(male|femal)", message = "Gender must be male or female")
    private String gender;
    @NotNull(message = "CardId not null")
    @Pattern(regexp = "\\d+",message = "CardId is a number")
    private String cardId;
    @NotNull(message = "Numberphone not null")
    @Pattern(regexp = "\\d{9}", message = "Number phone is contain 9 number")
    private String phone;
    @NotNull(message = "Email not null")
    @Email(message = "Wrong format for email")
    private String email;
    @JsonFormat(pattern="yyyy-MM-dd")
    @NotNull(message = "Birth day not null")
    @NotEmpty(message = "Birth day not empty")
//    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Wrong date format (yyyy-mm-dd)")
    private Date dateOfBirth;
    @NotNull(message = "Address not null")
    private String address;
    @NotNull(message = "Occupation not null")
    private String occupation;
}
