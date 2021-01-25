package com.dailycode.user.management.controller;

import com.dailycode.user.management.util.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class DashboardController {

    @GetMapping ("/dashboard")
    public String dashboard() {
        log.info("dashboard is loading with ", ViewNames.getHtmlFile(ViewNames.DASHBOARD));
        return ViewNames.DASHBOARD;
    }
}
