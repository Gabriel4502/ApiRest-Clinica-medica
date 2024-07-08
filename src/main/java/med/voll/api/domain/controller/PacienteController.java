package med.voll.api.domain.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarPaciente(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder){

        var paciente = new Paciente(dados);
        pacienteRepository.save(paciente);

        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));

    }

    @GetMapping
    public ResponseEntity<Page <DadosListagemPaciente>> listarPacientes(@PageableDefault(size = 5)Pageable paginacao){
        var page  = pacienteRepository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemPaciente::new);

        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarPaciente(@RequestBody @Valid DadosAtualizacaoPaciente dados){
        var paciente = pacienteRepository.getReferenceById(dados.id());
        paciente.atualizarPaciente(dados);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletarPaciente(@PathVariable Long id){
        var paciente = pacienteRepository.getReferenceById(id);
        paciente.excluir();

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }


}
