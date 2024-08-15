package com.wrtr.wrtr.core.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WrtrErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleErrors(HttpServletRequest request, Model model){
        Object code = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus status = HttpStatus.valueOf(Integer.parseInt(code.toString()));

        model.addAttribute("code", Integer.toString(status.value()));
        model.addAttribute("phrase", status.getReasonPhrase());
        return "error";

    }


}
