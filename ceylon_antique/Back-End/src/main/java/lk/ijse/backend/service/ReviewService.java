package lk.ijse.backend.service;

import lk.ijse.backend.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getAllReviews();
    ReviewDTO saveReview(ReviewDTO reviewDTO);
    ReviewDTO updateReview(ReviewDTO reviewDTO);
    String deleteReview(ReviewDTO reviewDTO);
    List<ReviewDTO> getReviewsByProductId(Long productId);
    List<ReviewDTO> getReviewsByUserId(Long userId);
    List<ReviewDTO> getReviewsByStatus(String status);
    List<ReviewDTO> getReviewsByRating(Integer rating);
    Double getAverageRatingByProductId(Long productId);
    Long getApprovedReviewsCountByProductId(Long productId);
    ReviewDTO approveReview(Long id);
    ReviewDTO rejectReview(Long id);
}