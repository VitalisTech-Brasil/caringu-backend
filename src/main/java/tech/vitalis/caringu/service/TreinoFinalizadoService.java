package tech.vitalis.caringu.service;

import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.TreinoFinalizado.HorasTreinadasResponseDTO;
import tech.vitalis.caringu.dtos.TreinoFinalizado.HorasTreinadasSemanaMesDTO;
import tech.vitalis.caringu.repository.TreinoFinalizadoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TreinoFinalizadoService {

    private final TreinoFinalizadoRepository repository;

    public TreinoFinalizadoService(TreinoFinalizadoRepository repository) {
        this.repository = repository;
    }


//    public List<EvolucaoCargaDashboardResponseDTO> buscarEvolucaoCarga(Integer alunoId, Integer exercicioId) {
//        return repository.buscarEvolucaoCarga(exercicioId, alunoId);
//    }
//
//    public List<EvolucaoTreinoCumpridoResponseDTO> buscarEvolucaoTreinosCumpridosMensal(Integer alunoId, Integer exercicioId) {
//        return repository.buscarEvolucaoTreinosCumpridosMensal(alunoId, exercicioId);
//    }

//    public HorasTreinadasMensalResponseDTO calcularHorasTreinadas(Integer alunoId, Integer exercicioId) {
//        List<HorasTreinadasMensalDadosBrutosDTO> treinos = repository.buscarTreinosFinalizadosBrutos(alunoId, exercicioId);
//
//        Map<YearMonth, Double> horasPorMes = new HashMap<>();
//
//        Map<YearWeek, Double> horasPorSemana = new HashMap<>();
//
//        for (HorasTreinadasMensalDadosBrutosDTO treino : treinos) {
//            if (treino.dataHorarioFim() == null) continue;
//
//            Duration duracao = Duration.between(treino.dataHorarioInicio(), treino.dataHorarioFim());
//            double horas = duracao.toMinutes() / 60.0;
//
//            YearMonth ym = YearMonth.from(treino.dataHorarioInicio());
//            horasPorMes.merge(ym, horas, Double::sum);
//
//            YearWeek yw = YearWeek.from(treino.dataHorarioInicio());
//            horasPorSemana.merge(yw, horas, Double::sum);
//        }
//
//        double totalHorasSemana = horasPorSemana.values().stream().mapToDouble(Double::doubleValue).sum();
//        double mediaHorasPorSemana = horasPorSemana.size() > 0 ? totalHorasSemana / horasPorSemana.size() : 0;
//
//        List<HorasPorMesDTO> listaHorasPorMes = horasPorMes.entrySet().stream()
//                .map(e -> new HorasPorMesDTO(e.getKey().getYear(), e.getKey().getMonthValue(), e.getValue()))
//                .sorted(Comparator.comparing(HorasPorMesDTO::ano).thenComparing(HorasPorMesDTO::mes))
//                .toList();
//
//        return new HorasTreinadasMensalResponseDTO(alunoId, exercicioId, mediaHorasPorSemana, listaHorasPorMes);
//    }

    public HorasTreinadasResponseDTO buscarHorasTreinadas(Integer alunoId, Integer exercicioId) {
        List<Object[]> resultados = repository.buscarHorasAgrupadasPorAlunoExercicio(alunoId, exercicioId);

        List<HorasTreinadasSemanaMesDTO> dados = resultados.stream()
                .map(r -> new HorasTreinadasSemanaMesDTO(
                        (Integer) r[0],
                        (String) r[1],
                        (Integer) r[2],
                        (String) r[3],
                        ((Number) r[4]).intValue(),
                        ((Number) r[5]).intValue(),
                        ((Number) r[6]).intValue(),
                        ((Number) r[7]).doubleValue()
                ))
                .collect(Collectors.toList());

        return new HorasTreinadasResponseDTO(alunoId, exercicioId, dados);
    }

//    public void atualizarDataHorarioFim(Integer idTreinoFinalizado, AtualizarDataFimDTO dto) {
//        TreinoFinalizado treino = repository.findById(idTreinoFinalizado)
//                .orElseThrow(() -> new TreinoFinalizadoNaoEncontradoException("Treino não encontrado com id: " + idTreinoFinalizado));
//
//        treino.setDataHorarioFim(dto.dataHorarioFim());
//        repository.save(treino);
//    }
}