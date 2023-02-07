package com.vtg.auto.reusablecomponents;



import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Browser {
	public Browser() {
		// Do-nothing..Do not allow to initialize this class from outside
	}

	public static String browserName = GlobalVariables.configData.get("Browser");
	public static String gridHost = GlobalVariables.configData.get("GridHost");
	public static String os = System.getProperty("os.name");

	public static Browser instance = new Browser();
	public static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();
	// public CapabilityFactory capabilityFactory = new CapabilityFactory();
	// public String browserName;

	public static Browser getInstance() {
		return instance;
	}

	// thread local driver object for web-driver
	public static ThreadLocal<RemoteWebDriver> threadDriver = new ThreadLocal<RemoteWebDriver>() {

		@Override
		public RemoteWebDriver initialValue() {

			if (GlobalVariables.isGridMode) {
				switch (browserName) {
				case "chrome":
					try {
						threadDriver.set(new RemoteWebDriver(new URL(gridHost + "/wd/hub"),
								CapabilityFactory.getCapabilities(BrowserType.chrome)));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					break;
				case "firefox":
					try {
						threadDriver.set(new RemoteWebDriver(new URL(gridHost + "/wd/hub"),
								CapabilityFactory.getCapabilities(BrowserType.firefox)));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					break;
				case "Edge":
					try {
						threadDriver.set(new RemoteWebDriver(new URL(gridHost + "/wd/hub"),
								CapabilityFactory.getCapabilities(BrowserType.Edge)));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					break;
				case "ie":
					try {
						threadDriver.set(new RemoteWebDriver(new URL(gridHost + "/wd/hub"),
								CapabilityFactory.getCapabilities(BrowserType.ie)));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					break;
				case "HeadlessChrome":
					try {
						threadDriver.set(new RemoteWebDriver(new URL(gridHost + "/wd/hub"),
								CapabilityFactory.getCapabilities(BrowserType.HeadlessChrome)));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					break;
				case "HeadlessFirefox":
					try {
						threadDriver.set(new RemoteWebDriver(new URL(gridHost + "/wd/hub"),
								CapabilityFactory.getCapabilities(BrowserType.HeadlessFirefox)));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					break;
				case "HeadlessEdge":
					try {
						threadDriver.set(new RemoteWebDriver(new URL(gridHost + "/wd/hub"),
								CapabilityFactory.getCapabilities(BrowserType.HeadlessEdge)));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					break;

				}
				return threadDriver.get();
			} else if (System.getProperty("os.name").contains("Linux")) {
				switch (browserName) {
				case "HeadlessChrome":
					System.out.println("LinuxTesting");
					ChromepathLinux();
					threadDriver.set(new ChromeDriver(OptionsManager.getHeadlessChromeOptions()));
					break;

				}
				return threadDriver.get();
			}

			else {
				if (GlobalVariables.configData.get("WebDriverManager").equalsIgnoreCase("Yes")) {
					switch (browserName) {
					case "chrome":
						WebDriverManager.chromedriver().setup();
						threadDriver.set(new ChromeDriver(OptionsManager.getChromeOptions()));
						break;

					case "firefox":
						WebDriverManager.firefoxdriver().setup();
						threadDriver.set(new FirefoxDriver(OptionsManager.getFirefoxOptions()));
						break;
					case "Edge":
						WebDriverManager.edgedriver().setup();
						threadDriver.set(new EdgeDriver(OptionsManager.getEdgeOptions()));
						break;
					case "ie":
						WebDriverManager.iedriver().setup();
						threadDriver.set(new InternetExplorerDriver(OptionsManager.getIEOptions()));
						break;
					case "HeadlessChrome":
						WebDriverManager.chromedriver().setup();
						threadDriver.set(new ChromeDriver(OptionsManager.getHeadlessChromeOptions()));
						break;

					case "HeadlessFirefox":
						WebDriverManager.firefoxdriver().setup();
						threadDriver.set(new FirefoxDriver(OptionsManager.getHeadlessFirefoxOptions()));
						break;
					case "HeadlessEdge":
						WebDriverManager.edgedriver().setup();
						threadDriver.set(new EdgeDriver(OptionsManager.getHeadlessEdgeOptions()));
						break;
					case "Safari":
						WebDriverManager.safaridriver().setup();
						threadDriver.set(new SafariDriver(OptionsManager.getSafariOptions()));
						break;

					}

					return threadDriver.get();
				} else {

					switch (browserName) {
					case "chrome":
						System.setProperty("webdriver.chrome.driver",
								GlobalVariables.configData.get("ChromeDriverPath"));
						threadDriver.set(new ChromeDriver(OptionsManager.getChromeOptions()));
						break;

					case "firefox":
						System.setProperty("webdriver.gecko.driver", GlobalVariables.configData.get("GeckoDriverPath"));
						threadDriver.set(new FirefoxDriver(OptionsManager.getFirefoxOptions()));
						break;
					case "Edge":
						System.setProperty("webdriver.edge.driver", GlobalVariables.configData.get("EdgeDriverPath"));
						threadDriver.set(new EdgeDriver(OptionsManager.getEdgeOptions()));
						break;
					case "ie":
						System.setProperty("webdriver.ie.driver", GlobalVariables.configData.get("IEDriverPath"));
						threadDriver.set(new InternetExplorerDriver(OptionsManager.getIEOptions()));
						break;
					case "HeadlessChrome":
						// WebDriverManager.chromedriver().setup();
						System.setProperty("webdriver.chrome.driver",
								GlobalVariables.configData.get("ChromeDriverPath"));
						threadDriver.set(new ChromeDriver(OptionsManager.getHeadlessChromeOptions()));
						break;
					case "HeadlessFirefox":

						// WebDriverManager.firefoxdriver().setup();
						System.setProperty("webdriver.gecko.driver", GlobalVariables.configData.get("GeckoDriverPath"));

						threadDriver.set(new FirefoxDriver(OptionsManager.getHeadlessFirefoxOptions()));
						break;
					case "HeadlessEdge":
						System.setProperty("webdriver.edge.driver", GlobalVariables.configData.get("EdgeDriverPath"));
						threadDriver.set(new EdgeDriver(OptionsManager.getHeadlessEdgeOptions()));
						break;
					case "Safari":
						threadDriver.set(new SafariDriver(OptionsManager.getSafariOptions()));
						break;

					}

					return threadDriver.get();
				}
			}

		}

	};

	public WebDriver getDriver() // call this method to get the driver object and launch the browser
	{
		return threadDriver.get();
	}

	public static void setWebDriver(RemoteWebDriver driver) {

		threadDriver.set(driver);
	}

	public void removeDriver() // Quits the driver and closes the browser
	{
		threadDriver.get().quit();
		threadDriver.remove();
	}

	public static void ChromepathLinux() {
		String[] cmd = { "/bin/sh", "-c", "cat /etc/*-release" };
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String oSName = "";
			while ((oSName = bri.readLine()) != null) {
				if (oSName.contains("Debian GNU/Linux")) {
					String chromePath = System.getenv("CHROME_HOME");
					System.setProperty("webdriver.chrome.driver", chromePath + "/chromedriver");
					break;
				}

				else if (oSName.contains("Red Hat Enterprise Linux")) {
					System.setProperty("webdriver.chrome.driver", "./Utilities/chromedriver");
					break;
				} else {
					System.out.println("Please Make sure you OS Name as Debian GNU/Linux Or Red Hat Enterprise Linux");
				}

			}
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public static void ChromepathLinux1() {
		String[] cmd = { "/bin/sh", "-c", "cat /etc/*-release" };
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String oSName = "";
			while ((oSName = bri.readLine()) != null) {

				if (oSName.contains("Debian GNU/Linux")) {
					switch (browserName) {
					case "HeadlessChrome":
						String chromePath = System.getenv("CHROME_HOME");
						System.setProperty("webdriver.chrome.driver", chromePath + "/chromedriver");
						break;
					case "HeadlessFirefox":
						String firefoxPath = System.getenv("FIREFOX_HOME");
						System.setProperty("webdriver.gecko.driver", firefoxPath + "/geckodriver");
						break;
					case "HeadlessEdge":
						String edgePath = System.getenv("EDGE_HOME");
						System.setProperty("webdriver.edge.driver", edgePath + "/msedgedriver");

						break;
					}

				}

				else if (oSName.contains("Red Hat Enterprise Linux")) {

					switch (browserName) {
					case "HeadlessChrome":

						System.setProperty("webdriver.chrome.driver", "./Utilities/chromedriver");
						break;
					case "HeadlessFirefox":
						System.setProperty("webdriver.chrome.driver", "./Utilities/geckodriver");
						break;

					case "HeadlessEdge":
						System.setProperty("webdriver.chrome.driver", "./Utilities/msedgedriver");
						break;
					}

				}

			}
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
