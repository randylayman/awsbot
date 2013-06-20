package awsbot.commands

import awsbot._
import com.ep.hippyjava.model.Room

object ReportCommand extends CommandFactory {
  
  override def accept(input: String): Command = {
    if (input.indexOf(" report ") >= 0 || input.endsWith(" report")) {
      new ReportCommand
    } else {
      null
    }
  }
  
  override def helpText: String = {
    "report - show all registered IP addresses and their owners"
  }
}

class ReportCommand extends Command {

  override def matchStrength() = {
    MatchStrength.LIKELY_MATCH
  }
  
  override def execute(user: String, room: Room, bot: AWSBot): Unit = {
    bot.sendMessage(AmazonSecurityRuleStorage.addresses.map(tuple => tuple._1 -> tuple._2.mkString(", ")).mkString("\n"))
  }

  private def formatListOfStrings(strs: Seq[String]): String = {
    // mkString is converting "123","345" -> "1", "2", "3", "3", "4", "5" instead of 123, 345
    strs.foldLeft("")((b,s) => b + "," + s)
  }
}