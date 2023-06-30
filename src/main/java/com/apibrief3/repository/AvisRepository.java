package com.apibrief3.repository;

import com.apibrief3.model.Avis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvisRepository  extends JpaRepository<Avis, Integer> {
}
