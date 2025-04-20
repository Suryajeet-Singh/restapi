package com.api.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.http.Header;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class ExtentReportManager {
    private static ExtentReports extentReports;

    public static synchronized ExtentReports createInstance(String fileName, String reportName) {
        if (extentReports == null) {
            ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(fileName);
            extentSparkReporter.config().setReportName(reportName);
            extentSparkReporter.config().setDocumentTitle("API Report");
            extentSparkReporter.config().setTheme(Theme.DARK);

            extentReports = new ExtentReports();
            extentReports.attachReporter(extentSparkReporter);
        }
        return extentReports;
    }

    public static String generateReportFileName() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm");
        LocalDateTime localDateTime = LocalDateTime.now();
        return "Test_Report_" + dateTimeFormatter.format(localDateTime) + ".html";
    }

    public static void logPassDetails(String log) {
        Setup.extentTest.get().pass(MarkupHelper.createLabel(log, ExtentColor.GREEN));
    }

    public static void logFailureDetails(String log) {
        Setup.extentTest.get().fail(MarkupHelper.createLabel(log, ExtentColor.RED));
    }

    public static void logExceptionDetails(String log) {
        Setup.extentTest.get().fail(log);
    }

    public static void logInfoDetails(String log) {
        Setup.extentTest.get().info(MarkupHelper.createLabel(log, ExtentColor.BLUE)); // Changed AMBER to BLUE
    }

    public static void logWarningDetails(String log) {
        Setup.extentTest.get().warning(MarkupHelper.createLabel(log, ExtentColor.YELLOW));
    }

    public static void logJson(String json) {
        Setup.extentTest.get().info(MarkupHelper.createCodeBlock(json, CodeLanguage.JSON));
    }
//    public static void logHeadersDetails(List<Header> headersList) {
//        String[][] arrayHeaders = headersList.stream().map(header -> new String[]{ header.getName(),header.getValue()}).toArray(String[][]::new);
//        Setup.extentTest.get().info(MarkupHelper.createTable(arrayHeaders));
//    }

    public static void flushReports() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }

    public static void logHeadersDetails(List<io.restassured.http.Header> list) {
        String[][] arrayHeaders = list.stream().map(header -> new String[]{ header.getName(),header.getValue()}).toArray(String[][]::new);
        Setup.extentTest.get().info(MarkupHelper.createTable(arrayHeaders));
    }
}
