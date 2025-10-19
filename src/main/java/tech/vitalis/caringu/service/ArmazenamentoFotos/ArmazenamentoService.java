package tech.vitalis.caringu.service.ArmazenamentoFotos;

import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;

public interface ArmazenamentoService {
    String uploadArquivo(MultipartFile arquivo);
    void deletarArquivoPorUrl(String url);
    String gerarUrlPreAssinada(String fileKey, Duration expiracao);
}