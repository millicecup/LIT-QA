package com.test.perplexity;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Automated test for Perplexity Anonymous Chat Functionality
 * Dependencies: Selenium WebDriver
 * Maven: 
 * <dependency>
 *     <groupId>org.seleniumhq.selenium</groupId>
 *     <artifactId>selenium-java</artifactId>
 *     <version>4.15.0</version>
 * </dependency>
 */
public class PerplexityChatTest {
    
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String OUTPUT_DIR = "perplexity_test_results";
    private static final String PERPLEXITY_URL = "https://www.perplexity.ai/";
    private static final String DEFAULT_QUESTION = "What is the Schrodinger Cat?";
    
    public static void main(String[] args) {
        PerplexityChatTest test = new PerplexityChatTest();
        boolean success = test.runTest();
        System.exit(success ? 0 : 1);
    }
    
    /**
     * Run the complete test
     */
    public boolean runTest() {
        printHeader("PERPLEXITY ANONYMOUS CHAT AUTOMATION TEST");
        
        try {
            setup();
            openPerplexity();
            WebElement chatbox = findAndFocusChatbox();
            typeQuestion(chatbox, DEFAULT_QUESTION);
            waitForResponse();
            String response = extractResponse();
            
            printHeader("TEST COMPLETED SUCCESSFULLY");
            System.out.println("All results saved in: " + OUTPUT_DIR + "/");
            
            return true;
            
        } catch (Exception e) {
            System.err.println("\n❌ TEST FAILED: " + e.getMessage());
            e.printStackTrace();
            
            // Take error screenshot
            try {
                takeScreenshot("error_screenshot.png");
            } catch (Exception ignored) {
            }
            
            return false;
            
        } finally {
            cleanup();
        }
    }
    
    /**
     * Initialize the browser and create output directory
     */
    private void setup() throws IOException {
        System.out.println("Setting up test environment...");
        
        // Create output directory if it doesn't exist
        Path outputPath = Paths.get(OUTPUT_DIR);
        if (!Files.exists(outputPath)) {
            Files.createDirectories(outputPath);
        }
        
        // Set up Chrome options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--remote-allow-origins=*");
        
        // Initialize ChromeDriver
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        
        System.out.println("Browser initialized successfully");
    }
    
    /**
     * Open Perplexity website
     */
    private void openPerplexity() throws InterruptedException {
        System.out.println("\n1. Opening " + PERPLEXITY_URL + "...");
        driver.get(PERPLEXITY_URL);
        Thread.sleep(3000); // Wait for page to load
        
        // Take screenshot of homepage
        takeScreenshot("01_homepage.png");
        System.out.println("   Screenshot saved: 01_homepage.png");
    }
    
    /**
     * Find the chatbox and center/focus on it
     */
    private WebElement findAndFocusChatbox() throws InterruptedException {
        System.out.println("\n2. Locating chatbox...");
        
        // Try multiple possible selectors for the input field
        String[] selectors = {
            "textarea[placeholder*='Ask']",
            "textarea",
            "input[type='text']",
            "[contenteditable='true']"
        };
        
        WebElement chatbox = null;
        for (String selector : selectors) {
            try {
                chatbox = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector(selector)
                ));
                System.out.println("   Found chatbox using selector: " + selector);
                break;
            } catch (Exception e) {
                // Try next selector
                continue;
            }
        }
        
        if (chatbox == null) {
            throw new RuntimeException("Could not find chatbox with any selector");
        }
        
        // Scroll to chatbox and center it
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
            "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", 
            chatbox
        );
        Thread.sleep(1000);
        
        // Take screenshot of focused chatbox
        takeScreenshot("02_chatbox_focused.png");
        System.out.println("   Screenshot saved: 02_chatbox_focused.png");
        
        return chatbox;
    }
    
    /**
     * Type question into chatbox
     */
    private void typeQuestion(WebElement chatbox, String question) throws InterruptedException {
        System.out.println("\n3. Typing question: '" + question + "'");
        
        // Click on chatbox to focus
        chatbox.click();
        Thread.sleep(500);
        
        // Type the question
        chatbox.sendKeys(question);
        Thread.sleep(1000);
        
        // Take screenshot after typing
        takeScreenshot("03_question_typed.png");
        System.out.println("   Screenshot saved: 03_question_typed.png");
        
        // Submit the question (Enter key)
        chatbox.sendKeys(Keys.RETURN);
        System.out.println("   Question submitted");
    }
    
    /**
     * Wait for and capture the response
     */
    private void waitForResponse() throws InterruptedException {
        System.out.println("\n4. Waiting for response...");
        
        // Initial wait for response to start
        Thread.sleep(5000);
        
        // Wait for streaming to complete
        int maxWaitSeconds = 60;
        long startTime = System.currentTimeMillis();
        
        System.out.println("   Monitoring response generation...");
        while ((System.currentTimeMillis() - startTime) / 1000 < maxWaitSeconds) {
            Thread.sleep(2000);
            
            // Take periodic screenshots during response generation
            long elapsed = (System.currentTimeMillis() - startTime) / 1000;
            if (elapsed % 10 == 0 && elapsed > 0) {
                String filename = String.format("04_response_progress_%ds.png", elapsed);
                takeScreenshot(filename);
                System.out.println("   Progress screenshot saved: " + filename);
            }
            
            // Check if response seems complete
            try {
                String pageSource = driver.getPageSource().toLowerCase();
                if (pageSource.contains("sources") || pageSource.contains("related")) {
                    System.out.println("   Response appears complete");
                    break;
                }
            } catch (Exception e) {
                // Continue waiting
            }
        }
        
        // Final wait to ensure everything is loaded
        Thread.sleep(3000);
        
        // Take final screenshot
        takeScreenshot("05_response_complete.png");
        System.out.println("   Final screenshot saved: 05_response_complete.png");
    }
    
    /**
     * Extract and save the response text
     */
    private String extractResponse() throws IOException {
        System.out.println("\n5. Extracting response text...");
        
        // Try to find response elements
        String[] responseSelectors = {
            "[class*='answer']",
            "[class*='response']",
            "[class*='message']",
            "div[role='article']",
            ".prose"
        };
        
        StringBuilder responseText = new StringBuilder();
        for (String selector : responseSelectors) {
            try {
                List<WebElement> elements = driver.findElements(By.cssSelector(selector));
                if (!elements.isEmpty()) {
                    for (WebElement element : elements) {
                        String text = element.getText();
                        if (text != null && !text.isEmpty()) {
                            responseText.append(text).append("\n\n");
                        }
                    }
                    if (responseText.length() > 0) {
                        System.out.println("   Found response using selector: " + selector);
                        break;
                    }
                }
            } catch (Exception e) {
                // Try next selector
                continue;
            }
        }
        
        // If no specific selector worked, get all visible text
        if (responseText.length() == 0) {
            System.out.println("   Using fallback method to extract text");
            WebElement body = driver.findElement(By.tagName("body"));
            responseText.append(body.getText());
        }
        
        // Save to file
        String timestamp = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
        );
        String filename = String.format("response_%s.txt", timestamp);
        String filepath = OUTPUT_DIR + File.separator + filename;
        
        try (FileWriter writer = new FileWriter(filepath)) {
            writer.write("Perplexity Chat Response\n");
            writer.write("=".repeat(50) + "\n");
            writer.write("Timestamp: " + LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            ) + "\n");
            writer.write("Question: " + DEFAULT_QUESTION + "\n");
            writer.write("=".repeat(50) + "\n\n");
            writer.write(responseText.toString());
        }
        
        System.out.println("   Response saved to: " + filepath);
        System.out.println("   Response length: " + responseText.length() + " characters");
        
        return responseText.toString();
    }
    
    /**
     * Take a screenshot and save it to output directory
     */
    private void takeScreenshot(String filename) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
            File destFile = new File(OUTPUT_DIR + File.separator + filename);
            Files.copy(srcFile.toPath(), destFile.toPath());
        } catch (Exception e) {
            System.err.println("   Error taking screenshot: " + e.getMessage());
        }
    }
    
    /**
     * Close browser and cleanup
     */
    private void cleanup() {
        System.out.println("\n6. Cleaning up...");
        if (driver != null) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            driver.quit();
            System.out.println("   Browser closed");
        }
    }
    
    /**
     * Print formatted header
     */
    private void printHeader(String title) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println(title);
        System.out.println("=".repeat(60));
    }
}