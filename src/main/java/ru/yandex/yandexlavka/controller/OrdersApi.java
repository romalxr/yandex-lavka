package ru.yandex.yandexlavka.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.yandex.yandexlavka.dto.CompleteOrderRequestDto;
import ru.yandex.yandexlavka.dto.CreateOrderRequest;
import ru.yandex.yandexlavka.dto.OrderAssignResponse;
import ru.yandex.yandexlavka.dto.OrderDto;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "order-controller", description = "the order-controller API")
public interface OrdersApi {

    @Operation(
            operationId = "completeOrder",
            tags = { "order-controller" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "ok", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OrderDto.class)))
                    }),
                    @ApiResponse(responseCode = "400", description = "bad request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/orders/complete",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    List<OrderDto> completeOrder(
            @Parameter(name = "CompleteOrderRequestDto", required = true)
        @RequestBody CompleteOrderRequestDto completeOrderRequestDto
    );

    @Operation(
            operationId = "createOrder",
            tags = { "order-controller" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "ok", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OrderDto.class)))
                    }),
                    @ApiResponse(responseCode = "400", description = "bad request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/orders",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    List<OrderDto> createOrder(
            @Parameter(name = "CreateOrderRequest", required = true)
        @RequestBody CreateOrderRequest createOrderRequest
    );

    @Operation(
            operationId = "getOrder",
            tags = { "order-controller" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "ok", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "bad request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/orders/{order_id}",
            produces = { "application/json" }
    )
    OrderDto getOrder(
            @Parameter(name = "order_id", description = "Order identifier", required = true, in = ParameterIn.PATH)
        @PathVariable("order_id") Long orderId
    );

    @Operation(
            operationId = "getOrders",
            tags = { "order-controller" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "ok", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OrderDto.class)))
                    }),
                    @ApiResponse(responseCode = "400", description = "bad request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/orders",
            produces = { "application/json" }
    )
    List<OrderDto> getOrders(
          @Parameter(name = "limit", description = "Максимальное количество заказов в выдаче. Если параметр не передан, то значение по умолчанию равно 1.", in = ParameterIn.QUERY)
        @RequestParam(value = "limit", required = false, defaultValue = "1") Integer limit,
          @Parameter(name = "offset", description = "Количество заказов, которое нужно пропустить для отображения текущей страницы. Если параметр не передан, то значение по умолчанию равно 0.", in = ParameterIn.QUERY)
        @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset
    );

    @Operation(
            operationId = "ordersAssign",
            summary = "Распределение заказов по курьерам",
            description = "Для распределения заказов между курьерами учитываются следующие параметры: <ul><li>вес заказа</li><li>регион доставки</li><li>стоимость доставки</li></ul>",
            tags = { "order-controller" },
            responses = {
                    @ApiResponse(responseCode = "201", description = "ok", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OrderAssignResponse.class)))
                    }),
                    @ApiResponse(responseCode = "400", description = "bad request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/orders/assign",
            produces = { "application/json" }
    )
    List<OrderAssignResponse> ordersAssign(
            @Parameter(name = "date", description = "Дата распределения заказов. Если не указана, то используется текущий день", in = ParameterIn.QUERY)
        @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    );
}
