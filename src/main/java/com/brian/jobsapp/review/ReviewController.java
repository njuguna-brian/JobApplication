package com.brian.jobsapp.review;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies/{companyId}/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@PathVariable Long companyId) {
        return ResponseEntity.ok(reviewService.getAllReviews(companyId));
    }

    @PostMapping
    public ResponseEntity<String> createReview(@PathVariable Long companyId,@RequestBody Review review) {
         boolean isReviewSaved = reviewService.addReview(companyId,review);
         if (!isReviewSaved) {
             return ResponseEntity.badRequest().body("Company not found");
         }
        return ResponseEntity.ok("Review created successfully");
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable Long companyId, @PathVariable Long reviewId) {
        Review review = reviewService.getReview(companyId, reviewId);
        if (review == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(review);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long companyId, @PathVariable Long reviewId, @RequestBody Review review) {
        boolean updatedReview = reviewService.updateReview(companyId, reviewId, review);
        if (!updatedReview) {
            return ResponseEntity.badRequest().body("Review not found");
        }
        return ResponseEntity.ok("Review updated successfully");
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long companyId, @PathVariable Long reviewId) {
        boolean deletedReview = reviewService.deleteReview(companyId, reviewId);
        if (!deletedReview) {
            return ResponseEntity.badRequest().body("Review not found");
        }
        return ResponseEntity.ok("Review deleted successfully");
    }
}
