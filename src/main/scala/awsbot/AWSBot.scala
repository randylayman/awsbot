package awsbot

import com.ep.hippyjava.bot.HippyBot
import com.ep.hippyjava.model.Room
import com.ep.hippyjava.HippyJava
import java.util.Properties
import java.io.FileInputStream

object AWSBot extends App {

  Console.println("Initalizing AWS connection from configuration " + args(0))
  AmazonSecurityRuleStorage.initialize(args(0))
  Console.println("Starting AWSBot")
  HippyJava.runBot(new AWSBot(args(0)))
  Console.println("After AWSBot")
}

class AWSBot(configFile: String) extends HippyBot {

  val props = new Properties()
  if (configFile != null) {
	  props.load(new FileInputStream(configFile))
  }
  
  override def username(): String = {
    props.getProperty("hippychat_username")
  }

  override def password(): String = {
    props.getProperty("hippychat_password")
  }
  
  override def nickname(): String = {
    props.getProperty("hippychat_nickname")
  }
  
  override def onLoad(): Unit = {
    super.joinRoom(props.getProperty("hippychat_room"))
    Console.println("Joined room")
  }
  
  def isForMe(message: String): Boolean = {
    message.indexOf("@" + nickname.replaceAll(" ", "")) == 0
  }
  
  override def recieveMessage(message: String, from: String, room: Room): Unit = {
    if (isForMe(message)) {
      Console.println("Got message from " + from + ": '" + message + "'")
      val cmd = BotCommandParser.parse(message)
      cmd.execute(from, room, this)
    }
  }
  
  override def apiKey(): String = {
    ""
  }
}