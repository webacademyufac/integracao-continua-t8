package br.ufac.sgcmapi.usuario;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public List<Usuario> consultar(String termoBusca) {
        if (termoBusca != null) {
            termoBusca = termoBusca.trim();
        }
        return repo.consultar(termoBusca);
    }

    public Usuario consultar(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Usuario salvar(Usuario objeto) {
        return repo.save(objeto);
    }

    public void remover(Long id) {
        repo.deleteById(id);
    }
    
}