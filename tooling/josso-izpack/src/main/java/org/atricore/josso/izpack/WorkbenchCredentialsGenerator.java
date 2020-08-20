package org.atricore.josso.izpack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import com.izforge.izpack.Pack;
import com.izforge.izpack.PackFile;
import com.izforge.izpack.event.InstallerListener;
import com.izforge.izpack.installer.AutomatedInstallData;
import com.izforge.izpack.util.AbstractUIProgressHandler;

public class WorkbenchCredentialsGenerator implements InstallerListener {

    public void beforePacks(AutomatedInstallData automatedInstallData, java.lang.Integer integer, AbstractUIProgressHandler abstractUIProgressHandler) throws Exception {

    }

    public void beforePack(Pack pack, java.lang.Integer integer, AbstractUIProgressHandler abstractUIProgressHandler) throws Exception {

    }

    public boolean isFileListener() {
        return true;
    }

    public void beforeDir(File file, PackFile packFile) throws Exception {

    }

    public void afterDir(File file, PackFile packFile) throws Exception {
        System.err.println("afterDir: " + file.getName());
    }

    public void beforeFile(File file, PackFile packFile) throws Exception {

    }

    public void afterFile(File file, PackFile packFile) throws Exception {
        String filename = file.getName();
        if (filename.equals("com.atricore.idbus.console.appliance.default.idau.cfg")) {
            configureWBCredentials(file);
        }
    }

    public void afterPack(Pack pack, java.lang.Integer integer, AbstractUIProgressHandler abstractUIProgressHandler) throws Exception {

    }

    public void afterPacks(AutomatedInstallData automatedInstallData, AbstractUIProgressHandler abstractUIProgressHandler) throws Exception {

    }

    public void afterInstallerInitialization(AutomatedInstallData automatedInstallData) throws Exception {

    }

    protected void configureWBCredentials(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(fis);
            fis.close();
            properties.setProperty("wb.clientid", "idbus-" + UUID.randomUUID().toString());
            properties.setProperty("wb.secret", PasswordUtil.generateRandomPassword(16));
            FileOutputStream fos = new FileOutputStream(file);
            properties.store(fos, "Modified by JOSSO installer");
            fos.close();
        } catch (IOException e) {
            // TODO :
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
