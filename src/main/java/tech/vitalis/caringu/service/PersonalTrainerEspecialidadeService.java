package tech.vitalis.caringu.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Especialidade.EspecialidadeResponseGetDTO;
import tech.vitalis.caringu.entity.Especialidade;
import tech.vitalis.caringu.entity.PersonalTrainer;
import tech.vitalis.caringu.entity.PersonalTrainerEspecialidade;
import tech.vitalis.caringu.repository.EspecialidadeRepository;
import tech.vitalis.caringu.repository.PersonalTrainerRepository;
import tech.vitalis.caringu.repository.PersonalTrainerEspecialidadeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonalTrainerEspecialidadeService {

    private final EspecialidadeRepository especialidadeRepository;
    private final PersonalTrainerRepository personalTrainerRepository;
    private final PersonalTrainerEspecialidadeRepository associationRepository;

    public PersonalTrainerEspecialidadeService(EspecialidadeRepository especialidadeRepository, PersonalTrainerRepository personalTrainerRepository, PersonalTrainerEspecialidadeRepository associationRepository) {
        this.especialidadeRepository = especialidadeRepository;
        this.personalTrainerRepository = personalTrainerRepository;
        this.associationRepository = associationRepository;
    }

    @Transactional
    public void associarEspecialidades(Integer personalId, List<EspecialidadeResponseGetDTO> especialidadesDTO) {
        PersonalTrainer personal = personalTrainerRepository.findById(personalId)
                .orElseThrow(() -> new RuntimeException("Personal trainer não encontrado"));

        for (EspecialidadeResponseGetDTO dto : especialidadesDTO) {
            Especialidade especialidade;

            if (dto.id() == null) {
                especialidade = especialidadeRepository.findByNomeIgnoreCase(dto.nome())
                        .orElseGet(() -> especialidadeRepository.save(new Especialidade(null, dto.nome(), new ArrayList<>())));
            } else {
                especialidade = especialidadeRepository.findById(dto.id())
                        .orElseThrow(() -> new RuntimeException("Especialidade não encontrada"));
            }

            // Evita duplicidade
            boolean jaAssociado = associationRepository.existsByPersonalTrainerAndEspecialidade(personal, especialidade);
            if (!jaAssociado) {
                associationRepository.save(new PersonalTrainerEspecialidade(personal, especialidade));
            }
        }
    }
}
