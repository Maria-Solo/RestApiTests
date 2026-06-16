package com.mary.fixtures;

import com.mary.models.Task;

public class TaskFixture {
    public Task buildTask(String title, String description, String status, Long clientId, Long providerId){
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        task.setClientId(clientId);
        task.setProviderId(providerId);
        return task;
    }

    public Task validTask(){
        Task task = new Task();
        task.setTitle("Test task");
        task.setDescription("This is a task description");
        task.setStatus("NEW");
        task.setClientId(2L);
        task.setProviderId(2L);
        return task;
    }

    public Task invalidTask(){
        Task task = new Task();
        task.setTitle("");
        task.setDescription("");
        task.setStatus("");
        task.setClientId(111L);
        task.setProviderId(112L);
        return task;
    }
}
