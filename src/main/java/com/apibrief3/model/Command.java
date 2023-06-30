package com.apibrief3.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Command {

    @Id
    @GeneratedValue
    private Integer id;

    private LocalDateTime dateCommand;

    @ManyToOne
    private User user;

    @OneToOne
    private CommandDetails commandDetails;

}
