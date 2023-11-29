package com.soumik.movieservice.ExternalService;

import com.soumik.movieservice.DTO.ArtistEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ARTIST-SERVICE")
public interface ArtistExternalService {

    @GetMapping("/api/artist/{id}")
    public ArtistEntity getArtistById(@PathVariable String id);

    @GetMapping("/api/artist")
    public ArtistEntity[] getArtists();


}
