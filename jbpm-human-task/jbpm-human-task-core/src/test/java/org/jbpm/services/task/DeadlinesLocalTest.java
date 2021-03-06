/*
 * Copyright 2012 JBoss by Red Hat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jbpm.services.task;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.jbpm.services.task.deadlines.notifications.impl.MockNotificationListener;
import org.jbpm.services.task.impl.TaskDeadlinesServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.kie.internal.task.api.InternalTaskService;

import bitronix.tm.resource.jdbc.PoolingDataSource;

/**
 *
 *
 */

public class DeadlinesLocalTest extends DeadlinesBaseTest {

	private PoolingDataSource pds;
	private EntityManagerFactory emf;
	
	@Before
	public void setup() {
		this.notificationListener = new MockNotificationListener();
		pds = setupPoolingDataSource();
		emf = Persistence.createEntityManagerFactory( "org.jbpm.services.task" );
		TaskDeadlinesServiceImpl.setNotificationListener(this.notificationListener);
		this.taskService = (InternalTaskService) HumanTaskServiceFactory.newTaskServiceConfigurator()
												.entityManagerFactory(emf)
												.getTaskService();
	}
	
	@After
	public void clean() {
		TaskDeadlinesServiceImpl.reset();
		super.tearDown();
		if (emf != null) {
			emf.close();
		}
		if (pds != null) {
			pds.close();
		}
	}

}
