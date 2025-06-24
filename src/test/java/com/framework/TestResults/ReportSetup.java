package com.framework.TestResults;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ReportSetup {

    public static String reportFolderPath;

    @BeforeSuite
    public void setupReportDirectory() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String randomSuffix = UUID.randomUUID().toString().substring(0, 5);
        reportFolderPath = "C:/Automation/TestAutomationFramework/Automation_Report_" + timeStamp + "_" + randomSuffix;

        File folder = new File(reportFolderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        System.setProperty("custom.report.output", reportFolderPath);
        System.out.println("✅ [ReportSetup] Folder created at: " + reportFolderPath);
    }
    @AfterSuite
    public void writeHtmlSummary() {
        try {
            String html = """
            <html>
              <head><title>Test Automation Summary</title></head>
              <body style='font-family: Arial;'>
                <h2>✅ Automation Test Summary</h2>
                <ul>
                  <li><strong>Execution Time:</strong> %s</li>
                  <li><strong>Folder:</strong> %s</li>
                </ul>
                <p>Check <code>console.txt</code> and any API/UI logs for more info.</p>
              </body>
            </html>
        """.formatted(
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                    reportFolderPath
            );

            Files.write(
                    Paths.get(reportFolderPath + "/summary.html"),
                    html.getBytes(StandardCharsets.UTF_8)
            );
            System.out.println("✅ HTML summary report generated.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void moveConsoleLog() {
        try {
            Path source = Paths.get("temp_console.log");
            Path dest = Paths.get(reportFolderPath + "/console.txt");
            Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
