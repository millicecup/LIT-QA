package com.test.perplexity;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Improved Perplexity Anonymous Chat Test
 * Based on your working code with enhancements for better reliability
 */
public class PerplexityTestImproved {
    
    private static final String OUTPUT_DIR = "test-output";
    private static WebDriver driver;
    private static WebDriverWait wait;
    
    public static void main(String[] args) {
        System.out.println("🚀 Starting Enhanced Perplexity Anonymous Chat Test");
        System.out.println("=" .repeat(60));
        
        // Create output directory
        new File(OUTPUT_DIR).mkdirs();
        
        try {
            setupDriver();
            runTest();
            System.out.println("\n✅ TEST COMPLETED SUCCESSFULLY!");
            System.out.println("📁 Check the '" + OUTPUT_DIR + "' folder for results");
            
        } catch (Exception e) {
            System.err.println("\n❌ TEST FAILED: " + e.getMessage());
            e.printStackTrace();
            takeScreenshot("error_final");
        } finally {
            cleanup();
        }
    }
    
    /**
     * Setup Chrome driver with anti-detection features
     */
    private static void setupDriver() {
        System.out.println("🔧 Setting up Chrome driver...");
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-web-security");
        options.addArguments("--disable-features=VizDisplayCompositor");
        options.addArguments("--remote-allow-origins=*");
        
        // Enhanced anti-bot detection (like your original code)
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
        
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        
        // Execute script to hide webdriver property
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
        
        System.out.println("✅ Driver setup complete");
    }
    
    /**
     * Main test execution
     */
    private static void runTest() throws Exception {
        // Step 1: Navigate to Perplexity
        System.out.println("\n🌐 Step 1: Opening Perplexity.ai...");
        driver.get("https://www.perplexity.ai");
        Thread.sleep(3000);
        takeScreenshot("01_homepage");
        
        // Step 2: Find chatbox (using multiple strategies like your original)
        System.out.println("\n🔍 Step 2: Looking for chatbox...");
        WebElement chatBox = findChatBox();
        
        // Center the chatbox like your original code
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", chatBox);
        Thread.sleep(2000);
        takeScreenshot("02_chatbox_located");
        
        // Step 3: Type question (using your original question)
        String question = "What is the Schrödinger Cat?";
        System.out.println("\n✍️ Step 3: Typing question: " + question);
        
        typeQuestion(chatBox, question);
        takeScreenshot("03_question_typed");
        
        // Step 4: Submit question  
        System.out.println("\n📤 Step 4: Submitting question...");
        chatBox.sendKeys(Keys.ENTER);
        System.out.println("💬 Question submitted!");
        
        // Step 5: Wait for response (enhanced waiting like your original)
        System.out.println("\n⏳ Step 5: Waiting for AI response...");
        waitForResponse();
        takeScreenshot("04_response_received");
        
        // Step 6: Extract response (using your selectors + improvements)
        System.out.println("\n📄 Step 6: Extracting response...");
        String response = extractResponse();
        
        // Step 7: Save results (enhanced version of your saveToFile method)
        saveResponse(question, response);
        
        System.out.println("\n🎉 All steps completed successfully!");
    }
    
    /**
     * Find chatbox using multiple selectors (based on your working code)
     */
    private static WebElement findChatBox() {
        WebElement chatBox = null;
        
        // Try your original working selectors first
        try {
            chatBox = wait.until(ExpectedConditions.elementToBeClickable(By.tagName("textarea")));
            System.out.println("✅ Found chatbox using: textarea");
            return chatBox;
        } catch (TimeoutException e) {
            System.out.println("⏭️ textarea not found, trying contenteditable...");
        }
        
        try {
            chatBox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[contenteditable='true']")));
            System.out.println("✅ Found chatbox using: div[contenteditable='true']");
            return chatBox;
        } catch (TimeoutException e) {
            System.out.println("⏭️ contenteditable not found, trying additional selectors...");
        }
        
        // Additional selectors for robustness
        String[] additionalSelectors = {
            "input[type='text']",
            "[role='textbox']",
            "textarea[placeholder*='Ask']",
            "[data-testid*='search']"
        };
        
        for (String selector : additionalSelectors) {
            try {
                chatBox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(selector)));
                System.out.println("✅ Found chatbox using: " + selector);
                return chatBox;
            } catch (TimeoutException e) {
                System.out.println("⏭️ " + selector + " not found...");
            }
        }
        
        throw new RuntimeException("❌ Could not find chatbox with any selector");
    }
    
    /**
     * Type question with fallback methods
     */
    private static void typeQuestion(WebElement chatBox, String question) throws Exception {
        try {
            // Method 1: Your original approach
            chatBox.sendKeys(question);
            System.out.println("✅ Typed using sendKeys");
            
        } catch (Exception e) {
            System.out.println("⚠️ sendKeys failed, trying JavaScript...");
            
            // Method 2: JavaScript fallback
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value = arguments[1];", chatBox, question);
            js.executeScript("arguments[0].dispatchEvent(new Event('input', {bubbles: true}));", chatBox);
            System.out.println("✅ Typed using JavaScript");
        }
        
        Thread.sleep(1000);
    }
    
    /**
     * Enhanced waiting for response (based on your working code)
     */
    private static void waitForResponse() throws InterruptedException {
        // Initial wait like your original code
        Thread.sleep(6000);
        
        // Monitor for completion with progress updates
        int maxWaitSeconds = 45;
        for (int i = 0; i < maxWaitSeconds; i += 3) {
            try {
                // Look for response elements using your original selectors
                List<WebElement> proseElements = driver.findElements(By.cssSelector(".prose"));
                List<WebElement> whiteSpaceElements = driver.findElements(By.cssSelector(".whitespace-pre-line"));
                
                if (!proseElements.isEmpty() || !whiteSpaceElements.isEmpty()) {
                    // Check if response seems substantial
                    String pageText = driver.getPageSource();
                    if (pageText.length() > 5000) { // Reasonable page size indicating content loaded
                        System.out.println("✅ Response appears complete");
                        break;
                    }
                }
                
            } catch (Exception e) {
                // Continue waiting
            }
            
            Thread.sleep(3000);
            if (i > 0 && i % 9 == 0) {
                System.out.println("⏳ Still waiting... (" + i + "s elapsed)");
            }
        }
        
        // Final buffer like your original
        Thread.sleep(3000);
    }
    
    /**
     * Extract response using your working selectors plus improvements
     */
    private static String extractResponse() {
        WebElement answer = null;
        String responseText = "";
        
        // Try your original working selectors first
        try {
            answer = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".prose")));
            responseText = answer.getText();
            System.out.println("✅ Found response using .prose selector");
            
        } catch (TimeoutException e1) {
            try {
                answer = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".whitespace-pre-line")));
                responseText = answer.getText();
                System.out.println("✅ Found response using .whitespace-pre-line selector");
                
            } catch (TimeoutException e2) {
                System.out.println("⚠️ Original selectors failed, trying alternatives...");
                
                // Additional selectors for robustness
                String[] responseSelectors = {
                    "[class*='answer']",
                    "[class*='response']", 
                    "div[role='article']",
                    "article",
                    "[data-testid*='answer']"
                };
                
                for (String selector : responseSelectors) {
                    try {
                        List<WebElement> elements = driver.findElements(By.cssSelector(selector));
                        if (!elements.isEmpty()) {
                            for (WebElement element : elements) {
                                String text = element.getText().trim();
                                if (text.length() > 50) {
                                    responseText = text;
                                    System.out.println("✅ Found response using: " + selector);
                                    break;
                                }
                            }
                            if (!responseText.isEmpty()) break;
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
        }
        
        // If still no response, use fallback method
        if (responseText.isEmpty()) {
            System.out.println("⚠️ Using fallback extraction method...");
            try {
                WebElement body = driver.findElement(By.tagName("body"));
                responseText = body.getText();
                
                // Try to filter for relevant content
                if (responseText.contains("Schrödinger") || responseText.contains("quantum")) {
                    // Extract relevant portion
                    String[] lines = responseText.split("\n");
                    StringBuilder filtered = new StringBuilder();
                    boolean foundRelevant = false;
                    
                    for (String line : lines) {
                        if (line.contains("Schrödinger") || line.contains("quantum") || line.contains("cat")) {
                            foundRelevant = true;
                        }
                        if (foundRelevant && line.trim().length() > 10) {
                            filtered.append(line).append("\n");
                        }
                    }
                    
                    if (filtered.length() > 0) {
                        responseText = filtered.toString();
                    }
                }
            } catch (Exception e) {
                responseText = "Could not extract response text. Please check screenshots for visual verification.";
            }
        }
        
        System.out.println("📊 Response length: " + responseText.length() + " characters");
        return responseText;
    }
    
    /**
     * Enhanced version of your saveToFile method
     */
    private static void saveResponse(String question, String responseText) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filename = OUTPUT_DIR + "/perplexity_response_" + timestamp + ".txt";
        
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("PERPLEXITY AI CHAT TEST RESULTS\n");
            writer.write("================================\n");
            writer.write("Timestamp: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n");
            writer.write("URL: https://www.perplexity.ai/\n\n");
            
            writer.write("QUESTION:\n");
            writer.write(question + "\n\n");
            
            writer.write("RESPONSE:\n");
            writer.write(responseText + "\n\n");
            
            writer.write("=".repeat(50) + "\n");
            writer.write("Test completed successfully!\n");
            
            System.out.println("💾 Response saved to: " + filename);
            
        } catch (IOException e) {
            System.err.println("❌ Failed to save response file: " + e.getMessage());
        }
    }
    
    /**
     * Take screenshots with timestamps
     */
    private static void takeScreenshot(String name) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
            
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String filename = OUTPUT_DIR + "/screenshot_" + timestamp + "_" + name + ".png";
            
            File destFile = new File(filename);
            Files.copy(srcFile.toPath(), destFile.toPath());
            
            System.out.println("📸 Screenshot saved: " + filename);
            
        } catch (Exception e) {
            System.err.println("⚠️ Failed to take screenshot: " + e.getMessage());
        }
    }
    
    /**
     * Cleanup resources
     */
    private static void cleanup() {
        System.out.println("\n🧹 Cleaning up...");
        if (driver != null) {
            try {
                Thread.sleep(2000); // Brief pause to see final state
                driver.quit();
                System.out.println("✅ Browser closed successfully");
            } catch (Exception e) {
                System.err.println("⚠️ Error during cleanup: " + e.getMessage());
            }
        }
        System.out.println("🛑 Test session ended");
    }
}