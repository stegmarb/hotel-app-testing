package com.greenfox.controller;

import com.greenfox.model.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainRestController {

  @RequestMapping("/")
  public Test test() {
    return new Test("that shit!");
  }
}
