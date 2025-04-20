package com.api.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.util.Arrays;

public class Setup implements ITestListener {
    private static ExtentReports extentReports;
    public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public void onStart(ITestContext context) {
        String fileName = ExtentReportManager.generateReportFileName(); // Updated method name
        String fullReportPath = System.getProperty("user.dir") + File.separator + "reports" + File.separator + fileName;

        synchronized (Setup.class) {  // Ensure thread safety
            if (extentReports == null) {
                extentReports = ExtentReportManager.createInstance(fullReportPath, "Test Automation Report");
            }
        }
    }

    public void onFinish(ITestContext context) {
        if (extentReports != null) {
            ExtentReportManager.flushReports(); // Ensure proper cleanup
        }
    }

    public void onTestStart(ITestResult result) {
        ExtentTest test = extentReports.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
    }

//    public void onTestSuccess(ITestResult result) {
//        if (extentTest.get() != null) {
//            extentTest.get().pass("Test Passed: " + result.getMethod().getMethodName());
//        }
//    }

    public void onTestFailure(ITestResult result) {
        if (extentTest.get() != null) {
//            extentTest.get().fail("Test Failed: " + result.getMethod().getMethodName());
            ExtentReportManager.logFailureDetails(result.getThrowable().getMessage());
           String stackTrace = Arrays.toString(result.getThrowable().getStackTrace());
           stackTrace = stackTrace.replaceAll(",", "<br>");
           String formatted = "<details>\n " +
                   "<summary> Click here to check the exception Logs details </summary>\n"+
                   stackTrace+
                   "</details>\n";

                    ExtentReportManager.logExceptionDetails(formatted);

        }
    }

    public void onTestSkipped(ITestResult result) {
        if (extentTest.get() != null) {
            extentTest.get().skip("Test Skipped: " + result.getMethod().getMethodName());
        }
    }
}
