package lk.ijse.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogDTO {
    private Long id;
    private String title;
    private String content;
    private String postedDate;
    private Long userId;
    private String authorName;
    private String category;
    private String tags;
    private String status;
    private String imageUrl;
}