//package tech.vitalis.caringu.mapper;
//
//import org.springframework.stereotype.Component;
//import tech.vitalis.caringu.dtos.Estado.EstadoRequestPostDTO;
//import tech.vitalis.caringu.dtos.Estado.EstadoResponseGetDTO;
//import tech.vitalis.caringu.entity.Estado;
//
//@Component
//public class EstadoMapper {
//    public Estado toEntity(EstadoRequestPostDTO dto) {
//        Estado estado = new Estado();
//        estado.setNome(dto.nome());
//        return estado;
//    }
//
//    public EstadoResponseGetDTO toResponseDTO(Estado estado) {
//        return new EstadoResponseGetDTO(
//                estado.getId(),
//                estado.getNome());
//    }
//}