package tech.vitalis.caringu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.vitalis.caringu.dtos.Plano.PlanoRequisicaoRecord;
import tech.vitalis.caringu.dtos.Plano.PlanoRespostaRecord;
import tech.vitalis.caringu.service.PlanoService;

import java.util.List;

@RestController
@RequestMapping("/planos")
@SecurityRequirement(name = "Bearer")
public class PlanoController {
    private final PlanoService planoService;

    public PlanoController(PlanoService planoService) {
        this.planoService = planoService;
    }

    @GetMapping("/buscarPorPersonal/{personalId}")
    @Operation(summary = "Buscar todos os planos")
    public ResponseEntity<List<PlanoRespostaRecord>> listarPlanosPorPersonal(@PathVariable Integer personalId){
        List<PlanoRespostaRecord> listaPlanoRespostaRecord = planoService.listarPlanosPorPersonal(personalId);
        if (listaPlanoRespostaRecord.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(listaPlanoRespostaRecord);
    }

    @PostMapping("/cadastrarPlano/{personalId}")
    @Operation(summary = "Cadastrar plano")
    public ResponseEntity<PlanoRespostaRecord> cadastrarPlano (@PathVariable Integer personalId, @RequestBody PlanoRequisicaoRecord planoRequisicaoRecord) {
        PlanoRespostaRecord planoRespostaRecord = planoService.cadastrarPlano(personalId, planoRequisicaoRecord);
        return ResponseEntity.status(201).body(planoRespostaRecord);
    }

    @PutMapping("/atualizarPlano/{personalId}/{planoId}")
    @Operation(summary = "Atualizar plano")
    public ResponseEntity<PlanoRespostaRecord> atualizarPlano(@PathVariable Integer personalId, @PathVariable Integer planoId, @RequestBody PlanoRequisicaoRecord planoRequisicaoRecord){
        PlanoRespostaRecord planoRespostaRecord = planoService.atualizarPlano(planoId, planoRequisicaoRecord, personalId);
        return ResponseEntity.status(200).body(planoRespostaRecord);
    }


    @DeleteMapping("/deletarPlano/{personalId}/{planoId}")
    @Operation(summary = "Deletar plano")
    public ResponseEntity<Void> deletarPlano(@PathVariable Integer personalId, @PathVariable Integer planoId){
        planoService.deletarPlano(personalId,planoId);
        return ResponseEntity.status(204).build();
    }




}
