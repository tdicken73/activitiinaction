package org.bpmnwithactiviti.chapter3.spring;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:chapter3/spring-nodeployment-application-context.xml")
public class SpringWithDeploymentTest {

	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private TaskService taskService;

	@Autowired
	@Rule
	public ActivitiRule activitiSpringRule;

	@Test
	@Deployment(resources = { "chapter3/bookorder.spring.bpmn20.xml" })
	public void simpleProcessTest() {
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put("isbn", Long.valueOf("123456"));
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
				"bookorder", variableMap);
		Task task = taskService.createTaskQuery().singleResult();
		assertEquals("Complete order", task.getName());
		assertEquals(123456l, runtimeService.getVariable(
				processInstance.getId(), "isbn"));
		//org.activiti.engine.repository.Deployment deployment = repositoryService.createDeploymentQuery().singleResult();
		//repositoryService.deleteDeploymentCascade(deployment.getId());
	}
}
