package ru.nextcloudnext.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Task Management Api",
                description = "Планировщик задач", version = "1.0.0",
                contact = @Contact(
                        name = "Alekseev Aleksandr",
                        email = "ford4601@yandex.ru"
                )
        )
)
public class OpenApiConfig {

}