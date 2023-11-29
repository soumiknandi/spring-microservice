package com.soumik.movieservice;

import com.soumik.movieservice.DTO.MovieRequest;
import com.soumik.movieservice.DTO.MovieResponse;
import com.soumik.movieservice.DTO.MovieWithoutArtistResponse;
import com.soumik.movieservice.Exception.InvalidIdException;
import com.soumik.movieservice.Exception.MovieNotFoundException;
import com.soumik.movieservice.Service.MovieService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.soumik.movieservice.Helper.Helper.convertId;

@RestController
@RequestMapping("/api/movie")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
@Tag(name = "Movie Service API")
public class Controller {

    Logger logger = LoggerFactory.getLogger(Controller.class);

    MovieService movieService;

    @Autowired
    public Controller(MovieService movieService) {
        this.movieService = movieService;
    }


    @GetMapping("/")
    @CircuitBreaker(name = "artistBreaker", fallbackMethod = "artistFallback")
    public ResponseEntity<List<MovieResponse>> getAllMovies () {
        return new ResponseEntity<>(movieService.findAllMoviesWithArtist(), HttpStatus.OK);
    }

    public ResponseEntity<List<MovieResponse>> artistFallback(Exception ex) {
        logger.info("Test :: " + ex.getMessage());
        return new ResponseEntity<>(movieService.findAllMoviesWithoutArtist(), HttpStatus.MULTI_STATUS);
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "artistBreaker", fallbackMethod = "artistByIdFallback")
    public ResponseEntity<MovieResponse> getMovieById (@PathVariable String id) throws MovieNotFoundException, InvalidIdException {
            return new ResponseEntity<>(movieService.findMovieAndArtistById(convertId(id)), HttpStatus.OK);
    }

    public ResponseEntity<MovieResponse> artistByIdFallback(String id, Exception ex) throws MovieNotFoundException {
        return new ResponseEntity<>(movieService.findMovieById(convertId(id)), HttpStatus.MULTI_STATUS);
    }


    @PostMapping
    @CircuitBreaker(name = "artistBreaker", fallbackMethod = "artistAddFallback")
    public ResponseEntity<MovieResponse> addMovie(@RequestBody @Valid MovieRequest movieRequest) {
        MovieResponse movieResponse = movieService.addMovie(movieRequest);
        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    public ResponseEntity<MovieResponse> artistAddFallback(MovieRequest movieRequest, Exception ex) {
        MovieResponse movieResponse = MovieResponse.builder()
                .id(-1L)
                .fullName("Dummy")
                .language("Dummy")
                .artistIds(null)
                .build();
        return new ResponseEntity<>(movieResponse, HttpStatus.MULTI_STATUS);
    }

    @PutMapping("/{id}")
    @CircuitBreaker(name = "artistBreaker", fallbackMethod = "artistUpdateFallback")
    public ResponseEntity<MovieResponse> updateMovie(@RequestBody @Valid MovieRequest movieRequest, @PathVariable String id) throws MovieNotFoundException, InvalidIdException {
        return new ResponseEntity<>(movieService.updateMovie(movieRequest, convertId(id)), HttpStatus.CREATED);
    }

    public ResponseEntity<MovieResponse> artistUpdateFallback(MovieRequest movieRequest, String id, Exception ex) {
        MovieResponse movieResponse = MovieResponse.builder()
                .id(-1L)
                .fullName("Dummy")
                .language("Dummy")
                .artistIds(null)
                .build();                ;
        return new ResponseEntity<>(movieResponse, HttpStatus.MULTI_STATUS);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable String id) throws MovieNotFoundException, InvalidIdException {
        movieService.deleteMovie(convertId(id));
    }


    @GetMapping("/artist/{id}")
    public ResponseEntity<List<MovieWithoutArtistResponse>> getMoviesByArtistId(@PathVariable String id){
        return new ResponseEntity<>(movieService.findAllMoviesByArtistId(convertId(id)), HttpStatus.OK);
    }

}
