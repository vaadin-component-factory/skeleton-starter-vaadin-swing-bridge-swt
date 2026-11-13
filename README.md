# Vaadin SwingBridge: SWT starter

This starter runs an existing **SWT application** inside a **Vaadin** application with the SWT extension for Vaadin SwingBridge, so that your users can reach it in the browser.

It comes with **WarehousePro**, a sample SWT inventory application, in `applibs/`. Every browser session gets its own copy of the application. Sign in with `admin / admin` or `manager / manager`.

## Prerequisites
- **A Windows server.** The applications run there with their native Win32 widgets, exactly as on the desktop. Set its display scale to 100 % (Settings › System › Display).
- **JDK 21 or later.** Maven is not needed: the project ships the Maven wrapper (`mvnw.cmd`).
- **A Vaadin account with a SwingBridge license or trial.** Sign in when the license check asks.

## How to Run
Double-click `start-vaadin.bat`, or run
```
mvnw.cmd spring-boot:run
```

Then open http://localhost:8888.

As with SwingBridge, every user's copy of the application runs in the Vaadin application's JVM: its classes come from a class loader of its own, and it runs on a UI thread and a hidden Windows desktop of its own, which nobody sees.

## Running your own SWT application
1. **Put your application's jars in `applibs/`**: the main jar and its dependencies, but **not SWT** itself (the `org.eclipse.swt.*` jars). The bridge provides SWT, version `swt.version` in `pom.xml`, loaded once for every user.
2. **Set `MAIN_CLASS`** in `src/main/java/com/example/swtbridge/ui/WarehouseView.java` to the class with your application's `main` method:
   ```java
   SwtBridge app = new SwtBridge("com.mycompany.MyApp");
   ```
   Program arguments go after the main class: `new SwtBridge("com.mycompany.MyApp", "--arg")`.
3. **If your application needs `-D` options** (the system properties you start it with, as in `java -Dfoo=bar -jar myapp.jar`), set them in the `<systemPropertyVariables>` section of `pom.xml`: every user's application runs in the server's JVM and reads the server's system properties.
4. **Run it** as above.

## Configuration
The bridge reads its settings from system properties of the server. The `<systemPropertyVariables>` section of `pom.xml` sets them for `spring-boot:run`, and lists the optional ones as comments.

| Property | Default | |
|---|---|---|
| `applibs.dir` | `applibs` | The directory with your application's jars |
| `swingbridge.swt.libDir` | `swt-bridge` | The jars the build puts in `target/swt-bridge` |
| `swingbridge.swt.frameInterval` | `40` | Milliseconds between captures of an application while it or its user is active |
| `swingbridge.swt.idleFrameInterval` | `300` | Milliseconds between captures when idle |
| `swingbridge.swt.debugInput` | `false` | Log the mouse and keyboard input sent to the applications |

## Dependencies
`pom.xml` declares Vaadin SwingBridge (`swing-bridge-flow`, version `swing-bridge.version`) and its SWT extension (`swing-bridge-swt-flow`, version `swing-bridge-swt.version`). SwingBridge is a commercial product and needs a SwingBridge license. Keep `swing-bridge.version` at the version the extension was built against (1.4.0-alpha1 for this release).

- SwingBridge 1.4.0-alpha1 is a pre-release, from Vaadin's pre-release repository.
- The [SWT extension](https://github.com/vaadin-component-factory/swing-bridge-swt-addon) (`org.vaadin.addons.componentfactory:swing-bridge-swt-flow`) is published through the Vaadin Directory, to Vaadin's add-on repository. `pom.xml` declares both repositories.
- SWT and JNA are not dependencies of the application: the build copies them to `target/swt-bridge`, where the bridge loads them, with its runtime, once for every user.

## Production Build
```
mvnw.cmd clean package
```

The result is a Spring Boot jar in `target/`, next to `target/swt-bridge`. Run it from the project directory as below. To deploy elsewhere, copy the jar, `target/swt-bridge` and `applibs` together and adjust the paths.

```cmd
java ^
  -Dswingbridge.swt.libDir=target\swt-bridge ^
  -Dapplibs.dir=applibs ^
  -Dwarehouse.db=%CD%\warehousedb ^
  -jar target\skeleton-starter-vaadin-swing-bridge-swt-1.0-SNAPSHOT.jar
```

## Learn more
- [Vaadin SwingBridge documentation](https://vaadin.com/docs/latest/tools/modernization-toolkit/swing-bridge).
- [Vaadin documentation](https://vaadin.com/docs/latest).
