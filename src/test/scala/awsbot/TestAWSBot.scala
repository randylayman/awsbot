package awsbot

import com.ep.hippyjava.model.Room
import scala.collection.mutable.ListBuffer

class TestAWSBot extends AWSBot(null) {

  var messages = new ListBuffer[String]
  
  override def sendMessage(message: String) = {
    messages += message
  }
  
  override def sendMessage(message: String, room: Room) = {
    messages += message
  }
}