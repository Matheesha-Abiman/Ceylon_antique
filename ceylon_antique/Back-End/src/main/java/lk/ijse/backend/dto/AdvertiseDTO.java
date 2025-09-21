package lk.ijse.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdvertiseDTO {
    private Long id;
    private String title;
    private String imageUrl;
    private String link;
    private String postedDate;
    private Long userId;
    private String status;
    private String type;
    private Integer impressions;
    private Integer clicks;
    private Double budget;
}