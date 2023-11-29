package com.soumik.artistservice.ExternalService;


import com.soumik.artistservice.DTO.MovieEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MOVIE-SERVICE")
public interface MovieService {

    @GetMapping("/api/movie/artist/{id}")
    public MovieEntity[] getMovieByArtistId(@PathVariable String id);

}
