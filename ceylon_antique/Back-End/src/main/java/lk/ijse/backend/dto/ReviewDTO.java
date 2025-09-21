package lk.ijse.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
    private Long id;
    private Integer rating;
    private String reviewText;
    private String commentText;
    private Long userId;
    private Long productId;
    private String userName;
    private String productName;
}