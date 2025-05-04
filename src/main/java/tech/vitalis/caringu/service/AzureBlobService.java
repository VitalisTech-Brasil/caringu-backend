package tech.vitalis.caringu.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class AzureBlobService {

//    @Value("${azure.storage.connection-string}")
    private String connectionString;

//    @Value("${azure.storage.container-name}")
    private String containerName;

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
}
