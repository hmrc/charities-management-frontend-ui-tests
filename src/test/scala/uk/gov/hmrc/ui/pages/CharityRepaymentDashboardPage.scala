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

import uk.gov.hmrc.configuration.TestEnvironment

object CharityRepaymentDashboardPage extends BasePage {
  val charityManagementHost: String = TestEnvironment.url("charities-management-frontend")
  val charityClaimsHost: String     = TestEnvironment.url("charities-claims-frontend")

  override def pageUrl: String   = "charity-repayment-dashboard"
  override def pageTitle: String = "Charity repayment dashboard - Charities - GOV.UK"

  def pageHeader: String    = "Charity repayment dashboard"
  def serviceName: String   = "Charities Management"
  def pageCaption: String   = "HMRC Charities reference:"
  def pageContent: String   = "Before you make a claim, complete and save any schedule spreadsheets."
  def cardOneHeader: String = "Make a repayment claim"
  def cardOneText: String   = "Use this to make a repayment claim."
  def cardTwoHeader: String = "Make a repayment claim using software"
  def cardTwoText: String   = "Use this to make a charity repayment claim using software, like a database."
  def hiddenText: String    = "(opens in new tab)"

  /** These values are for verifying that when we click the card(s) the user has navigated to the correct pages */
  def repaymentClaimURL: String         = s"$charityClaimsHost/make-a-charity-repayment-claim"
  def repaymentClaimSoftwareURL: String =
    "https://www.gov.uk/government/publications/charities-online-commercial-software-suppliers"

  def validatePageContent(): Unit = {
    verifyPageUrl(pageUrl)
    verifyPageTitle(pageTitle)
    verifyPageHeader(pageHeader)
    verifyServiceName(serviceName)
    verifyLanguageToggleIsPresent()
    verifyDynamicPageCaption(pageCaption)
    verifyParagraphText(pageContent)
    verifyFirstCardComponent(createSingleStringFromMany(cardOneHeader, cardOneText))
    verifySecondCardComponent(createSingleStringFromMany(hiddenText, cardTwoHeader, cardTwoText))
  }

  def goBackToDashboardAndValidateURL(): Unit = {
    navigateToPage(charityManagementHost + "/" + pageUrl)
    verifyPageUrl(pageUrl)
  }

  def clickFirstCardAndValidateURL(): Unit = {
    clickFirstCardComponent()
    verifyPageUrl(repaymentClaimURL)
  }

  def clickSecondCardAndValidateURL(): Unit = {
    clickSecondCardComponent()
    switchTab(1)
    verifyPageUrl(repaymentClaimSoftwareURL)
  }
}
