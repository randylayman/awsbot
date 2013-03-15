package awsbot.commands

import awsbot.Command
import awsbot.CommandFactory
import com.ep.hippyjava.model.Room
import awsbot.AWSBot
import awsbot.MatchStrength

object InvalidCommand extends CommandFactory {

  override def accept(input: String): Command = {
    // Potentially anything is invalid, accepting it doesn't mean it will be applied
    // it just means it might be applied
    new InvalidCommand
  }
  
  override def helpText: String = null
}

class InvalidCommand extends Command {

  override def matchStrength() = { MatchStrength.UNLIKELY_MATCH }
  override def execute(user: String, room: Room, bot: AWSBot): Unit = {
    bot.sendMessage(user + " - I don't understand.  Please try help for a list of commands")
  }
}