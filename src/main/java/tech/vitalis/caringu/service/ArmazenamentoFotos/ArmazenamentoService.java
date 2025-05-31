package tech.vitalis.caringu.service.ArmazenamentoFotos;

import org.springframework.web.multipart.MultipartFile;

public interface ArmazenamentoService {
    String uploadArquivo(MultipartFile arquivo);
    void deletarArquivoPorUrl(String url);
}