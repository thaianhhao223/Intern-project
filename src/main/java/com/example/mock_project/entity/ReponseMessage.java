package com.example.mock_project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReponseMessage {
    private int statusCode;
    private String message;
}
