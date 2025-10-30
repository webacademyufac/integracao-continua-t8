package br.ufac.sgcmapi.convenio;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ConvenioService {

    private final ConvenioRepository repo;

    public ConvenioService(ConvenioRepository repo) {
        this.repo = repo;
    }

    public List<Convenio> consultar(String termoBusca) {
        if (termoBusca != null) {
            termoBusca = termoBusca.trim();
        }
        return repo.consultar(termoBusca);
    }

    public Convenio consultar(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Convenio salvar(Convenio objeto) {
        return repo.save(objeto);
    }

    public void remover(Long id) {
        repo.deleteById(id);
    }

    public List<Convenio> consultarAtivos() {
        return repo.findByAtivo(true);
    }
    
}
