package tech.vitalis.caringu.dtos.PersonalTrainerEspecialidade;

import tech.vitalis.caringu.dtos.Especialidade.EspecialidadeResponseGetDTO;

import java.util.List;

public record EspecialidadeAssociacaoRequest(List<EspecialidadeResponseGetDTO> especialidades) {
}
