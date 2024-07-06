package com.brian.jobsapp.review.imp;

import com.brian.jobsapp.company.Company;
import com.brian.jobsapp.company.CompanyService;
import com.brian.jobsapp.review.Review;
import com.brian.jobsapp.review.ReviewRepository;
import com.brian.jobsapp.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CompanyService companyService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, CompanyService companyService) {
        this.reviewRepository = reviewRepository;
        this.companyService = companyService;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public boolean addReview(Long companyId, Review review) {
        Company company = companyService.getCompanyById(companyId);
        if (company != null) {
            review.setCompany(company);
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public Review getReview(Long companyId, Long reviewId) {
       List<Review> reviews= reviewRepository.findByCompanyId(companyId);
       return reviews.stream().filter(review -> review.getId().equals(reviewId)).findFirst().orElse(null);
    }

    @Override
    public boolean updateReview(Long companyId, Long reviewId, Review review) {
        Review reviewToUpdate = getReview(companyId, reviewId);
        if (reviewToUpdate != null) {
            reviewToUpdate.setReview(review.getReview());
            reviewToUpdate.setDescription(review.getDescription());
            reviewToUpdate.setRating(review.getRating());
            reviewRepository.save(reviewToUpdate);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReview(Long companyId, Long reviewId) {
        Review reviewToDelete = getReview(companyId, reviewId);
        if (reviewToDelete != null) {
            reviewRepository.delete(reviewToDelete);
            return true;
        }
        return false;
    }

}
