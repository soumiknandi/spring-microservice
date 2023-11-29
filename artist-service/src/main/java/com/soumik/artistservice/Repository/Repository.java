package com.soumik.artistservice.Repository;

import com.soumik.artistservice.Model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository<Artist,Long> {
}
