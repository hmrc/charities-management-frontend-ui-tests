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

import org.openqa.selenium.By
import uk.gov.hmrc.configuration.TestEnvironment
import uk.gov.hmrc.ui.helpers.UserType

object AuthWizardPage extends BasePage {
  val authHost: String = TestEnvironment.url("auth")
  val host: String     = TestEnvironment.url("charities-management-frontend")

  override def pageUrl: String   = s"$authHost/auth-login-stub/gg-sign-in"
  override def pageTitle: String = "Authority Wizard"

  val redirectUrl: By    = By.id("redirectionUrl")
  val affinityGroup: By  = By.id("affinityGroupSelect")
  val enrolmentKey: By   = By.id(s"enrolment[0].name") // Enrolment Key
  val enrolmentId: By    = By.name(s"enrolment[0].taxIdentifier[0].name") // Identifier Name
  val enrolmentValue: By = By.name(s"enrolment[0].taxIdentifier[0].value") // Identifier Value
  val btnSubmit: By      = By.id("submit")

  def fillAuthInputs(affGroup: UserType, enrolVal: String): Unit = {
    driver.findElement(redirectUrl).sendKeys(host)
    driver.findElement(affinityGroup).sendKeys(affGroup.toString)
    driver.findElement(enrolmentKey).sendKeys(affGroup.getEnrolmentKey)
    driver.findElement(enrolmentId).sendKeys(affGroup.getIdentifierName)
    driver.findElement(enrolmentValue).sendKeys(enrolVal)
  }

  def login(affinityGroup: UserType, enrolmentValue: String): Unit = {
    AuthWizardPage.navigateToPage(pageUrl)
    fillAuthInputs(affinityGroup, enrolmentValue)
    click(btnSubmit)
  }
}
