package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.Endereco;

@Entity(name = "Paciente")
@Table(name = "pacientes")
@NoArgsConstructor
@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String telefone;

    private String email;

    private String cpf;

    private boolean ativo;

    @Embedded
    private Endereco endereco;

    public Paciente (DadosCadastroPaciente dados){
        this.nome = dados.nome();
        this.email = dados.email();
        this.cpf = dados.cpf();
        this.telefone = dados.telefone();
        this.ativo = true;
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarPaciente(DadosAtualizacaoPaciente dados){
        if(dados.nome() != null){
            this.nome = dados.nome();
        }

        if(dados.endereco()!= null){
            this.endereco.atualizarInformacoes(dados.endereco());
        }

        if(dados.telefone()!= null){
            this.telefone = dados.telefone();
        }
    }

    public void excluir (){
        this.ativo = false;
    }

}
