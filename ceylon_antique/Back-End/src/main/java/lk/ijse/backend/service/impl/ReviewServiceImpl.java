package lk.ijse.backend.service.impl;

import lk.ijse.backend.dto.ReviewDTO;
import lk.ijse.backend.entity.Product;
import lk.ijse.backend.entity.Review;
import lk.ijse.backend.entity.User;
import lk.ijse.backend.repository.ProductRepository;
import lk.ijse.backend.repository.ReviewRepository;
import lk.ijse.backend.repository.UserRepository;
import lk.ijse.backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDTO saveReview(ReviewDTO reviewDTO) {
        Review review = convertToEntity(reviewDTO);
        review.setCreatedAt(LocalDateTime.now());
        review.setStatus("PENDING"); // Default status
        Review saved = reviewRepository.save(review);
        return convertToDTO(saved);
    }

    @Override
    public ReviewDTO updateReview(ReviewDTO reviewDTO) {
        Review existingReview = reviewRepository.findById(reviewDTO.getId()).orElse(null);
        if (existingReview != null) {
            existingReview.setRating(reviewDTO.getRating());
            existingReview.setReviewText(reviewDTO.getReviewText());
            existingReview.setCommentText(reviewDTO.getCommentText());

            if (reviewDTO.getUserId() != null && !reviewDTO.getUserId().equals(existingReview.getUser().getId())) {
                User user = userRepository.findById(reviewDTO.getUserId()).orElse(null);
                existingReview.setUser(user);
            }

            if (reviewDTO.getProductId() != null && !reviewDTO.getProductId().equals(existingReview.getProduct().getId())) {
                Product product = productRepository.findById(reviewDTO.getProductId()).orElse(null);
                existingReview.setProduct(product);
            }

            Review updated = reviewRepository.save(existingReview);
            return convertToDTO(updated);
        }
        return null;
    }

    @Override
    public String deleteReview(ReviewDTO reviewDTO) {
        reviewRepository.deleteById(reviewDTO.getId());
        return "Review deleted successfully!";
    }

    @Override
    public List<ReviewDTO> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProductId(productId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> getReviewsByStatus(String status) {
        return reviewRepository.findByStatus(status)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> getReviewsByRating(Integer rating) {
        return reviewRepository.findByRating(rating)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Double getAverageRatingByProductId(Long productId) {
        return reviewRepository.findAverageRatingByProductId(productId);
    }

    @Override
    public Long getApprovedReviewsCountByProductId(Long productId) {
        return reviewRepository.countApprovedReviewsByProductId(productId);
    }

    @Override
    public ReviewDTO approveReview(Long id) {
        Review review = reviewRepository.findById(id).orElse(null);
        if (review != null) {
            review.setStatus("APPROVED");
            Review updated = reviewRepository.save(review);
            return convertToDTO(updated);
        }
        return null;
    }

    @Override
    public ReviewDTO rejectReview(Long id) {
        Review review = reviewRepository.findById(id).orElse(null);
        if (review != null) {
            review.setStatus("REJECTED");
            Review updated = reviewRepository.save(review);
            return convertToDTO(updated);
        }
        return null;
    }

    // === Helper methods ===
    private ReviewDTO convertToDTO(Review review) {
        return ReviewDTO.builder()
                .id(review.getId())
                .rating(review.getRating())
                .reviewText(review.getReviewText())
                .commentText(review.getCommentText())
                .userId(review.getUser() != null ? review.getUser().getId() : null)
                .productId(review.getProduct() != null ? review.getProduct().getId() : null)
                .userName(review.getUser() != null ? review.getUser().getUsername() : null)
                .productName(review.getProduct() != null ? review.getProduct().getProductName() : null)
                .build();
    }

    private Review convertToEntity(ReviewDTO dto) {
        User user = null;
        if (dto.getUserId() != null) {
            user = userRepository.findById(dto.getUserId()).orElse(null);
        }

        Product product = null;
        if (dto.getProductId() != null) {
            product = productRepository.findById(dto.getProductId()).orElse(null);
        }

        return Review.builder()
                .id(dto.getId())
                .rating(dto.getRating())
                .reviewText(dto.getReviewText())
                .commentText(dto.getCommentText())
                .user(user)
                .product(product)
                .build();
    }
}