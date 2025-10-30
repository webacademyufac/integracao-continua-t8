package br.ufac.sgcmapi.atendimento;

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
@RequestMapping("/atendimento")
public class AtendimentoController {
    private final AtendimentoService servico;

    public AtendimentoController(AtendimentoService servico) {
        this.servico = servico;
    }

    @GetMapping("/consultar")
    public ResponseEntity<List<Atendimento>> consultar(
            @RequestParam(required = false) String termoBusca,
            @RequestParam(required = false) List<EStatus> status) {
        var registros = servico.consultar(termoBusca, status);
        return ResponseEntity.ok(registros);
    }

    @GetMapping("/consultar/{id}")
    public ResponseEntity<Atendimento> consultar(@PathVariable Long id) {
        var registro = servico.consultar(id);
        if (registro == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(registro);
    }

    @PostMapping("/inserir")
    public ResponseEntity<Long> inserir(@RequestBody Atendimento objeto) {
        var registro = servico.salvar(objeto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registro.getId());
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Void> atualizar(@RequestBody Atendimento objeto) {
        servico.salvar(objeto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        servico.remover(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<EStatus> atualizarStatus(@PathVariable Long id) {
        var registro = servico.atualizarStatus(id);
        return ResponseEntity.status(HttpStatus.OK).body(registro.getStatus());
    }
}
