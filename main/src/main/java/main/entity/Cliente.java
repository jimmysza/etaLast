package main.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @Column(nullable = false, unique = true)
    private Long cedula;


    private String direccion;

    @Lob
    private String preferencias;  // info adicional del cliente

    /**
     * @ManyToOne
    @JoinColumn(name = "id_actividad")
    private Actividad actividadSolicitada;


    Esta es la relacion para que en el dashboard de cliente se pueda ver la actividad que ha solicitado.
    El codigo me lo dio copilot y lo he puesto comentado porque no se si es correcto o no.
     */
}