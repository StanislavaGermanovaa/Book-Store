package bg.softuni.bookstore.web;

import bg.softuni.bookstore.application.error.*;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleObjectNotFoundException(ObjectNotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("id", ex.getId());

        return "error/object-not-found";
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotFoundException(UserNotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error/user-not-found";
    }

    @ExceptionHandler(ProfileUpdateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleProfileUpdateException(ProfileUpdateException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error/bad-request";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralException(Exception ex, Model model) {
        model.addAttribute("message", "An unexpected error occurred: " + ex.getMessage());
        return "error/general-error";
    }

    @ExceptionHandler(RoleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleRoleNotFoundException(RoleNotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error/user-not-found";
    }

    @ExceptionHandler(OutOfStockException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleOutOfStockException(OutOfStockException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error/out-of-stock";
    }

}
