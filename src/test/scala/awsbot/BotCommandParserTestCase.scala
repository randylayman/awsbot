package awsbot

import org.hamcrest.core.Is.is
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

import com.ep.hippyjava.model.Room
import awsbot.commands.InvalidCommand


class BotCommandParserSpec {

  object TestCommandFactory extends CommandFactory {
    override def accept(input: String): Command = {
      assertThat(input, is("test input"))
      return new TestCommand(MatchStrength.MATCH)
    }
    override def helpText = "" 
  }
  
  object TestCommandFactory2 extends CommandFactory {
    override def accept(input: String): Command = {
      return new TestCommand(MatchStrength.POSSIBLE_MATCH)
    }
    override def helpText = "" 
  }
  
  class TestCommand(m: MatchStrength.Value) extends Command {
    
    override def matchStrength() = { m }
    override def execute(list: String, room: Room, bot: AWSBot) = { }
  }
  
  @Test
  def passInputToCommandFactory() = {
    val factories = List(TestCommandFactory)
    val result = BotCommandParser.parse("test input", factories)
    assertTrue(result.isInstanceOf[TestCommand])
  }
  
  @Test
  def returnInvalidCommandIfNoneAccept() = {
    val result = BotCommandParser.parse("test input", List())
    assertTrue(result.isInstanceOf[InvalidCommand])
  }
  
  @Test
  def multipleAcceptsDeterminedByBestMatch() = {
    val result = BotCommandParser.parse("test input", List(TestCommandFactory2, TestCommandFactory))
    assertThat(result.matchStrength, is(MatchStrength.MATCH))
  }
  
  @Test
  def testParseJibberishIsInvalid() = {
    assertTrue(BotCommandParser.parse("@AWSBot jibberish").isInstanceOf[InvalidCommand])
  }
}