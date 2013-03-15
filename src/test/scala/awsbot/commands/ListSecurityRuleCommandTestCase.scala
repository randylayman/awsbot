package awsbot.commands

import org.junit.Assert.assertTrue
import org.junit.Test

class ListSecurityRuleCommandTestCase {

  @Test
  def parseCommand() = {
    assertTrue(ListSecurityRuleCommand.accept("@ASWBot list addresses").isInstanceOf[ListSecurityRuleCommand])
  }
}