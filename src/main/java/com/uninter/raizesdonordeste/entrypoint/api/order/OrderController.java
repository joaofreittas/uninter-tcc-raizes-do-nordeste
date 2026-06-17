package com.uninter.raizesdonordeste.entrypoint.api.order;

import com.uninter.raizesdonordeste.entrypoint.api.order.dto.CreateOrderRequest;
import com.uninter.raizesdonordeste.entrypoint.api.order.dto.OrderResponse;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@PreAuthorize("isAuthenticated()")
@Tag(name = "Pedidos", description = "Gerenciamento de pedidos")
public class OrderController {

    private final OrderFacade orderFacade;

    @Operation(
        summary = "Criar novo pedido",
        description = "Registra o pedido com status PENDING e inicia o processamento de pagamento de forma assíncrona."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "202", description = "Pedido recebido e em processamento",
            content = @Content(schema = @Schema(implementation = OrderResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou itens ausentes", content = @Content),
        @ApiResponse(responseCode = "404", description = "Cliente, unidade ou produto não encontrado", content = @Content)
    })
    @PostMapping
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody CreateOrderRequest request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderFacade.create(request));
    }

}
