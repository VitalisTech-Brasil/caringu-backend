package tech.vitalis.caringu.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.config.GerenciadorTokenJwt;
import tech.vitalis.caringu.dtos.Pessoa.security.PessoaLoginDTO;
import tech.vitalis.caringu.dtos.Pessoa.security.PessoaTokenDTO;
import tech.vitalis.caringu.dtos.auth.GoogleLoginResponseDTO;
import tech.vitalis.caringu.dtos.auth.GoogleUserInfo;
import tech.vitalis.caringu.dtos.auth.UserResponseDTO;
import tech.vitalis.caringu.entity.Aluno;
import tech.vitalis.caringu.entity.Pessoa;
import tech.vitalis.caringu.entity.PersonalTrainer;
import tech.vitalis.caringu.repository.PessoaRepository;
import tech.vitalis.caringu.service.SingleSignOn.GoogleAuthStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private final PessoaService pessoaService;
    private final GoogleAuthStrategy googleAuthStrategy;
    private final PessoaRepository pessoaRepository;
    private final GerenciadorTokenJwt jwtService;

    public AuthService(PessoaService pessoaService,
                       GoogleAuthStrategy googleAuthStrategy,
                       PessoaRepository pessoaRepository,
                       GerenciadorTokenJwt jwtService) {
        this.pessoaService = pessoaService;
        this.googleAuthStrategy = googleAuthStrategy;
        this.pessoaRepository = pessoaRepository;
        this.jwtService = jwtService;
    }

    /**
     * Login tradicional com e-mail e senha.
     * Mantém o uso de PessoaService.autenticar para não quebrar fluxos existentes.
     */
    public PessoaTokenDTO loginComSenha(PessoaLoginDTO pessoaLoginDTO) {
        Pessoa pessoa = new Pessoa();
        pessoa.setEmail(pessoaLoginDTO.getEmail());
        pessoa.setSenha(pessoaLoginDTO.getSenha());
        return pessoaService.autenticar(pessoa);
    }

    public GoogleLoginResponseDTO loginComGoogle(String codigoAutorizacao) {
        Optional<GoogleUserInfo> googleUsuarioOpcional = googleAuthStrategy.validateAuthorizationCode(codigoAutorizacao);

        // 5 - Token Google inválido ou expirado
        if (googleUsuarioOpcional.isEmpty()) {
            return GoogleLoginResponseDTO.tokenGoogleInvalido();
        }

        GoogleUserInfo googleUsuario = googleUsuarioOpcional.get();

        // 1 - E-mail não existe no banco -> precisaCadastro
        Optional<Pessoa> pessoaOptional = pessoaRepository.findByEmail(googleUsuario.email());
        if (pessoaOptional.isEmpty()) {
            return GoogleLoginResponseDTO.precisaCadastro(googleUsuario);
        }

        Pessoa pessoa = pessoaOptional.get();

        // 3 - E-mail existe, mas nunca usou Google -> vincular automaticamente
        boolean precisaSalvar = false;
        if (pessoa.getGoogleId() == null || pessoa.getGoogleId().isEmpty()) {
            pessoa.setGoogleId(googleUsuario.sub());
            precisaSalvar = true;
        }

        // Aproveita para atualizar a foto de perfil com a foto do Google, se ainda não houver
        if ((pessoa.getUrlFotoPerfil() == null || pessoa.getUrlFotoPerfil().isBlank())
                && googleUsuario.foto() != null && !googleUsuario.foto().isBlank()) {
            pessoa.setUrlFotoPerfil(googleUsuario.foto());
            precisaSalvar = true;
        }

        if (precisaSalvar) {
            pessoaRepository.save(pessoa);
        }

        // Monta perfis com base no tipo concreto
        List<String> perfis = new ArrayList<>();
        if (pessoa instanceof PersonalTrainer) {
            perfis.add("PERSONAL");
        }
        if (pessoa instanceof Aluno) {
            perfis.add("ALUNO");
        }

        boolean perfilCompleto = isPerfilCompleto(pessoa);

        UserResponseDTO usuario = new UserResponseDTO(
                pessoa.getId(),
                pessoa.getEmail(),
                pessoa.getNome(),
                pessoa.getUrlFotoPerfil(),
                perfis,
                perfilCompleto
        );

        // 2 - Login normal (gera JWT)
        Authentication autenticacao = new UsernamePasswordAuthenticationToken(
                pessoa.getEmail(),
                null,
                List.of() // permissão é tratada em outra camada; aqui só autenticamos
        );

        String token = jwtService.generateToken(autenticacao);

        // 4 - Perfil incompleto -> precisaCompletarPerfil = true
        boolean precisaCompletarPerfil = !perfilCompleto;

        return GoogleLoginResponseDTO.sucesso(token, usuario, precisaCompletarPerfil);
    }

    private boolean isPerfilCompleto(Pessoa pessoa) {
        // Se o campo boolean da entidade estiver true, já consideramos completo
        if (pessoa.isPerfilCompleto()) {
            return true;
        }

        // Critérios básicos mínimos
        boolean hasPhone = pessoa.getCelular() != null && !pessoa.getCelular().isBlank();
        boolean hasGenero = pessoa.getGenero() != null;
        boolean hasDataNascimento = pessoa.getDataNascimento() != null;

        boolean basicInfoComplete = hasPhone && hasGenero && hasDataNascimento;

        // Marca na entidade para evitar reprocessar no futuro
        if (basicInfoComplete && !pessoa.isPerfilCompleto()) {
            pessoa.setPerfilCompleto(true);
            pessoaRepository.save(pessoa);
        }

        return basicInfoComplete;
    }
}


