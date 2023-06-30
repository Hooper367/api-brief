package com.apibrief3.repository;


import com.apibrief3.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository  extends JpaRepository<Color, Integer> {
}
