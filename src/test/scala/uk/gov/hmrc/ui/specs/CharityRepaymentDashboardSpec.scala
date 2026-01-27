package uk.gov.hmrc.ui.specs

import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, GivenWhenThen}
import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.verbs.ShouldVerb
import uk.gov.hmrc.selenium.webdriver.{Browser, ScreenshotOnFailure}
import uk.gov.hmrc.ui.pages.CharityRepaymentDashboardPage

class CharityRepaymentDashboardSpec
    extends AnyFeatureSpec
    with BaseSpec
    with GivenWhenThen
    with ShouldVerb
    with BeforeAndAfterAll
    with BeforeAndAfterEach
    with Browser
    with ScreenshotOnFailure {
  Feature("Charities - Charity Repayment Dashboard") {
    Scenario("User navigates to the 'Charity Repayment Dashboard'") {
      Given("The user logs in through the Authority Wizard Page")
      // TODO: Get bearer token
      Then("User navigates to the 'Charity Repayment Dashboard'")
      CharityRepaymentDashboardPage.validatePageContent()
    }
  }
}
