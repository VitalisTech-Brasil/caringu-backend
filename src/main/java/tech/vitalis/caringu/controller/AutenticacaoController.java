package tech.vitalis.caringu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.vitalis.caringu.dtos.Pessoa.security.PessoaLoginDTO;
import tech.vitalis.caringu.dtos.Pessoa.security.PessoaTokenDTO;
import tech.vitalis.caringu.entity.Pessoa;
import tech.vitalis.caringu.mapper.PessoaMapper;
import tech.vitalis.caringu.service.PessoaService;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping("/login")
    public ResponseEntity<PessoaTokenDTO> login(@RequestBody PessoaLoginDTO pessoaLoginDto) {

        final Pessoa pessoa = PessoaMapper.of(pessoaLoginDto);
        PessoaTokenDTO pessoaTokenDto = this.pessoaService.autenticar(pessoa);

        return ResponseEntity.status(200).body(pessoaTokenDto);
    }

}
