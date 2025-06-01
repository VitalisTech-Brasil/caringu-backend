package tech.vitalis.caringu.service.ArmazenamentoFotos;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@Profile("prod")
public class AzureBlobService implements ArmazenamentoService {

    @Value("${azure.storage.connection-string}")
    private String connectionString;

    @Value("${azure.storage.container-name}")
    private String containerName;

    @Override
    public String uploadArquivo(MultipartFile arquivo) {
        try {
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                    .connectionString(connectionString)
                    .buildClient();

            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

            String nomeArquivo = UUID.randomUUID() + "-" + arquivo.getOriginalFilename();
            BlobClient blobClient = containerClient.getBlobClient(nomeArquivo);

            blobClient.upload(arquivo.getInputStream(), arquivo.getSize(), true);

            return blobClient.getBlobUrl();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao fazer upload para o Azure Blob", e);
        }
    }

    @Override
    public void deletarArquivoPorUrl(String url) {
        try {
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                    .connectionString(connectionString)
                    .buildClient();

            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

            String blobName = url.substring(url.lastIndexOf("/") + 1);
            BlobClient blobClient = containerClient.getBlobClient(blobName);

            if (blobClient.exists()) {
                blobClient.delete();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar arquivo do Azure Blob", e);
        }
    }
}
