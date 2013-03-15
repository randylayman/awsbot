package awsbot.commands

import org.hamcrest.core.Is.is
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Test
import awsbot.MatchStrength
import awsbot.TestAWSBot

class InvalidCommandTestCase {

  @Test
  def acceptsAllCommands() = {
    assertTrue(InvalidCommand.accept("jibberish").isInstanceOf[InvalidCommand])
  }
  
  @Test
  def commandHasLowMatchStrength() = {
    assertThat(InvalidCommand.accept("whatever").matchStrength, is(MatchStrength.UNLIKELY_MATCH))
  }
  
  @Test
  def executeTellsUserTheyWerentUnderstood() = {
    val bot = new TestAWSBot
    val cmd = new InvalidCommand
    cmd.execute("test user", null, bot)
    assertThat(bot.messages.head, is("test user - I don't understand.  Please try help for a list of commands"))
  }
}