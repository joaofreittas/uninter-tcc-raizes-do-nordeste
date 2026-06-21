package com.uninter.raizesdonordeste.core.usecase.order;

import com.uninter.raizesdonordeste.core.domain.customer.CustomerDomain;
import com.uninter.raizesdonordeste.core.domain.inventory.InventoryItemDomain;
import com.uninter.raizesdonordeste.core.domain.order.DeliveryType;
import com.uninter.raizesdonordeste.core.domain.order.OrderChannel;
import com.uninter.raizesdonordeste.core.domain.order.OrderCreatedEvent;
import com.uninter.raizesdonordeste.core.domain.order.OrderDomain;
import com.uninter.raizesdonordeste.core.domain.order.OrderItemDomain;
import com.uninter.raizesdonordeste.core.domain.order.OrderStatus;
import com.uninter.raizesdonordeste.core.domain.payment.PaymentType;
import com.uninter.raizesdonordeste.core.domain.product.ProductDomain;
import com.uninter.raizesdonordeste.core.domain.unit.UnitDomain;
import com.uninter.raizesdonordeste.core.exception.DomainException;
import com.uninter.raizesdonordeste.core.exception.ResourceNotFoundException;
import com.uninter.raizesdonordeste.core.gateway.CustomerGateway;
import com.uninter.raizesdonordeste.core.gateway.InventoryItemGateway;
import com.uninter.raizesdonordeste.core.gateway.OrderGateway;
import com.uninter.raizesdonordeste.core.gateway.OrderItemGateway;
import com.uninter.raizesdonordeste.core.gateway.ProductGateway;
import com.uninter.raizesdonordeste.core.gateway.UnitGateway;
import com.uninter.raizesdonordeste.core.input.order.CreateOrderInput;
import com.uninter.raizesdonordeste.core.input.order.OrderItemInput;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.instancio.Select.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateOrderUseCaseTest {

    @Mock
    private CustomerGateway customerGateway;

    @Mock
    private UnitGateway unitGateway;

    @Mock
    private ProductGateway productGateway;

    @Mock
    private InventoryItemGateway inventoryItemGateway;

    @Mock
    private OrderGateway orderGateway;

    @Mock
    private OrderItemGateway orderItemGateway;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private CreateOrderUseCase createOrderUseCase;

    @Test
    @DisplayName("Given a valid input with a single item, when execute is called, then should return the order domain with all correct fields")
    void givenValidInputWithSingleItem_whenExecute_thenShouldReturnOrderDomainWithAllCorrectFields() {
        var product = Instancio.of(ProductDomain.class)
            .set(field(ProductDomain::getId), 10L)
            .set(field(ProductDomain::getName), "Tapioca")
            .set(field(ProductDomain::getPrice), new BigDecimal("15.00"))
            .create();

        var inventoryItem = Instancio.of(InventoryItemDomain.class)
            .set(field(InventoryItemDomain::getQuantity), 20)
            .set(field(InventoryItemDomain::getMinimumQuantity), 5)
            .create();

        var savedOrder = Instancio.of(OrderDomain.class)
            .set(field(OrderDomain::getId), 100L)
            .set(field(OrderDomain::getCustomerId), 1L)
            .set(field(OrderDomain::getUnitId), 2L)
            .set(field(OrderDomain::getDeliveryType), DeliveryType.PICKUP)
            .set(field(OrderDomain::getOrderChannel), OrderChannel.APP)
            .set(field(OrderDomain::getStatus), OrderStatus.PENDING)
            .set(field(OrderDomain::getTotalAmount), new BigDecimal("30.00"))
            .create();

        var savedItem = Instancio.create(OrderItemDomain.class);

        var input = new CreateOrderInput(
            1L, 2L, DeliveryType.PICKUP, OrderChannel.APP, PaymentType.PIX,
            List.of(new OrderItemInput(10L, 2))
        );

        when(customerGateway.findById(1L)).thenReturn(Optional.of(Instancio.create(CustomerDomain.class)));
        when(unitGateway.findById(2L)).thenReturn(Optional.of(Instancio.create(UnitDomain.class)));
        when(productGateway.findById(10L)).thenReturn(Optional.of(product));
        when(inventoryItemGateway.findByUnitIdAndProductId(2L, 10L)).thenReturn(Optional.of(inventoryItem));
        when(orderGateway.save(any())).thenReturn(savedOrder);
        when(orderItemGateway.save(any())).thenReturn(savedItem);

        var result = createOrderUseCase.execute(input);

        assertThat(result.getId()).isEqualTo(100L);
        assertThat(result.getCustomerId()).isEqualTo(1L);
        assertThat(result.getUnitId()).isEqualTo(2L);
        assertThat(result.getDeliveryType()).isEqualTo(DeliveryType.PICKUP);
        assertThat(result.getOrderChannel()).isEqualTo(OrderChannel.APP);
        assertThat(result.getStatus()).isEqualTo(OrderStatus.PENDING);
        assertThat(result.getTotalAmount()).isEqualByComparingTo(new BigDecimal("30.00"));
        assertThat(result.getItems()).hasSize(1).containsExactly(savedItem);
    }

    @Test
    @DisplayName("Given a valid input with multiple items, when execute is called, then should calculate the total amount as the sum of price times quantity for each item")
    void givenValidInputWithMultipleItems_whenExecute_thenShouldCalculateTotalAmountCorrectly() {
        var productA = Instancio.of(ProductDomain.class)
            .set(field(ProductDomain::getId), 10L)
            .set(field(ProductDomain::getPrice), new BigDecimal("10.00"))
            .create();

        var productB = Instancio.of(ProductDomain.class)
            .set(field(ProductDomain::getId), 20L)
            .set(field(ProductDomain::getPrice), new BigDecimal("25.00"))
            .create();

        var sufficientInventory = Instancio.of(InventoryItemDomain.class)
            .set(field(InventoryItemDomain::getQuantity), 10)
            .set(field(InventoryItemDomain::getMinimumQuantity), 2)
            .create();

        var input = new CreateOrderInput(
            1L, 2L, DeliveryType.DELIVERY, OrderChannel.WEBSITE, PaymentType.CREDIT_CARD,
            List.of(new OrderItemInput(10L, 3), new OrderItemInput(20L, 2))
        );

        when(customerGateway.findById(1L)).thenReturn(Optional.of(Instancio.create(CustomerDomain.class)));
        when(unitGateway.findById(2L)).thenReturn(Optional.of(Instancio.create(UnitDomain.class)));
        when(productGateway.findById(10L)).thenReturn(Optional.of(productA));
        when(productGateway.findById(20L)).thenReturn(Optional.of(productB));
        when(inventoryItemGateway.findByUnitIdAndProductId(2L, 10L)).thenReturn(Optional.of(sufficientInventory));
        when(inventoryItemGateway.findByUnitIdAndProductId(2L, 20L)).thenReturn(Optional.of(sufficientInventory));

        ArgumentCaptor<OrderDomain> orderCaptor = ArgumentCaptor.forClass(OrderDomain.class);
        when(orderGateway.save(orderCaptor.capture())).thenReturn(Instancio.create(OrderDomain.class));
        when(orderItemGateway.save(any())).thenReturn(Instancio.create(OrderItemDomain.class));

        createOrderUseCase.execute(input);

        // expected: (10.00 * 3) + (25.00 * 2) = 30.00 + 50.00 = 80.00
        assertThat(orderCaptor.getValue().getTotalAmount())
            .isEqualByComparingTo(new BigDecimal("80.00"));
    }

    @Test
    @DisplayName("Given a valid input, when execute is called, then should save the order with the correct customer, unit, delivery type, channel and pending status")
    void givenValidInput_whenExecute_thenShouldSaveOrderWithCorrectAttributes() {
        var product = Instancio.of(ProductDomain.class)
            .set(field(ProductDomain::getId), 10L)
            .set(field(ProductDomain::getPrice), new BigDecimal("20.00"))
            .create();

        var inventoryItem = Instancio.of(InventoryItemDomain.class)
            .set(field(InventoryItemDomain::getQuantity), 10)
            .set(field(InventoryItemDomain::getMinimumQuantity), 1)
            .create();

        var input = new CreateOrderInput(
            5L, 7L, DeliveryType.COUNTER, OrderChannel.TOTEM, PaymentType.CASH,
            List.of(new OrderItemInput(10L, 1))
        );

        when(customerGateway.findById(5L)).thenReturn(Optional.of(Instancio.create(CustomerDomain.class)));
        when(unitGateway.findById(7L)).thenReturn(Optional.of(Instancio.create(UnitDomain.class)));
        when(productGateway.findById(10L)).thenReturn(Optional.of(product));
        when(inventoryItemGateway.findByUnitIdAndProductId(7L, 10L)).thenReturn(Optional.of(inventoryItem));

        ArgumentCaptor<OrderDomain> orderCaptor = ArgumentCaptor.forClass(OrderDomain.class);
        when(orderGateway.save(orderCaptor.capture())).thenReturn(Instancio.create(OrderDomain.class));
        when(orderItemGateway.save(any())).thenReturn(Instancio.create(OrderItemDomain.class));

        createOrderUseCase.execute(input);

        var captured = orderCaptor.getValue();
        assertThat(captured.getCustomerId()).isEqualTo(5L);
        assertThat(captured.getUnitId()).isEqualTo(7L);
        assertThat(captured.getDeliveryType()).isEqualTo(DeliveryType.COUNTER);
        assertThat(captured.getOrderChannel()).isEqualTo(OrderChannel.TOTEM);
        assertThat(captured.getStatus()).isEqualTo(OrderStatus.PENDING);
    }

    @Test
    @DisplayName("Given a valid input, when execute is called, then should save each order item with the correct order id, product id, name, quantity and unit price")
    void givenValidInput_whenExecute_thenShouldSaveOrderItemsWithCorrectAttributes() {
        var product = Instancio.of(ProductDomain.class)
            .set(field(ProductDomain::getId), 10L)
            .set(field(ProductDomain::getName), "Cuscuz")
            .set(field(ProductDomain::getPrice), new BigDecimal("8.50"))
            .create();

        var inventoryItem = Instancio.of(InventoryItemDomain.class)
            .set(field(InventoryItemDomain::getQuantity), 10)
            .set(field(InventoryItemDomain::getMinimumQuantity), 1)
            .create();

        var savedOrder = Instancio.of(OrderDomain.class)
            .set(field(OrderDomain::getId), 55L)
            .create();

        var input = new CreateOrderInput(
            1L, 2L, DeliveryType.PICKUP, OrderChannel.APP, PaymentType.PIX,
            List.of(new OrderItemInput(10L, 3))
        );

        when(customerGateway.findById(1L)).thenReturn(Optional.of(Instancio.create(CustomerDomain.class)));
        when(unitGateway.findById(2L)).thenReturn(Optional.of(Instancio.create(UnitDomain.class)));
        when(productGateway.findById(10L)).thenReturn(Optional.of(product));
        when(inventoryItemGateway.findByUnitIdAndProductId(2L, 10L)).thenReturn(Optional.of(inventoryItem));
        when(orderGateway.save(any())).thenReturn(savedOrder);

        ArgumentCaptor<OrderItemDomain> itemCaptor = ArgumentCaptor.forClass(OrderItemDomain.class);
        when(orderItemGateway.save(itemCaptor.capture())).thenReturn(Instancio.create(OrderItemDomain.class));

        createOrderUseCase.execute(input);

        var captured = itemCaptor.getValue();
        assertThat(captured.getOrderId()).isEqualTo(55L);
        assertThat(captured.getProductId()).isEqualTo(10L);
        assertThat(captured.getProductName()).isEqualTo("Cuscuz");
        assertThat(captured.getQuantity()).isEqualTo(3);
        assertThat(captured.getUnitPrice()).isEqualByComparingTo(new BigDecimal("8.50"));
    }

    @Test
    @DisplayName("Given a valid input, when execute is called, then should publish an OrderCreatedEvent with the correct order id and payment type")
    void givenValidInput_whenExecute_thenShouldPublishOrderCreatedEventWithCorrectData() {
        var product = Instancio.of(ProductDomain.class)
            .set(field(ProductDomain::getId), 10L)
            .set(field(ProductDomain::getPrice), new BigDecimal("12.00"))
            .create();

        var inventoryItem = Instancio.of(InventoryItemDomain.class)
            .set(field(InventoryItemDomain::getQuantity), 10)
            .set(field(InventoryItemDomain::getMinimumQuantity), 1)
            .create();

        var savedOrder = Instancio.of(OrderDomain.class)
            .set(field(OrderDomain::getId), 77L)
            .create();

        var input = new CreateOrderInput(
            1L, 2L, DeliveryType.DELIVERY, OrderChannel.WEBSITE, PaymentType.PIX,
            List.of(new OrderItemInput(10L, 1))
        );

        when(customerGateway.findById(1L)).thenReturn(Optional.of(Instancio.create(CustomerDomain.class)));
        when(unitGateway.findById(2L)).thenReturn(Optional.of(Instancio.create(UnitDomain.class)));
        when(productGateway.findById(10L)).thenReturn(Optional.of(product));
        when(inventoryItemGateway.findByUnitIdAndProductId(2L, 10L)).thenReturn(Optional.of(inventoryItem));
        when(orderGateway.save(any())).thenReturn(savedOrder);
        when(orderItemGateway.save(any())).thenReturn(Instancio.create(OrderItemDomain.class));

        createOrderUseCase.execute(input);

        ArgumentCaptor<OrderCreatedEvent> eventCaptor = ArgumentCaptor.forClass(OrderCreatedEvent.class);
        verify(eventPublisher).publishEvent(eventCaptor.capture());
        assertThat(eventCaptor.getValue().orderId()).isEqualTo(77L);
        assertThat(eventCaptor.getValue().paymentType()).isEqualTo(PaymentType.PIX);
    }

    @Test
    @DisplayName("Given a product whose stock equals exactly the minimum quantity, when execute is called, then should succeed without throwing any exception")
    void givenProductWithStockExactlyAtMinimumQuantity_whenExecute_thenShouldSucceed() {
        var product = Instancio.of(ProductDomain.class)
            .set(field(ProductDomain::getId), 10L)
            .set(field(ProductDomain::getPrice), new BigDecimal("10.00"))
            .create();

        var inventoryItem = Instancio.of(InventoryItemDomain.class)
            .set(field(InventoryItemDomain::getQuantity), 5)
            .set(field(InventoryItemDomain::getMinimumQuantity), 5)
            .create();

        var input = new CreateOrderInput(
            1L, 2L, DeliveryType.PICKUP, OrderChannel.APP, PaymentType.CASH,
            List.of(new OrderItemInput(10L, 1))
        );

        when(customerGateway.findById(1L)).thenReturn(Optional.of(Instancio.create(CustomerDomain.class)));
        when(unitGateway.findById(2L)).thenReturn(Optional.of(Instancio.create(UnitDomain.class)));
        when(productGateway.findById(10L)).thenReturn(Optional.of(product));
        when(inventoryItemGateway.findByUnitIdAndProductId(2L, 10L)).thenReturn(Optional.of(inventoryItem));
        when(orderGateway.save(any())).thenReturn(Instancio.create(OrderDomain.class));
        when(orderItemGateway.save(any())).thenReturn(Instancio.create(OrderItemDomain.class));

        assertThatCode(() -> createOrderUseCase.execute(input)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Given a non-existent customer id, when execute is called, then should throw ResourceNotFoundException and not interact with the order gateway")
    void givenNonExistentCustomer_whenExecute_thenShouldThrowResourceNotFoundException() {
        var input = new CreateOrderInput(
            99L, 2L, DeliveryType.PICKUP, OrderChannel.APP, PaymentType.PIX,
            List.of(new OrderItemInput(10L, 1))
        );

        when(customerGateway.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> createOrderUseCase.execute(input))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("99");

        verifyNoInteractions(orderGateway, orderItemGateway, eventPublisher);
    }

    @Test
    @DisplayName("Given a non-existent unit id, when execute is called, then should throw ResourceNotFoundException and not interact with the order gateway")
    void givenNonExistentUnit_whenExecute_thenShouldThrowResourceNotFoundException() {
        var input = new CreateOrderInput(
            1L, 99L, DeliveryType.PICKUP, OrderChannel.APP, PaymentType.PIX,
            List.of(new OrderItemInput(10L, 1))
        );

        when(customerGateway.findById(1L)).thenReturn(Optional.of(Instancio.create(CustomerDomain.class)));
        when(unitGateway.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> createOrderUseCase.execute(input))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("99");

        verifyNoInteractions(orderGateway, orderItemGateway, eventPublisher);
    }

    @Test
    @DisplayName("Given a non-existent product id in the order items, when execute is called, then should throw ResourceNotFoundException and not interact with the order gateway")
    void givenNonExistentProduct_whenExecute_thenShouldThrowResourceNotFoundException() {
        var input = new CreateOrderInput(
            1L, 2L, DeliveryType.PICKUP, OrderChannel.APP, PaymentType.PIX,
            List.of(new OrderItemInput(999L, 1))
        );

        when(customerGateway.findById(1L)).thenReturn(Optional.of(Instancio.create(CustomerDomain.class)));
        when(unitGateway.findById(2L)).thenReturn(Optional.of(Instancio.create(UnitDomain.class)));
        when(productGateway.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> createOrderUseCase.execute(input))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("999");

        verifyNoInteractions(orderGateway, orderItemGateway, eventPublisher);
    }

    @Test
    @DisplayName("Given a product that has no inventory entry for the order's unit, when execute is called, then should throw ResourceNotFoundException and not interact with the order gateway")
    void givenProductNotInUnitInventory_whenExecute_thenShouldThrowResourceNotFoundException() {
        var product = Instancio.of(ProductDomain.class)
            .set(field(ProductDomain::getId), 10L)
            .create();

        var input = new CreateOrderInput(
            1L, 2L, DeliveryType.PICKUP, OrderChannel.APP, PaymentType.PIX,
            List.of(new OrderItemInput(10L, 1))
        );

        when(customerGateway.findById(1L)).thenReturn(Optional.of(Instancio.create(CustomerDomain.class)));
        when(unitGateway.findById(2L)).thenReturn(Optional.of(Instancio.create(UnitDomain.class)));
        when(productGateway.findById(10L)).thenReturn(Optional.of(product));
        when(inventoryItemGateway.findByUnitIdAndProductId(2L, 10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> createOrderUseCase.execute(input))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("10");

        verifyNoInteractions(orderGateway, orderItemGateway, eventPublisher);
    }

    @Test
    @DisplayName("Given a product whose stock is below the minimum quantity, when execute is called, then should throw DomainException containing the product name and stock details")
    void givenProductWithStockBelowMinimumQuantity_whenExecute_thenShouldThrowDomainExceptionWithStockDetails() {
        var product = Instancio.of(ProductDomain.class)
            .set(field(ProductDomain::getId), 10L)
            .set(field(ProductDomain::getName), "Pamonha")
            .set(field(ProductDomain::getPrice), new BigDecimal("5.00"))
            .create();

        var inventoryItem = Instancio.of(InventoryItemDomain.class)
            .set(field(InventoryItemDomain::getQuantity), 2)
            .set(field(InventoryItemDomain::getMinimumQuantity), 10)
            .create();

        var input = new CreateOrderInput(
            1L, 2L, DeliveryType.PICKUP, OrderChannel.APP, PaymentType.PIX,
            List.of(new OrderItemInput(10L, 1))
        );

        when(customerGateway.findById(1L)).thenReturn(Optional.of(Instancio.create(CustomerDomain.class)));
        when(unitGateway.findById(2L)).thenReturn(Optional.of(Instancio.create(UnitDomain.class)));
        when(productGateway.findById(10L)).thenReturn(Optional.of(product));
        when(inventoryItemGateway.findByUnitIdAndProductId(2L, 10L)).thenReturn(Optional.of(inventoryItem));

        assertThatThrownBy(() -> createOrderUseCase.execute(input))
            .isInstanceOf(DomainException.class)
            .hasMessageContaining("Pamonha")
            .hasMessageContaining("2")
            .hasMessageContaining("10");

        verifyNoInteractions(orderGateway, orderItemGateway, eventPublisher);
    }

    @Test
    @DisplayName("Given a product whose stock is one unit below the minimum quantity, when execute is called, then should throw DomainException")
    void givenProductWithStockOneUnitBelowMinimum_whenExecute_thenShouldThrowDomainException() {
        var product = Instancio.of(ProductDomain.class)
            .set(field(ProductDomain::getId), 10L)
            .set(field(ProductDomain::getName), "Arroz Doce")
            .set(field(ProductDomain::getPrice), new BigDecimal("6.00"))
            .create();

        var inventoryItem = Instancio.of(InventoryItemDomain.class)
            .set(field(InventoryItemDomain::getQuantity), 4)
            .set(field(InventoryItemDomain::getMinimumQuantity), 5)
            .create();

        var input = new CreateOrderInput(
            1L, 2L, DeliveryType.PICKUP, OrderChannel.APP, PaymentType.DEBIT_CARD,
            List.of(new OrderItemInput(10L, 1))
        );

        when(customerGateway.findById(1L)).thenReturn(Optional.of(Instancio.create(CustomerDomain.class)));
        when(unitGateway.findById(2L)).thenReturn(Optional.of(Instancio.create(UnitDomain.class)));
        when(productGateway.findById(10L)).thenReturn(Optional.of(product));
        when(inventoryItemGateway.findByUnitIdAndProductId(2L, 10L)).thenReturn(Optional.of(inventoryItem));

        assertThatThrownBy(() -> createOrderUseCase.execute(input))
            .isInstanceOf(DomainException.class);

        verifyNoInteractions(orderGateway, orderItemGateway, eventPublisher);
    }

    @Test
    @DisplayName("Given multiple items where the second product is not found, when execute is called, then should throw ResourceNotFoundException for the missing product")
    void givenMultipleItemsAndSecondProductNotFound_whenExecute_thenShouldThrowResourceNotFoundException() {
        var productA = Instancio.of(ProductDomain.class)
            .set(field(ProductDomain::getId), 10L)
            .set(field(ProductDomain::getPrice), new BigDecimal("10.00"))
            .create();

        var inventoryItemA = Instancio.of(InventoryItemDomain.class)
            .set(field(InventoryItemDomain::getQuantity), 10)
            .set(field(InventoryItemDomain::getMinimumQuantity), 2)
            .create();

        var input = new CreateOrderInput(
            1L, 2L, DeliveryType.PICKUP, OrderChannel.APP, PaymentType.PIX,
            List.of(new OrderItemInput(10L, 1), new OrderItemInput(999L, 1))
        );

        when(customerGateway.findById(1L)).thenReturn(Optional.of(Instancio.create(CustomerDomain.class)));
        when(unitGateway.findById(2L)).thenReturn(Optional.of(Instancio.create(UnitDomain.class)));
        when(productGateway.findById(10L)).thenReturn(Optional.of(productA));
        when(productGateway.findById(999L)).thenReturn(Optional.empty());
        when(inventoryItemGateway.findByUnitIdAndProductId(2L, 10L)).thenReturn(Optional.of(inventoryItemA));

        assertThatThrownBy(() -> createOrderUseCase.execute(input))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("999");

        verifyNoInteractions(orderGateway, orderItemGateway, eventPublisher);
    }

    @Test
    @DisplayName("Given multiple items where the second product has insufficient stock, when execute is called, then should throw DomainException for that product")
    void givenMultipleItemsAndSecondProductHasInsufficientStock_whenExecute_thenShouldThrowDomainException() {
        var productA = Instancio.of(ProductDomain.class)
            .set(field(ProductDomain::getId), 10L)
            .set(field(ProductDomain::getPrice), new BigDecimal("10.00"))
            .create();

        var productB = Instancio.of(ProductDomain.class)
            .set(field(ProductDomain::getId), 20L)
            .set(field(ProductDomain::getName), "Baião de Dois")
            .set(field(ProductDomain::getPrice), new BigDecimal("18.00"))
            .create();

        var sufficientInventory = Instancio.of(InventoryItemDomain.class)
            .set(field(InventoryItemDomain::getQuantity), 10)
            .set(field(InventoryItemDomain::getMinimumQuantity), 2)
            .create();

        var insufficientInventory = Instancio.of(InventoryItemDomain.class)
            .set(field(InventoryItemDomain::getQuantity), 1)
            .set(field(InventoryItemDomain::getMinimumQuantity), 5)
            .create();

        var input = new CreateOrderInput(
            1L, 2L, DeliveryType.PICKUP, OrderChannel.APP, PaymentType.PIX,
            List.of(new OrderItemInput(10L, 1), new OrderItemInput(20L, 1))
        );

        when(customerGateway.findById(1L)).thenReturn(Optional.of(Instancio.create(CustomerDomain.class)));
        when(unitGateway.findById(2L)).thenReturn(Optional.of(Instancio.create(UnitDomain.class)));
        when(productGateway.findById(10L)).thenReturn(Optional.of(productA));
        when(productGateway.findById(20L)).thenReturn(Optional.of(productB));
        when(inventoryItemGateway.findByUnitIdAndProductId(2L, 10L)).thenReturn(Optional.of(sufficientInventory));
        when(inventoryItemGateway.findByUnitIdAndProductId(2L, 20L)).thenReturn(Optional.of(insufficientInventory));

        assertThatThrownBy(() -> createOrderUseCase.execute(input))
            .isInstanceOf(DomainException.class)
            .hasMessageContaining("Baião de Dois");

        verifyNoInteractions(orderGateway, orderItemGateway, eventPublisher);
    }

    @Test
    @DisplayName("Given multiple items where the second product has no inventory entry for the unit, when execute is called, then should throw ResourceNotFoundException")
    void givenMultipleItemsAndSecondProductNotInUnitInventory_whenExecute_thenShouldThrowResourceNotFoundException() {
        var productA = Instancio.of(ProductDomain.class)
            .set(field(ProductDomain::getId), 10L)
            .set(field(ProductDomain::getPrice), new BigDecimal("10.00"))
            .create();

        var productB = Instancio.of(ProductDomain.class)
            .set(field(ProductDomain::getId), 20L)
            .create();

        var inventoryItemA = Instancio.of(InventoryItemDomain.class)
            .set(field(InventoryItemDomain::getQuantity), 10)
            .set(field(InventoryItemDomain::getMinimumQuantity), 2)
            .create();

        var input = new CreateOrderInput(
            1L, 2L, DeliveryType.DELIVERY, OrderChannel.WEBSITE, PaymentType.VOUCHER,
            List.of(new OrderItemInput(10L, 1), new OrderItemInput(20L, 1))
        );

        when(customerGateway.findById(1L)).thenReturn(Optional.of(Instancio.create(CustomerDomain.class)));
        when(unitGateway.findById(2L)).thenReturn(Optional.of(Instancio.create(UnitDomain.class)));
        when(productGateway.findById(10L)).thenReturn(Optional.of(productA));
        when(productGateway.findById(20L)).thenReturn(Optional.of(productB));
        when(inventoryItemGateway.findByUnitIdAndProductId(2L, 10L)).thenReturn(Optional.of(inventoryItemA));
        when(inventoryItemGateway.findByUnitIdAndProductId(2L, 20L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> createOrderUseCase.execute(input))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("20");

        verifyNoInteractions(orderGateway, orderItemGateway, eventPublisher);
    }
}
