package com.kong.jobnews.dto;

import jakarta.validation.constraints.NotBlank;

public record PostCreateRequest(
        @NotBlank String title,
        @NotBlank String url
) {  /* @NotBlank : 유효성 검증(Validation)
                    null, 빈 문자열(""), 공백이 있어서는 안된다.
                    빈 값을 post할 경우 서버에서 자동으로 400 error
     */
}
