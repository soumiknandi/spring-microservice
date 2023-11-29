package com.soumik.movieservice.Repository;

import com.soumik.movieservice.Model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

@org.springframework.stereotype.Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    public ArrayList<Movie> findAllByArtistIds_artistId(Long id);
}
