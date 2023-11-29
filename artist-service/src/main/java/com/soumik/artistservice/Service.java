package com.soumik.artistservice;

import com.soumik.artistservice.DTO.ArtistRequest;
import com.soumik.artistservice.DTO.ArtistResponse;
import com.soumik.artistservice.DTO.MovieEntity;
import com.soumik.artistservice.Exception.ArtistNotFoundException;
import com.soumik.artistservice.ExternalService.MovieService;
import com.soumik.artistservice.Model.Artist;
import com.soumik.artistservice.Repository.Repository;

import java.util.List;

@org.springframework.stereotype.Service
public class Service {

    final
    Repository repository;
    final
    MovieService movieService;

    public Service(Repository repository, MovieService movieService) {
        this.repository = repository;
        this.movieService = movieService;
    }

    public List<ArtistResponse> getAllArtist() {
        return repository.findAll().stream().map(ArtistResponse::getArtistResponse).toList();
    }

    public List<ArtistResponse> getAllArtistAndMovies() {
        List<ArtistResponse> artists = repository.findAll().stream().map(ArtistResponse::getArtistResponse).toList();

        return artists.stream().peek(
                artistResponse -> {
                    MovieEntity[] movieEntity = movieService.getMovieByArtistId(String.valueOf(artistResponse.getId()));
                    artistResponse.setMovies(List.of(movieEntity));
                }
        ).toList();
    }

    public ArtistResponse getArtistById(Long id) throws ArtistNotFoundException {
        Artist artist = repository.findById(id).orElseThrow(ArtistNotFoundException::new);
        return ArtistResponse.getArtistResponse(artist);
    }

    public ArtistResponse getArtistAndMoviesByArtistId(Long id) throws ArtistNotFoundException {

        Artist artist = repository.findById(id).orElseThrow(ArtistNotFoundException::new);

        MovieEntity[] movieEntity = movieService.getMovieByArtistId(String.valueOf(artist.getId()));

        ArtistResponse artistResponse = ArtistResponse.getArtistResponse(artist);
        artistResponse.setMovies(List.of(movieEntity));

        return artistResponse;
    }

    public ArtistResponse addArtist(ArtistRequest artistRequest) {
        Artist newArtist = Artist.builder()
                .firstName(artistRequest.getFirstName().trim())
                .lastName(artistRequest.getLastName().trim())
                .gender(artistRequest.getGender().trim())
                .build();
        Artist artist = repository.save(newArtist);

        return ArtistResponse.getArtistResponse(artist);
    }

    public ArtistResponse updateArtist(ArtistRequest artistRequest, Long id) throws ArtistNotFoundException {
        Artist artist = repository.findById(id).orElseThrow(ArtistNotFoundException::new);

        artist.setFirstName(artistRequest.getFirstName());
        artist.setLastName(artistRequest.getLastName());
        artist.setGender(artistRequest.getGender());
        repository.save(artist);

        return ArtistResponse.getArtistResponse(artist);
    }

    public void deleteArtist(Long id) throws ArtistNotFoundException {
        Artist artist = repository.findById(id).orElseThrow(ArtistNotFoundException::new);
        repository.delete(artist);
    }
}
