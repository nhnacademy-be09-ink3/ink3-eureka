package com.nhnacademy.daily.service;

import com.nhnacademy.daily.exception.projectException.ProjectAlreadyExistException;
import com.nhnacademy.daily.model.project.Project;
import com.nhnacademy.daily.model.project.ProjectRequest;
import com.nhnacademy.daily.model.project.ProjectType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class ProjectService {
    private final RedisTemplate<String, Object> redisTemplate;
    private String HASH_NAME = "Project:";
    public ProjectService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;

        Project project1 = new Project("a", LocalDate.now(), ProjectType.PUBLIC);
        Project project2 = new Project("b", LocalDate.now(), ProjectType.PRIVATE);
        Project project3 = new Project("c", LocalDate.now(), ProjectType.PUBLIC);
        redisTemplate.opsForHash().put(HASH_NAME+project1.getCode(), "project", project1);
        redisTemplate.opsForHash().put(HASH_NAME+project2.getCode(), "project", project2);
        redisTemplate.opsForHash().put(HASH_NAME+project3.getCode(), "project", project3);
    }

    public void registerProject(ProjectRequest projectRequest){
        if(existProject(projectRequest.getCode())){
            throw new ProjectAlreadyExistException();
        }
        Project project = new Project(projectRequest.getCode(), LocalDate.now(), projectRequest.getType());
        redisTemplate.opsForHash().put(HASH_NAME + project.getCode(), "project", project);
    }

    public Project getProject(String code){
        if(!existProject(code)){
            return null;
        }
        Object o = redisTemplate.opsForHash().get(HASH_NAME + code, "project");
        return (Project) o;
    }

    public List<Project> getProjectList(){
        List<Project> returnList = new ArrayList<>();
        Set<String> keys = redisTemplate.keys("Project:*");
        if(Objects.isNull(keys)){
            return null;
        }

        for(String key : keys){
            Object o = redisTemplate.opsForHash().get(key, "project");
            if(o instanceof Project){
                returnList.add((Project) o);
            }
        }

        return returnList;
    }

    public boolean existProject(String projectCode){
        if (redisTemplate.opsForHash().hasKey(HASH_NAME + projectCode, "project")) {
            return true;
        }
        return false;
    }
}
