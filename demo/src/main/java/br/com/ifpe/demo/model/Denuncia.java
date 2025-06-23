package br.com.ifpe.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Denuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String motivo;

    @Column(length = 1000)
    private String detalhes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anuncio_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Anuncio anuncio;
}
