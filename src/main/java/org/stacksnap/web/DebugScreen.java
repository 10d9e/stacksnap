package org.stacksnap.web;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.stacksnap.serialization.Camera;
import org.stacksnap.serialization.FileSearchSourceLocator;
import org.stacksnap.serialization.Snapshot;
import org.stacksnap.serialization.SourceLocator;

import com.google.common.base.Optional;

import freemarker.template.Configuration;
import freemarker.template.Version;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

public class DebugScreen {
    protected final FreeMarkerEngine templateEngine;
    protected final Configuration templateConfig;
    protected final SourceLocator[] sourceLocators;

    public DebugScreen() {
        this(
                new FileSearchSourceLocator("./src/main/java"),
                new FileSearchSourceLocator("./src"), 
                new FileSearchSourceLocator("./src/test/java")
        );
    }

    public DebugScreen(SourceLocator... sourceLocators) {
        templateEngine = new FreeMarkerEngine();
        templateConfig = new Configuration(new Version(2, 3, 23));
        templateConfig.setClassForTemplateLoading(getClass(), "/");
        templateEngine.setConfiguration(templateConfig);
        this.sourceLocators = sourceLocators;
    }

    public final String handle(final String path, Request request, Response response) {
    	Throwable throwable = null;
        try {
        	Snapshot snapshot = Camera.restoreSnapshot("snap/" + path);
        	
        	// Find the original causing throwable; this will contain the most relevant information to 
            // display to the user.
        	throwable = snapshot.getError();
            while (throwable.getCause() != null) {
                throwable = throwable.getCause();
            }
            List<Map<String, Object>> frames = snapshot.getFrames(); //parseFrames(throwable);

            LinkedHashMap<String, Object> model = new LinkedHashMap<>();
            model.put("message", Optional.fromNullable(throwable.getMessage()).or(""));
            model.put("plain_exception", ExceptionUtils.getStackTrace(throwable));
            model.put("frames", frames);
            model.put("name", throwable.getClass().getCanonicalName().split("\\."));
            model.put("basic_type", throwable.getClass().getSimpleName());
            model.put("type", throwable.getClass().getCanonicalName());

            LinkedHashMap<String, Map<String, ? extends Object>> tables = new LinkedHashMap<>();
            installTables(tables, request, snapshot);
            model.put("tables", tables);
            
            return templateEngine.render(Spark.modelAndView(model, "stacksnap-error.html"));
            //response.body(templateEngine.render(Spark.modelAndView(model, "debugscreen.ftl")));
        } catch (Exception e) {
        	e.printStackTrace();
            // In case we encounter any exceptions trying to render the error page itself,
            // have this simple fallback.
            //response.body(
                    return "<html>"
                            + "  <head>"
                            + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"
                            + "  </head>"
                            + "  <body>"
                            + "    <h1>Caught Exception:</h1>"
                            + "    <pre>" + ExceptionUtils.getStackTrace(throwable) + "</pre>"
                            + "    <h1>Caught Exception Rendering DebugScreen:</h1>"
                            + "    <pre>" + ExceptionUtils.getStackTrace(e) + "</pre>"
                            + "  </body>"
                            + "</html>";
            //);
        }
    }


    /**
     * Install any tables you want to be shown in environment details.
     *
     * @param tables the map containing the tables to display on the debug screen
     */
    protected void installTables(LinkedHashMap<String, Map<String, ? extends Object>> tables, Request request, Snapshot snapshot) {
    	/*
        tables.put("Headers", setToLinkedHashMap(request.headers(), h -> h, request::headers));
        tables.put("Spark Request properties", getRequestInfo(request));
        tables.put("Route Parameters", request.params());
        tables.put("Query Parameters", setToLinkedHashMap(request.queryParams(), p -> p, request::queryParams));
        tables.put("Session Attributes", setToLinkedHashMap(request.session().attributes(), a -> a, request.session()::attribute));
        tables.put("Request Attributes", setToLinkedHashMap(request.attributes(), a -> a, request::attribute));
        tables.put("Cookies", request.cookies());
        tables.put("Environment", getEnvironmentInfo());
        */
    	
    	//tables.put("Method Arguments", getMethodArgs(snapshot));
    	tables.put("Environment", getEnvironmentInfo(snapshot));
    }
    
    private LinkedHashMap<String, Object> getMethodArgs(Snapshot snapshot) {
        LinkedHashMap<String, Object> info = new LinkedHashMap<>();
        Parameter [] params = snapshot.getMethod().getParameters();
        for (int i =0; i < params.length; i++) {
        	info.put(params[i].getName(), snapshot.getArguments()[i]);
        }
        return info;
    }
    
    private LinkedHashMap<String, Object> getTargetInfo(Snapshot snapshot) {
        LinkedHashMap<String, Object> info = new LinkedHashMap<>();
        
        Object target = snapshot.getTarget();
        for(Field f : target.getClass().getDeclaredFields()){
        	try {
        		f.setAccessible(true);
				info.put(f.getName(), f.get(target).toString());
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return info;
    }

    private LinkedHashMap<String, String> setToLinkedHashMap(Set<String> set,
                                                             Function<String, String> keyMapper,
                                                             Function<String, String> valueMapper) {
        return set.stream().collect(Collectors.toMap(keyMapper, valueMapper, (k, v) -> k, LinkedHashMap::new));
    }

    private LinkedHashMap<String, Object> getEnvironmentInfo(Snapshot snapshot) {
        LinkedHashMap<String, Object> environment = new LinkedHashMap<>();
        environment.put("Thread ID", snapshot.getThreadId());
        return environment;
    }

    private LinkedHashMap<String, Object> getRequestInfo(Request request) {
        LinkedHashMap<String, Object> req = new LinkedHashMap<>();
        req.put("URL", Optional.fromNullable(request.url()).or("-"));
        req.put("Scheme", Optional.fromNullable(request.scheme()).or("-"));
        req.put("Method", Optional.fromNullable(request.requestMethod()).or("-"));
        req.put("Protocol", Optional.fromNullable(request.protocol()).or("-"));
        req.put("Remote IP", Optional.fromNullable(request.ip()).or("-"));
        //req.put("Path Info", Optional.fromNullable(request.pathInfo()).or("-"));
        //req.put("Query String", Optional.fromNullable(request.queryString()).or("-"));
        //req.put("Host", Optional.fromNullable(request.host()).or("-"));
        //req.put("Port", Optional.fromNullable(Integer.toString(request.port())).or("-"));
        //req.put("URI", Optional.fromNullable(request.uri()).or("-"));
        //req.put("Content Type", Optional.fromNullable(request.contentType()).or("-"));
        //req.put("Content Length", request.contentLength() == -1 ? "-" : Integer.toString(request.contentLength()));
        //req.put("Context Path", Optional.fromNullable(request.contextPath()).or("-"));
        //req.put("Body", Optional.fromNullable(request.body()).or("-"));
        //req.put("User-Agent", Optional.fromNullable(request.userAgent()).or("-"));
        return req;
    }

}
