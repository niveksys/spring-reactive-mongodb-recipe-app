package com.niveksys.recipeapp.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ControllerExceptionHandler {

    // @ResponseStatus(HttpStatus.BAD_REQUEST)
    // @ExceptionHandler(NumberFormatException.class)
    // public ModelAndView handleNumberFormatException(Exception ex) {
    // log.error("Handling Number Format Exception: " + ex.getMessage());
    // ModelAndView mav = new ModelAndView();
    // mav.addObject("exception", ex);
    // mav.setViewName("400error");
    // return mav;
    // }

}
