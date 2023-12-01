package com.example.solace.DTO;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CartItemDTO {
    private Integer itemId;

    private int count;
}
