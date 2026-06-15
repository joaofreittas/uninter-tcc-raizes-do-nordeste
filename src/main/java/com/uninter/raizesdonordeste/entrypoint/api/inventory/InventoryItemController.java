package com.uninter.raizesdonordeste.entrypoint.api.inventory;

import com.uninter.raizesdonordeste.entrypoint.api.inventory.dto.AdjustStockRequest;
import com.uninter.raizesdonordeste.entrypoint.api.inventory.dto.CreateInventoryItemRequest;
import com.uninter.raizesdonordeste.entrypoint.api.inventory.dto.InventoryItemResponse;
import com.uninter.raizesdonordeste.entrypoint.api.inventory.dto.UpdateInventoryItemRequest;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/inventory-items")
@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
@Tag(name = "Estoque", description = "Controle de estoque de produtos por unidade")
public class InventoryItemController {

    private final InventoryItemFacade inventoryItemFacade;

    @Operation(summary = "Cadastrar item no estoque")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Item criado com sucesso",
            content = @Content(schema = @Schema(implementation = InventoryItemResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Unidade ou produto não encontrado", content = @Content)
    })
    @PostMapping
    public ResponseEntity<InventoryItemResponse> create(@Valid @RequestBody CreateInventoryItemRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryItemFacade.create(request));
    }

    @Operation(summary = "Listar itens do estoque",
        description = "Retorna todos os itens. Filtre por unitId ou productId via query param.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<InventoryItemResponse>> findAll(
        @RequestParam Optional<Long> unitId,
        @RequestParam Optional<Long> productId) {
        return ResponseEntity.ok(inventoryItemFacade.findAll(unitId, productId));
    }

    @Operation(summary = "Buscar item do estoque por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Item encontrado",
            content = @Content(schema = @Schema(implementation = InventoryItemResponse.class))),
        @ApiResponse(responseCode = "404", description = "Item não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<InventoryItemResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryItemFacade.findById(id));
    }

    @Operation(summary = "Atualizar quantidades do item")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = InventoryItemResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Item não encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<InventoryItemResponse> update(@PathVariable Long id,
                                                        @Valid @RequestBody UpdateInventoryItemRequest request) {
        return ResponseEntity.ok(inventoryItemFacade.update(id, request));
    }

    @Operation(summary = "Adicionar quantidade ao estoque")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estoque incrementado com sucesso",
            content = @Content(schema = @Schema(implementation = InventoryItemResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Item não encontrado", content = @Content)
    })
    @PatchMapping("/{id}/add-stock")
    public ResponseEntity<InventoryItemResponse> addStock(@PathVariable Long id,
                                                          @Valid @RequestBody AdjustStockRequest request) {
        return ResponseEntity.ok(inventoryItemFacade.addStock(id, request));
    }

    @Operation(summary = "Remover quantidade do estoque")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estoque decrementado com sucesso",
            content = @Content(schema = @Schema(implementation = InventoryItemResponse.class))),
        @ApiResponse(responseCode = "400", description = "Quantidade insuficiente ou dados inválidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Item não encontrado", content = @Content)
    })
    @PatchMapping("/{id}/remove-stock")
    public ResponseEntity<InventoryItemResponse> removeStock(@PathVariable Long id,
                                                             @Valid @RequestBody AdjustStockRequest request) {
        return ResponseEntity.ok(inventoryItemFacade.removeStock(id, request));
    }

}
