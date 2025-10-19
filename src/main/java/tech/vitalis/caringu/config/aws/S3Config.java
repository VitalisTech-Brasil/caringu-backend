package tech.vitalis.caringu.config.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
@Profile("prod")
public class S3Config {

//    @Value("${aws.access-key-id}")
//    private String accessKeyId;

//    @Value("${aws.secret-access-key}")
//    private String secretAccessKey;

//    @Value("${aws.session-token}")
//    private String sessionToken;

    @Value("${aws.s3.region}")
    private String region;

    @Bean
    public S3Client s3Client() {
        // Descomenta o de baixo para teste local batendo na AWS
//        AwsSessionCredentials awsCredentials = AwsSessionCredentials.create(
//                accessKeyId,
//                secretAccessKey,
//                sessionToken
//        );

        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(
                        // Descomenta o de baixo para teste local batendo na AWS
                        // StaticCredentialsProvider.create(awsCredentials)
                        DefaultCredentialsProvider.create() // se estiver em EC2
                )
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
//        AwsSessionCredentials awsCredentials = AwsSessionCredentials.create(
//                accessKeyId,
//                secretAccessKey,
//                sessionToken
//        );

        return S3Presigner.builder()
                .region(Region.of(region))
                .credentialsProvider(
                        // Descomenta o de baixo para teste local batendo na AWS
                        // StaticCredentialsProvider.create(awsCredentials)
                        DefaultCredentialsProvider.create() // se estiver em EC2
                )
                .build();
    }
}
