# helidon-native-issue

## Step 1
```shell
git clone git@github.com:rbygrave/helidon-native-issue.git
cd helidon-native-issue
```
## Step 2
Setup environment and check java and maven versions
```shell
sdk use java 24-graal
mvn --version
```
```
WARNING: A restricted method in java.lang.System has been called
WARNING: java.lang.System::load has been called by org.fusesource.jansi.internal.JansiLoader in an unnamed module (file:/Users/robinbygrave/.sdkman/candidates/maven/current/lib/jansi-2.4.1.jar)
WARNING: Use --enable-native-access=ALL-UNNAMED to avoid a warning for callers in this module
WARNING: Restricted methods will be blocked in a future release unless native access is enabled

Apache Maven 3.9.9 (8e8579a9e76f7d015ee5ec7bfcdc97d260186937)
Maven home: /Users/robinbygrave/.sdkman/candidates/maven/current
Java version: 24, vendor: Oracle Corporation, runtime: /Users/robinbygrave/.sdkman/candidates/java/24-graal
Default locale: en_NZ, platform encoding: UTF-8
OS name: "mac os x", version: "15.5", arch: "aarch64", family: "mac"
```

## Step 3
Build the native image
```shell
mvn clean package -Pnative
```

## Step 4
Run the native image
```shell
./target/hello-helidon
```

## Step 5 - reproduce issue
```shell
curl -X POST "http://localhost:8080/basic" -d 'IAmBodyContent'
```

## Expected output from hello-helidon
```
May 14, 2025 2:58:44 PM io.helidon.webserver.http1.Http1Connection handleRequestException
WARNING: Internal server error
io.helidon.http.RequestException: Internal error
        at io.helidon.http.RequestException$Builder.build(RequestException.java:139)
        at io.helidon.webserver.http1.Http1Connection.handle(Http1Connection.java:249)
        at io.helidon.webserver.ConnectionHandler.run(ConnectionHandler.java:175)
        at io.helidon.common.task.InterruptableTask.call(InterruptableTask.java:47)
        at io.helidon.webserver.ThreadPerTaskExecutor$ThreadBoundFuture.run(ThreadPerTaskExecutor.java:239)
        at java.base@24/java.lang.Thread.runWith(Thread.java:1460)
        at java.base@24/java.lang.VirtualThread.run(VirtualThread.java:466)
        at java.base@24/java.lang.VirtualThread$VThreadContinuation$1.run(VirtualThread.java:258)
Caused by: io.helidon.service.registry.ServiceRegistryException: Resolution of type "io.helidon.common.mapper.spi.MapperProvider" to class failed.
        at io.helidon.service.registry.CoreServiceDiscovery.toClass(CoreServiceDiscovery.java:107)
        at io.helidon.service.registry.CoreServiceDiscovery$DescriptorHandlerImpl.parseServiceProvider(CoreServiceDiscovery.java:250)
        at java.base@24/java.util.stream.ReferencePipeline$7$1FlatMap.accept(ReferencePipeline.java:289)
        at java.base@24/java.util.stream.ReferencePipeline$2$1.accept(ReferencePipeline.java:197)
        at java.base@24/java.util.stream.ReferencePipeline$2$1.accept(ReferencePipeline.java:197)
        at java.base@24/java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1716)
        at java.base@24/java.util.stream.ReferencePipeline$Head.forEach(ReferencePipeline.java:807)
        at java.base@24/java.util.stream.ReferencePipeline$7$1FlatMap.accept(ReferencePipeline.java:294)
        at java.base@24/java.util.Iterator.forEachRemaining(Iterator.java:133)
        at java.base@24/java.util.Spliterators$IteratorSpliterator.forEachRemaining(Spliterators.java:1939)
        at java.base@24/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:570)
        at java.base@24/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:560)
        at java.base@24/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:153)
        at java.base@24/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:176)
        at java.base@24/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:265)
        at java.base@24/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:636)
        at io.helidon.service.registry.CoreServiceDiscovery.<init>(CoreServiceDiscovery.java:76)
        at io.helidon.service.registry.CoreServiceDiscovery.create(CoreServiceDiscovery.java:83)
        at io.helidon.service.registry.ServiceRegistryManager.create(ServiceRegistryManager.java:197)
        at io.helidon.service.registry.GlobalServiceRegistry.registry(GlobalServiceRegistry.java:64)
        at io.helidon.service.registry.Services.get(Services.java:156)
        at io.helidon.common.mapper.GlobalManager.mapperManager(GlobalManager.java:55)
        at io.helidon.common.mapper.MapperManager.global(MapperManager.java:56)
        at io.helidon.http.HeaderValueBase.get(HeaderValueBase.java:61)
        at io.helidon.webserver.http1.Http1Connection.route(Http1Connection.java:510)
        at io.helidon.webserver.http1.Http1Connection.handle(Http1Connection.java:224)
        ... 6 more
        Suppressed: java.lang.ClassNotFoundException: io.helidon.common.mapper.spi.MapperProvider
                at java.base@24/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:59)
                at java.base@24/java.lang.ClassLoader.loadClass(ClassLoader.java:115)
                at io.helidon.service.registry.CoreServiceDiscovery.toClass(CoreServiceDiscovery.java:101)
                ... 31 more
Caused by: java.lang.ClassNotFoundException: io.helidon.common.mapper.spi.MapperProvider
        at java.base@24/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:59)
        at java.base@24/java.lang.ClassLoader.loadClass(ClassLoader.java:115)
        at io.helidon.service.registry.CoreServiceDiscovery.toClass(CoreServiceDiscovery.java:105)
        ... 31 more


```