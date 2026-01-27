package uk.gov.hmrc.ui.pages

object CharityRepaymentDashboardPage extends BasePage {

  override def pageUrl: String   = "charity-repayment-dashboard"
  override def pageTitle: String = "Charity repayment dashboard - Charities - GOV.UK"

  def pageHeader: String  = "Charity repayment dashboard"
  def pageCaption: String = "HMRC Charities reference:"
  def pageContent: String = "Before you make a claim, complete and save any schedule spreadsheets."

  /** TODO: Card Component */

  def validatePageContent(): Unit = {
    verifyPageUrl(pageUrl)
    verifyPageTitle(pageTitle)
    verifyPageHeader(pageHeader)
    verifyPageCaption(pageCaption)
    verifyParagraphText(pageContent)
  }
}
