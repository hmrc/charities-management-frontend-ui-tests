/*
 * Copyright 2026 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ui.pages

import com.typesafe.scalalogging.LazyLogging
import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}
import org.openqa.selenium.{By, WebElement}
import org.scalatest.concurrent.Eventually
import org.scalatest.matchers.must.Matchers
import uk.gov.hmrc.selenium.component.PageObject
import uk.gov.hmrc.ui.driver.BrowserDriver
import java.time.Duration

trait BasePage extends PageObject with Eventually with Matchers with LazyLogging with BrowserDriver {

  /** Implicit wait */
  implicit def w: WebDriverWait = new WebDriverWait(driver, Duration.ofSeconds(30))

  /** Locator values */
  object Locators {
    val btnContinue         = "//button[@type='submit']"
    val lnkBack             = "Back"
    val lnkHeader           = ".govuk-header__link.govuk-header__service-name"
    val txtCaption          = By.ByClassName("govuk-caption-l")
    val txtHeader: By       = By.xpath("//h1")
    val hintText            = By.ById("value-hint")
    val paragraphText       = By.ByClassName("govuk-body")
    val errorSummary        = By.ByClassName("govuk-error-summary__body")
    val errorMsg            = By.ById("value-error")
    val pageNotFoundContent = By.ByClassName("govuk-grid-row")
    val serviceName         = By.ByClassName("govuk-service-navigation__text")
    val languageToggle      = By.ByClassName("hmrc-service-navigation-language-select__list")
    val firstCard           = By.xpath("//*[@id=\"main-content\"]/div/div/div/div/ul/li[1]/div")
    val secondCard          = By.xpath("//*[@id=\"main-content\"]/div/div/div/div/ul/li[2]/div")
  }

  def pageUrl: String
  def pageTitle: String

  /** Navigation method(s) */
  def navigateToPage(url: String): Unit = driver.navigate().to(url)

  /** Wait for visibility of an element */
  def waitForVisibilityOfElement(selector: By): WebElement =
    w.until(ExpectedConditions.visibilityOfElementLocated(selector))

  /** Wait for the page to load to ensure the URL is ready to check */
  def waitForUrl(expectedUrl: String): Unit =
    w.until(ExpectedConditions.urlContains(expectedUrl))

  /** Wait to ensure an element is clickable */
  override def click(selector: By): Unit = {
    val element = waitForVisibilityOfElement(selector)
    element.click()
  }

  /** Switch tabs, when we use card two the page will open in a new tab */
  def switchTab(whichTabToBeOn: Int): Unit = {
    val windowHandles = driver.getWindowHandles.toArray
    driver.switchTo.window(windowHandles(whichTabToBeOn).asInstanceOf[String])
  }

  /** Generic methods that all pages will use to ensure correct elements are rendered / included on the page */
  def verifyPageUrl(expectedUrl: String): Unit = {
    waitForUrl(expectedUrl)
    assert(
      driver.getCurrentUrl.contains(expectedUrl),
      s"Page URL mismatch! Expected: $expectedUrl, Actual: ${driver.getCurrentUrl}"
    )
    println("Actual URL is: " + driver.getCurrentUrl)
  }

  def waitForPageTitle(expectedTitle: String): Unit =
    w.until(ExpectedConditions.titleIs(expectedTitle))

  def verifyPageTitle(expectedTitle: String): Unit = {
    waitForPageTitle(expectedTitle)
    assert(
      driver.getTitle == expectedTitle,
      s"Page title mismatch! Expected: $expectedTitle, Actual: ${driver.getTitle}"
    )
    println("Actual page title is: " + driver.getTitle)
  }

  def verifyPageCaption(expectedCaption: String): Unit = {
    waitForVisibilityOfElement(Locators.txtCaption)
    val actualCaption = driver.findElement(Locators.txtCaption).getText
    assert(
      actualCaption == expectedCaption,
      s"Page header mismatch! Expected: $expectedCaption, Actual: $actualCaption"
    )
    println("Actual page caption is: " + driver.findElement(Locators.txtCaption).getText)
  }

  def verifyDynamicPageCaption(expectedCaption: String): Unit = {
    waitForVisibilityOfElement(Locators.txtCaption)
    val actualCaption = driver.findElement(Locators.txtCaption).getText
    assert(
      actualCaption.contains(expectedCaption),
      s"Page header mismatch! Expected: $expectedCaption, Actual: $actualCaption"
    )
    println("Actual page caption is: " + driver.findElement(Locators.txtCaption).getText)
  }

  def verifyPageHeader(expectedHeader: String): Unit = {
    waitForVisibilityOfElement(Locators.txtHeader)
    val actualHeader = driver.findElement(Locators.txtHeader).getText
    assert(
      actualHeader == expectedHeader,
      s"Page header mismatch! Expected: $expectedHeader, Actual: $actualHeader"
    )
    println("Actual page header is: " + driver.findElement(Locators.txtHeader).getText)
  }

  /** Verify service navigation elements (title & language toggle) */
  def verifyServiceName(expectedName: String): Unit = {
    waitForVisibilityOfElement(Locators.serviceName)
    val actualName = driver.findElement(Locators.serviceName).getText
    assert(
      actualName == expectedName,
      s"Service name mismatch! Expected: $expectedName, Actual: $actualName"
    )
  }

  def verifyLanguageToggleIsPresent(): Unit = {
    waitForVisibilityOfElement(Locators.languageToggle)
    val languageToggle = driver.findElement(Locators.languageToggle)
    assert(languageToggle.isDisplayed, "Language toggle isn't present!")
  }

  /** Verify that a hint includes expected message */
  def verifyHintText(expectedText: String): Unit = {
    waitForVisibilityOfElement(Locators.hintText)
    val actualText = driver.findElement(Locators.hintText).getText
    assert(
      actualText == expectedText,
      s"Page hint mismatch! Expected: $expectedText, Actual: $actualText"
    )
    println("Actual page hint is: " + driver.findElement(Locators.hintText).getText)
  }

  /** Verify that a paragraph includes expected message */
  def verifyParagraphText(expectedText: String): Unit = {
    waitForVisibilityOfElement(Locators.paragraphText)
    val actualText = driver.findElement(Locators.paragraphText).getText
    assert(
      actualText == expectedText,
      s"Page paragraph mismatch! Expected: $expectedText, Actual: $actualText"
    )
    println("Actual page paragraph is: " + driver.findElement(Locators.paragraphText).getText)
  }

  /** Verify that the card components are present */
  def verifyFirstCardComponent(expectedText: String): Unit = {
    waitForVisibilityOfElement(Locators.firstCard)
    val actualText = driver.findElement(Locators.firstCard).getText
    assert(
      actualText == expectedText,
      s"First card components mismatch! Expected: $expectedText, Actual: $actualText"
    )
  }

  def verifySecondCardComponent(expectedText: String): Unit = {
    waitForVisibilityOfElement(Locators.secondCard)
    val actualText = driver.findElement(Locators.secondCard).getText
    assert(
      actualText == expectedText,
      s"First card components mismatch! Expected: $expectedText, Actual: $actualText"
    )
  }

  /** Verify clicking the card */
  def clickFirstCardComponent(): Unit =
    click(Locators.firstCard)

  def clickSecondCardComponent(): Unit =
    click(Locators.secondCard)

  /** Helper method for passing one string to verify a list of text instead of repeating for components I.e., a
    * paragraph could have multiple bullet points or our card component returns the text as individual strings
    */
  def createSingleStringFromMany(listOfStrings: String*): String = listOfStrings.mkString("\n")
}
