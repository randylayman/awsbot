package awsbot.commands

import org.hamcrest.core.Is.is;
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Test


class AddSecurityRuleCommandTestCase {

  @Test
  def parseSpecificCommand() = {
    val cmd = AddSecurityRuleCommand.accept("@AWSBot allow address 1.2.3.4")
    assertThat(cmd.address, is("1.2.3.4"))
  }

  @Test
  def parseMultidigitIPAddress() = {
    assertTrue(AddSecurityRuleCommand.accept("@AWSBot allow address 1.12.113.4").isInstanceOf[AddSecurityRuleCommand])
  }
  
  @Test
  def parseAdditionalSecurityRuleCommand() = {
    val cmd = AddSecurityRuleCommand.accept("@AWSBot allow additional address 1.2.3.4")
    assertTrue(cmd.isAdditional)
  }
}