package com.code.srmsystem.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class ScheduledBackUp {

    @Scheduled(cron = "* * * * * ?")
    public void DBBackUp() throws IOException, InterruptedException {
        String backUpFilePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\dump\\";
        String command = String.format("C:\\xampp\\mysql\\bin\\mysqldump -u%s -p%s --add-drop-table --databases %s -r %s",
                "root", "admin", "srmsystem", backUpFilePath + "SRMSYSTEM_BackUpFile_" + new Date().getTime() + ".bak");
        Process process = Runtime.getRuntime().exec(command);
        int processComplete = process.waitFor();
        if (processComplete == 0) {
            System.out.println("Backup up file has been successfully created");
        } else {
            System.out.println("Unsuccessful backup file");
        }
    }
}
