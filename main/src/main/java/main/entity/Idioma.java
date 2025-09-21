package main.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "idioma")
public class Idioma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idIdioma;

    @Column(nullable = false, length = 10)
    private String codigo; // 'es', 'en', 'fr'

    @Column(nullable = false, length = 50)
    private String nombre; // 'Español', 'Inglés'
}