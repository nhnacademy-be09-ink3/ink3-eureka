package com.nhnacademy.daily.controller;

import com.nhnacademy.daily.exception.projectException.ProjectAlreadyExistException;
import com.nhnacademy.daily.exception.projectException.ProjectNotFoundException;
import com.nhnacademy.daily.model.project.Project;
import com.nhnacademy.daily.model.project.ProjectRequest;
import com.nhnacademy.daily.service.ProjectService;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;

@RestController
public class ProjectController {

    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }


    /**
     * project 리스트 조회
     * @param pageable
     * @return : project 리스트
     */
    @GetMapping("/projects")
    public List<Project> getProjects(Pageable pageable){
        System.out.println(pageable.getPageNumber());
        System.out.println(pageable.getPageSize());
        return service.getProjectList();
    }


    /**
     * Project 등록
     * @param projectRequest
     */
    @PostMapping("/projects")
    public void registerMember(@RequestBody ProjectRequest projectRequest){
        if(service.existProject(projectRequest.getCode())){
            throw new ProjectAlreadyExistException();
        }else{
            service.registerProject(projectRequest);
        }
    }


    /**
     * project 조회
     * @param code
     * @return : 조회된 project
     */
    @GetMapping("/projects/{code}")
    public Project getProject(@PathVariable String code){
        Project project = service.getProject(code);
        if(Objects.isNull(project)){
            throw new ProjectNotFoundException();
        }
        return project;
    }
}
