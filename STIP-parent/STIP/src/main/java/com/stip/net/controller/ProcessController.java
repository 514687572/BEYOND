package com.stip.net.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Scope("request")
@RequestMapping("/process")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class, Exception.class })
public class ProcessController extends AbstractController {
	@Autowired
	RepositoryService repositoryService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	TaskService taskService;
	@Autowired
	HistoryService historyService;

	@RequestMapping(value = "/tp.do", method = { RequestMethod.GET, RequestMethod.HEAD })
	public @ResponseBody Map<String, Object> test(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 开启流程，myprocess是流程的ID
        runtimeService.startProcessInstanceByKey("myProcess");
        // 查询历史表中的Task
        List<Task> task = taskService.createTaskQuery().list();
        Task task1 = task.get(0);
        taskService.complete(task1.getId());
        task1 = taskService.createTaskQuery().executionId(task1.getExecutionId()).singleResult();
		
		return jsonResult;
	}
	
	@RequestMapping(value = "/tpr.do", method = { RequestMethod.GET, RequestMethod.HEAD })
	public void monthtest() {
        // 启动流程实例
        String procId = runtimeService.startProcessInstanceByKey("financialReport").getId();
        // 获得第一个任务
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("sales").list();
        for (Task task : tasks) {
            // 认领任务这里由qizheng认领
            taskService.claim(task.getId(), "qizheng");
        }
        // 查看fozzie现在是否能够获取到该任务
        tasks = taskService.createTaskQuery().taskAssignee("qizheng").list();
        for (Task task : tasks) {
            // 执行(完成)任务
            taskService.complete(task.getId());
        }
        // 现在fozzie的可执行任务数就为0了
        System.out.println(taskService.createTaskQuery().taskAssignee("qizheng").count());
        // 获得第二个任务
        tasks = taskService.createTaskQuery().taskCandidateGroup("management").list();
        for (Task task : tasks) {
            System.out.println("Following task is available for accountancy group:" + task.getName());
            // 认领任务这里由liu认领
            taskService.claim(task.getId(), "liu");
        }
        // 完成第二个任务结束流程
        for (Task task : tasks) {
            taskService.complete(task.getId());
        }
        // 核实流程是否结束,输出流程结束时间
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(procId).singleResult();
        System.out.println("Process instance end time: " + historicProcessInstance.getEndTime());
}
	
}
