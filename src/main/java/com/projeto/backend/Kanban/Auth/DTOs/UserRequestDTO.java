package com.projeto.backend.Kanban.Auth.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(

        @NotBlank(message = "Nome obrigatorio")
        @Size(min = 2, message = "Nome precisa conter pelo menos 2 caracteres")
        String name,

        @NotBlank(message = "Email obrigatorio")
        @Email(message = "E-mail invalido")
        String email,

        @NotBlank(message = "Senha e obrigatorio")
        @Size(min = 6, message = "Senha deve conter pelo menos 6 caracteres")
        String password

) {}
