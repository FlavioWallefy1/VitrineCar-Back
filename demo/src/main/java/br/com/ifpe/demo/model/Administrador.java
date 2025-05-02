package br.com.ifpe.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("ADMIN")
public class Administrador extends Usuario {
    // Aqui você pode adicionar mais campos, caso necessário
}
