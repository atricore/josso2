/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.atricore.josso.tooling.wrapper;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.apache.felix.gogo.commands.Option;
import org.apache.felix.gogo.commands.Command;
import org.fusesource.jansi.Ansi;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Installs this Karaf instance as a service in your operating systems.
 *
 * @version $Rev: 603634 $ $Date: 2007-12-12 16:07:16 +0100 (Wed, 12 Dec 2007) $
 */
@Command(scope = "wrapper", name = "install", description = "Install the container as a system service in the OS.")
public class InstallCommand extends OsgiCommandSupport {

    private static final Log logger  = LogFactory.getLog(OsgiCommandSupport.class);

    @Option(name = "-n", aliases = {"--name"}, description = "The service name that will be used when installing the service. (Default: josso)", required = false, multiValued = false)
    private String name = "josso2";

    @Option(name = "-d", aliases = {"--display"}, description = "The display name of the service.", required = false, multiValued = false)
    private String displayName = "Atricore JOSSO 2";

    @Option(name = "-D", aliases = {"--description"}, description = "The description of the service.", required = false, multiValued = false)
    private String description = "Provides Open Source Internet SSO solution for rapid and standards-based (SAML) Internet-scale Single Sign-On implementations";

    @Option(name = "-s", aliases = {"--start-type"}, description = "Mode in which the service is installed. AUTO_START or DEMAND_START (Default: AUTO_START)", required = false, multiValued = false)
    private String startType = "AUTO_START";

    @Option(name = "-j", aliases = {"--java-home"}, description = "Installation directory for JDK or JRE", required = false, multiValued = false)
    private String javaHome;

    protected Object doExecute() throws Exception {

        try {
            String name = getName();
            String linuxDistro = null;
            File base = new File(System.getProperty("karaf.base"));
            File bin = new File(base, "bin");
            File etc = new File(base, "etc");
            File lib = new File(base, "lib");
            File wrapper = new File(lib, "wrapper");

            File templates = new File(wrapper, "templates");

            File wrapperLib = new File(wrapper, "lib");

            File core = new File(wrapperLib, "core");
            File netty = new File(core, "netty");
            File jna = new File(core, "jna");
            File commons = new File(core, "commons");
            File yajsw = new File(core, "yajsw");
            File regex = new File(core, "regex");
            File groovy = new File(core, "groovy");

            File extended = new File(wrapperLib, "extended");
            File velocity = new File(extended, "velocity");


            mkdir(lib);
            mkdir(wrapper);
            mkdir(wrapperLib);

            mkdir(core);
            mkdir(netty);
            mkdir(jna);
            mkdir(commons);
            mkdir(groovy);
            mkdir(yajsw);
            mkdir(regex);

            mkdir(extended);
            mkdir(velocity);

            mkdir(templates);

            HashMap<String, String> props = new HashMap<String, String>();

            // Use system java home if none is provided
            if (javaHome == null || "".equals(javaHome))
                javaHome = System.getProperty("java.home");

            // Replace windows slashes for unix friendly values
            props.put("${karaf.home}", System.getProperty("karaf.home").replace('\\', '/'));
            props.put("${karaf.base}", base.getPath().replace('\\', '/'));
            props.put("${karaf.data}", System.getProperty("karaf.data").replace('\\', '/'));
            props.put("${java.home}", javaHome.replace('\\', '/'));


            props.put("${name}", name);
            props.put("${displayName}", getDisplayName());
            props.put("${description}", getDescription());
            props.put("${startType}", getStartType());



            String os = System.getProperty("os.name", "Unknown");
            File serviceInstallFile = null;
            File serviceRemoveFile = null;

            File wrapperConf = null;
            File wrapperStopperConf = null;
            File wrapperSetEnvFile = null;
            File wrapperScriptFile = null;

            if (os.startsWith("Win")) {
                mkdir(bin);

                serviceInstallFile = new File(bin, "installService.bat");
                copyFilteredResourceTo(serviceInstallFile, "windows/installService.bat", props);

                serviceRemoveFile = new File(bin, "uninstallService.bat");
                copyFilteredResourceTo(serviceRemoveFile, "windows/uninstallService.bat", props);

                wrapperSetEnvFile = new File(bin, "wrapper-setenv.bat");
                copyFilteredResourceTo(wrapperSetEnvFile, "windows/wrapper-setenv.bat", props);

                wrapperScriptFile = new File(bin, "wrapper.bat");
                copyFilteredResourceTo(wrapperScriptFile, "windows/wrapper.bat", props);

                wrapperConf = new File(etc, name + "-wrapper.conf");
                copyFilteredResourceTo(wrapperConf, "windows/josso-wrapper.conf", props);

                wrapperStopperConf = new File(etc, name + "-wrapper-stop.conf");
                copyFilteredResourceTo(wrapperStopperConf, "windows/josso-wrapper-stop.conf", props);


            } else if (os.startsWith("Mac OS X")) {
                System.out.println("Your operating system '" + os + "' is not currently supported.");
                return 1;

            } else if (os.startsWith("Linux")) {

                linuxDistro = this.getLinuxFlavor();

                mkdir(bin);

                serviceInstallFile = new File(bin, name + "-install-service.sh");
                copyFilteredResourceTo(serviceInstallFile, "linux/installDaemon.sh", props);

                serviceRemoveFile = new File(bin, name + "-uninstall-service.sh");
                copyFilteredResourceTo(serviceRemoveFile, "linux/installDaemon.sh", props);

                wrapperSetEnvFile = new File(bin, "wrapper-setenv.sh");
                copyFilteredResourceTo(wrapperSetEnvFile, "linux/wrapper-setenv.sh", props);

                wrapperScriptFile = new File(bin, "wrapper.sh");
                copyFilteredResourceTo(wrapperScriptFile, "linux/wrapper.sh", props);

                wrapperConf = new File(etc, name + "-wrapper.conf");
                copyFilteredResourceTo(wrapperConf, "linux/josso-wrapper.conf", props);

                File wrapperDaemonTemplate = new File(templates, "daemon.vm");
                copyResourceTo(wrapperDaemonTemplate, "linux/lib/wrapper/templates/daemon.vm", false);

            } else {
                System.out.println("Your operating system '" + os + "' is not currently supported.");
                return 1;
            }

            // Install the wrapper jar to the lib directory..
            copyResourceTo(new File(wrapper, "wrapper.jar"), "all/wrapper.jar", false);
            copyResourceTo(new File(wrapper, "wrapperApp.jar"), "all/wrapperApp.jar", false);
            copyResourceTo(new File(netty, "netty-3.2.4.Final.jar"), "all/lib/core/netty/netty-3.2.4.Final.jar", false);
            copyResourceTo(new File(jna, "jna-3.3.0.jar"), "all/lib/core/jna/jna-3.3.0.jar", false);
            copyResourceTo(new File(jna, "jnacontrib-platform-3.3.0.jar"), "all/lib/core/jna/jnacontrib-platform-3.3.0.jar", false);
            copyResourceTo(new File(commons, "commons-configuration-1.7-SNAPSHOT.jar"), "all/lib/core/commons/commons-configuration-1.7-SNAPSHOT.jar", false);
            copyResourceTo(new File(commons, "commons-vfs-2.0-SNAPSHOT.jar"), "all/lib/core/commons/commons-vfs-2.0-SNAPSHOT.jar", false);
            copyResourceTo(new File(groovy, "groovy-all-1.8.0.jar"), "all/lib/core/groovy/groovy-all-1.8.0.jar", false);
            copyResourceTo(new File(commons, "commons-httpclient-3.0.1.jar"), "all/lib/core/commons/commons-httpclient-3.0.1.jar", false);
            copyResourceTo(new File(commons, "commons-codec-1.3.jar"), "all/lib/core/commons/commons-codec-1.3.jar", false);
            copyResourceTo(new File(commons, "commons-collections-3.2.jar"), "all/lib/core/commons/commons-collections-3.2.jar", false);
            copyResourceTo(new File(commons, "commons-io-1.3.1.jar"), "all/lib/core/commons/commons-io-1.3.1.jar", false);
            copyResourceTo(new File(commons, "commons-lang-2.4.jar"), "all/lib/core/commons/commons-lang-2.4.jar", false);
            copyResourceTo(new File(commons, "commons-logging-1.1.jar"), "all/lib/core/commons/commons-logging-1.1.jar", false);
            copyResourceTo(new File(commons, "commons-cli-2-SNAPSHOT.jar"), "all/lib/core/commons/commons-cli-2-SNAPSHOT.jar", false);
            copyResourceTo(new File(yajsw, "ahessian.jar"), "all/lib/core/yajsw/ahessian.jar", false);
            copyResourceTo(new File(regex, "jrexx-1.1.1.jar"), "all/lib/core/regex/jrexx-1.1.1.jar", false);
            copyResourceTo(new File(velocity, "velocity-1.6.3.jar"), "all/lib/extended/velocity/velocity-1.6.3.jar", false);
            mkdir(etc);

            System.out.println("");
            System.out.println("Setup complete.  You may want to tweak the JVM properties in the wrapper configuration file:");
            System.out.println("\t" + wrapperConf.getPath());
            System.out.println("before installing and starting the service.");
            System.out.println("");
            if (os.startsWith("Win")) {
                System.out.println("To install the service, run: ");
                System.out.println("  C:> " + serviceInstallFile.getPath());
                System.out.println("");
                System.out.println("Once installed, to start the service run: ");
                System.out.println("  C:> net start \"" + name + "\"");
                System.out.println("");
                System.out.println("Once running, to stop the service run: ");
                System.out.println("  C:> net stop \"" + name + "\"");
                System.out.println("");
                System.out.println("Once stopped, to remove the installed the service run: ");
                System.out.println("  C:> " + serviceRemoveFile.getPath() + " remove");
                System.out.println("");
            } else if (os.startsWith("Mac OS X")) {
                System.out.println("");
                System.out.println("At this time it is not known how to get this service to start when the machine is rebooted.");
                System.out.println("If you know how to install the following service script so that it gets started");
                System.out.println("when OS X starts, please email dev@felix.apache.org and let us know how so");
                System.out.println("we can update this message.");
                System.out.println(" ");
                System.out.println("  To start the service:");
                System.out.println("    $ " + serviceInstallFile.getPath() + " start");
                System.out.println("");
                System.out.println("  To stop the service:");
                System.out.println("    $ " + serviceInstallFile.getPath() + " stop");
                System.out.println("");
            } else if (os.startsWith("Linux")) {

                System.out.println("The way the service is installed depends upon your flavor of Linux.");

                if (linuxDistro != null)
                    System.out.println("The detected Linux flavor is " + linuxDistro);
                else
                    System.out.println("The Linux flavor was not detected");

                if (isRedhatFamily(linuxDistro) || !isDebianFamily(linuxDistro)) {
                    System.out.println("");
                    System.out.println(Ansi.ansi().a(Ansi.Attribute.INTENSITY_BOLD).a("On Redhat/Fedora/CentOS Systems (run as root):").a(Ansi.Attribute.RESET).toString());
                    System.out.println("  Before starting: ");
                    System.out.println("");
                    System.out.println("   Create 'josso' user)");
                    System.out.println("    $ useradd josso");
                    System.out.println("");
                    System.out.println("   Prepare the installer and the environment)");
                    System.out.println("    $ cd " + base.getPath() + "/bin");
                    System.out.println("    $ chmod a+x *.sh");
                    System.out.println("    $ mkdir /var/run/josso2");
                    System.out.println("    $ chown josso.josso /var/run/josso2");
                    System.out.println("");
                    System.out.println("  To install the service: ");
                    System.out.println("    $ ./" + name + "-service-install.sh");
                    System.out.println("    $ chkconfig " + name + " --add");
                    System.out.println("");
                    System.out.println("  To start the service when the machine is rebooted:");
                    System.out.println("    $ chkconfig " + name + " on");
                    System.out.println("");
                    System.out.println("  To disable starting the service when the machine is rebooted:");
                    System.out.println("    $ chkconfig " + name + " off");
                    System.out.println("");
                    System.out.println("  To start the service:");
                    System.out.println("    $ service " + name + " start");
                    System.out.println("");
                    System.out.println("  To stop the service:");
                    System.out.println("    $ service " + name + " stop");
                    System.out.println("");
                    System.out.println("  To uninstall the service :");
                    System.out.println("    $ chkconfig " + name + " --del");
                    System.out.println("    $ rm /etc/init.d/" + name);
                }

                if (isDebianFamily(linuxDistro) || !isRedhatFamily(linuxDistro)) {

                    System.out.println("");
                    System.out.println(Ansi.ansi().a(Ansi.Attribute.INTENSITY_BOLD).a("On Ubuntu/Debian Systems:").a(Ansi.Attribute.RESET).toString());
                    System.out.println("  Before starting: ");
                    System.out.println("");
                    System.out.println("   Create 'josso' user)");
                    System.out.println("    $ useradd josso");
                    System.out.println("");
                    System.out.println("   Prepare the installer and the environment)");
                    System.out.println("    $ cd " + base.getPath() + "/bin");
                    System.out.println("    $ chmod a+x *.sh");
                    System.out.println("    $ mkdir /var/run/josso2");
                    System.out.println("    $ chown josso.josso /var/run/josso2");
                    System.out.println("");
                    System.out.println("  To install the service:");
                    System.out.println("");
                    System.out.println("  To start the service when the machine is rebooted:");
                    System.out.println("    $ update-rc.d " + name + " defaults");
                    System.out.println("");
                    System.out.println("  To disable starting the service when the machine is rebooted:");
                    System.out.println("    $ update-rc.d -f " + name + " remove");
                    System.out.println("");
                    System.out.println("  To start the service:");
                    System.out.println("    $ /etc/init.d/" + name + " start");
                    System.out.println("");
                    System.out.println("  To stop the service:");
                    System.out.println("    $ /etc/init.d/" + name + " stop");
                    System.out.println("");
                    System.out.println("  To uninstall the service :");
                    System.out.println("    $ rm /etc/init.d/" + name);
                }

            }


        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            System.err.println(e.getMessage());
            e.printStackTrace();
            throw e;
        }

        return null;
    }

    private int chmod(File serviceFile, String mode) throws Exception {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("chmod", mode, serviceFile.getCanonicalPath());
        Process p = builder.start();

        PumpStreamHandler handler = new PumpStreamHandler(System.in, System.out, System.err);
        handler.attach(p);
        handler.start();
        int status = p.waitFor();
        handler.stop();
        return status;
    }

    private void createJar(File outFile, String resource) throws Exception {
        if (!outFile.exists()) {
            System.out.println(Ansi.ansi().a("Creating file: ")
                    .a(Ansi.Attribute.INTENSITY_BOLD).a(outFile.getPath()).a(Ansi.Attribute.RESET).toString());
            InputStream is = getClass().getClassLoader().getResourceAsStream(resource);
            if (is == null) {
                throw new IllegalStateException("Resource " + resource + " not found!");
            }
            try {
                JarOutputStream jar = new JarOutputStream(new FileOutputStream(outFile));
                int idx = resource.indexOf('/');
                while (idx > 0) {
                    jar.putNextEntry(new ZipEntry(resource.substring(0, idx)));
                    jar.closeEntry();
                    idx = resource.indexOf('/', idx + 1);
                }
                jar.putNextEntry(new ZipEntry(resource));
                int c;
                while ((c = is.read()) >= 0) {
                    jar.write(c);
                }
                jar.closeEntry();
                jar.close();
            } finally {
                safeClose(is);
            }
        }
    }

    private void copyResourceTo(File outFile, String resource, boolean text) throws Exception {
        if (!outFile.exists()) {
            System.out.println(Ansi.ansi().a("Creating file: ")
                    .a(Ansi.Attribute.INTENSITY_BOLD).a(outFile.getPath()).a(Ansi.Attribute.RESET).toString());
            InputStream is = InstallCommand.class.getResourceAsStream(resource);
            try {
                if (text) {
                    // Read it line at a time so that we can use the platform line ending when we write it out.
                    PrintStream out = new PrintStream(new FileOutputStream(outFile));
                    try {
                        Scanner scanner = new Scanner(is);
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            System.out.println("writing: " + line);
                            out.println(line);
                        }
                    } finally {
                        safeClose(out);
                    }
                } else {
                    // Binary so just write it out the way it came in.
                    FileOutputStream out = new FileOutputStream(outFile);
                    try {
                        int c = 0;
                        while ((c = is.read()) >= 0) {
                            out.write(c);
                        }
                    } finally {
                        safeClose(out);
                    }
                }
            } finally {
                safeClose(is);
            }
        } else {
            System.out.println(Ansi.ansi()
                    .fg(Ansi.Color.RED).a("File already exists").a(Ansi.Attribute.RESET)
                    .a(". Move it out of the way if you want it re-created: ").a(outFile.getPath()).toString());
        }
    }

    private void copyFilteredResourceTo(File outFile, String resource, HashMap<String, String> props) throws Exception {
        if (!outFile.exists()) {
            System.out.println(Ansi.ansi().a("Creating file: ")
                    .a(Ansi.Attribute.INTENSITY_BOLD).a(outFile.getPath()).a(Ansi.Attribute.RESET).toString());
            InputStream is = InstallCommand.class.getResourceAsStream(resource);
            try {
                // Read it line at a time so that we can use the platform line ending when we write it out.
                PrintStream out = new PrintStream(new FileOutputStream(outFile));
                try {
                    Scanner scanner = new Scanner(is);
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        line = filter(line, props);
                        out.println(line);
                    }
                } finally {
                    safeClose(out);
                }
            } finally {
                safeClose(is);
            }
        } else {
            System.out.println(Ansi.ansi()
                    .fg(Ansi.Color.RED).a("File already exists").a(Ansi.Attribute.RESET)
                    .a(". Move it out of the way if you want it re-created: ").a(outFile.getPath()).toString());
        }
    }

    private void safeClose(InputStream is) throws IOException {
        if (is == null)
            return;
        try {
            is.close();
        } catch (Throwable ignore) {
        }
    }

    private void safeClose(OutputStream is) throws IOException {
        if (is == null)
            return;
        try {
            is.close();
        } catch (Throwable ignore) {
        }
    }

    private String filter(String line, HashMap<String, String> props) {
        for (Map.Entry<String, String> i : props.entrySet()) {
            int p1 = line.indexOf(i.getKey());
            if (p1 >= 0) {
                String l1 = line.substring(0, p1);
                String l2 = line.substring(p1 + i.getKey().length());
                line = l1 + i.getValue() + l2;
            }
        }
        return line;
    }

    private void mkdir(File file) {
        if (!file.exists()) {
            System.out.println(Ansi.ansi().a("Creating missing directory: ")
                    .a(Ansi.Attribute.INTENSITY_BOLD).a(file.getPath()).a(Ansi.Attribute.RESET).toString());
            file.mkdirs();
        }
    }

    public String getName() {
        if (name == null) {
            File base = new File(System.getProperty("karaf.base"));
            name = base.getName();
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        if (displayName == null) {
            displayName = getName();
        }
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartType() {
        return startType;
    }

    public void setStartType(String startType) {
        this.startType = startType;
    }

    protected boolean isRedhatFamily(String linuxDistro) {
        if (linuxDistro == null)
            return false;

        String s = linuxDistro.toLowerCase();

        if (s.contains("fedora"))
            return true;
        if (s.contains("redhat"))
            return true;
        if (s.contains("centos"))
            return true;

        return false;
    }

    protected boolean isDebianFamily(String linuxDistro) {
        if (linuxDistro == null)
            return false;

        String s = linuxDistro.toLowerCase();

        if (s.contains("ubuntu"))
            return true;
        if (s.contains("debian"))
            return true;

        return false;
    }


    protected String getLinuxFlavor() {
        // There's no way to get linux flavor from Java system properties, so we use this 'hack'
        try {
            String cmd = "lsb_release -i";
            Runtime run = Runtime.getRuntime();
            Process pr = null;

            pr = run.exec(cmd);
            pr.waitFor();

            BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line = "";
            String result = "";
            while ((line = buf.readLine()) != null) {
                result += line;
            }

            return result;

        } catch (InterruptedException e) {
            logger.warn(e.getMessage());
            if (logger.isDebugEnabled())
                logger.debug(e);
        } catch (IOException e) {
            logger.warn(e.getMessage());
            if (logger.isDebugEnabled())
                logger.debug(e);
        } catch (Exception e) {
            logger.warn(e.getMessage());
            if (logger.isDebugEnabled())
                logger.debug(e);
        }

        return null;

    }
}
