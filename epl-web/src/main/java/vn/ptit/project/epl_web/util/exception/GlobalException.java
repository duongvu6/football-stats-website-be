package vn.ptit.project.epl_web.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.ptit.project.epl_web.dto.response.RestResponse;
import vn.ptit.project.epl_web.util.error.StorageException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalException {
    //handle all exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse<Object>> handleAllException(Exception exception) {
        RestResponse<Object> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setError("Internal Server Error");
        response.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(value = {
            InvalidRequestException.class,
            UsernameNotFoundException.class,
    } )
    public ResponseEntity<RestResponse<Object>> handleInvalidRequestException(InvalidRequestException exception) {
        RestResponse<Object> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage(exception.getMessage());
        response.setError("Exception occurs");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    // Bắt exception validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Object>> validationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();

        RestResponse<Object> res = new RestResponse<Object>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError(ex.getBody().getDetail());

        // Viết bằng cú pháp lamda
        // List<String> errors = fieldErrors.stream().map(f ->
        // f.getDefaultMessage()).collect(Collectors.toList());
        // res.setMessage(errors.size() > 1 ? errors : errors.get(0));

        List<String> errors = new ArrayList<>();
        for (FieldError error : fieldErrors) {
            errors.add(error.getDefaultMessage());
        }
        res.setMessage(errors.size() > 1 ? errors : errors.get(0));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);

    }
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<RestResponse<Object>> handleAuthorizationDeniedException(AuthorizationDeniedException exception) {
        RestResponse<Object> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.FORBIDDEN.value());
        response.setMessage(exception.getMessage());
        response.setError("Authorization Denied");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<RestResponse<Object>> handleInternalAuthenticationServiceException(InternalAuthenticationServiceException exception) {
        RestResponse<Object> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        response.setMessage("Bad credentials");
        response.setError(exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(value = {

            StorageException.class })
    public ResponseEntity<RestResponse<Object>> handleFileUploadException(Exception ex) {

        RestResponse<Object> res = new RestResponse<Object>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError(ex.getMessage());
        // res.setMessage("IdInvalidException");
        res.setMessage("Exception upload file...");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }
}
