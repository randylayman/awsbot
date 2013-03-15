package awsbot.commands

import awsbot.CommandFactory
import awsbot.MatchStrength
import awsbot.Command
import com.ep.hippyjava.model.Room
import awsbot.AWSBot
import com.amazonaws.services.dynamodb.AmazonDynamoDBClient
import com.amazonaws.auth.PropertiesCredentials
import java.io.FileInputStream
import awsbot.AmazonSecurityRuleStorage
import awsbot.AmazonSecurityRules

object ListSecurityRuleCommand extends CommandFactory {
  
  override def accept(input: String) = {
    if (input.indexOf(" list addresses") >= 0) {
      new ListSecurityRuleCommand()
    } else {
      null
    }
  }
  
  override def helpText: String = {
    "list addresses -- to see list of addresses assigned to you"
  }
}

class ListSecurityRuleCommand() extends Command{

  override def matchStrength = { MatchStrength.MATCH }
  override def execute(user: String, room: Room, bot: AWSBot) = {
    bot.sendMessage("Allowed address for " + user + " is " + AmazonSecurityRules.getAddressesForUser(user).mkString(", "))
    
  }
}