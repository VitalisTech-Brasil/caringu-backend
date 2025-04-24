package tech.vitalis.caringu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.vitalis.caringu.dtos.Pessoa.security.PessoaDetalhesDTO;
import tech.vitalis.caringu.entity.Pessoa;
import tech.vitalis.caringu.repository.PessoaRepository;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Pessoa> pessoaOpt = pessoaRepository.findByEmail(username);

        if (pessoaOpt.isEmpty()) {

            throw new UsernameNotFoundException(String.format("Pessoa: %s nao encontrada", username));
        }

        return new PessoaDetalhesDTO(pessoaOpt.get());
    }
}
