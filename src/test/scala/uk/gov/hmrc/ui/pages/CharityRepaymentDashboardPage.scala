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

object CharityRepaymentDashboardPage extends BasePage {

  val charityManagementHost: String = TestEnvironment.url("charities-management-frontend")
  val charityClaimsHost: String     = TestEnvironment.url("charities-claims-frontend")

  override def pageUrl: String   = "manage-charity-repayment-claim"
  override def pageTitle: String = "Manage charity repayment claim - Make a charity tax repayment claim - GOV.UK"

  def pageHeader: String                = "Manage charity repayment claim"
  def serviceName: String               = "Make a charity tax repayment claim"
  def pageCaption: String               = "HMRC Charities reference:"
  def pageContent: String               =
    "Complete any Gift Aid, Other Income or Community Buildings (opens in new tab) schedule spreadsheets before making a new claim."
  // Current dashboard links
  def repaymentClaimLink: By            = By.linkText("Use the charities online service")
  def repaymentSoftwareLink: By         = By.linkText("Use HMRC-recognised software (opens in new tab)")
  def repaymentClaimURL: String         = s"$charityClaimsHost/make-a-charity-repayment-claim?claimId=blank"
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
    validateRepaymentLinks()
  }

  def validateRepaymentLinks(): Unit = {
    val claimLink    = waitForVisibilityOfElement(repaymentClaimLink)
    val softwareLink = waitForVisibilityOfElement(repaymentSoftwareLink)

    assert(
      claimLink.getAttribute("href") == repaymentClaimURL,
      s"Unexpected repayment claim URL: ${claimLink.getAttribute("href")}"
    )

    assert(
      softwareLink.getAttribute("href") == repaymentClaimSoftwareURL,
      s"Unexpected software URL: ${softwareLink.getAttribute("href")}"
    )
  }

  def goBackToDashboardAndValidateURL(): Unit = {
    navigateToPage(charityManagementHost + "/" + pageUrl)
    verifyPageUrl(pageUrl)
  }

  // Keep existing method names so CharityRepaymentDashboardSpec still works
  def clickFirstCardAndValidateURL(): Unit = {
    click(repaymentClaimLink)
    verifyPageUrl(repaymentClaimURL)
  }

  def clickSecondCardAndValidateURL(): Unit = {
    click(repaymentSoftwareLink)
    switchTab(1)
    verifyPageUrl(repaymentClaimSoftwareURL)
  }
}
