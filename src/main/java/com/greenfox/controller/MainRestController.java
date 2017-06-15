package com.greenfox.controller;

import com.greenfox.model.Send;
import com.greenfox.model.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainRestController {
  private Send send = new Send();

  @RequestMapping("/")
  public Test test() throws Exception {
    send.send();
    send.consume();
    return new Test("that shit!");
  }
}
