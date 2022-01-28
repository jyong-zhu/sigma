package com.zone.gateway.handler;

import com.zone.commons.entity.ResponseData;
import java.util.Collections;
import java.util.List;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/17 10:55 下午
 * @Description: 对网关层的异常进行处理
 */
@Slf4j
@Data
@Component
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {

  private List<HttpMessageReader<?>> messageReaders = Collections.emptyList();
  private List<HttpMessageWriter<?>> messageWriters = Collections.emptyList();
  private List<ViewResolver> viewResolvers = Collections.emptyList();
  private ThreadLocal<ResponseData> threadLocal = new ThreadLocal<>();

  @Override
  public Mono<Void> handle(ServerWebExchange exchange, Throwable throwable) {
    log.error("网关异常处理：{}", throwable.getMessage());

    ResponseData result = ResponseData.error(throwable.getMessage());
    threadLocal.set(result);
    ServerRequest newRequest = ServerRequest.create(exchange, this.messageReaders);

    return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse)
        .route(newRequest)
        .switchIfEmpty(Mono.error(throwable))
        .flatMap((handler) -> handler.handle(newRequest))
        .flatMap((response) -> write(exchange, response));
  }

  /**
   * 统一返回指定异常信息(指定json格式输出)
   */
  protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
    return ServerResponse.status(HttpStatus.BAD_REQUEST)
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(threadLocal.get()));
  }

  /**
   * 参考DefaultErrorWebExceptionHandler
   */
  private Mono<? extends Void> write(ServerWebExchange exchange, ServerResponse response) {
    exchange.getResponse().getHeaders().setContentType(response.headers().getContentType());
    return response.writeTo(exchange, new ResponseContext());
  }

  /**
   * 参考DefaultErrorWebExceptionHandler
   */
  private class ResponseContext implements ServerResponse.Context {

    private ResponseContext() {
    }

    @Override
    public List<HttpMessageWriter<?>> messageWriters() {
      return GatewayExceptionHandler.this.messageWriters;
    }

    @Override
    public List<ViewResolver> viewResolvers() {
      return GatewayExceptionHandler.this.viewResolvers;
    }
  }
}
