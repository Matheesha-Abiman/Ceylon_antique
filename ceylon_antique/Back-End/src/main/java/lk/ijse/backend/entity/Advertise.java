package lk.ijse.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "advertise")
public class Advertise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String imageUrl;
    private String link;
    private LocalDateTime postedDate;
    private String status;
    private String type;
    private Integer impressions;
    private Integer clicks;
    private Double budget;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}