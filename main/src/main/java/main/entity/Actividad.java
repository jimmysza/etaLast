package main.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "actividad")
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idActividad;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Lob
    private String descripcion;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    private String ubicacion;


    @Lob
    private String normas;

    /*borrables*/
    @Lob
    private String incluye;
    
    @Lob
    private String lenguaje;




    @Lob
    private String condiciones;

    private LocalDateTime fechaActividad;
    private String imagen;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    private Integer capacidad;

    @ManyToOne
    @JoinColumn(name = "id_colaborador", nullable = false)
    private Colaborador colaborador;

    // Nueva relación con Categoria (N:M)
    @ManyToMany
    @JoinTable(name = "actividad_categoria", joinColumns = @JoinColumn(name = "id_actividad"), inverseJoinColumns = @JoinColumn(name = "id_categoria"))
    private Set<Categoria> categorias = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "actividad_beneficio", joinColumns = @JoinColumn(name = "id_actividad"), inverseJoinColumns = @JoinColumn(name = "id_beneficio"))
    private Set<Beneficio> beneficios = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "actividad_idioma", joinColumns = @JoinColumn(name = "id_actividad"), inverseJoinColumns = @JoinColumn(name = "id_idioma"))
    private Set<Idioma> idiomas = new HashSet<>();
}
