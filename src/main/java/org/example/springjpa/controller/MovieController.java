package org.example.springjpa.controller;

import lombok.RequiredArgsConstructor;
import org.example.springjpa.model.Movie;
import org.example.springjpa.repository.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {
    private final MovieRepository movieRepository;

    @GetMapping
    public List<Movie> findAll(@RequestParam int page, @RequestParam int size) {
        if (page < 1 || size < 1 || size > 50) {
            throw new IndexOutOfBoundsException();
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Movie> pageResult = movieRepository.findAll(pageable);

        return pageResult.getContent();
    }

    @GetMapping("/filter-by-rating")
    public List<Movie> findAllByRatingBetween(
            @RequestParam double minRating,
            @RequestParam double maxRating,
            @RequestParam int page,
            @RequestParam int size) {

        if (page < 1 || size < 1 || size > 50) {
            throw new IndexOutOfBoundsException();
        }

        Pageable pageable = PageRequest.of(page - 1, size);

        return movieRepository.findAllByRatingBetween(minRating, maxRating, pageable).getContent();
    }

    @GetMapping("/filter-by-rating-year")
    public List<Movie> findAllByRatingBetweenAndYearBetween(@RequestParam double minRating, @RequestParam double maxRating, @RequestParam int startYear, @RequestParam int endYear) {
        return movieRepository.findAllByRatingBetweenAndReleaseYearBetween(minRating, maxRating, startYear, endYear);
    }

    @GetMapping("/filter-by-years-in")
    public List<Movie> findAllByReleaseYearIn(@RequestParam List<Integer> years) {
        return movieRepository.findAllByReleaseYearIn(years);
    }

    @GetMapping("/filter")
    public List<Movie> findAllByRatingBetweenAndReleaseYearBetweenSortedAndPaged(
            @RequestParam double minRating, @RequestParam double maxRating,
            @RequestParam int startYear, @RequestParam int endYear,
            @RequestParam String sortBy, @RequestParam String sortOrder,
            @RequestParam int page, @RequestParam int size
    ) {
        if (page < 1 || size < 1 || size > 50) {
            throw new IndexOutOfBoundsException();
        }
        Pageable pageable = PageRequest.of(page - 1, size);


        Sort.Direction dir = Sort.Direction.ASC;
        if (sortOrder.equals("desc")) {
            dir = Sort.Direction.DESC;
        }

        return movieRepository.findAllByRatingBetweenAndReleaseYearBetween(
                minRating, maxRating,
                startYear, endYear,
                Sort.by(dir, sortBy), pageable);
    }

    @GetMapping("/search")
    public List<Movie> findAllByTitleContainingAndRatingGreaterThanAndReleaseYearAfterSortedAndPaged(
            @RequestParam String query,
            @RequestParam double minRating,
            @RequestParam int startYear,
            @RequestParam String sortBy, @RequestParam String sortOrder,
            @RequestParam int page, @RequestParam int size
    ) {
        if (page < 1 || size < 1 || size > 50) {
            throw new IndexOutOfBoundsException();
        }
        Pageable pageable = PageRequest.of(page - 1, size);


        Sort.Direction dir = Sort.Direction.ASC;
        if (sortOrder.equals("desc")) {
            dir = Sort.Direction.DESC;
        }

        return movieRepository.findAllByTitleContainingIgnoreCaseAndRatingGreaterThanAndReleaseYearAfter(
                query, minRating, startYear,
                Sort.by(dir, sortBy), pageable);
    }
}
