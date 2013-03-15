package awsbot.commands

import awsbot.Command
import awsbot.CommandFactory
import com.ep.hippyjava.model.Room
import awsbot.AWSBot
import awsbot.MatchStrength
import awsbot.BotCommandParser

object HelpCommand extends CommandFactory {

  override def accept(input: String): Command = {
    if (input.indexOf(" help ") >= 0 || input.endsWith(" help")) {
      new HelpCommand
    } else {
      null
    }
  }
  
  override def helpText: String = {
    "help - for a list of commands"
  }
}
class HelpCommand extends Command {

  override def matchStrength() = {
    MatchStrength.LIKELY_MATCH
  }
  override def execute(user: String, room: Room, bot: AWSBot): Unit = {
    bot.sendMessage(BotCommandParser.validCommands.map(cf => cf.helpText).filter(text => text != null).mkString("\n"))
  }  

}