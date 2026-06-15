package com.uninter.raizesdonordeste.entrypoint.api.unit;

import com.uninter.raizesdonordeste.entrypoint.api.unit.dto.CreateUnitRequest;
import com.uninter.raizesdonordeste.entrypoint.api.unit.dto.UnitResponse;
import com.uninter.raizesdonordeste.entrypoint.api.unit.dto.UpdateUnitRequest;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/units")
@Tag(name = "Unidades", description = "Gerenciamento de unidades de negócio")
public class UnitController {

    private final UnitFacade unitFacade;

    @Operation(summary = "Cadastrar nova unidade")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Unidade criada com sucesso",
            content = @Content(schema = @Schema(implementation = UnitResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<UnitResponse> create(@Valid @RequestBody CreateUnitRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(unitFacade.create(request));
    }

    @Operation(summary = "Listar todas as unidades")
    @ApiResponse(responseCode = "200", description = "Lista de unidades retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<UnitResponse>> findAll() {
        return ResponseEntity.ok(unitFacade.findAll());
    }

    @Operation(summary = "Buscar unidade por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Unidade encontrada",
            content = @Content(schema = @Schema(implementation = UnitResponse.class))),
        @ApiResponse(responseCode = "404", description = "Unidade não encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<UnitResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(unitFacade.findById(id));
    }

    @Operation(summary = "Atualizar unidade")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Unidade atualizada com sucesso",
            content = @Content(schema = @Schema(implementation = UnitResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Unidade não encontrada", content = @Content)
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<UnitResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody UpdateUnitRequest request) {
        return ResponseEntity.ok(unitFacade.update(id, request));
    }

    @Operation(summary = "Desativar unidade")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Unidade desativada com sucesso",
            content = @Content(schema = @Schema(implementation = UnitResponse.class))),
        @ApiResponse(responseCode = "404", description = "Unidade não encontrada", content = @Content)
    })
    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<UnitResponse> deactivate(@PathVariable Long id) {
        return ResponseEntity.ok(unitFacade.deactivate(id));
    }

    @Operation(summary = "Remover unidade")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Unidade removida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Unidade não encontrada", content = @Content)
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        unitFacade.delete(id);
        return ResponseEntity.noContent().build();
    }

}
