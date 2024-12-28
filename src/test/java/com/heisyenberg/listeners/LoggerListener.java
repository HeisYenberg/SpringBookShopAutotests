package com.heisyenberg.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class LoggerListener extends TestListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerListener.class);

    @Override
    public void onTestSkipped(ITestResult result) {
        LOGGER.warn(
                "Test completes with status [SKIP] {}.{}",
                result.getInstanceName(),
                result.getName(),
                result.getThrowable()
        );
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOGGER.error(
                "Test completes with status [FAIL] {}.{}",
                result.getInstanceName(),
                result.getName(),
                result.getThrowable()
        );
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOGGER.info(
                "Test completes with status [SUCCESS] {}.{}",
                result.getInstanceName(),
                result.getName()
        );
    }
}
