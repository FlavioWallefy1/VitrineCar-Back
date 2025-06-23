package br.com.ifpe.demo.service;

import br.com.ifpe.demo.model.Denuncia;
import br.com.ifpe.demo.repository.DenunciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DenunciaService {

    @Autowired
    private DenunciaRepository denunciaRepository;

    public Denuncia salvar(Denuncia denuncia) {
        return denunciaRepository.save(denuncia);
    }

    public List<Denuncia> listarTodas() {
        return denunciaRepository.findAll();
    }
}
