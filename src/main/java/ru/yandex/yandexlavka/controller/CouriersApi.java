package ru.yandex.yandexlavka.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.yandex.yandexlavka.dto.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "courier-controller", description = "the courier-controller API")
public interface CouriersApi {

    @Operation(
        operationId = "couriersAssignments",
        summary = "Список распределенных заказов",
        tags = { "courier-controller" },
        responses = {
            @ApiResponse(responseCode = "200", description = "ok", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OrderAssignResponse.class)))
            }),
            @ApiResponse(responseCode = "400", description = "bad request", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))
            })
        }
    )
    @GetMapping("/couriers/assignments")
    List<OrderAssignResponse> couriersAssignments(
            @Parameter(name = "date", description = "Дата распределения заказов. Если не указана, то используется текущий день", in = ParameterIn.QUERY)
        @RequestParam(value = "date", required = false) LocalDate date,
            @Parameter(name = "courier_id", description = "Идентификатор курьера для получения списка распредленных заказов. Если не указан, возвращаются данные по всем курьерам.", in = ParameterIn.QUERY)
        @RequestParam(value = "courier_id", required = false) Long courierId
    );

    @Operation(
        operationId = "createCourier",
        tags = { "courier-controller" },
        responses = {
            @ApiResponse(responseCode = "200", description = "ok", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CourierDto.class)))
            }),
            @ApiResponse(responseCode = "400", description = "bad request", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))
            })
        }
    )
    @PostMapping("/couriers")
    CreateCouriersResponse createCourier(
            @Parameter(name = "CreateCourierRequest", required = true)
        @RequestBody CreateCourierRequest createCourierRequest
    );

    @Operation(
        operationId = "getCourierById",
        tags = { "courier-controller" },
        responses = {
            @ApiResponse(responseCode = "200", description = "ok", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OrderDto.class)))
            }),
            @ApiResponse(responseCode = "400", description = "bad request", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))
            }),
            @ApiResponse(responseCode = "404", description = "not found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))
            })
        }
    )
    @GetMapping(value = "/couriers/{courier_id}", produces = {"application/json"})
    CourierDto getCourierById(
            @Parameter(name = "courier_id", description = "Courier identifier", required = true, in = ParameterIn.PATH)
        @PathVariable("courier_id") Long courierId
    );

    @Operation(
        operationId = "getCourierMetaInfo",
        tags = { "courier-controller" },
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = GetCourierMetaInfoResponse.class))
            })
        }
    )
    @GetMapping("/couriers/meta-info/{courier_id}")
    GetCourierMetaInfoResponse getCourierMetaInfo(
            @Parameter(name = "courier_id", description = "Courier identifier", required = true, in = ParameterIn.PATH)
        @PathVariable("courier_id") Long courierId,
            @Parameter(name = "startDate", description = "Rating calculation start date", required = true, in = ParameterIn.QUERY)
        @RequestParam(value = "startDate") LocalDate startDate,
            @Parameter(name = "endDate", description = "Rating calculation end date", required = true, in = ParameterIn.QUERY)
        @RequestParam(value = "endDate") LocalDate endDate
    );

    @Operation(
        operationId = "getCouriers",
        tags = { "courier-controller" },
        responses = {
            @ApiResponse(responseCode = "200", description = "ok", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OrderDto.class)))
            }),
            @ApiResponse(responseCode = "400", description = "bad request", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))
            })
        }
    )
    @GetMapping("/couriers")
    GetCouriersResponse getCouriers(
            @Parameter(name = "limit", description = "Максимальное количество курьеров в выдаче. Если параметр не передан, то значение по умолчанию равно 1.", in = ParameterIn.QUERY)
        @RequestParam(value = "limit", required = false, defaultValue = "1") Integer limit,
            @Parameter(name = "offset", description = "Количество курьеров, которое нужно пропустить для отображения текущей страницы. Если параметр не передан, то значение по умолчанию равно 0.", in = ParameterIn.QUERY)
        @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset
    );

}
