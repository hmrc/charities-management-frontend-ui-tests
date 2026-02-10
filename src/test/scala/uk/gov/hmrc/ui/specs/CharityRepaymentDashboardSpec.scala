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

package uk.gov.hmrc.ui.specs

import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, GivenWhenThen}
import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.verbs.ShouldVerb
import uk.gov.hmrc.selenium.webdriver.{Browser, ScreenshotOnFailure}
import uk.gov.hmrc.ui.helpers.UserType
import uk.gov.hmrc.ui.pages.{AuthWizardPage, CharityRepaymentDashboardPage}

class CharityRepaymentDashboardSpec
    extends AnyFeatureSpec
    with BaseSpec
    with GivenWhenThen
    with ShouldVerb
    with BeforeAndAfterAll
    with BeforeAndAfterEach
    with Browser
    with ScreenshotOnFailure {
  Feature("Charities - Charity Repayment Dashboard - Checking content and behavior") {
    Scenario("User navigates to the 'Charity Repayment Dashboard'") {
      Given("The user logs in through the Authority Wizard Page")
      AuthWizardPage.login(UserType.Organisation, "abc")
      Then("User navigates to the 'Charity Repayment Dashboard'")
      CharityRepaymentDashboardPage.validatePageContent()
      Then("The user clicks on 'Make a repayment claim'")
      CharityRepaymentDashboardPage.clickFirstCardAndValidateURL()
      Then("The user goes back to the dashboard")
      CharityRepaymentDashboardPage.goBackToDashboardAndValidateURL()
      Then("The user clicks on 'Make a repayment claim with software'")
      CharityRepaymentDashboardPage.clickSecondCardAndValidateURL()
      Then("The user goes back to the dashboard")
      CharityRepaymentDashboardPage.goBackToDashboardAndValidateURL()
    }
  }
}
