package com.server.gym_serverapplication.exception;
import com.server.gym_serverapplication.modelView.ResponseObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.security.auth.login.AccountNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseObject> handleAllExceptions(Exception ex, WebRequest request) {
        return ResponseEntity.internalServerError().body(
                ResponseObject.builder()
                        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message(ExceptionType.UNKNOWN_EXCEPTION.name())
                        .build()
        );
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ResponseObject> handleNullPointerException(NullPointerException ex) {
       return ResponseEntity.internalServerError().body(
               ResponseObject.builder()
                       .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                       .message(ex.getMessage())
                       .build()
       );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseObject> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.internalServerError().body(
                ResponseObject.builder()
                       .message(ex.getMessage())
                       .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                       .build()
        );
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseObject> handleAccountNotFoundException(AccountNotFoundException ex) {
        return ResponseEntity.internalServerError().body(
                ResponseObject.builder()
                        .message("Login Failed!")
                        .httpStatus(HttpStatus.UNAUTHORIZED)
                        .build()
        );
    }


    @ExceptionHandler(IOException.class)
    public ResponseEntity<ResponseObject> handleIOException(IOException ex) {
        return ResponseEntity.internalServerError().body(
                ResponseObject.builder()
                        .message(ex.getMessage())
                        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .build()
        );
    }
    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ResponseObject> handleDuplicateException(DuplicateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ResponseObject(ex.getMessage(), HttpStatus.CONFLICT, null));
    }



    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseObject> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ResponseObject.builder()
                        .message(ex.getMessage())
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .build()
        );
    }

    @ExceptionHandler(SocketException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseObject> handleSocketException(SocketException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
               .body(new ResponseObject(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null));
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ResponseObject> handleTokenException(TokenException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
               .body(new ResponseObject(ex.getMessage(), HttpStatus.UNAUTHORIZED, null));
    }

    @ExceptionHandler(ProductNullException.class)
    public ResponseEntity<ResponseObject> handleProductNullException(ProductNullException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
               .body(new ResponseObject(ex.getMessage(), HttpStatus.NOT_FOUND, null));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseObject> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
               .body(new ResponseObject(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null));
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("Unauthenticated");
        response.flushBuffer();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(HttpServletRequest request,
                                            HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.getWriter().write("Forbidden");
        response.flushBuffer();
    }




}
