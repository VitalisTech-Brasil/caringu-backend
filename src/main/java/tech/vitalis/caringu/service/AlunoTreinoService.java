    package tech.vitalis.caringu.service;

    import org.springframework.stereotype.Service;

    @Service
    public class AlunoTreinoService {

//        private final AlunoTreinoRepository alunoTreinoRepository;
//        private final AlunoRepository alunoRepository;
//        private final TreinoExercicioRepository2 treinoExercicioRepository;
//        private final AlunoTreinoMapper alunoTreinoMapper;
//
//        public AlunoTreinoService(AlunoTreinoRepository alunoTreinoRepository, AlunoRepository alunoRepository, TreinoExercicioRepository2 treinoExercicioRepository, AlunoTreinoMapper alunoTreinoMapper) {
//            this.alunoTreinoRepository = alunoTreinoRepository;
//            this.alunoRepository = alunoRepository;
//            this.treinoExercicioRepository = treinoExercicioRepository;
//            this.alunoTreinoMapper = alunoTreinoMapper;
//        }

//        public AlunoTreinoResponseGetDTO cadastrar(AlunoTreinoRequestPostDTO alunoTreinoDTO){
//            // Verifica se o aluno existe
//            Aluno alunoExistente = alunoRepository.findById(alunoTreinoDTO.alunosId())
//                    .orElseThrow(() -> new ApiExceptions.BadRequestException(
//                            String.format("Aluno com o ID %d não encontrado.", alunoTreinoDTO.alunosId())
//                    ));
//
//            // Verifica se o treino exercício existe
//            TreinoExercicio treinoExercicioExistente = treinoExercicioRepository.findById(alunoTreinoDTO.treinosExerciciosId())
//                    .orElseThrow(() -> new ApiExceptions.BadRequestException(
//                            String.format("Treino Exercício com o ID %d não encontrado.", alunoTreinoDTO.treinosExerciciosId())
//                    ));
//
//            AlunoTreino alunoTreino = alunoTreinoMapper.toEntity(alunoTreinoDTO);
//            alunoTreino.setAlunos(alunoExistente); // Associa o aluno
//            alunoTreino.setTreinosExercicios(treinoExercicioExistente); // Associa o treino exercício
//
//            AlunoTreino alunoTreinoSalvo = alunoTreinoRepository.save(alunoTreino);
//
//            return alunoTreinoMapper.toResponseDTO(alunoTreinoSalvo);
//        }

//        public List<AlunoTreinoResponseGetDTO> listarTodos(){
//            return alunoTreinoRepository.findAll()
//                    .stream()
//                    .map(alunoTreinoMapper::toResponseDTO)
//                    .collect(Collectors.toList());
//        }
//
//        public AlunoTreinoResponseGetDTO buscarPorId(Integer id){
//            AlunoTreino alunoTreino = alunoTreinoRepository.findById(id)
//                    .orElseThrow(() -> new ApiExceptions.ResourceNotFoundException("Aluno Treino com ID " + id + " não encontrado"));
//
//            return alunoTreinoMapper.toResponseDTO(alunoTreino);
//        }

//        public Integer contarTreinosProximosVencimento(Integer personalId, int dias) {
//            LocalDate dataLimite = LocalDate.now().plusDays(dias);
//            return alunoTreinoRepository.countTreinosProximosVencimento(personalId, dataLimite);
//        }

//        public AlunoTreinoResponseGetDTO atualizar(Integer id, AlunoTreinoRequestUpdateDTO treinoDTO, Integer alunosId, Integer treinosExerciciosId){
//            AlunoTreino alunoTreinoExistente = alunoTreinoRepository.findById(id)
//                    .orElseThrow(() -> new ApiExceptions.BadRequestException("Aluno Treino com o ID " + id + " não encontrado."));
//
//            Aluno alunoExistente = alunoRepository.findById(alunosId) // Mudando id para alunosId
//                    .orElseThrow(() -> new ApiExceptions.BadRequestException("Aluno com o ID " + alunosId + " não encontrado."));
//
//            TreinoExercicio treinoExercicioExistente = treinoExercicioRepository.findById(treinosExerciciosId)
//                    .orElseThrow(() -> new ApiExceptions.BadRequestException("Treino Exercício com o ID " + treinosExerciciosId + " não encontrado."));
//
//            alunoTreinoExistente.setAlunos(alunoExistente);
//            alunoTreinoExistente.setTreinosExercicios(treinoExercicioExistente);
////            alunoTreinoExistente.setDataHorarioInicio(treinoDTO.dataHorarioInicio());
////            alunoTreinoExistente.setDataHorarioFim(treinoDTO.dataHorarioFim());
//            alunoTreinoExistente.setDiasSemana(treinoDTO.diasSemana());
////            alunoTreinoExistente.setPeriodoAvaliacao(treinoDTO.periodoAvaliacao());
//            alunoTreinoExistente.setDataVencimento(treinoDTO.dataVencimento());
//
//            AlunoTreino alunoTreino = alunoTreinoRepository.save(alunoTreinoExistente);
//            return alunoTreinoMapper.toResponseDTO(alunoTreino);
//        }

//        public void remover(Integer id){
//            AlunoTreino alunoTreinoExistente = alunoTreinoRepository.findById(id)
//                    .orElseThrow(() -> new ApiExceptions.BadRequestException("Aluno Treino com o ID " + id + " não encontrado."));

//            if (alunoTreinoExistente.getDataHorarioInicio().isBefore(LocalDateTime.now())) {throw new ApiExceptions.BadRequestException("A data de início deve ser no futuro.");}

//            if (alunoTreinoExistente.getDataHorarioFim().isBefore(LocalDateTime.now())) {throw new ApiExceptions.BadRequestException("A data de fim deve ser no futuro.");}

//            if (alunoTreinoExistente.getDataVencimento().isBefore(LocalDate.now())) {
//                throw new ApiExceptions.BadRequestException("A data de vencimento deve ser no futuro.");
//            }
//            alunoTreinoRepository.deleteById(id);
//        }

//        public void removerAssociacaoComTreino(Integer id){
//            AlunoTreino alunoTreinoExistente = alunoTreinoRepository.findById(id)
//                    .orElseThrow(() -> new ApiExceptions.BadRequestException("Aluno Treino com o ID " + id + " não encontrado."));
//
//
//            alunoTreinoExistente.setTreinosExercicios(null);
//            alunoTreinoRepository.save(alunoTreinoExistente);
//        }

//        public void removerAssociacaoComAluno(Integer id){
//            AlunoTreino alunoTreinoExistente = alunoTreinoRepository.findById(id)
//                    .orElseThrow(() -> new ApiExceptions.BadRequestException("Aluno Treino com o ID " + id + " não encontrado."));
//
//
//            alunoTreinoExistente.setAlunos(null);
//            alunoTreinoRepository.save(alunoTreinoExistente);
//        }

//        public void removerAssociacao(Integer id) {
//            AlunoTreino alunoTreinoExistente = alunoTreinoRepository.findById(id)
//                    .orElseThrow(() -> new ApiExceptions.BadRequestException("Aluno Treino com o ID " + id + " não encontrado."));
//
//            alunoTreinoExistente.setTreinosExercicios(null);
//            alunoTreinoExistente.setAlunos(null);
//            alunoTreinoRepository.save(alunoTreinoExistente); // desassocia
//        }
    }
