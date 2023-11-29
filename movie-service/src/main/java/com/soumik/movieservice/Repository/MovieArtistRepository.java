package com.soumik.movieservice.Repository;

import com.soumik.movieservice.Model.MovieArtist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@org.springframework.stereotype.Repository
public interface MovieArtistRepository extends JpaRepository<MovieArtist, Long> {

    public List<MovieArtist> findAllByArtistId(Long artistId);

//
//    @Query("select ma.id, ma.artistId from movie_artist ma INNER JOIN ma.movieId where ma.artistId = :id")
//    public List<MovieResponse> findAllArtistByArtistId(Long id);

}
