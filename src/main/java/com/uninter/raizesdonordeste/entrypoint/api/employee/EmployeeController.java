package com.uninter.raizesdonordeste.entrypoint.api.employee;

import com.uninter.raizesdonordeste.entrypoint.api.employee.dto.AddEvaluationRequest;
import com.uninter.raizesdonordeste.entrypoint.api.employee.dto.CreateEmployeeRequest;
import com.uninter.raizesdonordeste.entrypoint.api.employee.dto.EmployeeResponse;
import com.uninter.raizesdonordeste.entrypoint.api.employee.dto.EvaluationResponse;
import com.uninter.raizesdonordeste.entrypoint.api.employee.dto.UpdateEmployeeRequest;
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
@RequestMapping("/api/v1/employees")
@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
@Tag(name = "Funcionários", description = "Gerenciamento de funcionários")
public class EmployeeController {

    private final EmployeeFacade employeeFacade;

    @Operation(summary = "Cadastrar novo funcionário")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Funcionário criado com sucesso",
            content = @Content(schema = @Schema(implementation = EmployeeResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou documento já cadastrado", content = @Content),
        @ApiResponse(responseCode = "404", description = "Unidade não encontrada", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EmployeeResponse> create(@Valid @RequestBody CreateEmployeeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeFacade.create(request));
    }

    @Operation(summary = "Listar todos os funcionários")
    @ApiResponse(responseCode = "200", description = "Lista de funcionários retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> findAll() {
        return ResponseEntity.ok(employeeFacade.findAll());
    }

    @Operation(summary = "Buscar funcionário por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Funcionário encontrado",
            content = @Content(schema = @Schema(implementation = EmployeeResponse.class))),
        @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeFacade.findById(id));
    }

    @Operation(summary = "Atualizar funcionário")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Funcionário atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = EmployeeResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> update(@PathVariable Long id,
                                                   @Valid @RequestBody UpdateEmployeeRequest request) {
        return ResponseEntity.ok(employeeFacade.update(id, request));
    }

    @Operation(summary = "Adicionar avaliação ao funcionário")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Avaliação adicionada com sucesso",
            content = @Content(schema = @Schema(implementation = EvaluationResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos (rating deve ser entre 1 e 5)", content = @Content),
        @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content)
    })
    @PatchMapping("/{id}/evaluations")
    public ResponseEntity<EvaluationResponse> addEvaluation(@PathVariable Long id,
                                                            @Valid @RequestBody AddEvaluationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeFacade.addEvaluation(id, request));
    }

    @Operation(summary = "Remover funcionário")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Funcionário removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeFacade.delete(id);
        return ResponseEntity.noContent().build();
    }

}
