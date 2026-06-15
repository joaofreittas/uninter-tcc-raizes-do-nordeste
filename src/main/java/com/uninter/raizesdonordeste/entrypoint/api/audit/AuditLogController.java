package com.uninter.raizesdonordeste.entrypoint.api.audit;

import com.uninter.raizesdonordeste.entrypoint.api.audit.dto.AuditLogResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/audit-logs")
@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
@Tag(name = "Auditoria", description = "Consulta de operações auditadas")
public class AuditLogController {

    private final AuditLogFacade auditLogFacade;

    @Operation(summary = "Listar todas as operações auditadas")
    @ApiResponse(responseCode = "200", description = "Lista de registros de auditoria retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<AuditLogResponse>> findAll() {
        return ResponseEntity.ok(auditLogFacade.findAll());
    }

    @Operation(summary = "Buscar registro de auditoria por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registro encontrado",
            content = @Content(schema = @Schema(implementation = AuditLogResponse.class))),
        @ApiResponse(responseCode = "404", description = "Registro não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<AuditLogResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(auditLogFacade.findById(id));
    }

}
