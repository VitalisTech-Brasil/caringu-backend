package tech.vitalis.caringu.service.ArmazenamentoFotos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.ServerSideEncryption;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import tech.vitalis.caringu.exception.Aws.ArmazenamentoException;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Profile("prod")
public class AwsS3Service implements ArmazenamentoService{

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public AwsS3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
        public String uploadArquivo(MultipartFile file) {
        try{
            String fileName = generateFileName(file);
            Map<String, String> metadata = new HashMap<>();

            metadata.put("uploaded-by", "caringu-api");
            metadata.put("upload-date", Instant.now().toString());
            metadata.put("original-filename", sanitizeFilename(file.getOriginalFilename()));
            metadata.put("file-size", String.valueOf(file.getSize()));

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .serverSideEncryption(ServerSideEncryption.AES256)
                    .metadata(metadata)
                    .cacheControl("max-age=31536000")
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            return getFileUrl(fileName);
        } catch (IOException e) {
            throw new ArmazenamentoException("Erro ao fazer upload do arquivo: " + e.getMessage(), e);
        }
    }


    @Override
    public void deletarArquivoPorUrl(String fileName){
        try{
            String fileKey = extractFileKey(fileName);

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileKey)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
        }catch (Exception e){
            throw new ArmazenamentoException("Erro ao deletar o arquivo: " + e.getMessage(), e);
        }
    }

    private String generateFileName(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String extension = "";

        if(originalFileName != null && originalFileName.contains(".")){
            extension = originalFileName
                    .substring(originalFileName.lastIndexOf("."))
                    .toLowerCase();
        }

        return UUID.randomUUID().toString() + "-" + System.currentTimeMillis() + extension;
    }

    private String sanitizeFilename(String fileName){
        if (fileName == null){
            return "unknown";
        }

        return fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
    }

    private String extractFileKey(String fileNameOrUrl){
        if (fileNameOrUrl.startsWith("http")){
            return fileNameOrUrl.substring(fileNameOrUrl.lastIndexOf("/") + 1);
        }
        return fileNameOrUrl;
    }

    private String getFileUrl(String fileName){
        return String.format("https://%s.s3.amazonaws.com/%s", bucketName, fileName);
    }
}
