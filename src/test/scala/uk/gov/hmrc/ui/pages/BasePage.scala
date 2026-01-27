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
    val btnContinue                   = "//button[@type='submit']"
    val lnkBack                       = "Back"
    val lnkHeader                     = ".govuk-header__link.govuk-header__service-name"
    val rdoYes                        = "#value_0"
    val rdoNo                         = "#value_1"
    val txtFileName                   = ".govuk-body"
    val txtCaption                    = By.ByClassName("govuk-caption-l")
    val txtHeader: By                 = By.xpath("//h1")
    val txtAddressPostCode            = By.ById("postcode")
    val inputYourClaimReferenceNumber = By.ByClassName("govuk-input")
    val inputYourUserId: By           = By.xpath("//input[@name='authorityId']")
    val hintText                      = By.ById("value-hint")
    val paragraphText                 = By.ByClassName("govuk-body")
    val errorSummary                  = By.ByClassName("govuk-error-summary__body")
    val errorMsg                      = By.ById("value-error")
    val listText                      = By.ByClassName("govuk-list")
    val legendText                    = By.ByClassName("govuk-fieldset__legend")
    val checkYouAnswersSummaryList    = By.ByClassName("govuk-summary-list__row")
    val pageNotFoundContent           = By.ByClassName("govuk-grid-row")
  }

  def pageUrl: String
  def pageTitle: String

  /** Wait for visibility of an element */
  def waitForVisibilityOfElement(selector: By): WebElement =
    w.until(ExpectedConditions.visibilityOfElementLocated(selector))

  /** Wait for the page to load to ensure the URL is ready to check */
  def waitForUrl(expectedUrl: String): Unit =
    w.until(ExpectedConditions.urlContains(expectedUrl))

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

  def verifyPageHeader(expectedHeader: String): Unit = {
    waitForVisibilityOfElement(Locators.txtHeader)
    val actualHeader = driver.findElement(Locators.txtHeader).getText
    assert(
      actualHeader == expectedHeader,
      s"Page header mismatch! Expected: $expectedHeader, Actual: $actualHeader"
    )
    println("Actual page header is: " + driver.findElement(Locators.txtHeader).getText)
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
}
