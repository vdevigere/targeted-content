package com.viddu.content;

import static io.undertow.servlet.Servlets.listener;
import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.servlet.api.DeploymentInfo;

import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.viddu.content.elasticsearch.ElasticSearchConnectionManager;

public class StartServer {
    public static final String MYAPP = "/targeted-content";

    private static Integer port = 8080;
    private static final Logger logger = LoggerFactory.getLogger(StartServer.class);

    public static UndertowJaxrsServer startServer() {
        logger.debug("Binding to Port: [{}]", port);
        logger.debug("Number of processors: [{}]", Runtime.getRuntime().availableProcessors());
        Undertow.Builder builder = Undertow.builder().addHttpListener(port, "0.0.0.0").setBufferSize(1024 * 16)
                .setIoThreads(Runtime.getRuntime().availableProcessors() * 2).setWorkerThreads(10)
                .setServerOption(UndertowOptions.ALWAYS_SET_KEEP_ALIVE, false);
        return new UndertowJaxrsServer().start(builder);
    }

    public static void main(String[] args) {
        // Start Server
        UndertowJaxrsServer server = startServer();
        DeploymentInfo deploymentInfo = server.undertowDeployment(ApiApplication.class);
        deploymentInfo
                .setDeploymentName("targeted-content")
                .setContextPath("/targeted-content")
                .setClassLoader(StartServer.class.getClassLoader())
                .addListeners(listener(org.jboss.weld.environment.servlet.Listener.class),
                        listener(ElasticSearchConnectionManager.class));
        server.deploy(deploymentInfo);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                server.stop();
            }
         });
    }


}
