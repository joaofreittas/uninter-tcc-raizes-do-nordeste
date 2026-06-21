package com.uninter.raizesdonordeste.entrypoint.api.payment;

import com.uninter.raizesdonordeste.entrypoint.api.payment.dto.PaymentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
@Tag(name = "Pagamentos", description = "Gerenciamento de pagamentos")
public class PaymentController {

    private final PaymentFacade paymentFacade;

    @Operation(summary = "Listar todos os pagamentos")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de pagamentos retornada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<PaymentResponse>> findAll() {
        return ResponseEntity.ok(paymentFacade.findAll());
    }

}
