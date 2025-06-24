package org.example.event.dtos;

import jakarta.persistence.ManyToOne;
import lombok.Value;
import org.example.event.entity.User;

import java.time.LocalDateTime;

@Value
public class EvenDTO {
     String title;
     String description;
     String location;
     String imageUrl;

}
