package br.com.ifpe.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Anuncio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoVeiculo;
    private String titulo;
    private String descricao;
    private double preco;
    private String marca;
    private String modelo;
    private int anoModelo;
    private int anoFabricacao;
    private int ano;
    private int km;
    private String cor;
    private String categoria;
    private String cambio;
    private String combustivel;

    @ElementCollection
    private List<String> opcionais;

    @ElementCollection
    private List<String> imagens;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
