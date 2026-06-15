package com.uninter.raizesdonordeste.entrypoint.api.promotion;

import com.uninter.raizesdonordeste.entrypoint.api.promotion.dto.CreatePromotionRequest;
import com.uninter.raizesdonordeste.entrypoint.api.promotion.dto.PromotionResponse;
import com.uninter.raizesdonordeste.entrypoint.api.promotion.dto.UpdatePromotionRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/promotions")
@Tag(name = "Promoções", description = "Gerenciamento de promoções das unidades")
public class PromotionController {

    private final PromotionFacade promotionFacade;

    @Operation(summary = "Cadastrar nova promoção")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Promoção criada com sucesso",
            content = @Content(schema = @Schema(implementation = PromotionResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou datas inconsistentes", content = @Content),
        @ApiResponse(responseCode = "404", description = "Unidade não encontrada", content = @Content)
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<PromotionResponse> create(@Valid @RequestBody CreatePromotionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(promotionFacade.create(request));
    }

    @Operation(summary = "Listar todas as promoções")
    @ApiResponse(responseCode = "200", description = "Lista de promoções retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<PromotionResponse>> findAll() {
        return ResponseEntity.ok(promotionFacade.findAll());
    }

    @Operation(summary = "Buscar promoção por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Promoção encontrada",
            content = @Content(schema = @Schema(implementation = PromotionResponse.class))),
        @ApiResponse(responseCode = "404", description = "Promoção não encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PromotionResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(promotionFacade.findById(id));
    }

    @Operation(summary = "Atualizar promoção")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Promoção atualizada com sucesso",
            content = @Content(schema = @Schema(implementation = PromotionResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou datas inconsistentes", content = @Content),
        @ApiResponse(responseCode = "404", description = "Promoção não encontrada", content = @Content)
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<PromotionResponse> update(@PathVariable Long id,
                                                    @Valid @RequestBody UpdatePromotionRequest request) {
        return ResponseEntity.ok(promotionFacade.update(id, request));
    }

    @Operation(summary = "Remover promoção")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Promoção removida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Promoção não encontrada", content = @Content)
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        promotionFacade.delete(id);
        return ResponseEntity.noContent().build();
    }

}
