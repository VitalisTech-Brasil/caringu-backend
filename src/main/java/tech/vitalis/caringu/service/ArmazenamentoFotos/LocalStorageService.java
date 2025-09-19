package tech.vitalis.caringu.service.ArmazenamentoFotos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@Profile({"dev", "docker"})
public class LocalStorageService implements ArmazenamentoService {

    @Override
    public String uploadArquivo(MultipartFile arquivo) {
        try {
            String nomeArquivo = UUID.randomUUID() + "-" + arquivo.getOriginalFilename();

            String tmpDir = System.getProperty("java.io.tmpdir");
            Path destino = Paths.get(tmpDir, nomeArquivo);

            Files.copy(arquivo.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

            return nomeArquivo;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar arquivo local", e);
        }
    }

    @Override
    public void deletarArquivoPorUrl(String url) {
        try {
            String nomeArquivo = url.substring(url.lastIndexOf("/") + 1);
            String tmpDir = System.getProperty("java.io.tmpdir");
            Path arquivo = Paths.get(tmpDir, nomeArquivo);

            Files.deleteIfExists(arquivo);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao deletar arquivo local", e);
        }
    }
}
