package com.jydev.noticeboard;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class CommonController {

    @GetMapping("/")
    public void mainPage(HttpServletResponse response) throws IOException {
        response.sendRedirect("/post/page");
    }
}
