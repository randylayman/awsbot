package awsbot.commands

import awsbot.CommandFactory
import awsbot.Command
import awsbot.MatchStrength
import com.ep.hippyjava.model.Room
import awsbot.AWSBot
import awsbot.AmazonSecurityRuleStorage
import awsbot.AmazonSecurityRules

object AddSecurityRuleCommand extends CommandFactory {
  override def accept(input: String): AddSecurityRuleCommand = {
    val pattern           = """.*\s*allow address (\d+)\.(\d+).(\d+).(\d+)\s*""".r 
    val patternAdditional = """.*\s*allow additional address (\d+)\.(\d+).(\d+).(\d+)\s*""".r 
    input match {
      case pattern(w,x,y,z) => new AddSecurityRuleCommand(w + "." + x + "." + y + "." + z, false)
      case patternAdditional(w,x,y,z) => new AddSecurityRuleCommand(w + "." + x + "." + y + "." + z, true)
      case _ => null
    }
  }
  
  override def helpText = {
    "allow address w.x.y.z - Add w.x.y.z to HDAP_Voice security group and remove all assigned addresses"
  }
}

class AddSecurityRuleCommand(val address: String, val isAdditional: Boolean) extends Command {

  override def matchStrength() = { MatchStrength.MATCH }
  override def execute(user: String, room: Room, bot: AWSBot) = {
    val oldAddresses = AmazonSecurityRules.addAddressForUser(user, address)
    bot.sendMessage("Added " + address + " for " + user + " and removed " + oldAddresses.mkString(", "))
  }
}