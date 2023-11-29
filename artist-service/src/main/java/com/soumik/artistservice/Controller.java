package com.soumik.artistservice;

import com.soumik.artistservice.DTO.ArtistRequest;
import com.soumik.artistservice.DTO.ArtistResponse;
import com.soumik.artistservice.Exception.ArtistNotFoundException;
import com.soumik.artistservice.Exception.InvalidIdException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.soumik.artistservice.Helper.Helper.convertId;

@RestController
@RequestMapping("/api/artist")
@SecurityRequirement(name = "Authorization")
@Tag(name = "Artist Service API")
public class Controller {

    Logger logger = LoggerFactory.getLogger(Controller.class);

    final
    Service service;

    public Controller(Service service) {
        this.service = service;
    }

    @GetMapping
    @CircuitBreaker(name = "movieBreaker", fallbackMethod = "getAllArtistWithoutMovie")
    public ResponseEntity<List<ArtistResponse>> getAllArtist() {
        return new ResponseEntity<>(service.getAllArtistAndMovies(), HttpStatus.OK);
    }

    public ResponseEntity<List<ArtistResponse>> getAllArtistWithoutMovie(Exception ex) {
        logger.info("Service Down" + ex.getMessage());
        return new ResponseEntity<>(service.getAllArtist(), HttpStatus.MULTI_STATUS);
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "movieBreaker", fallbackMethod = "getArtistByIdWithoutMovie")
    public ResponseEntity<ArtistResponse> getArtistById(@PathVariable String id) throws ArtistNotFoundException, InvalidIdException {
        return new ResponseEntity<>(service.getArtistAndMoviesByArtistId(convertId(id)), HttpStatus.OK);
    }

    public ResponseEntity<ArtistResponse> getArtistByIdWithoutMovie(@PathVariable String id, Exception ex) throws ArtistNotFoundException {
        return new ResponseEntity<>(service.getArtistById(convertId(id)), HttpStatus.MULTI_STATUS);
    }

    @PostMapping
    public ArtistResponse addArtist(@RequestBody @Valid ArtistRequest artistRequest) {
        return service.addArtist(artistRequest);
    }

    @PutMapping("/{id}")
    public ArtistResponse updateArtist(@RequestBody @Valid ArtistRequest artistRequest, @PathVariable String id) throws ArtistNotFoundException, InvalidIdException {
        return service.updateArtist(artistRequest,convertId(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtist(@PathVariable String id) throws ArtistNotFoundException, InvalidIdException {
        service.deleteArtist(convertId(id));
    }

}
