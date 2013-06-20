package awsbot.commands

import awsbot.Command
import awsbot.CommandFactory
import awsbot.MatchStrength
import com.ep.hippyjava.model.Room
import awsbot.AWSBot
import awsbot.AmazonSecurityRules
import awsbot.AmazonSecurityRuleStorage

object AddressOwnerCommand extends CommandFactory {

  override def accept(input: String): Command = {
    val pattern           = """.*\s*who owns (\d+)\.(\d+).(\d+).(\d+)\s*""".r 
    input match {
      case pattern(w,x,y,z) => new AddressOwner(w + "." + x + "." + y + "." + z)
      case _ => null
    }
  }
  
  override def helpText = {
    "who owns w.x.y.z - Report which user(s) added the address"
  }

}

class AddressOwner(val address: String) extends Command {
  override def matchStrength() = { MatchStrength.MATCH }
  override def execute(user: String, room: Room, bot: AWSBot) = {
    bot.sendMessage(address + " is owned by " + AmazonSecurityRuleStorage.userForAddress(address).mkString(","))
  }
}
