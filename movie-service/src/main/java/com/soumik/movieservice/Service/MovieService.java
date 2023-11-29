package com.soumik.movieservice.Service;

import com.soumik.movieservice.DTO.*;
import com.soumik.movieservice.Exception.MovieNotFoundException;
import com.soumik.movieservice.ExternalService.ArtistExternalService;
import com.soumik.movieservice.Model.Movie;
import com.soumik.movieservice.Model.MovieArtist;
import com.soumik.movieservice.Repository.MovieArtistRepository;
import com.soumik.movieservice.Repository.MovieRepository;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static com.soumik.movieservice.DTO.MovieRequest.getMovieModel;


@org.springframework.stereotype.Service
@NoArgsConstructor
public class MovieService {

    Logger logger = LoggerFactory.getLogger(MovieService.class);

    ArtistExternalService artistExternalService;

    MovieRepository movieRepository;

    MovieArtistRepository movieArtistRepository;

    @Autowired
    public MovieService(ArtistExternalService artistExternalService, MovieRepository movieRepository, MovieArtistRepository movieArtistRepository) {
        this.artistExternalService = artistExternalService;
        this.movieRepository = movieRepository;
        this.movieArtistRepository = movieArtistRepository;
    }


    public List<MovieResponse> findAllMoviesWithArtist() {
        List<MovieResponse> movies = movieRepository.findAll().stream().map(MovieResponse::getMovieResponse).toList();

        ArtistEntity[] artists = artistExternalService.getArtists();


        return movies.stream().peek(
                movieResponse -> {
                    List<ArtistEntity> temp = new ArrayList<>();

                    for (ArtistEntity artistId : movieResponse.getArtistIds()) {
                        if (artists != null) {
                            for (ArtistEntity artist : artists) {

                                if (artist.getId().equals(artistId.getId())) {
                                    artistId.setFirstName(artist.getFirstName());
                                    artistId.setLastName(artist.getLastName());
                                    artistId.setGender(artist.getGender());
                                }
                            }
                        }
                        temp.add(artistId);
                    }
                    movieResponse.setArtistIds(temp);
                }

        ).toList();


    }

    public List<MovieResponse> findAllMoviesWithoutArtist() {
        return movieRepository.findAll().stream().map(MovieResponse::getMovieResponse).toList();
    }

    public MovieResponse findMovieAndArtistById(Long id) throws MovieNotFoundException {
        Movie movies = movieRepository.findById(id)
                .orElseThrow(MovieNotFoundException::new);
        MovieResponse movieResponse = MovieResponse.getMovieResponse(movies);

        List<ArtistEntity> artistIds = movieResponse.getArtistIds();

        for (ArtistEntity artistId : artistIds) {
            ArtistEntity artistEntityBody = artistExternalService.getArtistById(artistId.getId().toString());


                if (artistEntityBody != null) {
                    artistId.setFirstName(artistEntityBody.getFirstName());
                    artistId.setLastName(artistEntityBody.getLastName());
                    artistId.setGender(artistEntityBody.getGender());
                }

        }

        movieResponse.setArtistIds(artistIds);

        return movieResponse;
    }


    public MovieResponse findMovieById(Long id) throws MovieNotFoundException {
        Movie movies = movieRepository.findById(id)
                .orElseThrow(MovieNotFoundException::new);

        return MovieResponse.getMovieResponse(movies);
    }

    public List<MovieWithoutArtistResponse> findAllMoviesByArtistId(Long id) {
        List<MovieArtist> movies = movieArtistRepository.findAllByArtistId(id);
        return MovieWithoutArtistResponse.getMovieWithoutArtistList(movies);
    }

    public MovieResponse addMovie(MovieRequest movieRequest){
        List<Long> artistIds = movieRequest.getArtistIds();
        Movie movie = getMovieModel(movieRequest);
        Movie savedMovie = movieRepository.save(movie);


        for (Long artistId : artistIds) {
            ArtistEntity artistEntityBody = artistExternalService.getArtistById(artistId.toString());
            logger.info(String.valueOf(artistEntityBody));

            MovieArtist movieArtist = new MovieArtist();
            movieArtist.setArtistId(artistEntityBody.getId());
            movieArtist.setMovieId(savedMovie);
            movieArtistRepository.save(movieArtist);
        }

        return MovieResponse.getMovieResponse(savedMovie);

    }

    public MovieResponse updateMovie(MovieRequest movieRequest, Long id) throws MovieNotFoundException {
        Movie movieMovie = movieRepository.findById(id)
                .orElseThrow(MovieNotFoundException::new);

        movieMovie.setFullName(movieRequest.getFullName());
        movieMovie.setLanguage(movieRequest.getLanguage());

        Movie updatedMovieMovie =  movieRepository.save(movieMovie);

        return MovieResponse.getMovieResponse(updatedMovieMovie);
    }

    public void deleteMovie(Long id) throws MovieNotFoundException {
        Movie movieMovie = movieRepository.findById(id)
                .orElseThrow(MovieNotFoundException::new);

        movieRepository.delete(movieMovie);
    }

}
