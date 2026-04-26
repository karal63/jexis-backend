package com.jexis.jexis_backend.project;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jexis.jexis_backend.project.dto.CreateProjectDto;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @PostMapping("/create")
    public String create(@RequestBody CreateProjectDto body) {
        System.out.println(body.getName());
        return "Create project";
    }
}
