package com.seun.c3.controller;

import com.seun.c3.entity.Movie;
import com.seun.c3.service.CategoryService;
import com.seun.c3.service.MovieCoverService;
import com.seun.c3.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieController {

    private MovieService movieService;
    private CategoryService categoryService;
    private MovieCoverService movieCoverService;

    @Autowired
    public MovieController(MovieService movieService, MovieCoverService movieCoverService, CategoryService categoryService) {
        this.movieService = movieService;
        this.movieCoverService = movieCoverService;
        this.categoryService = categoryService;
    }

    @GetMapping("")
    String listMovies(Model model, @RequestParam(required = false) Long categoryId) {

        List<Movie> movies;
        if (categoryId == null) {
            movies = movieService.allMovies();
        } else {
            movies = categoryService.getCategory(categoryId).getMovies();
        }

        model.addAttribute("title", "Movie list");
        model.addAttribute("movies", movies);
        model.addAttribute("categories", categoryService.allCategories());
        return "movies/all";
    }

    @GetMapping("/new")
    String getForm(Model model){
        Movie movie = new Movie();
        model.addAttribute("movie", movie);
        model.addAttribute("title", "Create new movie");
        model.addAttribute("categories", categoryService.allCategories());
        return "movies/new";
    }

    @PostMapping("/new")
    String addMovie(
            @ModelAttribute Movie movie,
            @RequestParam("image") MultipartFile imageFile ) throws IOException {

        String imageName = StringUtils.cleanPath(imageFile.getOriginalFilename());
        movie.setImageName(imageName);
        Long id = movieService.save(movie).getId();

        movieCoverService.saveMovieCover(id, imageName, imageFile);

        return "redirect:/movies";
    }

    @GetMapping("/edit/{id}")
    String editBook(Model model, @PathVariable Long id){
        Movie movie = movieService.findById(id);
        model.addAttribute("movie", movie);
        model.addAttribute("title", "Edit movie");
        return "movies/new";
    }

    @GetMapping("/delete/{id}")
    String removeMovie(@PathVariable Long id){
        movieService.delete(id);
        return "redirect:/movies";
    }
}

