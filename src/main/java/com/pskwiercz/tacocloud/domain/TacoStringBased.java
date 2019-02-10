package com.pskwiercz.tacocloud.domain;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class TacoStringBased {

    private Long id;

    @NotNull
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<String> ingredients;
}

