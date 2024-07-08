package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteEMedicoInativo implements ValidadorAgendamentoConsultas {

    @Autowired
    MedicoRepository medicoRepository;
    @Autowired
    PacienteRepository pacienteRepository;

    public void validar(DadosAgendamentoConsulta dados){

        if(dados.idMedico()== null){
            return;
        }

        var medicoInativo = medicoRepository.findAtivoById(dados.idMedico());
        var pacienteInativo = pacienteRepository.findAtivoById(dados.idPaciente());
        if(!medicoInativo){
            throw new ValidacaoException("Medico inativo!");
        }else if(!pacienteInativo){
            throw new ValidacaoException("Paciente inativo!");
        }
    }

}
