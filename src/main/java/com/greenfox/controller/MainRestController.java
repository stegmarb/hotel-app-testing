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
    System.out.println(77777777);
    send.send();
    System.out.println(88888888);
    send.consume();
    System.out.println(99999999);
    return new Test("that shit!");
  }
}
