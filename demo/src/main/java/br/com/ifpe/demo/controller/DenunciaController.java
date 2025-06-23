package br.com.ifpe.demo.controller;

import br.com.ifpe.demo.model.Denuncia;
import br.com.ifpe.demo.service.DenunciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/denuncias")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") // permitir requisições do frontend
public class DenunciaController {

    @Autowired
    private DenunciaService denunciaService;

    @PostMapping
    public Denuncia criarDenuncia(@RequestBody Denuncia denuncia) {
        return denunciaService.salvar(denuncia);
    }

    @GetMapping
    public List<Denuncia> listarDenuncias() {
        return denunciaService.listarTodas();
    }
}
