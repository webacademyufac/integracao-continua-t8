package br.ufac.sgcmapi.profissional;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ProfissionalService {

    private final ProfissionalRepository repo;

    public ProfissionalService(ProfissionalRepository repo) {
        this.repo = repo;
    }

    public List<Profissional> consultar(String termoBusca) {
        if (termoBusca != null) {
            termoBusca = termoBusca.trim();
        }
        return repo.consultar(termoBusca);
    }

    public Profissional consultar(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Profissional salvar(Profissional objeto) {
        return repo.save(objeto);
    }

    public void remover(Long id) {
        repo.deleteById(id);
    }
    
}