package com.shopping.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
    code = HttpStatus.NOT_FOUND,
    reason = "Buyer is not found."
)
public class BuyerNotFoundException extends RuntimeException
{
}
