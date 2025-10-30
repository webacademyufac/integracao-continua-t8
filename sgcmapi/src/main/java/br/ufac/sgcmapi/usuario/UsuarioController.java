package br.ufac.sgcmapi.usuario;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config/usuario")
public class UsuarioController {

    private final UsuarioService servico;

    public UsuarioController(UsuarioService servico) {
        this.servico = servico;
    }

    @GetMapping("/consultar")
    public ResponseEntity<List<Usuario>> consultar(@RequestParam(required = false) String termoBusca) {
        var registros = servico.consultar(termoBusca);
        return ResponseEntity.status(HttpStatus.OK).body(registros);
    }

    @GetMapping("/consultar/{id}")
    public ResponseEntity<Usuario> consultar(@PathVariable Long id) {
        var registro = servico.consultar(id);
        if (registro == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(registro);
    }

    @PostMapping("/inserir")
    public ResponseEntity<Long> inserir(@RequestBody Usuario objeto) {
        var registro = servico.salvar(objeto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registro.getId());
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Void> atualizar(@RequestBody Usuario objeto) {
        servico.salvar(objeto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        servico.remover(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
}