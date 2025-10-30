package br.ufac.sgcmapi.unidade;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UnidadeService {

    private final UnidadeRepository repo;

    public UnidadeService(UnidadeRepository repo) {
        this.repo = repo;
    }

    public List<Unidade> consultar(String termoBusca) {
        if (termoBusca != null) {
            termoBusca = termoBusca.trim();
        }
        return repo.consultar(termoBusca);
    }

    public Unidade consultar(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Unidade salvar(Unidade objeto) {
        return repo.save(objeto);
    }

    public void remover(Long id) {
        repo.deleteById(id);
    }
    
}