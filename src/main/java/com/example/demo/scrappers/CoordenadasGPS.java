package com.example.demo.scrappers;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CoordenadasGPS {
    // Singleton
    private static CoordenadasGPS instance;

    public static CoordenadasGPS getInstance() {
        if (instance == null)
            instance = new CoordenadasGPS();
        return instance;
    }

    // Clase
    WebDriver driver;
    Actions actions;
    WebDriverWait waiting;
    WebDriverWait shortWaiting;

    private CoordenadasGPS() {
        // Creamos una instancia de Chrome
        this.driver = new ChromeDriver();

        // Creamos actions para movernos por la pagina
        this.actions = new Actions(driver);

        // Creamos un waiting para esperar que ocurran eventos
        this.waiting = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.shortWaiting = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Vamos a la pagina
        this.driver.get("https://www.coordenadas-gps.com/");

        // Hacemos scroll al mapa
        WebElement elementToFind = driver.findElement(By.id("longitude_degres"));
        actions.moveToElement(elementToFind);
        actions.perform();

        // Esperamos a que cargue geocodedAddress
        waiting.until(ExpectedConditions.presenceOfElementLocated(By.id("geocodedAddress")));
    }

    /**
     * Devuelve la dirección, código postal, ciudad, país
     * 
     * @param latitud
     * @param longitud
     * @return Dirección de las coordenadas
     */
    public String direccionDeCoordenadas(double latitud, double longitud) {
        // Guardamos lo que vale actualmente para esperar hasta que cambie
        WebElement address_txtf;
        address_txtf = driver.findElement(By.id("address"));
        String initialAddress = address_txtf.getAttribute("value");

        // Localizamos los textfield de longitud y latitud
        WebElement latitude_txtf = driver.findElement(By.id("latitude"));
        WebElement longitude_txtf = driver.findElement(By.id("longitude"));

        // Escribimos la longitud y latitud en los textfield
        latitude_txtf.clear();
        latitude_txtf.sendKeys(String.valueOf(latitud));

        longitude_txtf.clear();
        longitude_txtf.sendKeys(String.valueOf(longitud));

        // Localizamos el boton de obtener direccion
        WebElement getaddress_btn = driver.findElement(By.xpath(".//button[contains(@onclick, 'codeLatLng(1)')]"));

        // Hacemos click en el boton
        getaddress_btn.click();

        // Esperamos a que cargue el resultado

        try {
            shortWaiting.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    return !d.findElement(By.id("address")).getAttribute("value").equals(initialAddress);
                }
            });
            // shortWaiting
            // .until(ExpectedConditions.invisibilityOfElementWithText(By.id("geocodedAddress"),
            // initialAddress));
        } catch (TimeoutException e) {
            // Si tarda mas de 5 segundos asumimos que ha cargado bien
        }

        // Recuperamos el span con la info
        address_txtf = driver.findElement(By.id("address"));

        // Recuperamos su valor
        String addressText = address_txtf.getAttribute("value");

        return addressText;
    }

    public String[] longlatcp(String direccionBuscar) {
        try {
            WebElement address_txtf, latitude_txtf, longitude_txtf;
            ;

            // Localizamos los textfield direccion
            address_txtf = driver.findElement(By.id("address"));

            // Escribimos la direccion en el textfield
            address_txtf.clear();
            address_txtf.sendKeys(String.valueOf(direccionBuscar));

            // Localizamos el boton de obtener direccion GPS
            WebElement getaddress_btn = driver.findElement(By.xpath(".//button[contains(@onclick, 'codeAddress()')]"));

            // Hacemos click en el boton
            getaddress_btn.click();

            // Esperamos a que cargue el resultado
            try {
                shortWaiting.until(new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver d) {
                        return !d.findElement(By.id("address")).getAttribute("value").equals(direccionBuscar);
                    }
                });
            } catch (TimeoutException e) {
            }

            // Recuperamos el span con la info
            address_txtf = driver.findElement(By.id("address"));

            // Recuperamos su valor
            String addressText = address_txtf.getAttribute("value");

            // Recuperamos el span con la latitud
            latitude_txtf = driver.findElement(By.id("latitude"));

            // Recuperamos su valor
            String latitud = latitude_txtf.getAttribute("value");

            // Recuperamos el span con la longitud
            longitude_txtf = driver.findElement(By.id("longitude"));

            // Recuperamos su valor
            String longitud = longitude_txtf.getAttribute("value");

            return new String[] { latitud, longitud, addressText };
        } catch (UnhandledAlertException e) {
            return new String[3];
        }

    }
}