package com.viddu.api;

import static io.undertow.servlet.Servlets.*;
import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.servlet.api.DeploymentInfo;

import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartServer {
    private static UndertowJaxrsServer server;

    private static final Logger logger = LoggerFactory.getLogger(StartServer.class);
    private static Integer port = 9000;

    public static void startServer() {
        logger.debug("Binding to Port: [{}]", port);
        logger.debug("Number of processors: [{}]", Runtime.getRuntime().availableProcessors());
        Undertow.Builder builder = Undertow.builder().addHttpListener(port, "0.0.0.0").setBufferSize(1024 * 16)
                .setIoThreads(Runtime.getRuntime().availableProcessors() * 2).setWorkerThreads(10)
                .setServerOption(UndertowOptions.ALWAYS_SET_KEEP_ALIVE, false);
        server = new UndertowJaxrsServer().start(builder);

        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setInjectorFactoryClass("org.jboss.resteasy.cdi.CdiInjectorFactory");
        deployment.setApplicationClass("com.viddu.api.ApiApplication");
        DeploymentInfo deploymentInfo = server.undertowDeployment(deployment, "/api");
        deploymentInfo
                .setDeploymentName("targeted-content")
                .setContextPath("/")
                .setClassLoader(StartServer.class.getClassLoader())
                .addListeners(listener(org.jboss.weld.environment.servlet.Listener.class),
                        listener(com.viddu.api.elasticsearch.ElasticSearchConnectionManager.class));
        server.deploy(deploymentInfo);
    }

    public void stopServer() {
        server.stop();
    }

    public static void main(String[] args) {
        StartServer.startServer();
    }
}
