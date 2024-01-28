package com.github.idelvan.ifood2.cadastro.infra;


import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;

//@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException>{

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        // TODO Auto-generated method stub
        return Response.status(Status.BAD_REQUEST).entity(ConstraintViolationResponse.of(exception)).build();
    }
    
}
