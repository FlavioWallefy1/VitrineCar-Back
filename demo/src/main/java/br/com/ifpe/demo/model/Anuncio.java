package br.com.ifpe.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Anuncio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private double preco;
    private String marca;
    private String modelo;
    private int anoModelo;
    private int anoFabricacao;
    private int ano;
    private String favorito;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}