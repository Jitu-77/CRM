package com.Jitu.Employees.advices;

import com.Jitu.Employees.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice // will consider all the controllers
public class GlobalExceptionHandler {
//    Basic
//    @ExceptionHandler(NoSuchElementException.class)
//    public ResponseEntity<String> handleResourceNotFound(NoSuchElementException exception){
//         return new ResponseEntity<>("Resource Not found", HttpStatus.NOT_FOUND);
//    }

    //Before custom exception -- ResourceNotFoundException
//    @ExceptionHandler(NoSuchElementException.class)
//    public ResponseEntity<ApiError> handleResourceNotFound(NoSuchElementException exception){
//        ApiError apiError = ApiError.builder().status(HttpStatus.NOT_FOUND).message("Resource Not found").build();
//        ApiError apiError = ApiError.builder().status(HttpStatus.NOT_FOUND).message(exception.getMessage()).build();
//        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
//    }

    // custom exception using ResourceNotFoundException class
    // Before API Response Type
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException exception){
//        ApiError apiError = ApiError.builder().status(HttpStatus.NOT_FOUND).message(exception.getMessage()).build();
//        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
//    }


    //custom exception using Exception class
    // give a un-readable format of all the error
    //  Before API Response Type
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiError> handleInternalServerError(Exception exception){
//        ApiError apiError = ApiError.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(exception.getMessage()).build();
//        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
//    }


//    //custom exception using MethodArgumentNotValidException class
//    // give a readable format of all the error
//    // meaning errors
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ApiError> handleInputValidationErrors(MethodArgumentNotValidException exception){
//
//        List<String> errors = exception.
//                getBindingResult()
//                .getAllErrors()
//                .stream()
//                .map((error)->error.getDefaultMessage())
//                .collect(Collectors.toList());
//
//
//        ApiError apiError = ApiError.
//                builder()
//                .status(HttpStatus.BAD_REQUEST)
//                //                  .message(errors.toString()) // we are getting errors as a string
//                //or
//                .message("Input Validation failed")
//                //new add for Sub Errors
//                .subErrors(errors)
//                .build();
//        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
//    }

    // custom exception using ResourceNotFoundException class
    //Response Type is API Response
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFoundException exception){
        ApiError apiError = ApiError.builder().status(HttpStatus.NOT_FOUND).message(exception.getMessage()).build();
        return buildErrorResponseEntity(apiError);
    }



    //custom exception using Exception class
    // give a un-readable format of all the error
    //    Response Type is API Response
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerError(Exception exception){
        ApiError apiError = ApiError.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(exception.getMessage()).build();
        return  buildErrorResponseEntity(apiError);
    }

    //custom exception using MethodArgumentNotValidException class
    // give a readable format of all the error
    // meaning errors
//    Response Type is API Response
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleInputValidationErrors(MethodArgumentNotValidException exception){

            List<String> errors = exception.
                                getBindingResult()
                                .getAllErrors()
                                .stream()
                                .map((error)->error.getDefaultMessage())
                                .collect(Collectors.toList());


        ApiError apiError = ApiError.
                            builder()
                            .status(HttpStatus.BAD_REQUEST)
        //                  .message(errors.toString()) // we are getting errors as a string
                //or
                .message("Input Validation failed")
                //new add for Sub Errors
                .subErrors(errors)
                            .build();
        return buildErrorResponseEntity(apiError);
    }


    private ResponseEntity<ApiResponse<?>> buildErrorResponseEntity(ApiError apiError){
        return new ResponseEntity<>( new ApiResponse(apiError),apiError.getStatus());
    }
}
