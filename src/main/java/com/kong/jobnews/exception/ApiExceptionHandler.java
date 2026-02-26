package com.kong.jobnews.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice   // 전역 예외 처리 클래스 : 모든 @RestController에서 발생하는 예외를 가로챔
public class ApiExceptionHandler {

    // @Valid 검증 실패 처리 : 400
    @ResponseStatus(HttpStatus.BAD_REQUEST) // HTTP 상태코드를 400으로 설정, ResponseEntity 없이도 상태 코드 지정 가능
    @ExceptionHandler(MethodArgumentNotValidException.class) // @Valid 검증 실패 시 발생하는 예외
    public Map<String, Object> handleValidation(MethodArgumentNotValidException e){
        Map<String, String> fieldErrors = e.getBindingResult() // 바인딩 결과 정보
                .getFieldErrors() // 필드 단위 에러 리스트를 가져옴
                .stream()   // 리스트를 stream으로 변환
                .collect(Collectors.toMap(  // Map으로 변환
                        fe -> fe.getField(),    // 필드 이름(title, url)
                        fe -> fe.getDefaultMessage(),   // @NotBlank(message="...") 메시지
                        (a, b) -> a // 동일 필드에 여러 에러가 있을 경우 첫 번째 것만 유지 (충돌 방지)
                ));
        return Map.of(
                "message", "요청값이 올바르지 않습니다.",
                "errors", fieldErrors
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)    // 409 상태코드 반환
    @ExceptionHandler(IllegalArgumentException.class)   // 직접 던져놓은 예외(중복 등)
    public Map<String, Object> handleIllegalArgumentException(IllegalArgumentException e){
        return Map.of(
                "message", e.getMessage()
        );
    }
}
