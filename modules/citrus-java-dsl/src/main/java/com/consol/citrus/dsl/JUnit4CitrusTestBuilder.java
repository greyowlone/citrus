/*
 * Copyright 2006-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.consol.citrus.dsl;

import com.consol.citrus.*;
import com.consol.citrus.actions.*;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.container.*;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.definition.*;
import com.consol.citrus.dsl.util.PositionHandle;
import com.consol.citrus.endpoint.Endpoint;
import com.consol.citrus.junit.AbstractJUnit4CitrusTest;
import com.consol.citrus.junit.CitrusJUnit4Runner;
import com.consol.citrus.server.Server;
import com.consol.citrus.ws.client.WebServiceClient;
import com.consol.citrus.ws.server.WebServiceServer;
import org.springframework.core.io.Resource;
import org.springframework.util.ReflectionUtils;

import javax.jms.ConnectionFactory;
import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * JUnit Citrus test provides Java DSL access to builder pattern methods in
 * CitrusTestBuilder by simple method delegation.
 *
 * @author Christoph Deppisch
 * @since 1.3.1
 * @deprecated since 2.3 in favor of {@link com.consol.citrus.dsl.junit.JUnit4CitrusTestDesigner}
 */
public class JUnit4CitrusTestBuilder extends AbstractJUnit4CitrusTest implements TestBuilder {

    /** Test builder delegate */
    private CitrusTestBuilder testBuilder;

    @Override
    protected void run(CitrusJUnit4Runner.CitrusFrameworkMethod frameworkMethod) {
        if (frameworkMethod.getMethod().getAnnotation(CitrusTest.class) != null || isConfigure(frameworkMethod.getMethod())) {
            if (citrus == null) {
                citrus = Citrus.newInstance(applicationContext);
            }

            TestContext ctx = prepareTestContext(citrus.createTestContext());

            testBuilder = new CitrusTestBuilder(applicationContext);
            testBuilder.name(frameworkMethod.getTestName());
            testBuilder.packageName(frameworkMethod.getPackageName());

            if (isConfigure(frameworkMethod.getMethod())) {
                configure();
            } else {
                ReflectionUtils.invokeMethod(frameworkMethod.getMethod(), this);
            }

            citrus.run(testBuilder.build(), ctx);
        } else {
            super.run(frameworkMethod);
        }
    }

    @Override
    protected void executeTest() {
        run(new CitrusJUnit4Runner.CitrusFrameworkMethod(ReflectionUtils.findMethod(this.getClass(), "configure"),
                this.getClass().getSimpleName(), this.getClass().getPackage().getName()));
    }

    /**
     * Main entrance method for builder pattern usage. Subclasses may override
     * this method and call Java DSL builder methods for adding test actions and
     * basic test case properties.
     */
    protected void configure() {
    }

    /**
     * Checks if the given method is this designer's configure method.
     * @param method
     * @return
     */
    private boolean isConfigure(Method method) {
        return method.getDeclaringClass().equals(this.getClass()) && method.getName().equals("configure");
    }

    @Override
    public TestCase build() {
        return testBuilder.build();
    }

    @Override
    public TestCase getTestCase() {
        return testBuilder.build();
    }

    @Override
    public void name(String name) {
        testBuilder.name(name);
    }

    @Override
    public void description(String description) {
        testBuilder.description(description);
    }

    @Override
    public void author(String author) {
        testBuilder.author(author);
    }

    @Override
    public void packageName(String packageName) {
        testBuilder.packageName(packageName);
    }

    @Override
    public void status(TestCaseMetaInfo.Status status) {
        testBuilder.status(status);
    }

    @Override
    public void creationDate(Date date) {
        testBuilder.creationDate(date);
    }

    @Override
    public void variable(String name, Object value) {
        testBuilder.variable(name, value);
    }

    @Override
    public CreateVariablesActionDefinition variables() {
        return testBuilder.variables();
    }

    @Override
    public CreateVariablesAction setVariable(String variableName, String value) {
        return testBuilder.setVariable(variableName, value);
    }

    @Override
    public void action(TestAction testAction) {
        testBuilder.action(testAction);
    }

    @Override
    public void applyBehavior(TestBehavior behavior) {
        testBuilder.applyBehavior(behavior);
    }

    @Override
    public AntRunActionDefinition antrun(String buildFilePath) {
        return testBuilder.antrun(buildFilePath);
    }

    @Override
    public EchoAction echo(String message) {
        return testBuilder.echo(message);
    }

    @Override
    public ExecutePLSQLActionDefinition plsql(DataSource dataSource) {
        return testBuilder.plsql(dataSource);
    }

    @Override
    public ExecuteSQLActionDefinition sql(DataSource dataSource) {
        return testBuilder.sql(dataSource);
    }

    @Override
    public ExecuteSQLQueryActionDefinition query(DataSource dataSource) {
        return testBuilder.query(dataSource);
    }

    @Override
    public ReceiveTimeoutActionDefinition expectTimeout(Endpoint messageEndpoint) {
        return testBuilder.expectTimeout(messageEndpoint);
    }

    @Override
    public ReceiveTimeoutActionDefinition expectTimeout(String messageEndpointName) {
        return testBuilder.expectTimeout(messageEndpointName);
    }

    @Override
    public FailAction fail(String message) {
        return testBuilder.fail(message);
    }

    @Override
    public InputActionDefinition input() {
        return testBuilder.input();
    }

    @Override
    public JavaActionDefinition java(String className) {
        return testBuilder.java(className);
    }

    @Override
    public JavaActionDefinition java(Class<?> clazz) {
        return testBuilder.java(clazz);
    }

    @Override
    public JavaActionDefinition java(Object instance) {
        return testBuilder.java(instance);
    }

    @Override
    public LoadPropertiesAction load(String filePath) {
        return testBuilder.load(filePath);
    }

    @Override
    public PurgeJmsQueueActionDefinition purgeQueues(ConnectionFactory connectionFactory) {
        return testBuilder.purgeQueues(connectionFactory);
    }

    @Override
    public PurgeJmsQueueActionDefinition purgeQueues() {
        return testBuilder.purgeQueues();
    }

    @Override
    public PurgeMessageChannelActionDefinition purgeChannels() {
        return testBuilder.purgeChannels();
    }

    @Override
    public ReceiveSoapMessageActionDefinition receive(WebServiceServer server) {
        return testBuilder.receive(server);
    }

    @Override
    public ReceiveMessageActionDefinition receive(Endpoint messageEndpoint) {
        return testBuilder.receive(messageEndpoint);
    }

    @Override
    public ReceiveMessageActionDefinition receive(String messageEndpointName) {
        return testBuilder.receive(messageEndpointName);
    }

    @Override
    public SendSoapMessageActionDefinition send(WebServiceClient client) {
        return testBuilder.send(client);
    }

    @Override
    public SendMessageActionDefinition send(Endpoint messageEndpoint) {
        return testBuilder.send(messageEndpoint);
    }

    @Override
    public SendMessageActionDefinition send(String messageEndpointName) {
        return testBuilder.send(messageEndpointName);
    }

    @Override
    public SendSoapFaultActionDefinition sendSoapFault(String messageEndpointName) {
        return testBuilder.sendSoapFault(messageEndpointName);
    }

    @Override
    public SendSoapFaultActionDefinition sendSoapFault(Endpoint messageEndpoint) {
        return testBuilder.sendSoapFault(messageEndpoint);
    }

    @Override
    public SleepAction sleep() {
        return testBuilder.sleep();
    }

    @Override
    public SleepAction sleep(long milliseconds) {
        return testBuilder.sleep(milliseconds);
    }

    @Override
    public SleepAction sleep(double seconds) {
        return testBuilder.sleep(seconds);
    }

    @Override
    public StartServerAction start(Server... servers) {
        return testBuilder.start(servers);
    }

    @Override
    public StartServerAction start(Server server) {
        return testBuilder.start(server);
    }

    @Override
    public StopServerAction stop(Server... servers) {
        return testBuilder.stop(servers);
    }

    @Override
    public StopServerAction stop(Server server) {
        return testBuilder.stop(server);
    }

    @Override
    public StopTimeAction stopTime() {
        return testBuilder.stopTime();
    }

    @Override
    public StopTimeAction stopTime(String id) {
        return testBuilder.stopTime(id);
    }

    @Override
    public TraceVariablesAction traceVariables() {
        return testBuilder.traceVariables();
    }

    @Override
    public TraceVariablesAction traceVariables(String... variables) {
        return testBuilder.traceVariables(variables);
    }

    @Override
    public GroovyActionDefinition groovy(String script) {
        return testBuilder.groovy(script);
    }

    @Override
    public GroovyActionDefinition groovy(Resource scriptResource) {
        return testBuilder.groovy(scriptResource);
    }

    @Override
    public TransformActionDefinition transform() {
        return testBuilder.transform();
    }

    @Override
    public AssertDefinition assertException(TestAction testAction) {
        return testBuilder.assertException(testAction);
    }

    @Override
    public CatchDefinition catchException(String exception, TestAction ... actions) {
        return testBuilder.catchException(exception, actions);
    }

    @Override
    public CatchDefinition catchException(Class<? extends Throwable> exception, TestAction ... actions) {
        return testBuilder.catchException(exception, actions);
    }

    @Override
    public CatchDefinition catchException(TestAction ... actions) {
        return testBuilder.catchException(actions);
    }

    @Override
    public AssertSoapFaultDefinition assertSoapFault(TestAction testAction) {
        return testBuilder.assertSoapFault(testAction);
    }

    @Override
    public ConditionalDefinition conditional(TestAction ... actions) {
        return testBuilder.conditional(actions);
    }

    @Override
    public IterateDefinition iterate(TestAction ... actions) {
        return testBuilder.iterate(actions);
    }

    @Override
    public Parallel parallel(TestAction ... actions) {
        return testBuilder.parallel(actions);
    }

    @Override
    public RepeatOnErrorUntilTrueDefinition repeatOnError(TestAction... actions) {
        return testBuilder.repeatOnError(actions);
    }

    @Override
    public RepeatUntilTrueDefinition repeat(TestAction... actions) {
        return testBuilder.repeat(actions);
    }

    @Override
    public Sequence sequential(TestAction ... actions) {
        return testBuilder.sequential(actions);
    }

    @Override
    public TemplateDefinition template(String name) {
        return testBuilder.template(name);
    }

    @Override
    public void doFinally(TestAction ... actions) {
        testBuilder.doFinally(actions);
    }

    @Override
    public PositionHandle positionHandle() {
        return testBuilder.positionHandle();
    }

    /**
     * Get the test variables.
     * @return
     */
    protected Map<String, Object> getVariables() {
        return testBuilder.getVariables();
    }

}
