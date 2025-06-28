package org.example.event.dtos.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.event.entity.Review;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResDTO {
    Review review;
    Double totalRating;

}
