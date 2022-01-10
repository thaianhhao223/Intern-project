package com.example.mock_project.advice;

import com.example.mock_project.entity.ErrorMessage;
import com.example.mock_project.storage.StorageException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.BindException;

//@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * Tất cả các Exception không được khai báo sẽ được xử lý tại đây
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleAllException(Exception ex, WebRequest request) {
        // quá trình kiểm soat lỗi diễn ra ở đây
        return new ErrorMessage(500, ex.getMessage());
    }

    /**
     * IndexOutOfBoundsException sẽ được xử lý riêng tại đây
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage NotFoundException(Exception ex, WebRequest request) {
        return new ErrorMessage(400, ex.getMessage());
    }

    /**
     * Dùng để xử lý các exception liên quan đến việc lưu dữ liệu
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(StorageException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage NotStorageException(Exception ex, WebRequest request) {
        return new ErrorMessage(400, ex.getMessage());
    }

    @ExceptionHandler(ClassCastException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage NotCastClassException(Exception ex, WebRequest request) {
        return new ErrorMessage(400, ex.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage NullPointerException(Exception ex, WebRequest request) {
        return new ErrorMessage(400, ex.getMessage());
    }

    /**
     * Xử lý exception liên quan đến việc valid dữ liệu
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage BindExceptionHandler(Exception ex, WebRequest request) {
        return new ErrorMessage(400, ex.getMessage());
    }

    @ExceptionHandler({MalformedJwtException.class,ExpiredJwtException.class,
            UnsupportedJwtException.class,IllegalArgumentException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage JWTExceptionHandler(Exception ex, WebRequest request) {
        return new ErrorMessage(400, ex.getMessage());
    }
}
