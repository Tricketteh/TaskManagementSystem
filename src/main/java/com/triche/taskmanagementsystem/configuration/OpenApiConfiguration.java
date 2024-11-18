package com.triche.taskmanagementsystem.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Task Management System",
                description = "Task Management System", version = "1.0.0",
                contact = @Contact(
                        email = "tr1cste@yandex.ru",
                        url = "https://github.com/Tricketteh"
                )
        )
)
public class OpenApiConfiguration {
}
