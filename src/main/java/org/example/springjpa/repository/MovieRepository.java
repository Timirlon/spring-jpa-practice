package org.example.springjpa.repository;

import org.example.springjpa.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Page<Movie> findAllByRatingBetween(double minRating, double maxRating, Pageable pageable);

    List<Movie> findAllByRatingBetweenAndReleaseYearBetween(double minRating, double maxRating, int minYear, int maxYear);

    List<Movie> findAllByReleaseYearIn(Collection<Integer> releaseYearCol);
}
