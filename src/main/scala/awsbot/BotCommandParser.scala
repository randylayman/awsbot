package awsbot

import awsbot.commands.HelpCommand
import awsbot.commands.InvalidCommand
import awsbot.commands.AddSecurityRuleCommand
import awsbot.commands.ListSecurityRuleCommand

object MatchStrength extends Enumeration {
  val MATCH, LIKELY_MATCH, POSSIBLE_MATCH, UNLIKELY_MATCH = Value
}

object BotCommandParser {

  val validCommands = List[CommandFactory](
        AddSecurityRuleCommand,
        ListSecurityRuleCommand,
        HelpCommand,
        InvalidCommand
      )
  
  def parse(input: String): Command = {
    parse(input, validCommands)
  }
  
  def parse(input:String, commandFactories: List[CommandFactory]): Command = {
    commandFactories.map(fac => fac.accept(input)).filter(cmd => cmd != null).sortWith(_.matchStrength.id < _.matchStrength.id).headOption.getOrElse(new InvalidCommand)
  }
}