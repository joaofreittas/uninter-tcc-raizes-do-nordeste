package com.uninter.raizesdonordeste.entrypoint.api.product;

import com.uninter.raizesdonordeste.core.input.product.CreateProductInput;
import com.uninter.raizesdonordeste.core.input.product.UpdateProductInput;
import com.uninter.raizesdonordeste.core.usecase.product.CreateProductUseCase;
import com.uninter.raizesdonordeste.core.usecase.product.DeleteProductUseCase;
import com.uninter.raizesdonordeste.core.usecase.product.FindAllProductsUseCase;
import com.uninter.raizesdonordeste.core.usecase.product.FindProductByIdUseCase;
import com.uninter.raizesdonordeste.core.usecase.product.UpdateProductUseCase;
import com.uninter.raizesdonordeste.entrypoint.api.product.dto.CreateProductRequest;
import com.uninter.raizesdonordeste.entrypoint.api.product.dto.ProductResponse;
import com.uninter.raizesdonordeste.entrypoint.api.product.dto.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductFacade {

    private final CreateProductUseCase createProductUseCase;
    private final FindAllProductsUseCase findAllProductsUseCase;
    private final FindProductByIdUseCase findProductByIdUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;

    public ProductResponse create(final CreateProductRequest request) {
        return ProductResponse.fromDomain(createProductUseCase.execute(
            new CreateProductInput(request.name(), request.price())));
    }

    public List<ProductResponse> findAll() {
        return findAllProductsUseCase.execute().stream().map(ProductResponse::fromDomain).toList();
    }

    public ProductResponse findById(final Long id) {
        return ProductResponse.fromDomain(findProductByIdUseCase.execute(id));
    }

    public ProductResponse update(final Long id, final UpdateProductRequest request) {
        return ProductResponse.fromDomain(updateProductUseCase.execute(
            new UpdateProductInput(id, request.name(), request.price())));
    }

    public void delete(final Long id) {
        deleteProductUseCase.execute(id);
    }

}
