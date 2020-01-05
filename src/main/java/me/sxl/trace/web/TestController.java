package me.sxl.trace.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @GetMapping("/book/{name}")
    public String test(@PathVariable String name) {
        log.debug("Test traceId and print params: {}", name);
        log.info("Test traceId and print params: {}", name);
        log.warn("Test traceId and print params: {}", name);
        log.error("Test traceId and print params: {}", name);
        return "your book name is " + name;
    }

}
