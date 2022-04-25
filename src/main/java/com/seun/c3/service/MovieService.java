package com.seun.c3.service;

import com.seun.c3.entity.Movie;
import com.seun.c3.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> allMovies() {
        List<Movie> allMovies = (List<Movie>) movieRepository.findAll();
        return allMovies;
    }

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie findById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        movieRepository.deleteById(id);
    }

}

