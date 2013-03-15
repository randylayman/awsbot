package awsbot.commands

import org.hamcrest.core.Is.is
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Test
import awsbot.TestAWSBot

class HelpCommandTestCase {

  @Test
  def containsHelpReturnsCommand():Unit = {
    assertTrue(HelpCommand.accept("@AWSBot please help me").isInstanceOf[HelpCommand])
  }
  
  @Test
  def endsWithHelpReturnsCommand() = {
    assertTrue(HelpCommand.accept("@AWSBot help").isInstanceOf[HelpCommand])
  }
  
  @Test
  def sendHelpText() = {
    val bot = new TestAWSBot
    val cmd = new HelpCommand
    cmd.execute("test user", null, bot)
    assertTrue(bot.messages.head.length > 0)
  }
}